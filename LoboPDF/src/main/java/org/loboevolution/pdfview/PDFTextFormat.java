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

import lombok.Getter;
import org.loboevolution.pdfview.font.PDFFont;
import org.loboevolution.pdfview.font.PDFGlyph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * a class encapsulating the text state
 * Author Mike Wessler
 */
public class PDFTextFormat implements Cloneable {
    /**
     * current matrix transform
     */
    private final AffineTransform transform;
    /**
     * build text rep of word
     */
    private final StringBuilder word = new StringBuilder();
    /**
     * location of the end of the previous hunk of text
     */
    private final Point2D.Float prevEnd;
    /**
     * character spacing
     */
    private float tc;
    /**
     * word spacing
     */
    private float tw;
    /**
     * horizontal scaling
     */
    private float th = 1;
    /**
     * leading
     */
    private float tl = 0;
    /**
     * rise amount
     */
    private float tr;
    /**
     * text mode
     */
    private int tm = PDFShapeCmd.FILL;
    /**
     * matrix transform at start of line
     */
    private AffineTransform line;
    /**
     * font
     */
    @Getter
    private PDFFont font;
    /**
     * font size
     */
    @Getter
    private float fontSize = 1;
    /**
     * are we between BT and ET?
     */
    private boolean inuse = false;

    /**
     * create a new PDFTextFormat, with initial values
     */
    public PDFTextFormat() {
        this.transform = new AffineTransform();
        this.line = new AffineTransform();
        this.prevEnd = new Point2D.Float(-100, -100);
        this.tc = this.tw = this.tr = 0;
    }

    /**
     * reset the PDFTextFormat for a new run
     */
    public void reset() {
        this.transform.setToIdentity();
        this.line.setToIdentity();
        this.inuse = true;
        this.word.setLength(0);
    }

    /**
     * end a span of text
     */
    public void end() {
        this.inuse = false;
    }

    /**
     * get the char spacing
     *
     * @return a float.
     */
    public float getCharSpacing() {
        return this.tc;
    }

    /**
     * set the character spacing
     *
     * @param spc a float.
     */
    public void setCharSpacing(final float spc) {
        this.tc = spc;
    }

    /**
     * get the word spacing
     *
     * @return a float.
     */
    public float getWordSpacing() {
        return this.tw;
    }

    /**
     * set the word spacing
     *
     * @param spc a float.
     */
    public void setWordSpacing(final float spc) {
        this.tw = spc;
    }

    /**
     * Get the horizontal scale
     *
     * @return the horizontal scale, in percent
     */
    public float getHorizontalScale() {
        return this.th * 100;
    }

    /**
     * set the horizontal scale.
     *
     * @param scl the horizontal scale, in percent (100=normal)
     */
    public void setHorizontalScale(final float scl) {
        this.th = scl / 100;
    }

    /**
     * get the leading
     *
     * @return a float.
     */
    public float getLeading() {
        return this.tl;
    }

    /**
     * set the leading
     *
     * @param spc a float.
     */
    public void setLeading(final float spc) {
        this.tl = spc;
    }

    /**
     * set the font and size
     *
     * @param f    a {@link org.loboevolution.pdfview.font.PDFFont} object.
     * @param size a float.
     */
    public void setFont(final PDFFont f, final float size) {
        this.font = f;
        this.fontSize = size;
    }

    /**
     * Get the mode of the text
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getMode() {
        return this.tm;
    }

    /**
     * set the mode of the text. The correspondence of m to mode is
     * show in the following table. m is a value from 0-7 in binary:
     * 000 Fill
     * 001 Stroke
     * 010 Fill + Stroke
     * 011 Nothing
     * 100 Fill + Clip
     * 101 Stroke + Clip
     * 110 Fill + Stroke + Clip
     * 111 Clip
     * Therefore: Fill corresponds to the low bit being 0; Clip
     * corresponds to the hight bit being 1; and Stroke corresponds
     * to the middle xor low bit being 1.
     *
     * @param m a {@link java.lang.Integer} object.
     */
    public void setMode(final int m) {
        int mode = 0;
        if ((m & 0x1) == 0) {
            mode |= PDFShapeCmd.FILL;
        }
        if ((m & 0x4) != 0) {
            mode |= PDFShapeCmd.CLIP;
        }
        if (((m & 0x1) ^ ((m & 0x2) >> 1)) != 0) {
            mode |= PDFShapeCmd.STROKE;
        }
        this.tm = mode;
    }

    /**
     * Set the mode from another text format mode
     *
     * @param mode the text render mode using the
     *             codes from PDFShapeCmd and not the wacky PDF codes
     */
    public void setTextFormatMode(final int mode) {
        this.tm = mode;
    }

    /**
     * Get the rise
     *
     * @return a float.
     */
    public float getRise() {
        return this.tr;
    }

    /**
     * set the rise
     *
     * @param spc a float.
     */
    public void setRise(final float spc) {
        this.tr = spc;
    }

    /**
     * perform a carriage return
     */
    public void carriageReturn() {
        carriageReturn(0, -this.tl);
    }

