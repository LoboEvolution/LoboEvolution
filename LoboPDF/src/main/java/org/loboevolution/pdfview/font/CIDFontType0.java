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
import org.loboevolution.pdfview.font.cid.PDFCMap;
import org.loboevolution.pdfview.font.ttf.AdobeGlyphList;

import java.io.IOException;

/**
 * **************************************************************************
 * At the moment this is not fully supported to parse CID based fonts
 * As a hack we try to use a built in font as substitution and use a
 * toUnicode map to translate the characters if available.
 *
 * @version $Id: CIDFontType0.java,v 1.1 2011-08-03 15:48:56 bros Exp $
 * Author  Bernd Rosstauscher
 * @since 03.08.2011
 * **************************************************************************
 */
public class CIDFontType0 extends BuiltinFont {

    private PDFCMap glyphLookupMap;

    /**
     * **********************************************************************
     * Constructor
     *
     * @param baseFont   a {@link java.lang.String} object.
     * @param fontObj    a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param descriptor a {@link org.loboevolution.pdfview.font.PDFFontDescriptor} object.
     * @throws java.io.IOException if any.
     */
    public CIDFontType0(final String baseFont, final PDFObject fontObj,
                        final PDFFontDescriptor descriptor) throws IOException {
        super(baseFont, fontObj, descriptor);
    }

    /**
     * <p>parseToUnicodeMap.</p>
     *
     * @param fontObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public void parseToUnicodeMap(final PDFObject fontObj) throws IOException {
        final PDFObject toUnicode = fontObj.getDictRef("ToUnicode");
        if (toUnicode != null) {
            final PDFCMap cmap = PDFCMap.getCMap(toUnicode);
            this.glyphLookupMap = cmap;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a character from the first font in the descendant fonts array
     */
    @Override
    protected PDFGlyph getGlyph(char src, final String glyph) {
        String name = glyph;
        if (this.glyphLookupMap != null) {
            src = this.glyphLookupMap.map(src);
            //The preferred method of getting the glyph should be by name. 
            if (name == null && src != 160) {//unless it NBSP
                //so, try to find the name by the char
                name = AdobeGlyphList.getGlyphName(src);
            }
        }
        return super.getGlyph(src, name);
    }
}
