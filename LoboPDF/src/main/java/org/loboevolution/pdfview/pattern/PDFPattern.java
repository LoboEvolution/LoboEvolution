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

package org.loboevolution.pdfview.pattern;


import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Map;

/**
 * The abstract superclass of all PDF Pattern types
 */
public abstract class PDFPattern {

    /**
     * the pattern type (1 or 2)
     */
    private final int type;

    /**
     * the matrix to transform from pattern space to PDF space
     */
    private AffineTransform xform;

    /**
     * Creates a new instance of PDFPattern
     *
     * @param type a {@link java.lang.Integer} object.
     */
    protected PDFPattern(final int type) {
        this.type = type;
    }

    /**
     * Read a pattern from the given pattern stream
     *
     * @param patternObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param resources  a {@link java.util.Map} object.
     * @return a {@link org.loboevolution.pdfview.pattern.PDFPattern} object.
     * @throws java.io.IOException if any.
     */
    public static PDFPattern getPattern(final PDFObject patternObj, final Map resources)
            throws IOException {
        // see if the pattern is already cached
        PDFPattern pattern = (PDFPattern) patternObj.getCache();
        if (pattern != null) {
            return pattern;
        }

        // get the pattern type
        final int type = patternObj.getDictRef("PatternType").getIntValue();

        // read the pattern transform matrix
        final PDFObject matrix = patternObj.getDictRef("Matrix");
        AffineTransform xform = null;
        if (matrix == null) {
            xform = new AffineTransform();
        } else {
            final float[] elts = new float[6];
            for (int i = 0; i < elts.length; i++) {
                elts[i] = matrix.getAt(i).getFloatValue();
            }

            xform = new AffineTransform(elts);
        }

        switch (type) {
            case 1:
                pattern = new PatternType1();
                break;
            case 2:
                pattern = new PatternType2();
                break;
            default:
                throw new PDFParseException("Unknown pattern type " + type);
        }

        // set the transform
        pattern.setTransform(xform);

        // parse the pattern-specific data
        pattern.parse(patternObj, resources);

        // set the cache
        patternObj.setCache(pattern);

        return pattern;
    }

    /**
     * Get the type of this pattern
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getPatternType() {
        return this.type;
    }

    /**
     * Get the transform associated with this pattern
     *
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    public AffineTransform getTransform() {
        return this.xform;
    }

    /**
     * Set the transform associated with this pattern
     *
     * @param xform a {@link java.awt.geom.AffineTransform} object.
     */
    protected void setTransform(final AffineTransform xform) {
        this.xform = xform;
    }

    /**
     * Parse the pattern-specific information from the pdf object
     *
     * @param patternObj the pdfobject with data for this pattern
     * @param resources  a {@link java.util.Map} object.
     * @throws java.io.IOException if any.
     */
    protected abstract void parse(PDFObject patternObj, Map resources)
            throws IOException;

    /**
     * Returns paint that represents the selected pattern
     *
     * @param basePaint the background paint color, or null for none
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public abstract PDFPaint getPaint(PDFPaint basePaint);
}