    /**
     * perform a carriage return by translating by x and y. The next
     * carriage return will be relative to the new location.
     *
     * @param x a float.
     * @param y a float.
     */
    public void carriageReturn(final float x, final float y) {
        this.line.concatenate(AffineTransform.getTranslateInstance(x, y));
        this.transform.setTransform(this.line);
    }

    /**
     * set the transform matrix directly
     *
     * @param matrix an array of {@link float} objects.
     */
    public void setMatrix(final float[] matrix) {
        this.line = new AffineTransform(matrix);
        this.transform.setTransform(this.line);
    }

    /**
     * add some text to the page.
     *
     * @param cmds             the PDFPage to add the commands to
     * @param text             the text to add
     * @param autoAdjustStroke a boolean.
     */
    public void doText(final PDFPage cmds, final String text, final boolean autoAdjustStroke) {
        final Point2D.Float zero = new Point2D.Float();
        final AffineTransform scale = new AffineTransform(this.fontSize * this.th, 0, /* 0 */
                0, this.fontSize, /* 0 */
                0, this.tr /* 1 */);
        final AffineTransform at = new AffineTransform();
        final List<PDFGlyph> l = this.font.getGlyphs(text);
        if (PDFDebugger.SHOW_TEXT_ANCHOR) {
            if (PDFDebugger.DEBUG_TEXT) {
                PDFDebugger.debug("POINT count: " + l.size());
            }
        }
        for (final PDFGlyph glyph : l) {
            at.setTransform(this.transform);
            at.concatenate(scale);
            if (PDFDebugger.SHOW_TEXT_REGIONS) {
                GeneralPath path = new GeneralPath();
                path.moveTo(0, 0);
                path.lineTo(1, 0);
                path.lineTo(1, 1);
                path.lineTo(0, 1);
                path.lineTo(0, 0);
                path.closePath();
                path = (GeneralPath) path.createTransformedShape(at);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("BOX " + path.getBounds());
                }
                final PDFCmd lastColor = cmds.findLastCommand(PDFFillPaintCmd.class);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("BOX " + lastColor);
                }
                cmds.addFillPaint(PDFPaint.getColorPaint(new Color(160, 160, 255)));
                cmds.addPath(path, PDFShapeCmd.FILL, autoAdjustStroke);
                if (lastColor != null) {
                    cmds.addCommand(lastColor);
                }
            }
            Point2D advance = glyph.getAdvance();
            if (!PDFDebugger.DISABLE_TEXT) {
                advance = glyph.addCommands(cmds, at, this.tm);
            }
            double advanceX = (advance.getX() * this.fontSize) + this.tc;
            if (glyph.getChar() == ' ') {
                advanceX += this.tw;
            }
            advanceX *= this.th;
            if (PDFDebugger.SHOW_TEXT_ANCHOR) {
                final AffineTransform at2 = new AffineTransform();
                at2.setTransform(this.transform);
                GeneralPath path = new GeneralPath();
                path.moveTo(0, 0);
                path.lineTo(6, 0);
                path.lineTo(6, 6);
                path.lineTo(0, 6);
                path.lineTo(0, 0);
                path.closePath();
                path = (GeneralPath) path.createTransformedShape(at2);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("POINT " + advance);
                }
                final PDFCmd lastColor = cmds.findLastCommand(PDFFillPaintCmd.class);
                cmds.addFillPaint(PDFPaint.getColorPaint(new Color(255, 0, 0)));
                cmds.addPath(path, PDFShapeCmd.FILL, autoAdjustStroke);
                if (lastColor != null) {
                    cmds.addCommand(lastColor);
                }
            }
            this.transform.translate(advanceX, advance.getY());
        }
        this.transform.transform(zero, this.prevEnd);
    }

    /**
     * add some text to the page.
     *
     * @param cmds             the PDFPage to add the commands to
     * @param ary              an array of Strings and Doubles, where the Strings
     *                         represent text to be added, and the Doubles represent kerning
     *                         amounts.
     * @param autoAdjustStroke a boolean.
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public void doText(final PDFPage cmds, final Object[] ary, final boolean autoAdjustStroke) throws PDFParseException {
        for (final Object o : ary) {
            if (o instanceof String) {
                doText(cmds, (String) o, autoAdjustStroke);
            } else if (o instanceof Double) {
                final float val = ((Double) o).floatValue() / 1000f;
                this.transform.translate(-val * this.fontSize * this.th, 0);
            } else {
                throw new PDFParseException("Bad element in TJ array");
            }
        }
    }

    /**
     * finish any unfinished words. TODO: write this!
     */
    public void flush() {
        // TODO: finish any unfinished words
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clone the text format
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        final PDFTextFormat newFormat = new PDFTextFormat();
        newFormat.setCharSpacing(getCharSpacing());
        newFormat.setWordSpacing(getWordSpacing());
        newFormat.setHorizontalScale(getHorizontalScale());
        newFormat.setLeading(getLeading());
        newFormat.setTextFormatMode(getMode());
        newFormat.setRise(getRise());
        newFormat.setFont(getFont(), getFontSize());
        return newFormat;
    }
}
