/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.pdfview;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.pdfview.PDFDebugger.DebugStopException;
import org.loboevolution.pdfview.colorspace.PDFColorSpace;
import org.loboevolution.pdfview.colorspace.PatternSpace;
import org.loboevolution.pdfview.decode.PDFDecoder;
import org.loboevolution.pdfview.font.PDFFont;
import org.loboevolution.pdfview.pattern.PDFShader;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.*;

import static java.awt.geom.Path2D.WIND_EVEN_ODD;

/**
 * PDFParser is the class that parses a PDF content stream and
 * produces PDFCmds for a PDFPage. You should never ever see it run:
 * it gets created by a PDFPage only if needed, and may even run in
 * its own thread.
 * <p>
 * Author Mike Wessler
 */
@Slf4j
public class PDFParser extends BaseWatchable {
    // ---- result variables
    final byte[] stream;
    /**
     * a weak reference to the page we render into. For the page
     * to remain available, some other code must retain a strong reference to
     * it.
     */
    private final WeakReference<PDFPage> pageRef;
    Map<String, PDFObject> resources;
    boolean errorwritten = false;
    private int mDebugCommandIndex;
    // ---- parsing variables
    private Deque<Object> stack; // stack of Object
    private Deque<ParserState> parserStates; // stack of RenderState
    // the current render state
    private ParserState state;
    private GeneralPath path;
    private int clip;
    private int loc;
    private boolean resend = false;
    private Tok tok;
    private boolean catchexceptions = true; // Indicates state of BX...EX
    /**
     * the actual command, for use within a singe iteration. Note that
     * this must be released at the end of each iteration to assure the
     * page can be collected if not in use
     */
    private PDFPage cmds;
    private boolean autoAdjustStroke = false;
    private boolean strokeOverprint;
    private int strokeOverprintMode;
    private boolean fillOverprint;
    private int fillOverprintMode;
    private boolean addAnnotation;

    /**
     * Don't call this constructor directly. Instead, use
     * PDFFile.getPage(int pagenum) to get a PDFPage. There should
     * never be any reason for a user to create, access, or hold
     * on to a PDFParser.
     *
     * @param cmds      a {@link org.loboevolution.pdfview.PDFPage} object.
     * @param stream    an array of {@link byte} objects.
     * @param resources a {@link java.util.Map} object.
     */
    public PDFParser(final PDFPage cmds, final byte[] stream, final Map<String, PDFObject> resources) {
        super();
        this.pageRef = new WeakReference<>(cmds);
        this.resources = resources;
        if (resources == null) {
            this.resources = new HashMap<>();
        }
        this.stream = stream;
    }

    // ///////////////////////////////////////////////////////////////
    // B E G I N R E A D E R S E C T I O N
    // ///////////////////////////////////////////////////////////////

