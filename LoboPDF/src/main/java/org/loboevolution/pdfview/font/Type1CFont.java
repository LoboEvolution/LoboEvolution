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
package org.loboevolution.pdfview.font;

import org.loboevolution.pdfview.PDFDebugger;
import org.loboevolution.pdfview.PDFObject;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.io.IOException;

/**
 * A representation, with parser, of an Adobe Type 1C font.
 * You can find information about CFF and Type 1C font encoding
 * in
 * <a href="http://partners.adobe.com/public/developer/en/font/5176.CFF.pdf">...</a>
 * and
 * <a href="http://partners.adobe.com/public/developer/en/font/5177.Type2.pdf">...</a>
 * Author Mike Wessler
 */
public class Type1CFont extends OutlineFont {

    static final int CMD = 0;
    static final int NUM = 1;
    static final int FLT = 2;
    final float[] stack = new float[100];
    final int[] encoding = new int[256];
    final float[] temps = new float[32];
    byte[] data;
    int pos;
    int stackptr = 0;
    int stemhints = 0;
    String[] names;
    int[] glyphnames;
    String fontname;
    AffineTransform at = new AffineTransform(0.001f, 0, 0, 0.001f, 0, 0);
    int number;
    float fnum;
    int type;
    // Top DICT: NAME    CODE   DEFAULT
    // charstringtype    12 6    2
    // fontmatrix        12 7    0.001 0 0 0.001
    // charset           15      - (offset)  names of glyphs (ref to name idx)
    // encoding          16      - (offset)  array of codes
    // CharStrings       17      - (offset)
    // Private           18      - (size, offset)
    // glyph at position i in CharStrings has name charset[i]
    // and code encoding[i]
    int charstringtype = 2;
    int charsetbase = 0;
    int encodingbase = 0;
    int charstringbase = 0;
    int privatebase = 0;

    // DICT structure:
    // operand operator operand operator ...
    // INDEX structure:
    // count(2) offsize [offset offset ... offset] data
    // offset array has count+1 entries
    // data starts at 3+(count+1)*offsize
    // offset for data is offset+2+(count+1)*offsize
    int privatesize = 0;
    int gsubrbase = 0;
    int lsubrbase = 0;
    int gsubrsoffset = 0;
    int lsubrsoffset = 0;
    int nglyphs = 1;

    /**
     * create a new Type1CFont based on a font data stream and a descriptor
     *
     * @param baseFont   the postscript name of this font
     * @param src        a stream containing the font
     * @param descriptor the descriptor for this font
     * @throws java.io.IOException if any.
     */
    public Type1CFont(final String baseFont, final PDFObject src,
                      final PDFFontDescriptor descriptor) throws IOException {
        super(baseFont, src, descriptor);

        final PDFObject dataObj = descriptor.getFontFile3();
        this.data = dataObj.getStream();
        this.pos = 0;
        parse();

        // TODO: free up (set to null) unused structures (data, subrs, stack)
    }

    /**
     * a debug method for printing the data
     */
    private void printData() {
        final char[] parts = new char[17];
        int partsloc = 0;
        for (int i = 0; i < this.data.length; i++) {
            final int d = (this.data[i]) & 0xff;
            if (d == 0) {
                parts[partsloc++] = '.';
            } else if (d < 32 || d >= 127) {
                parts[partsloc++] = '?';
            } else {
                parts[partsloc++] = (char) d;
            }
            if (d < 16) {
                PDFDebugger.debug("0" + Integer.toHexString(d), 200);
            } else {
                PDFDebugger.debug(Integer.toHexString(d), 200);
            }
            if ((i & 15) == 15) {
                PDFDebugger.debug("      " + new String(parts), 200);
                partsloc = 0;
            } else if ((i & 7) == 7) {
                PDFDebugger.debug("  ", 200);
                parts[partsloc++] = ' ';
            } else if ((i & 1) == 1) {
                PDFDebugger.debug(" ", 200);
            }
        }
    }

