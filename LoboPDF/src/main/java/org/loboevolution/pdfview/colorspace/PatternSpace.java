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

package org.loboevolution.pdfview.colorspace;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.pattern.PDFPattern;

import java.io.IOException;
import java.util.Map;

/**
 * A PatternSpace fills with a pattern, the name of which is
 * specified in the call to getPaint().  This pattern is
 * read from the resources of the current page.  The pattern space
 * may also have a base color space which the pattern is defined in.
 */
public class PatternSpace extends PDFColorSpace {
    private PDFColorSpace base;

    /**
     * <p>Constructor for PatternSpace.</p>
     */
    public PatternSpace() {
        super(null);
    }

    /**
     * Create a pattern space with the given color space as a base
     *
     * @param base a {@link org.loboevolution.pdfview.colorspace.PDFColorSpace} object.
     */
    public PatternSpace(final PDFColorSpace base) {
        super(null);

        this.base = base;
    }

    /**
     * Get the base color space
     *
     * @return a {@link org.loboevolution.pdfview.colorspace.PDFColorSpace} object.
     */
    public PDFColorSpace getBase() {
        return this.base;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the number of components we want
     */
    @Override
    public int getNumComponents() {
        if (this.base == null) {
            return 0;
        } else {
            return this.base.getNumComponents();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the PDFPaint representing the color described by the
     * given color components
     */
    @Override
    public PDFPaint getPaint(final float[] components) {
        throw new IllegalArgumentException("Pattern spaces require a pattern " +
                "name!");
    }

    /**
     * Get the paint representing a pattern, optionally with the given
     * base paint.
     *
     * @param patternObj the pattern to render
     * @param components the components of the base paint
     * @param resources  a {@link java.util.Map} object.
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     * @throws java.io.IOException if any.
     */
    public PDFPaint getPaint(final PDFObject patternObj, final float[] components,
                             final Map resources)
            throws IOException {
        PDFPaint basePaint = null;

        if (getBase() != null) {
            basePaint = getBase().getPaint(components);
        }

        PDFPattern pattern = (PDFPattern) patternObj.getCache();
        if (pattern == null) {
            pattern = PDFPattern.getPattern(patternObj, resources);
            patternObj.setCache(pattern);
        }

        return pattern.getPaint(basePaint);
    }
}