    /**
     * get the next token.
     */
    private Tok nextToken() {
        if (this.resend) {
            this.resend = false;
            return this.tok;
        }
        if (this.tok != null) {
            this.tok.reset();
        } else {
            tok = new Tok();
        }
        // skip whitespace
        while (this.loc < this.stream.length && PDFFile.isWhiteSpace(this.stream[this.loc])) {
            this.loc++;
        }
        if (this.loc >= this.stream.length) {
            this.tok.type = Tok.EOF;
            return this.tok;
        }
        int c = this.stream[this.loc++];
        // examine the character:
        while (c == '%' || c == 28) {
            // skip comments
            final StringBuilder comment = new StringBuilder();
            while (this.loc < this.stream.length && c != '\n') {
                comment.append((char) c);
                c = this.stream[this.loc++];
            }
            if (this.loc < this.stream.length) {
                c = this.stream[this.loc++]; // eat the newline
                if (c == '\r') {
                    c = this.stream[this.loc++]; // eat a following return
                }

                while (this.loc < this.stream.length && PDFFile.isWhiteSpace(c)) {
                    c = this.stream[this.loc++];
                }
            }
            PDFDebugger.debug("Read comment: " + comment, -1);
        }
        if (c == '[') {
            this.tok.type = Tok.ARYB;
        } else if (c == ']') {
            this.tok.type = Tok.ARYE;
        } else if (c == '(') {
            // read a string
            this.tok.type = Tok.STR;
            this.tok.name = readString();
        } else if (c == '{') {
            this.tok.type = Tok.BRCB;
        } else if (c == '}') {
            this.tok.type = Tok.BRCE;
        } else if (c == '<' && this.stream[this.loc++] == '<') {
            this.tok.type = Tok.BRKB;
        } else if (c == '>' && this.stream[this.loc++] == '>') {
            this.tok.type = Tok.BRKE;
        } else if (c == '<') {
            this.loc--;
            this.tok.type = Tok.STR;
            this.tok.name = readByteArray();
        } else if (c == '/') {
            this.tok.type = Tok.NAME;
            this.tok.name = readName();
        } else if (c == '.' || c == '-' || (c >= '0' && c <= '9')) {
            this.loc--;
            this.tok.type = Tok.NUM;
            this.tok.value = readNum();
        } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '\'' || c == '"') {
            this.loc--;
            this.tok.type = Tok.CMD;
            this.tok.name = readName();
        } else {
            PDFDebugger.debug("Encountered character: " + c + " (" + (char) c + ")", 1);
            this.tok.type = Tok.UNK;
        }
        return this.tok;
    }

    /**
     * read a name (sequence of non-PDF-delimiting characters) from the
     * stream.
     */
    private String readName() {
        final int start = this.loc;
        while (this.loc < this.stream.length && PDFFile.isRegularCharacter(this.stream[this.loc])) {
            this.loc++;
        }
        return new String(this.stream, start, this.loc - start);
    }

    /**
     * read a floating point number from the stream
     */
    private double readNum() {
        int c = this.stream[this.loc++];
        final boolean neg = c == '-';
        boolean sawdot = c == '.';
        double dotmult = sawdot ? 0.1 : 1;
        double value = (c >= '0' && c <= '9') ? c - '0' : 0;
        while (true) {
            c = this.stream[this.loc++];
            if (c == '.') {
                if (sawdot) {
                    this.loc--;
                    break;
                }
                sawdot = true;
                dotmult = 0.1;
            } else if (c >= '0' && c <= '9') {
                final int val = c - '0';
                if (sawdot) {
                    value += val * dotmult;
                    dotmult *= 0.1;
                } else {
                    value = value * 10 + val;
                }
            } else {
                this.loc--;
                break;
            }
        }
        if (neg) {
            value = -value;
        }
        return value;
    }

    /**
     * <p>
     * read a String from the stream. Strings begin with a '(' character, which has already been
     * read, and end with a balanced ')' character. A '\' character starts an escape sequence of up
     * to three octal digits.
     * </p>
     *
     * <p>
     * Parenthesis must be enclosed by a balanced set of parenthesis, so a string may enclose
     * balanced parenthesis.
     * </p>
     *
     * @return the string with escape sequences replaced with their
     * values
     */
    private String readString() {
        int parenLevel = 0;
        final StringBuilder sb = new StringBuilder();
        while (this.loc < this.stream.length) {
            int c = this.stream[this.loc++];
            if (c == ')') {
                if (parenLevel-- == 0) {
                    break;
                }
            } else if (c == '(') {
                parenLevel++;
            } else if (c == '\\') {
                // escape sequences
                c = this.stream[this.loc++];
                if (c >= '0' && c < '8') {
                    int count = 0;
                    int val = 0;
                    while (c >= '0' && c < '8' && count < 3) {
                        val = val * 8 + c - '0';
                        c = this.stream[this.loc++];
                        count++;
                    }
                    this.loc--;
                    c = val;
                } else if (c == 'n') {
                    c = '\n';
                } else if (c == 'r') {
                    c = '\r';
                } else if (c == 't') {
                    c = '\t';
                } else if (c == 'b') {
                    c = '\b';
                } else if (c == 'f') {
                    c = '\f';
                } else if (c == '\n' || c == '\r') {
                    continue;
                }
            }
            sb.append((char) c);
        }
        return sb.toString();
    }

    /**
     * read a byte array from the stream. Byte arrays begin with a '&#60;'
     * character, which has already been read, and end with a '	&#62;'
     * character. Each byte in the array is made up of two hex characters,
     * the first being the high-order bit.
     * <p>
     * We translate the byte arrays into char arrays by combining two bytes
     * into a character, and then translate the character array into a string.
     * [JK FIXME this is probably a really bad idea!]
     *
     * @return the byte array
     */
    private String readByteArray() {
        final StringBuilder buf = new StringBuilder();
        int count = 0;
        char w = (char) 0;
        // read individual bytes and format into a character array
        while ((this.loc < this.stream.length) && (this.stream[this.loc] != '>')) {
            final char c = (char) this.stream[this.loc];
            byte b;
            if (c >= '0' && c <= '9') {
                b = (byte) (c - '0');
            } else if (c >= 'a' && c <= 'f') {
                b = (byte) (10 + (c - 'a'));
            } else if (c >= 'A' && c <= 'F') {
                b = (byte) (10 + (c - 'A'));
            } else {
                this.loc++;
                continue;
            }
            // calculate where in the current byte this character goes
            final int offset = 1 - (count % 2);
            w |= (char) ((0xf & b) << (offset * 4));
            // increment to the next char if we've written four bytes
            if (offset == 0) {
                buf.append(w);
                w = (char) 0;
            }
            count++;
            this.loc++;
        }
        // ignore trailing '>'
        this.loc++;
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Called to prepare for some iterations
     */
    @Override
    public void setup() {
        this.stack = new ArrayDeque<>();
        this.parserStates = new ArrayDeque<>();
        this.state = new ParserState();
        this.path = new GeneralPath();
        this.loc = 0;
        this.clip = 0;
        // initialize the ParserState
        this.state.fillCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY);
        this.state.strokeCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY);
        this.state.textFormat = new PDFTextFormat();
    }

    // ///////////////////////////////////////////////////////////////
    // B E G I N P A R S E R S E C T I O N
    // ///////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     * <p>
     * parse the stream. commands are added to the PDFPage initialized
     * in the constructor as they are encountered.
     * <p>
     * Page numbers in comments refer to the Adobe PDF specification.<br>
     * commands are listed in PDF spec 32000-1:2008 in Table A.1
     */
    @SuppressWarnings("unused")
    @Override
    public int iterate() throws Exception {
        // make sure the page is still available, and create the reference
        // to it for use within this iteration
        this.cmds = this.pageRef.get();
        if (this.cmds == null) {
            PDFDebugger.debug("Page gone. Stopping", 10);
            return Watchable.STOPPED;
        }
        final Object obj;
        try {
            obj = parseObject();
        } catch (final DebugStopException e) {
            return Watchable.STOPPED;
        }
        // if there's nothing left to parse, we're done
        if (obj == null) {
            return Watchable.COMPLETED;
        }
        if (obj instanceof Tok) {
            // it's a command. figure out what to do.
            // (if not, the token will be "pushed" onto the stack)
            final String cmd = ((Tok) obj).name;
            PDFDebugger.debug("Command: " + cmd + " (stack size is " + this.stack.size() + ")", 10);
            switch (cmd) {
                case "q":
                    // push the parser state
                    this.parserStates.push((ParserState) this.state.clone());
                    // push graphics state
                    this.cmds.addPush();
                    break;
                case "Q":
                    processQCmd();
                    break;
                case "cm":
                    // set transform to array of values
                    final float[] elts = popFloat(6);
                    final AffineTransform xform = new AffineTransform(elts);
                    this.cmds.addXform(xform);
                    break;
                case "w":
                    // set stroke width
                    this.cmds.addStrokeWidth(popFloat());
                    break;
                case "J":
                    // set end cap style
                    this.cmds.addEndCap(popInt());
                    break;
                case "j":
                    // set line join style
                    this.cmds.addLineJoin(popInt());
                    break;
                case "M":
                    // set miter limit
                    this.cmds.addMiterLimit(popInt());
                    break;
                case "d":
                    // set dash style and phase
                    final float phase = popFloat();
                    final float[] dashary = popFloatArray();
                    if (!PDFDebugger.DISABLE_PATH_STROKE) {
                        this.cmds.addDash(dashary, phase);
                    }
                    break;
                case "ri":
                case "BMC":
                case "MP":
                    // mark point (role= mark role name)
                    // begin marked content (role)
                    popString();
                    // TODO: do something with rendering intent (page 197)
                    break;
                case "i":
                    popFloat();
                    // TODO: do something with flatness tolerance
                    break;
                case "gs":
                    // set graphics state to values in a named dictionary
                    final String popString = popString();
                    PDFDebugger.debug("Set GS state " + popString, 10);
                    setGSState(popString);
                    break;
                case "m": {
                    // path move to
                    final float y = popFloat();
                    final float x = popFloat();
                    this.path.moveTo(x, y);
                    PDFDebugger.logPath(path, "2 moved to " + x + ", " + y);
                    break;
                }
                case "l": {
                    // path line to
                    final float y = popFloat();
                    final float x = popFloat();
                    this.path.lineTo(x, y);
                    PDFDebugger.logPath(path, "1 line to " + x + ", " + y);
                    break;
                }
                case "c": {
                    // path curve to
                    final float[] a = popFloat(6);
                    this.path.curveTo(a[0], a[1], a[2], a[3], a[4], a[5]);
                    PDFDebugger.logPath(path, "1 curve to " + Arrays.toString(a));
                    break;
                }
                case "v": {
                    // path curve; first control point= start
                    final float[] a = popFloat(4);
                    final Point2D cp = this.path.getCurrentPoint();
                    this.path.curveTo((float) cp.getX(), (float) cp.getY(), a[0], a[1], a[2], a[3]);
                    PDFDebugger.logPath(path, "2 curve to " + Arrays.toString(a) + ", " + cp.getX() + "," + cp.getY());
                    break;
                }
                case "y": {
                    // path curve; last control point= end
                    final float[] a = popFloat(4);
                    this.path.curveTo(a[0], a[1], a[2], a[3], a[2], a[3]);
                    PDFDebugger.logPath(path, "3 curve to " + Arrays.toString(a));
                    break;
                }
                case "h":
                    tryClosingPath();
                    PDFDebugger.logPath(path, "closed");
                    break;
                case "re": {
                    // path add rectangle
                    final float[] a = popFloat(4);
                    this.path.moveTo(a[0], a[1]);
                    PDFDebugger.logPath(path, "1 moved to " + a[0] + "," + a[1]);
                    this.path.lineTo(a[0] + a[2], a[1]);
                    PDFDebugger.logPath(path, "2 line to " + (a[0] + a[2]) + "," + a[1]);
                    this.path.lineTo(a[0] + a[2], a[1] + a[3]);
                    PDFDebugger.logPath(path, "3 line to " + (a[0] + a[2]) + "," + (a[1] + a[3]));
                    this.path.lineTo(a[0], a[1] + a[3]);
                    PDFDebugger.logPath(path, "4 line to " + a[0] + "," + (a[1] + a[3]));
                    tryClosingPath();
                    PDFDebugger.logPath(path, "closed");
                    break;
                }
                case "S":
                    // stroke the path
                    if (!PDFDebugger.DISABLE_PATH_STROKE || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        if (autoAdjustStroke || strokeOverprint || fillOverprint) {
                            path.closePath();
                            PDFDebugger.logPath(path, "closed");
                        }
                        this.cmds.addPath(this.path, PDFShapeCmd.STROKE | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "s":
                    tryClosingPath();
                    PDFDebugger.logPath(path, "closed");
                    if (!PDFDebugger.DISABLE_PATH_STROKE || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.STROKE | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "f":
                case "F":
                    tryClosingPath();
                    // fill the path (close/not close identical)
                    if (!PDFDebugger.DISABLE_PATH_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.FILL | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "f*":
                    // fill the path using even/odd rule
                    this.path.setWindingRule(WIND_EVEN_ODD);
                    PDFDebugger.logPath(path, "set winding rule" + WIND_EVEN_ODD);
                    if (!PDFDebugger.DISABLE_PATH_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.FILL | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "B":
                    // fill and stroke the path
                    if (!PDFDebugger.DISABLE_PATH_STROKE_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.BOTH | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "B*":
                    // fill path using even/odd rule and stroke it
                    this.path.setWindingRule(WIND_EVEN_ODD);
                    PDFDebugger.logPath(path, "set winding rule" + WIND_EVEN_ODD);
                    if (!PDFDebugger.DISABLE_PATH_STROKE_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.BOTH | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "b":
                    tryClosingPath();
                    PDFDebugger.logPath(path, "close");
                    if (!PDFDebugger.DISABLE_PATH_STROKE_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.BOTH | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "b*":
                    tryClosingPath();
                    PDFDebugger.logPath(path, "close");
                    this.path.setWindingRule(WIND_EVEN_ODD);
                    PDFDebugger.logPath(path, "set winding rule " + WIND_EVEN_ODD);
                    if (!PDFDebugger.DISABLE_PATH_STROKE_FILL || (!PDFDebugger.DISABLE_CLIP && this.clip == PDFShapeCmd.CLIP)) {
                        this.cmds.addPath(this.path, PDFShapeCmd.BOTH | this.clip, this.autoAdjustStroke);
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "n":
                    if (path.getCurrentPoint() != null) {
                        tryClosingPath();
                        PDFDebugger.logPath(path, "closed");
                    }
                    // clip with the path and discard it
                    if (!PDFDebugger.DISABLE_CLIP) {
                        if (this.clip != 0) {
                            this.cmds.addPath(this.path, this.clip, this.autoAdjustStroke);
                        }
                    }
                    this.clip = 0;
                    this.path = new GeneralPath();
                    PDFDebugger.logPath(path, "new path");
                    break;
                case "W":
                    // mark this path for clipping!
                    this.clip = PDFShapeCmd.CLIP;
                    break;
                case "W*":
                    // mark this path using even/odd rule for clipping
                    this.path.setWindingRule(WIND_EVEN_ODD);
                    PDFDebugger.logPath(path, "set winding rule " + WIND_EVEN_ODD);
                    this.clip = PDFShapeCmd.CLIP;
                    break;
                case "sh":
                    // shade a region that is defined by the shader itself.
                    // shading the current space from a dictionary
                    // should only be used for limited-dimension shadings
                    final String gdictname = popString();
                    // set up the pen to do a gradient fill according
                    // to the dictionary
                    final PDFObject shobj = findResource(gdictname, "Shading");
                    if (!PDFDebugger.DISABLE_SHADER) {
                        doShader(shobj);
                    }
                    break;
                case "CS":
                    // set the stroke color space
                    this.state.strokeCS = parseColorSpace(new PDFObject(this.stack.pop()));
                    break;
                case "cs":
                    // set the fill color space
                    this.state.fillCS = parseColorSpace(new PDFObject(this.stack.pop()));
                    break;
                case "SC": {
                    // set the stroke color
                    final int n = this.state.strokeCS.getNumComponents();
                    this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(n)));
                    break;
                }
                case "SCN":
                    // set the stroke colour
                    if (this.state.strokeCS instanceof PatternSpace) {
                        this.cmds.addFillPaint(doPattern((PatternSpace) this.state.strokeCS));
                    } else {
                        final int n = this.state.strokeCS.getNumComponents();
                        this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(n)));
                    }
                    break;
                case "sc": {
                    // set the fill color
                    final int n = this.state.fillCS.getNumComponents();
                    this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(n)));
                    break;
                }
                case "scn":
                    if (this.state.fillCS instanceof PatternSpace) {
                        this.cmds.addFillPaint(doPattern((PatternSpace) this.state.fillCS));
                    } else {
                        final int n = this.state.fillCS.getNumComponents();
                        this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(n)));
                    }
                    break;
                case "G":
                    // set the stroke color to a Gray value
                    this.state.strokeCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY);
                    this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(1)));
                    break;
                case "g":
                    // set the fill color to a Gray value
                    this.state.fillCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_GRAY);
                    this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(1)));
                    break;
                case "RG":
                    // set the stroke color to an RGB value
                    this.state.strokeCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_RGB);
                    this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(3)));
                    break;
                case "rg":
                    // set the fill color to an RGB value
                    this.state.fillCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_RGB);
                    this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(3)));
                    break;
                case "K":