    /**
     * read the next decoded value from the stream
     *
     * @param charstring ????
     */
    private int readNext(final boolean charstring) {
        this.number = (this.data[this.pos++]) & 0xff;
        if (this.number == 30 && !charstring) { // goofy floatingpoint rep
            readFNum();
            return this.type = FLT;
        } else if (this.number == 28) {
            this.number = ((this.data[this.pos]) << 8) + ((this.data[this.pos + 1]) & 0xff);
            this.pos += 2;
            return this.type = NUM;
        } else if (this.number == 29 && !charstring) {
            this.number = ((this.data[this.pos] & 0xff) << 24) |
                    ((this.data[this.pos + 1] & 0xff) << 16) |
                    ((this.data[this.pos + 2] & 0xff) << 8) |
                    ((this.data[this.pos + 3] & 0xff));
            this.pos += 4;
            return this.type = NUM;
        } else if (this.number == 12) {  // two-byte command
            this.number = 1000 + ((this.data[this.pos++]) & 0xff);
            return this.type = CMD;
        } else if (this.number < 32) {
            return this.type = CMD;
        } else if (this.number < 247) {
            this.number -= 139;
            return this.type = NUM;
        } else if (this.number < 251) {
            this.number = (this.number - 247) * 256 + ((this.data[this.pos++]) & 0xff) + 108;
            return this.type = NUM;
        } else if (this.number < 255) {
            this.number = -(this.number - 251) * 256 - ((this.data[this.pos++]) & 0xff) - 108;
            return this.type = NUM;
        } else if (!charstring) { // dict shouldn't have a 255 code
            printData();
            throw new RuntimeException("Got a 255 code while reading dict");
        } else { // num was 255
            this.fnum = (((this.data[this.pos] & 0xff) << 24) |
                    ((this.data[this.pos + 1] & 0xff) << 16) |
                    ((this.data[this.pos + 2] & 0xff) << 8) |
                    ((this.data[this.pos + 3] & 0xff))) / 65536f;
            this.pos += 4;
            return this.type = FLT;
        }
    }

    /**
     * read the next funky floating point number from the input stream.
     * value gets put into the fnum field.
     */
    public void readFNum() {
        // work in nybbles: 0-9=0-9, a=. b=E, c=E-, d=rsvd e=neg f=end
        float f = 0;
        boolean neg = false;
        int exp = 0;
        int eval = 0;
        float mul = 1;
        byte work = this.data[this.pos++];
        while (true) {
            if (work == (byte) 0xdd) {
                work = this.data[this.pos++];
            }
            final int nyb = (work >> 4) & 0xf;
            work = (byte) ((work << 4) | 0xd);
            if (nyb < 10) {
                if (exp != 0) {         // working on the exponent
                    eval = eval * 10 + nyb;
                } else if (mul == 1) {  // working on an int
                    f = f * 10 + nyb;
                } else {              // working on decimal part
                    f += nyb * mul;
                    mul /= 10f;
                }
            } else if (nyb == 0xa) {    // decimal
                mul = 0.1f;
            } else if (nyb == 0xb) {    // E+
                exp = 1;
            } else if (nyb == 0xc) {    // E-
                exp = -1;
            } else if (nyb == 0xe) {      // neg
                neg = true;
            } else {
                break;
            }
        }
        this.fnum = (neg ? -1 : 1) * f * (float) Math.pow(10, eval * exp);
    }

    /**
     * read an integer from the input stream
     *
     * @param len the number of bytes in the integer
     * @return the integer
     */
    private int readInt(final int len) {
        int n = 0;
        for (int i = 0; i < len; i++) {
            n = (n << 8) | ((this.data[this.pos++]) & 0xff);
        }
        return n;
    }

    /**
     * read the next byte from the stream
     *
     * @return the byte
     */
    private int readByte() {
        return (this.data[this.pos++]) & 0xff;
    }

    /**
     * get the size of the dictionary located within the stream at
     * some offset.
     *
     * @param loc the index of the start of the dictionary
     * @return the size of the dictionary, in bytes.
     */
    public int getIndexSize(final int loc) {
        final int hold = this.pos;
        this.pos = loc;
        final int count = readInt(2);
        if (count <= 0) {
            return 2;
        }
        final int encsz = readByte();
        if (encsz < 1 || encsz > 4) {
            throw new RuntimeException("Offsize: " + encsz +
                    ", must be in range 1-4.");
        }
        // pos is now at the first offset.  last offset is at count*encsz
        this.pos += count * encsz;
        final int end = readInt(encsz);
        this.pos = hold;
        return 2 + (count + 1) * encsz + end;
    }

