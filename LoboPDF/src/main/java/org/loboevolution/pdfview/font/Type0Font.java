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

import java.io.IOException;

/**
 * Type 0 fonts are composite fonts with a CMAP to map between
 * source character codes and destination fonts/codes
 * <p>
 * Author  Jonathan Kaplan
 */
public class Type0Font extends PDFFont {

    /**
     * The decendant fonts, indexed by font number from the CMAP
     */
    PDFFont[] fonts;

    /**
     * Creates a new instance of Type0Font
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @throws java.io.IOException if any.
     */
    public Type0Font(final String baseFont, final PDFObject fontObj,
                     final PDFFontDescriptor descriptor) throws IOException {
        super(baseFont, descriptor);

        final PDFObject[] descendantFonts = fontObj.getDictRef("DescendantFonts").getArray();

        this.fonts = new PDFFont[descendantFonts.length];

        for (int i = 0; i < descendantFonts.length; i++) {
            final PDFFont descFont = PDFFont.getFont(descendantFonts[i], null);
            if (descFont instanceof CIDFontType0) {
                ((CIDFontType0) descFont).parseToUnicodeMap(fontObj);
            }
            this.fonts[i] = descFont;
        }
    }

    /**
     * Get a descendant font of this font by fontId
     *
     * @param fontID a {@link java.lang.Integer} object.
     * @return a {@link org.loboevolution.pdfview.font.PDFFont} object.
     */
    public PDFFont getDescendantFont(final int fontID) {
        return this.fonts[fontID];
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a character from the first font in the descendant fonts array
     */
    @Override
    protected PDFGlyph getGlyph(final char src, final String name) {
        return (getDescendantFont(0).getGlyph(src, name));
    }
}
