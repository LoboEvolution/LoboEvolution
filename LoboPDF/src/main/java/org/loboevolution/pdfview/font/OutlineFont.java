/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.pdfview.PDFObject;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Supports width operations for Type1, Type1C, TrueType and Type3 fonts
 */
public abstract class OutlineFont extends PDFFont {

    /**
     * the first character code
     */
    private int firstChar = -1;
    /**
     * the last character code
     */
    private int lastChar = -1;
    /**
     * the widths for each character code
     */
    private float[] widths;

    /**
     * Creates a new instance of OutlineFont
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @throws java.io.IOException if any.
     */
    public OutlineFont(final String baseFont, final PDFObject fontObj,
                       final PDFFontDescriptor descriptor) throws IOException {
        super(baseFont, descriptor);

        final PDFObject firstCharObj = fontObj.getDictRef("FirstChar");
        final PDFObject lastCharObj = fontObj.getDictRef("LastChar");
        final PDFObject widthArrayObj = fontObj.getDictRef("Widths");

        if (firstCharObj != null) {
            this.firstChar = firstCharObj.getIntValue();
        }
        if (lastCharObj != null) {
            this.lastChar = lastCharObj.getIntValue();
        }

        if (widthArrayObj != null) {
            final PDFObject[] widthArray = widthArrayObj.getArray();

            this.widths = new float[widthArray.length];

            for (int i = 0; i < widthArray.length; i++) {
                this.widths[i] = widthArray[i].getFloatValue() / getDefaultWidth();
            }
        }
    }

    /**
     * Get the first character code
     *
     * @return a int.
     */
    public int getFirstChar() {
        return this.firstChar;
    }

    /**
     * Get the last character code
     *
     * @return a int.
     */
    public int getLastChar() {
        return this.lastChar;
    }

    /**
     * Get the default width in text space
     *
     * @return a int.
     */
    public int getDefaultWidth() {
        return 1000;
    }

    /**
     * Get the number of characters
     *
     * @return a int.
     */
    public int getCharCount() {
        return (getLastChar() - getFirstChar()) + 1;
    }

    /**
     * Get the width of a given character
     *
     * @param code a char.
     * @param name a {@link java.lang.String} object.
     * @return a float.
     */
    public float getWidth(final char code, final String name) {
        final int idx = (code & 0xff) - getFirstChar();

        // make sure we're in range
        if (idx < 0 || this.widths == null || idx >= this.widths.length) {
            // try to get the missing width from the font descriptor
            if (getDescriptor() != null) {
                return getDescriptor().getMissingWidth() / (float) getDefaultWidth();
            } else {
                return 0;
            }
        }

        return this.widths[idx];
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the glyph for a given character code and name
     * <p>
     * The preferred method of getting the glyph should be by name.  If the
     * name is null or not valid, then the character code should be used.
     * If the both the code and the name are invalid, the undefined glyph
     * should be returned.
     * <p>
     * Note this method must *always* return a glyph.
     */
    @Override
    protected PDFGlyph getGlyph(final char src, final String name) {
        GeneralPath outline = null;
        final float width = getWidth(src, name);

        // first try by name
        if (name != null) {
            outline = getOutline(name, width);
        }

        // now try by character code (guaranteed to return)
        if (outline == null) {
            outline = getOutline(src, width);
        }

        // calculate the advance
        final Point2D.Float advance = new Point2D.Float(width, 0);
        return new PDFGlyph(src, name, outline, advance);
    }

    /**
     * Get a glyph outline by name
     *
     * @param name  the name of the desired glyph
     * @param width a float.
     * @return the glyph outline, or null if unavailable
     */
    protected abstract GeneralPath getOutline(final String name, final float width);

    /**
     * Get a glyph outline by character code
     * <p>
     * Note this method must always return an outline
     *
     * @param src   the character code of the desired glyph
     * @param width a float.
     * @return the glyph outline
     */
    protected abstract GeneralPath getOutline(char src, final float width);
}