    /**
     * return the number of entries in an Index table.
     *
     * @param loc a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    public int getTableLength(final int loc) {
        final int hold = this.pos;
        this.pos = loc;
        final int count = readInt(2);
        if (count <= 0) {
            return 2;
        }
        this.pos = hold;
        return count;
    }

    /**
     * Get the range of a particular index in a dictionary.
     *
     * @param index the start of the dictionary.
     * @param id    the index of the entry in the dictionary
     * @return a range describing the offsets of the start and end of
     * the entry from the start of the file, not the dictionary
     */
    Range getIndexEntry(final int index, final int id) {
        final int hold = this.pos;
        this.pos = index;
        final int count = readInt(2);
        final int encsz = readByte();
        if (encsz < 1 || encsz > 4) {
            throw new RuntimeException("Offsize: " + encsz +
                    ", must be in range 1-4.");
        }
        this.pos += encsz * id;
        final int from = readInt(encsz);
        final Range r = new Range(from + 2 + index + encsz * (count + 1), readInt(
                encsz) - from);
        this.pos = hold;
        return r;
    }

    /**
     * read a dictionary that exists within some range, parsing the entries
     * within the dictionary.
     */
    private void readDict(final Range r) {
        this.pos = r.getStart();
        while (this.pos < r.getEnd()) {
            final int cmd = readCommand(false);
            if (cmd == 1006) { // charstringtype, default=2
                this.charstringtype = (int) this.stack[0];
            } else if (cmd == 1007) { // fontmatrix
                if (this.stackptr == 4) {
                    this.at = new AffineTransform(this.stack[0], this.stack[1],
                            this.stack[2], this.stack[3],
                            0, 0);
                } else {
                    this.at = new AffineTransform(this.stack[0], this.stack[1],
                            this.stack[2], this.stack[3],
                            this.stack[4], this.stack[5]);
                }
            } else if (cmd == 15) { // charset
                this.charsetbase = (int) this.stack[0];
            } else if (cmd == 16) { // encoding
                this.encodingbase = (int) this.stack[0];
            } else if (cmd == 17) { // charstrings
                this.charstringbase = (int) this.stack[0];
            } else if (cmd == 18) { // private
                this.privatesize = (int) this.stack[0];
                this.privatebase = (int) this.stack[1];
            } else if (cmd == 19) { // subrs (in Private dict)
                this.lsubrbase = this.privatebase + (int) this.stack[0];
                this.lsubrsoffset = calcoffset(this.lsubrbase);
            }
            this.stackptr = 0;
        }
    }

    /**
     * read a complete command.  this may involve several numbers
     * which go onto a stack before an actual command is read.
     *
     * @param charstring ????
     * @return the command.  Some numbers may also be on the stack.
     */
    private int readCommand(final boolean charstring) {
        while (true) {
            final int t = readNext(charstring);
            if (t == CMD) {
                return this.number;
            } else {
                this.stack[this.stackptr++] = (t == NUM) ? (float) this.number : this.fnum;
            }
        }
    }

    /**
     * parse information about the encoding of this file.
     *
     * @param base the start of the encoding data
     */
    private void readEncodingData(final int base) {
        if (base == 0) {  // this is the StandardEncoding
            System.arraycopy(FontSupport.standardEncoding, 0, this.encoding, 0,
                    FontSupport.standardEncoding.length);
        } else if (base == 1) {  // this is the expert encoding
            PDFDebugger.debug("**** EXPERT ENCODING not yet implemented!");
            // TODO: copy ExpertEncoding
        } else {
            this.pos = base;
            final int encodingtype = readByte();
            if ((encodingtype & 127) == 0) {
                final int ncodes = readByte();
                for (int i = 1; i < ncodes + 1; i++) {
                    final int idx = readByte() & 0xff;
                    this.encoding[idx] = i;
                }
            } else if ((encodingtype & 127) == 1) {
                final int nranges = readByte();
                int p = 1;
                for (int i = 0; i < nranges; i++) {
                    final int start = readByte();
                    final int more = readByte();
                    for (int j = start; j < start + more + 1; j++) {
                        this.encoding[j] = p++;
                    }
                }
            } else {
                PDFDebugger.debug("Bad encoding type: " + encodingtype);
            }
            // TODO: now check for supplemental encoding data
        }
    }