//                if (strokeOverprint && strokeOverprintMode == 1) {
//                    if (this.state.strokeCS instanceof PatternSpace) {
//                        this.cmds.addFillPaint(doPattern((PatternSpace) this.state.strokeCS));
//                    } else {
//                        int n = this.state.strokeCS.getNumComponents();
//                        this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(n)));
//                    }
//                } else {
                    // set the stroke color to a CMYK value
                    this.state.strokeCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_CMYK);
                    this.cmds.addStrokePaint(this.state.strokeCS.getPaint(popFloat(4)));
//                }
                    break;
                case "k":
//                if (fillOverprint && fillOverprintMode == 1) {
//                    // if OP = true and OPM = 1 apply the same as in "scn"
//                    if (this.state.fillCS instanceof PatternSpace) {
//                        this.cmds.addFillPaint(doPattern((PatternSpace) this.state.fillCS));
//                    } else {
//                        // set the fill color to a CMYK value
//                        int n = this.state.fillCS.getNumComponents();
//                        this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(n)));
//                    }
//                } else {
                    this.state.fillCS = PDFColorSpace.getColorSpace(PDFColorSpace.COLORSPACE_CMYK);
                    this.cmds.addFillPaint(this.state.fillCS.getPaint(popFloat(4)));
//                }
                    break;
                case "Do":
                    // make a do call on the referenced object
                    final String name = popString();
                    if (PDFDebugger.DEBUG_IMAGES) {
                        PDFDebugger.debug("XObject reference to " + name);
                    }
                    final PDFObject xobj = findResource(name, "XObject");
                    doXObject(xobj);
                    break;
                case "BT":
                    processBTCmd();
                    break;
                case "ET":
                    // end of text. noop
                    this.state.textFormat.end();
                    break;
                case "Tc":
                    // set character spacing
                    this.state.textFormat.setCharSpacing(popFloat());
                    break;
                case "Tw":
                    // set word spacing
                    this.state.textFormat.setWordSpacing(popFloat());
                    break;
                case "Tz":
                    // set horizontal scaling
                    this.state.textFormat.setHorizontalScale(popFloat());
                    break;
                case "TL":
                    // set leading
                    this.state.textFormat.setLeading(popFloat());
                    break;
                case "Tf":
                    // set text font
                    final float sz = popFloat();
                    final String fontref = popString();
                    this.state.textFormat.setFont(getFontFrom(fontref), sz);
                    break;
                case "Tr":
                    // set text rendering mode
                    this.state.textFormat.setMode(popInt());
                    break;
                case "Ts":
                    // set text rise
                    this.state.textFormat.setRise(popFloat());
                    break;
                case "Td": {
                    // set text matrix location
                    final float y = popFloat();
                    final float x = popFloat();
                    this.state.textFormat.carriageReturn(x, y);
                    break;
                }
                case "TD": {
                    // set leading and matrix: -y TL x y Td
                    final float y = popFloat();
                    final float x = popFloat();
                    this.state.textFormat.setLeading(-y);
                    this.state.textFormat.carriageReturn(x, y);
                    break;
                }
                case "Tm":
                    // set text matrix
                    this.state.textFormat.setMatrix(popFloat(6));
                    break;
                case "T*":
                    // go to next line
                    this.state.textFormat.carriageReturn();
                    break;
                case "Tj":
                    // show text
                    this.state.textFormat.doText(this.cmds, popString(), this.autoAdjustStroke);
                    break;
                case "'":
                    // next line and show text: T* string Tj
                    this.state.textFormat.carriageReturn();
                    this.state.textFormat.doText(this.cmds, popString(), this.autoAdjustStroke);
                    break;
                case "\"":
                    // draw string on new line with char & word spacing:
                    // aw Tw ac Tc string '
                    final String string = popString();
                    final float ac = popFloat();
                    final float aw = popFloat();
                    this.state.textFormat.setWordSpacing(aw);
                    this.state.textFormat.setCharSpacing(ac);
                    this.state.textFormat.doText(this.cmds, string, this.autoAdjustStroke);
                    break;
                case "TJ":
                    // show kerned string
                    this.state.textFormat.doText(this.cmds, popArray(), this.autoAdjustStroke);
                    break;
                case "BI":
                    // parse inline image
                    parseInlineImage();
                    break;
                case "BX":
                    this.catchexceptions = true; // ignore errors

                    break;
                case "EX":
                    this.catchexceptions = false; // stop ignoring errors

                    break;
                case "DP":
                case "BDC":
                    // begin marked content with dict (role, ref)
                    // mark point with dictionary (role, ref)
                    // result is either inline dict or name in "Properties" rsrc
                    this.stack.pop();
                    popString();
                    break;
                case "EMC":
                    // end marked content
                    break;
                case "d0":
                    // character width in type3 fonts
                    popFloat(2);
                    break;
                case "d1":
                    // character width in type3 fonts
                    popFloat(6);
                    break;
                case "QBT": // 'Q' & 'BT' mushed together!
                    processQCmd();
                    processBTCmd();
                    break;
                case "Qq": // 'Q' & 'q' mushed together!
                    processQCmd();
                    // push the parser state
                    this.parserStates.push((ParserState) this.state.clone());
                    // push graphics state
                    this.cmds.addPush();
                    break;
                case "qBT": // 'q' & 'BT' mushed together!
                    // push the parser state
                    this.parserStates.push((ParserState) this.state.clone());
                    // push graphics state
                    this.cmds.addPush();
                    processBTCmd();
                    break;
                case "q1":
                case "q0":
                    PDFDebugger.debug("**** WARNING: Not handled command: " + cmd + " **************************", 10);
                    break;
                default:
                    if (this.catchexceptions) {
                        PDFDebugger.debug("**** WARNING: Unknown command: " + cmd + " **************************", 10);
                    } else {
                        throw new PDFParseException("Unknown command: " + cmd);
                    }
                    break;
            }
            if (!this.stack.isEmpty()) {
                PDFDebugger.debug("**** WARNING! Stack not zero! (cmd=" + cmd + ", size=" + this.stack.size() + ") *************************", 10);
                this.stack.clear();
            }
        } else {
            this.stack.push(obj);
        }
        // release or reference to the page object, so that it can be
        // gc'd if it is no longer in use
        this.cmds = null;
        return Watchable.RUNNING;
    }

    /**
     * Try to close a path but don't fail with exception if this is not working.
     * This is just a workaround for some PDFs with wrong content...
     */
    private void tryClosingPath() {
        try {
            this.path.closePath();
            PDFDebugger.logPath(path, "closed");
        } catch (final java.awt.geom.IllegalPathStateException e) {
            PDFDebugger.debug("Failed to close path", 1000);
        }
    }

    @SuppressWarnings("unused")
    private void onNextObject(final Tok obj) throws DebugStopException {
        final String progress;
        if (true) {
            final double percent = (100d * this.loc) / this.stream.length;
            final NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(1);
            nf.setMaximumFractionDigits(1);
            progress = nf.format(percent) + "%";
        } else {
            progress = this.loc + " of " + this.stream.length;
        }
        final StringBuilder operators = new StringBuilder();
        for (final Object operator : this.stack) {
            operators.append(operator).append(" ");
        }
        if (PDFDebugger.DEBUG_OPERATORS) {
            PDFDebugger.debug("parser{" + hashCode() + "} " + progress + ": #" + mDebugCommandIndex + " \t" + operators + obj.name);
        }
        mDebugCommandIndex++;
        if (PDFDebugger.DEBUG_STOP_AT_INDEX > 0 && mDebugCommandIndex > PDFDebugger.DEBUG_STOP_AT_INDEX) {
            log.error("Debugging: stopped at instruction #{}", mDebugCommandIndex);
            throw new DebugStopException();
        }
        if (PDFDebugger.DRAW_DELAY > 0) {
            try {
                Thread.sleep(PDFDebugger.DRAW_DELAY);
            } catch (final InterruptedException e) {
                log.info(e.getMessage());
            }
        }
    }

    /**
     * abstracted command processing for Q command. Used directly and as
     * part of processing of mushed QBT command.
     */
    private void processQCmd() {
        // pop graphics state ('Q')
        this.cmds.addPop();
        // pop the parser state
        if (!this.parserStates.isEmpty()) {
            this.state = this.parserStates.pop();
        }
    }

    /**
     * abstracted command processing for BT command. Used directly and as
     * part of processing of mushed QBT command.
     */
    private void processBTCmd() {
        // begin text block: reset everything.
        this.state.textFormat.reset();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Cleanup when iteration is done
     */
    @Override
    public void cleanup() {
        this.state.textFormat.flush();
        this.cmds.finish();
        this.stack = null;
        this.parserStates = null;
        this.state = null;
        this.path = null;
        this.cmds = null;
        this.tok = null;
    }

    /**
     * get a property from a named dictionary in the resources of this
     * content stream.
     *
     * @param name   the name of the property in the dictionary
     * @param inDict the name of the dictionary in the resources
     * @return the value of the property in the dictionary
     */
    private PDFObject findResource(final String name, final String inDict) throws IOException {
        if (inDict != null) {
            final PDFObject in = this.resources.get(inDict);
            if (in == null || in.getType() != PDFObject.DICTIONARY) {
                throw new PDFParseException("No dictionary called " + inDict + " found in the resources");
            }
            return in.getDictRef(name);
        } else {
            return this.resources.get(name);
        }
    }

    // ///////////////////////////////////////////////////////////////
    // H E L P E R S
    // ///////////////////////////////////////////////////////////////

    /**
     * Insert a PDF object into the command stream. The object must
     * either be an Image or a Form, which is a set of PDF commands
     * in a stream.
     *
     * @param obj the object to insert, an Image or a Form.
     */
    private void doXObject(final PDFObject obj) throws IOException {
        String type = obj.getDictRef("Subtype").getStringValue();
        if (type == null) {
            type = obj.getDictRef("S").getStringValue();
        }
        if (type.equals("Image")) {
            doImage(obj);
        } else if (type.equals("Form")) {
            doForm(obj);
        } else {
            throw new PDFParseException("Unknown XObject subtype: " + type);
        }
    }

    /**
     * Parse image data into a Java BufferedImage and add the image
     * command to the page.
     *
     * @param obj contains the image data, and a dictionary describing
     *            the width, height and color space of the image.
     */
    private void doImage(final PDFObject obj) throws IOException {
        if (!PDFDebugger.DISABLE_IMAGES) {
            if (PDFDebugger.DEBUG_IMAGES) {
                final boolean jpegDecode = PDFDecoder.isLastFilter(obj, PDFDecoder.DCT_FILTERS);
                if (jpegDecode) {
                    PDFDebugger.debug("Image is JPEG");
                } else {
                    PDFDebugger.debug("Image not JPEG");
                }
            }
            this.cmds.addImage(PDFImage.createImage(obj, this.resources, false));
        }
    }

    /**
     * Inject a stream of PDF commands onto the page. Optimized to cache
     * a parsed stream of commands, so that each Form object only needs
     * to be parsed once.
     *
     * @param obj a stream containing the PDF commands, a transformation
     *            matrix, bounding box, and resources.
     */
    private void doForm(final PDFObject obj) throws IOException {
        // check to see if we've already parsed this sucker
        PDFPage formCmds = (PDFPage) obj.getCache();
        if (formCmds == null) {
            // rats. parse it.
            final AffineTransform at;
            final Rectangle2D bbox;
            final PDFObject matrix = obj.getDictRef("Matrix");
            if (matrix == null) {
                at = new AffineTransform();
            } else {
                final float[] elts = new float[6];
                for (int i = 0; i < elts.length; i++) {
                    elts[i] = (matrix.getAt(i)).getFloatValue();
                }
                at = new AffineTransform(elts);
            }
            final PDFObject bobj = obj.getDictRef("BBox");
            bbox = new Rectangle2D.Float(bobj.getAt(0).getFloatValue(), bobj.getAt(1).getFloatValue(), bobj.getAt(2).getFloatValue(), bobj.getAt(3).getFloatValue());
            formCmds = new PDFPage(bbox, 0);
            formCmds.addXform(at);
            final Map<String, PDFObject> r = new HashMap<>(this.resources);
            final PDFObject rsrc = obj.getDictRef("Resources");
            if (rsrc != null) {
                r.putAll(rsrc.getDictionary());
            }
            final PDFParser form = new PDFParser(formCmds, obj.getStream(), r);
            form.go(true);
            obj.setCache(formCmds);
        }
        if (!PDFDebugger.DISABLE_FORMS) {
            this.cmds.addPush();
            this.cmds.addCommands(formCmds);
            this.cmds.addPop();
        }
    }

    /**
     * Set the values into a PatternSpace
     */
    private PDFPaint doPattern(final PatternSpace patternSpace) throws IOException {
        float[] components = null;
        final String patternName = popString();
        final PDFObject pattern = findResource(patternName, "Pattern");
        if (pattern == null) {
            throw new PDFParseException("Unknown pattern : " + patternName);
        }
        if (!this.stack.isEmpty()) {
            components = popFloat(this.stack.size());
        }
        return patternSpace.getPaint(pattern, components, this.resources);
    }

    /**
     * Parse the next object out of the PDF stream. This could be a
     * Double, a String, a HashMap (dictionary), Object[] array, or
     * a Tok containing a PDF command.
     */
    private Object parseObject() throws PDFParseException, DebugStopException {
        final Tok t = nextToken();
        if (t.type == Tok.NUM) {
            return this.tok.value;
        } else if (t.type == Tok.STR) {
            return this.tok.name;
        } else if (t.type == Tok.NAME) {
            return this.tok.name;
        } else if (t.type == Tok.BRKB) {
            final Map<String, PDFObject> hm = new HashMap<>();
            String name = null;
            Object obj;
            while ((obj = parseObject()) != null) {
                if (name == null) {
                    name = (String) obj;
                } else {
                    hm.put(name, new PDFObject(obj));
                    name = null;
                }
            }
            if (this.tok.type != Tok.BRKE) {
                throw new PDFParseException("Inline dict should have ended with '>>'");
            }
            return hm;
        } else if (t.type == Tok.ARYB) {
            // build an array
            final ArrayList<Object> ary = new ArrayList<>();
            Object obj;
            while ((obj = parseObject()) != null) {
                ary.add(obj);
            }
            if (this.tok.type != Tok.ARYE) {
                throw new PDFParseException("Expected ']'");
            }
            return ary.toArray();
        } else if (t.type == Tok.CMD) {
            onNextObject(t);
            return t;
        }
        PDFDebugger.debug("**** WARNING! parseObject unknown token! (t.type=" + t.type + ") *************************", 10);
        return null;
    }

    /**
     * Parse an inline image. An inline image starts with BI (already
     * read, contains a dictionary until ID, and then image data until
     * EI.
     */
    private void parseInlineImage() throws IOException, DebugStopException {
        // build dictionary until ID, then read image until EI
        final Map<String, PDFObject> hm = new HashMap<>();
        while (true) {
            final Tok t = nextToken();
            if (t.type == Tok.CMD) {
                onNextObject(t);
                if (t.name.equals("ID")) {
                    break;
                }
            }
            // it should be a name;
            String name = t.name;
            if (PDFDebugger.DEBUG_IMAGES) {
                PDFDebugger.debug("ParseInlineImage, token: " + name);
            }
            switch (name) {
                case "BPC":
                    name = "BitsPerComponent";
                    break;
                case "CS":
                    name = "ColorSpace";
                    break;
                case "D":
                    name = "Decode";
                    break;
                case "DP":
                    name = "DecodeParms";
                    break;
                case "F":
                    name = "Filter";
                    break;
                case "H":
                    name = "Height";
                    break;
                case "IM":
                    name = "ImageMask";
                    break;
                case "W":
                    name = "Width";
                    break;
                case "I":
                    name = "Interpolate";
                    break;
                default:
                    break;
            }
            final Object vobj = parseObject();
            hm.put(name, new PDFObject(vobj));
        }
        if (this.stream[this.loc] == '\r') {
            this.loc++;
        }
        if (this.stream[this.loc] == '\n' || this.stream[this.loc] == ' ') {
            this.loc++;
        }
        final PDFObject imObj = hm.get("ImageMask");
        if (imObj != null && imObj.getBooleanValue()) {
            // [PATCHED by michal.busta@gmail.com] - default value according to PDF spec. is [0, 1]
            // there is no need to swap array - PDF image should handle this values
            final Double[] decode = {(double) 0, 1d};
            final PDFObject decodeObj = hm.get("Decode");
            if (decodeObj != null) {
                decode[0] = decodeObj.getAt(0).getDoubleValue();
                decode[1] = decodeObj.getAt(1).getDoubleValue();
            }
            hm.put("Decode", new PDFObject(decode));
        }
        final PDFObject obj = new PDFObject(null, PDFObject.DICTIONARY, hm);
        final int dstart = this.loc;
        // now skip data until a whitespace followed by EI
        while (!PDFFile.isWhiteSpace(this.stream[this.loc]) || this.stream[this.loc + 1] != 'E' || this.stream[this.loc + 2] != 'I') {
            this.loc++;
        }
        // data runs from dstart to loc
        if (PDFDebugger.DEBUG_IMAGES) {
            PDFDebugger.debug("InlineImage from " + dstart + " to " + this.loc);
        }
        final byte[] data = new byte[this.loc - dstart];
        System.arraycopy(this.stream, dstart, data, 0, this.loc - dstart);
        obj.setStream(ByteBuffer.wrap(data));
        this.loc += 3;
        doImage(obj);
    }

    /**
     * build a shader from a dictionary.
     */
    private void doShader(final PDFObject shaderObj) throws IOException {
        final PDFShader shader = PDFShader.getShader(shaderObj, this.resources);
        if (shader == null) {
            return;
        }
        this.cmds.addPush();
        final Rectangle2D bbox = shader.getBbox();
        if (bbox != null) {
            this.cmds.addFillPaint(shader.getPaint());
            this.cmds.addPath(new GeneralPath(bbox), PDFShapeCmd.FILL, this.autoAdjustStroke);
        } else {
            this.cmds.addFillPaint(shader.getPaint());
            this.cmds.addPath(null, PDFShapeCmd.FILL, this.autoAdjustStroke);
        }
        this.cmds.addPop();
    }

    /**
     * get a PDFFont from the resources, given the resource name of the
     * font.
     *
     * @param fontref the resource key for the font
     */
    private PDFFont getFontFrom(final String fontref) throws IOException {
        final PDFObject obj = findResource(fontref, "Font");
        return PDFFont.getFont(obj, (HashMap<String, PDFObject>) this.resources);
    }

    /**
     * add graphics state commands contained within a dictionary.
     *
     * @param name the resource name of the graphics state dictionary
     */
    private void setGSState(final String name) throws IOException {
        // obj must be a string that is a key to the "ExtGState" dict
        final PDFObject gsobj = findResource(name, "ExtGState");
        // TODO: lots of graphic states are not yet considered, see chapter 8.4.5 of the PDF specification.
        // get LW, LC, LJ, Font, SM, CA, ML, D, RI, FL, BM, ca
        // out of the reference, which is a dictionary
        if (gsobj == null) {
            return;
        }
        PDFObject d;
        boolean handled = false;
        if ((d = gsobj.getDictRef("LW")) != null) {
            this.cmds.addStrokeWidth(d.getFloatValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("LC")) != null) {
            this.cmds.addEndCap(d.getIntValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("LJ")) != null) {
            this.cmds.addLineJoin(d.getIntValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("Font")) != null) {
            this.state.textFormat.setFont(getFontFrom(d.getAt(0).getStringValue()), d.getAt(1).getFloatValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("ML")) != null) {
            this.cmds.addMiterLimit(d.getFloatValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("D")) != null) {
            final PDFObject[] pdash = d.getAt(0).getArray();
            final float[] dash = new float[pdash.length];
            for (int i = 0; i < pdash.length; i++) {
                dash[i] = pdash[i].getFloatValue();
            }
            if (!PDFDebugger.DISABLE_PATH_STROKE) {
                this.cmds.addDash(dash, d.getAt(1).getFloatValue());
            }
            handled = true;
        }
        if ((d = gsobj.getDictRef("CA")) != null) {
            this.cmds.addStrokeAlpha(d.getFloatValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("ca")) != null) {
            this.cmds.addFillAlpha(d.getFloatValue());
            handled = true;
        }
        if ((d = gsobj.getDictRef("SA")) != null) {
            // automatic stroke adjustment
            this.autoAdjustStroke = d.getBooleanValue();
            handled = true;
        }
        if ((d = gsobj.getDictRef("OP")) != null) {
            this.strokeOverprint = d.getBooleanValue();
            final PDFObject x = gsobj.getDictRef("OPM");
            if (x != null) {
                this.strokeOverprintMode = x.getIntValue();
            }
            handled = true;
        }
        if ((d = gsobj.getDictRef("op")) != null) {
            this.fillOverprint = d.getBooleanValue();
            final PDFObject x = gsobj.getDictRef("OPM");
            if (x != null) {
                this.fillOverprintMode = x.getIntValue();
            }
            handled = true;
        }
        if (!handled) {
            PDFDebugger.debug("graphic state command unknown!", 10);
        }
    }

    /**
     * generate a PDFColorSpace description based on a PDFObject. The
     * object could be a standard name, or the name of a resource in
     * the ColorSpace dictionary, or a color space name with a defining
     * dictionary or stream.
     */
    private PDFColorSpace parseColorSpace(final PDFObject csobj) throws IOException {
        if (csobj == null) {
            return this.state.fillCS;
        }
        return PDFColorSpace.getColorSpace(csobj, this.resources);
    }

    /**
     * pop a single float value off the stack.
     *
     * @return the float value of the top of the stack
     * @throws PDFParseException if the value on the top of the stack
     *                           isn't a number
     */
    private float popFloat() throws PDFParseException {
        if (!this.stack.isEmpty()) {
            final Object obj = this.stack.pop();
            if (obj instanceof Double) {
                return ((Double) obj).floatValue();
            } else {
                throw new PDFParseException("Expected a number here.");
            }
        }
        return 0;
    }

    /**
     * pop an array of float values off the stack. This is equivalent
     * to filling an array from end to front by popping values off the
     * stack.
     *
     * @param count the number of numbers to pop off the stack
     * @return an array of length <tt>count</tt>
     * @throws PDFParseException if any of the values popped off the
     *                           stack are not numbers.
     */
    private float[] popFloat(final int count) throws PDFParseException {
        final float[] ary = new float[count];
        for (int i = count - 1; i >= 0; i--) {
            ary[i] = popFloat();
        }
        return ary;
    }

    /**
     * pop a single integer value off the stack.
     *
     * @return the integer value of the top of the stack
     * @throws PDFParseException if the top of the stack isn't a number.
     */
    private int popInt() throws PDFParseException {
        final Object obj = this.stack.pop();
        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        } else {
            throw new PDFParseException("Expected a number here.");
        }
    }

    /**
     * pop an array of integer values off the stack. This is equivalent
     * to filling an array from end to front by popping values off the
     * stack.
     *
     * @return an array of length <tt>count</tt>
     * @throws PDFParseException if any of the values popped off the
     *                           stack are not numbers.
     */
    private float[] popFloatArray() throws PDFParseException {
        final Object obj = this.stack.pop();
        if (!(obj instanceof Object[] source)) {
            throw new PDFParseException("Expected an [array] here.");
        }
        final float[] ary = new float[source.length];
        for (int i = 0; i < ary.length; i++) {
            if (source[i] instanceof Double) {
                ary[i] = ((Double) source[i]).floatValue();
            } else {
                throw new PDFParseException("This array doesn't consist only of floats.");
            }
        }
        return ary;
    }

    /**
     * pop a String off the stack.
     *
     * @return the String from the top of the stack
     * @throws PDFParseException if the top of the stack is not a NAME
     *                           or STR.
     */
    private String popString() throws PDFParseException {
        final Object obj = this.stack.pop();
        if (!(obj instanceof String)) {
            throw new PDFParseException("Expected string here: " + obj.toString());
        } else {
            return (String) obj;
        }
    }

    /**
     * pop a PDFObject off the stack.
     *
     * @return the PDFObject from the top of the stack
     * @throws PDFParseException if the top of the stack does not contain
     *                           a PDFObject.
     */
    private PDFObject popObject() throws PDFParseException {
        final Object obj = this.stack.pop();
        if (!(obj instanceof PDFObject)) {
            throw new PDFParseException("Expected a reference here: " + obj.toString());
        }
        return (PDFObject) obj;
    }

    /**
     * pop an array off the stack
     *
     * @return the array of objects that is the top element of the stack
     * @throws PDFParseException if the top element of the stack does not
     *                           contain an array.
     */
    private Object[] popArray() throws PDFParseException {
        final Object obj = this.stack.pop();
        if (!(obj instanceof Object[])) {
            throw new PDFParseException("Expected an [array] here: " + obj.toString());
        }
        return (Object[]) obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setStatus(final int status) {
        if (status == BaseWatchable.COMPLETED) {
            if (!addAnnotation) {
                // corresponding push in constructor PDFPage
                this.cmds.addPop();
                this.cmds.addAnnotations();
                addAnnotation = true;
            }
        }
        super.setStatus(status);
    }

    /**
     * a token from a PDF Stream
     */
    static class Tok {
        /**
         * begin bracket &lt;
         */
        public static final int BRKB = 11;
        /**
         * end bracket &gt;
         */
        public static final int BRKE = 10;
        /**
         * begin array [
         */
        public static final int ARYB = 9;
        /**
         * end array ]
         */
        public static final int ARYE = 8;
        /**
         * String (, readString looks for trailing )
         */
        public static final int STR = 7;
        /**
         * begin brace {
         */
        public static final int BRCB = 5;
        /**
         * end brace }
         */
        public static final int BRCE = 4;
        /**
         * number
         */
        public static final int NUM = 3;
        /**
         * keyword
         */
        public static final int CMD = 2;
        /**
         * name (begins with /)
         */
        public static final int NAME = 1;
        /**
         * unknown token
         */
        public static final int UNK = 0;
        /**
         * end of stream
         */
        public static final int EOF = -1;
        /**
         * the string value of a STR, NAME, or CMD token
         */
        public String name;
        /**
         * the value of a NUM token
         */
        public double value;
        /**
         * the type of the token
         */
        public int type;

        /**
         * a printable representation of the token
         */
        @Override
        public String toString() {
            if (this.type == NUM) {
                return "NUM: " + this.value;
            } else if (this.type == CMD) {
                return "CMD: " + this.name;
            } else if (this.type == UNK) {
                return "UNK";
            } else if (this.type == EOF) {
                return "EOF";
            } else if (this.type == NAME) {
                return "NAME: " + this.name;
            } else if (this.type == STR) {
                return "STR: (" + this.name;
            } else if (this.type == ARYB) {
                return "ARY [";
            } else if (this.type == ARYE) {
                return "ARY ]";
            } else {
                return "some kind of brace (" + this.type + ")";
            }
        }

        /**
         * reset the token to it's original state
         */
        public void reset() {
            name = null;
            value = 0.0;
            type = UNK;
        }
    }

    /**
     * A class to store state needed whiel rendering. This includes the
     * stroke and fill color spaces, as well as the text formatting
     * parameters.
     */
    static class ParserState implements Cloneable {
        /**
         * the fill color space
         */
        PDFColorSpace fillCS;
        /**
         * the stroke color space
         */
        PDFColorSpace strokeCS;
        /**
         * the text paramters
         */
        PDFTextFormat textFormat;

        /**
         * Clone the render state.
         */
        @Override
        public Object clone() throws CloneNotSupportedException {
            final ParserState newState = new ParserState();
            // no need to clone color spaces, since they are immutable
            newState.fillCS = this.fillCS;
            newState.strokeCS = this.strokeCS;
            // we do need to clone the textFormat
            newState.textFormat = (PDFTextFormat) this.textFormat.clone();
            return newState;
        }
    }
}
