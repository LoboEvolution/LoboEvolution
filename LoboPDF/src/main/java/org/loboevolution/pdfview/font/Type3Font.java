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

import org.loboevolution.pdfview.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Type 3 Font, in which each glyph consists of a sequence of PDF
 * commands.
 * <p>
 * Author Mike Wessler
 */
public class Type3Font extends PDFFont {

    /**
     * resources for the character definitions
     */
    final HashMap<String, PDFObject> rsrc;
    /**
     * the character processes, mapped by name
     */
    Map charProcs;
    /**
     * bounding box for the font characters
     */
    Rectangle2D bbox;
    /**
     * affine transform for the font characters
     */
    AffineTransform at;
    /**
     * the widths
     */
    float[] widths;
    /**
     * the start code
     */
    int firstChar;
    /**
     * the end code
     */
    int lastChar;

    /**
     * Generate a Type 3 font.
     *
     * @param baseFont   the postscript name of this font
     * @param fontObj    a dictionary containing references to the character
     *                   definitions and font information
     * @param resources  a set of resources used by the character definitions
     * @param descriptor the descriptor for this font
     * @throws java.io.IOException if any.
     */
    public Type3Font(final String baseFont, final PDFObject fontObj,
                     final HashMap<String, PDFObject> resources, final PDFFontDescriptor descriptor) throws IOException {
        super(baseFont, descriptor);

        this.rsrc = new HashMap<>();

        if (resources != null) {
            this.rsrc.putAll(resources);
        }

        // get the transform matrix
        final PDFObject matrix = fontObj.getDictRef("FontMatrix");
        final float[] matrixAry = new float[6];
        for (int i = 0; i < 6; i++) {
            matrixAry[i] = matrix.getAt(i).getFloatValue();
        }
        this.at = new AffineTransform(matrixAry);

        // get the scale from the matrix
        final float scale = matrixAry[0] + matrixAry[2];

        // put all the resources in a Hash
        final PDFObject rsrcObj = fontObj.getDictRef("Resources");
        if (rsrcObj != null) {
            this.rsrc.putAll(rsrcObj.getDictionary());
        }

        // get the character processes, indexed by name
        this.charProcs = fontObj.getDictRef("CharProcs").getDictionary();

        // get the font bounding box
        bbox = PDFFile.parseNormalisedRectangle(fontObj.getDictRef("FontBBox"));
        if (bbox.isEmpty()) {
            bbox = null;
        }

        // get the widths
        final PDFObject[] widthArray = fontObj.getDictRef("Widths").getArray();
        this.widths = new float[widthArray.length];
        for (int i = 0; i < widthArray.length; i++) {
            this.widths[i] = widthArray[i].getFloatValue();
        }

        // get first and last chars
        this.firstChar = fontObj.getDictRef("FirstChar").getIntValue();
        this.lastChar = fontObj.getDictRef("LastChar").getIntValue();
    }

    /**
     * Get the first character code
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getFirstChar() {
        return this.firstChar;
    }

    /**
     * Get the last character code
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getLastChar() {
        return this.lastChar;
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
        if (name == null) {
            throw new IllegalArgumentException("Glyph name required for Type3 font!" +
                    "Source character: " + (int) src);
        }

        final PDFObject pageObj = (PDFObject) this.charProcs.get(name);
        if (pageObj == null) {
            // glyph not found.  Return an empty glyph...
            return new PDFGlyph(src, name, new GeneralPath(), new Point2D.Float(0, 0));
        }

        try {
            final PDFPage page = new PDFPage(this.bbox, 0);
            page.addXform(this.at);

            final PDFParser prc = new PDFParser(page, pageObj.getStream(), this.rsrc);
            prc.go(true);

            final float width = this.widths[src - this.firstChar];

            Point2D advance = new Point2D.Float(width, 0);
            advance = this.at.transform(advance, null);

            return new PDFGlyph(src, name, page, advance);
        } catch (final IOException ioe) {
            // help!
            PDFDebugger.debug("IOException in Type3 font: " + ioe);
            BaseWatchable.getErrorHandler().publishException(ioe);
            return null;
        }
    }
}