    /**
     * read the names of the glyphs.
     *
     * @param base the start of the glyph name table
     */
    private void readGlyphNames(final int base) {
        if (base == 0) {
            this.glyphnames = new int[229];
            for (int i = 0; i < this.glyphnames.length; i++) {
                this.glyphnames[i] = i;
            }
            return;
        } else if (base == 1) {
            this.glyphnames = FontSupport.type1CExpertCharset;
            return;
        } else if (base == 2) {
            this.glyphnames = FontSupport.type1CExpertSubCharset;
            return;
        }
        // nglyphs has already been set.
        this.glyphnames = new int[this.nglyphs];
        this.glyphnames[0] = 0;
        this.pos = base;
        final int t = readByte();
        if (t == 0) {
            for (int i = 1; i < this.nglyphs; i++) {
                this.glyphnames[i] = readInt(2);
            }
        } else if (t == 1) {
            int n = 1;
            while (n < this.nglyphs) {
                int sid = readInt(2);
                final int range = readByte() + 1;
                for (int i = 0; i < range; i++) {
                    this.glyphnames[n++] = sid++;
                }
            }
        } else if (t == 2) {
            int n = 1;
            while (n < this.nglyphs) {
                int sid = readInt(2);
                final int range = readInt(2) + 1;
                for (int i = 0; i < range; i++) {
                    this.glyphnames[n++] = sid++;
                }
            }
        }
    }

    /**
     * read a list of names
     *
     * @param base the start of the name table
     */
    private void readNames(final int base) {
        this.pos = base;
        final int nextra = readInt(2);
        this.names = new String[nextra];
        //	safenames= new String[nextra];
        for (int i = 0; i < nextra; i++) {
            final Range r = getIndexEntry(base, i);
            this.names[i] = new String(this.data, r.getStart(), r.getLen());
            PDFDebugger.debug("Read name: " + i + " from " + r.getStart() + " to " + r.getEnd() + ": " + safe(names[i]), 1000);
        }
    }

    /**
     * parse the font data.
     */
    private void parse() {
        // jump over rest of header: base of font names index
        final int fnames = readByte();
        // offset in the file of the array of font dicts
        final int topdicts = fnames + getIndexSize(fnames);
        // offset in the file of local names
        final int theNames = topdicts + getIndexSize(topdicts);
        // offset in the file of the array of global subroutines
        this.gsubrbase = theNames + getIndexSize(theNames);
        this.gsubrsoffset = calcoffset(this.gsubrbase);
        // read extra names
        readNames(theNames);
        // does this file have more than one font?
        this.pos = topdicts;
        if (readInt(2) != 1) {
            printData();
            throw new RuntimeException("More than one font in this file!");
        }
        final Range r = getIndexEntry(fnames, 0);
        this.fontname = new String(this.data, r.getStart(), r.getLen());
        // read first dict
        readDict(getIndexEntry(topdicts, 0));
        // read the private dictionary
        readDict(new Range(this.privatebase, this.privatesize));
        // calculate the number of glyphs
        this.pos = this.charstringbase;
        this.nglyphs = readInt(2);
        // now get the glyph names
        readGlyphNames(this.charsetbase);
        // now figure out the encoding
        readEncodingData(this.encodingbase);
    }

    /**
     * get the index of a particular name.  The name table starts with
     * the standard names in FontSupport.stdNames, and is appended by
     * any names in the name table from this font's dictionary.
     */
    private int getNameIndex(final String name) {
        int val = FontSupport.findName(name, FontSupport.stdNames);
        if (val == -1) {
            val = FontSupport.findName(name, this.names) + FontSupport.stdNames.length;
        }
        if (val == -1) {
            val = 0;
        }
        return val;
    }

    /**
     * convert a string to one in which any non-printable bytes are
     * replaced by "<###>" where ## is the value of the byte.
     */
    private String safe(final String src) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            final char c = src.charAt(i);
            if (c >= 32 && c < 128) {
                sb.append(c);
            } else {
                sb.append("<").append((int) c).append(">");
            }
        }
        return sb.toString();
    }

    /**
     * Read the data for a glyph from the glyph table, and transform
     * it based on the current transform.
     *
     * @param base   the start of the glyph table
     * @param offset the index of this glyph in the glyph table
     */
    private synchronized GeneralPath readGlyph(final int base, final int offset) {
        final FlPoint pt = new FlPoint();

        // find this entry
        final Range r = getIndexEntry(base, offset);

        // create a path
        final GeneralPath gp = new GeneralPath();


        // rember the start position (for recursive calls due to seac)
        final int hold = this.pos;

        // read the glyph itself
        this.stackptr = 0;
        this.stemhints = 0;
        parseGlyph(r, gp, pt);

        // restore the start position
        this.pos = hold;

        gp.transform(this.at);

        return gp;
    }

    /**
     * calculate an offset code for a dictionary. Uses the count of entries
     * to determine what the offset should be.
     *
     * @param base the index of the start of the dictionary
     * @return a {@link java.lang.Integer} object.
     */
    public int calcoffset(final int base) {
        final int len = getTableLength(base);
        if (len < 1240) {
            return 107;
        } else if (len < 33900) {
            return 1131;
        } else {
            return 32768;
        }
    }

    /**
     * build an accented character out of two pre-defined glyphs.
     *
     * @param x  the x offset of the accent
     * @param y  the y offset of the accent
     * @param b  the index of the base glyph
     * @param a  the index of the accent glyph
     * @param gp the GeneralPath into which the combined glyph will be
     *           written.
     */
    private void buildAccentChar(final float x, final float y, final char b, final char a,
                                 final GeneralPath gp) {
        // get the outline of the accent
        final GeneralPath pathA = getOutline(a, getWidth(a, null));

        // undo the effect of the transform applied in read
        final AffineTransform xformA = AffineTransform.getTranslateInstance(x, y);
        try {
            xformA.concatenate(this.at.createInverse());
        } catch (final NoninvertibleTransformException nte) {
            // oh well ...
        }
        pathA.transform(xformA);

        final GeneralPath pathB = getOutline(b, getWidth(b, null));

        try {
            final AffineTransform xformB = this.at.createInverse();
            pathB.transform(xformB);
        } catch (final NoninvertibleTransformException nte) {
            // ignore
        }

        gp.append(pathB, false);
        gp.append(pathA, false);
    }

    /**
     * parse a glyph defined in a particular range
     *
     * @param r  the range of the glyph definition
     * @param gp a GeneralPath in which to store the glyph outline
     * @param pt a FlPoint representing the end of the current path
     */
    void parseGlyph(final Range r, final GeneralPath gp, final FlPoint pt) {
        this.pos = r.getStart();
        int i;
        float x1, y1, x2, y2, ybase;
        int hold;
        while (this.pos < r.getEnd()) {
            final int cmd = readCommand(true);
            hold = 0;
            switch (cmd) {
                case 1: // hstem
                case 3: // vstem
                case 1000: // old dotsection command.  ignore.
                    this.stackptr = 0;
                    break;
                case 4: // vmoveto
                    if (this.stackptr > 1) {  // this is the first call, arg1 is width
                        this.stack[0] = this.stack[1];
                    }
                    pt.y += this.stack[0];
                    if (pt.open) {
                        gp.closePath();
                    }
                    pt.open = false;
                    gp.moveTo(pt.x, pt.y);
                    this.stackptr = 0;
                    break;
                case 5: // rlineto
                    for (i = 0; i < this.stackptr; ) {
                        pt.x += this.stack[i++];
                        pt.y += this.stack[i++];
                        gp.lineTo(pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 6: // hlineto
                    for (i = 0; i < this.stackptr; ) {
                        if ((i & 1) == 0) {
                            pt.x += this.stack[i++];
                        } else {
                            pt.y += this.stack[i++];
                        }
                        gp.lineTo(pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 7: // vlineto
                    for (i = 0; i < this.stackptr; ) {
                        if ((i & 1) == 0) {
                            pt.y += this.stack[i++];
                        } else {
                            pt.x += this.stack[i++];
                        }
                        gp.lineTo(pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 8: // rrcurveto
                    for (i = 0; i < this.stackptr; ) {
                        x1 = pt.x + this.stack[i++];
                        y1 = pt.y + this.stack[i++];
                        x2 = x1 + this.stack[i++];
                        y2 = y1 + this.stack[i++];
                        pt.x = x2 + this.stack[i++];
                        pt.y = y2 + this.stack[i++];
                        gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 10: // callsubr
                    hold = this.pos;
                    i = (int) this.stack[--this.stackptr] + this.lsubrsoffset;
                    final Range lsubr = getIndexEntry(this.lsubrbase, i);
                    parseGlyph(lsubr, gp, pt);
                    this.pos = hold;
                    break;
                case 11: // return
                    return;
                case 14: // endchar
                    // width x y achar bchar endchar == x y achar bchar seac
                    if (this.stackptr == 5) {
                        buildAccentChar(this.stack[1], this.stack[2], (char) this.stack[3],
                                (char) this.stack[4], gp);
                    } else if (this.stackptr == 4) {
                        // see page 58 on specification 5177.Type2.pdf which indicates that
                        // these parameters are valid for Type1C as the width is optional
                        buildAccentChar(this.stack[0], this.stack[1], (char) this.stack[2], (char) this.stack[3], gp);
                    }
                    if (pt.open) {
                        gp.closePath();
                    }
                    pt.open = false;
                    this.stackptr = 0;
                    stemhints = 0;
                    break;
                case 18: // hstemhm
                case 23: // vstemhm
                    stemhints += (this.stackptr) / 2;
                    this.stackptr = 0;
                    break;
                case 19: // hintmask
                case 20: // cntrmask
                    stemhints += (this.stackptr) / 2;
                    this.pos += (stemhints - 1) / 8 + 1;
                    this.stackptr = 0;
                    break;
                case 21: // rmoveto
                    if (this.stackptr > 2) {
                        this.stack[0] = this.stack[1];
                        this.stack[1] = this.stack[2];
                    }
                    pt.x += this.stack[0];
                    pt.y += this.stack[1];
                    if (pt.open) {
                        gp.closePath();
                    }
                    gp.moveTo(pt.x, pt.y);
                    pt.open = false;
                    this.stackptr = 0;
                    break;
                case 22: // hmoveto
                    if (this.stackptr > 1) {
                        this.stack[0] = this.stack[1];
                    }
                    pt.x += this.stack[0];
                    if (pt.open) {
                        gp.closePath();
                    }
                    gp.moveTo(pt.x, pt.y);
                    pt.open = false;
                    this.stackptr = 0;
                    break;
                case 24: // rcurveline
                    for (i = 0; i < this.stackptr - 2; ) {
                        x1 = pt.x + this.stack[i++];
                        y1 = pt.y + this.stack[i++];
                        x2 = x1 + this.stack[i++];
                        y2 = y1 + this.stack[i++];
                        pt.x = x2 + this.stack[i++];
                        pt.y = y2 + this.stack[i++];
                        gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    }
                    pt.x += this.stack[i++];
                    pt.y += this.stack[i++];
                    gp.lineTo(pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 25: // rlinecurve
                    for (i = 0; i < this.stackptr - 6; ) {
                        pt.x += this.stack[i++];
                        pt.y += this.stack[i++];
                        gp.lineTo(pt.x, pt.y);
                    }
                    x1 = pt.x + this.stack[i++];
                    y1 = pt.y + this.stack[i++];
                    x2 = x1 + this.stack[i++];
                    y2 = y1 + this.stack[i++];
                    pt.x = x2 + this.stack[i++];
                    pt.y = y2 + this.stack[i++];
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 26: // vvcurveto
                    i = 0;
                    if ((this.stackptr & 1) == 1) { // odd number of arguments
                        pt.x += this.stack[i++];
                    }
                    while (i < this.stackptr) {
                        x1 = pt.x;
                        y1 = pt.y + this.stack[i++];
                        x2 = x1 + this.stack[i++];
                        y2 = y1 + this.stack[i++];
                        pt.x = x2;
                        pt.y = y2 + this.stack[i++];
                        gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 27: // hhcurveto
                    i = 0;
                    if ((this.stackptr & 1) == 1) { // odd number of arguments
                        pt.y += this.stack[i++];
                    }
                    while (i < this.stackptr) {
                        x1 = pt.x + this.stack[i++];
                        y1 = pt.y;
                        x2 = x1 + this.stack[i++];
                        y2 = y1 + this.stack[i++];
                        pt.x = x2 + this.stack[i++];
                        pt.y = y2;
                        gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 29: // callgsubr
                    hold = this.pos;
                    i = (int) this.stack[--this.stackptr] + this.gsubrsoffset;
                    final Range gsubr = getIndexEntry(this.gsubrbase, i);
                    parseGlyph(gsubr, gp, pt);
                    this.pos = hold;
                    break;
                case 30: // vhcurveto
                    hold = 4;
                    break;
                case 31: // hvcurveto
                    for (i = 0; i < this.stackptr; ) {
                        final boolean hv = (((i + hold) & 4) == 0);
                        x1 = pt.x + (hv ? this.stack[i++] : 0);
                        y1 = pt.y + (hv ? 0 : this.stack[i++]);
                        x2 = x1 + this.stack[i++];
                        y2 = y1 + this.stack[i++];
                        pt.x = x2 + (hv ? 0 : this.stack[i++]);
                        pt.y = y2 + (hv ? this.stack[i++] : 0);
                        if (i == this.stackptr - 1) {
                            if (hv) {
                                pt.x += this.stack[i++];
                            } else {
                                pt.y += this.stack[i++];
                            }
                        }
                        gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    }
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 1003: // and
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = ((x1 != 0) && (y1 != 0)) ? 1 : 0;
                    break;
                case 1004: // or
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = ((x1 != 0) || (y1 != 0)) ? 1 : 0;
                    break;
                case 1005: // not
                    x1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = (x1 == 0) ? 1 : 0;
                    break;
                case 1009: // abs
                    this.stack[this.stackptr - 1] = Math.abs(this.stack[this.stackptr - 1]);
                    break;
                case 1010: // add
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = x1 + y1;
                    break;
                case 1011: // sub
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = y1 - x1;
                    break;
                case 1012: // div
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = y1 / x1;
                    break;
                case 1014: // neg
                    this.stack[this.stackptr - 1] = -this.stack[this.stackptr - 1];
                    break;
                case 1015: // eq
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = (x1 == y1) ? 1 : 0;
                    break;
                case 1018: // drop
                    this.stackptr--;
                    break;
                case 1020: // put
                    i = (int) this.stack[--this.stackptr];
                    x1 = this.stack[--this.stackptr];
                    this.temps[i] = x1;
                    break;
                case 1021: // get
                    i = (int) this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = this.temps[i];
                    break;
                case 1022: // ifelse
                    if (this.stack[this.stackptr - 2] > this.stack[this.stackptr - 1]) {
                        this.stack[this.stackptr - 4] = this.stack[this.stackptr - 3];
                    }
                    this.stackptr -= 3;
                    break;
                case 1023: // random
                    this.stack[this.stackptr++] = (float) Math.random();
                    break;
                case 1024: // mul
                    x1 = this.stack[--this.stackptr];
                    y1 = this.stack[--this.stackptr];
                    this.stack[this.stackptr++] = y1 * x1;
                    break;
                case 1026: // sqrt
                    this.stack[this.stackptr - 1] = (float) Math.sqrt(this.stack[this.stackptr - 1]);
                    break;
                case 1027: // dup
                    x1 = this.stack[this.stackptr - 1];
                    this.stack[this.stackptr++] = x1;
                    break;
                case 1028: // exch
                    x1 = this.stack[this.stackptr - 1];
                    this.stack[this.stackptr - 1] = this.stack[this.stackptr - 2];
                    this.stack[this.stackptr - 2] = x1;
                    break;
                case 1029: // index
                    i = (int) this.stack[this.stackptr - 1];
                    if (i < 0) {
                        i = 0;
                    }
                    this.stack[this.stackptr - 1] = this.stack[this.stackptr - 2 - i];
                    break;
                case 1030: // roll
                    i = (int) this.stack[--this.stackptr];
                    final int n = (int) this.stack[--this.stackptr];
                    // roll n number by i (+ = upward)
                    if (i > 0) {
                        i = i % n;
                    } else {
                        i = n - (-i % n);
                    }
                    // x x x x i y y y -> y y y x x x x i (where i=3)
                    if (i > 0) {
                        final float[] roll = new float[n];
                        System.arraycopy(this.stack, this.stackptr - 1 - i, roll, 0, i);
                        System.arraycopy(this.stack, this.stackptr - 1 - n, roll, i,
                                n - i);
                        System.arraycopy(roll, 0, this.stack, this.stackptr - 1 - n, n);
                    }
                    break;
                case 1034: // hflex
                    x1 = pt.x + this.stack[0];
                    y1 = ybase = pt.y;
                    x2 = x1 + this.stack[1];
                    y2 = y1 + this.stack[2];
                    pt.x = x2 + this.stack[3];
                    pt.y = y2;
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    x1 = pt.x + this.stack[4];
                    y1 = pt.y;
                    x2 = x1 + this.stack[5];
                    y2 = ybase;
                    pt.x = x2 + this.stack[6];
                    pt.y = y2;
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 1035: // flex
                    x1 = pt.x + this.stack[0];
                    y1 = pt.y + this.stack[1];
                    x2 = x1 + this.stack[2];
                    y2 = y1 + this.stack[3];
                    pt.x = x2 + this.stack[4];
                    pt.y = y2 + this.stack[5];
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    x1 = pt.x + this.stack[6];
                    y1 = pt.y + this.stack[7];
                    x2 = x1 + this.stack[8];
                    y2 = y1 + this.stack[9];
                    pt.x = x2 + this.stack[10];
                    pt.y = y2 + this.stack[11];
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 1036: // hflex1
                    ybase = pt.y;
                    x1 = pt.x + this.stack[0];
                    y1 = pt.y + this.stack[1];
                    x2 = x1 + this.stack[2];
                    y2 = y1 + this.stack[3];
                    pt.x = x2 + this.stack[4];
                    pt.y = y2;
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    x1 = pt.x + this.stack[5];
                    y1 = pt.y;
                    x2 = x1 + this.stack[6];
                    y2 = y1 + this.stack[7];
                    pt.x = x2 + this.stack[8];
                    pt.y = ybase;
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                case 1037: // flex1
                    ybase = pt.y;
                    final float xbase = pt.x;
                    x1 = pt.x + this.stack[0];
                    y1 = pt.y + this.stack[1];
                    x2 = x1 + this.stack[2];
                    y2 = y1 + this.stack[3];
                    pt.x = x2 + this.stack[4];
                    pt.y = y2 + this.stack[5];
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    x1 = pt.x + this.stack[6];
                    y1 = pt.y + this.stack[7];
                    x2 = x1 + this.stack[8];
                    y2 = y1 + this.stack[9];
                    if (Math.abs(x2 - xbase) > Math.abs(y2 - ybase)) {
                        pt.x = x2 + this.stack[10];
                        pt.y = ybase;
                    } else {
                        pt.x = xbase;
                        pt.y = y2 + this.stack[10];
                    }
                    gp.curveTo(x1, y1, x2, y2, pt.x, pt.y);
                    pt.open = true;
                    this.stackptr = 0;
                    break;
                default:
                    PDFDebugger.debug("ERROR! TYPE1C CHARSTRING CMD IS " + cmd);
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a glyph outline by name
     */
    @Override
    protected GeneralPath getOutline(final String name, final float width) {
        // first find the index of this name
        final int index = getNameIndex(name);

        // now find the glyph with that name
        for (int i = 0; i < this.glyphnames.length; i++) {
            if (this.glyphnames[i] == index) {
                return readGlyph(this.charstringbase, i);
            }
        }

        // not found -- return the unknown glyph
        return readGlyph(this.charstringbase, 0);
    }

    /**
     * {@inheritDoc}
     * Get a glyph outline by character code
     * Note this method must always return an outline
     */
    @Override
    protected GeneralPath getOutline(final char src, final float width) {
        // ignore high bits
        final int index = (src & 0xff);

        // if we use a standard encoding, the mapping is from glyph to SID
        // therefore we must find the glyph index in the name table
        if (this.encodingbase == 0 || this.encodingbase == 1) {
            for (int i = 0; i < this.glyphnames.length; i++) {
                if (this.glyphnames[i] == this.encoding[index]) {
                    return readGlyph(this.charstringbase, i);
                }
            }
        } else {
            // for a custom encoding, the mapping is from glyph to GID, so
            // we can just map the glyph directly
            if (index > 0 && index < this.encoding.length) {
                return readGlyph(this.charstringbase, this.encoding[index]);
            }
        }

        // for some reason the glyph was not found, return the empty glyph
        return readGlyph(this.charstringbase, 0);
    }

    /**
     * A range.  There's probably a version of this class floating around
     * somewhere already in Java.
     */
    static class Range {

        private final int start;

        private final int len;

        public Range(final int start, final int len) {
            this.start = start;
            this.len = len;
        }

        public final int getStart() {
            return this.start;
        }

        public final int getLen() {
            return this.len;
        }

        public final int getEnd() {
            return this.start + this.len;
        }

        @Override
        public String toString() {
            return "Range: start: " + this.start + ", len: " + this.len;
        }
    }
}
