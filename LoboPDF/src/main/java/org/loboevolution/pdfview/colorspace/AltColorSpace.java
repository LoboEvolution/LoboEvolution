/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.loboevolution.pdfview.function.PDFFunction;

import java.awt.color.ColorSpace;

/**
 * **************************************************************************
 * Color Space implementation for handling the PDF AlternateColorSpace.
 * A PDF function is applied to colorvalues before converting.
 * <p>
 * Author  Katja Sondermann
 *
 * @since 06.01.2011
 * **************************************************************************
 */
public class AltColorSpace extends ColorSpace {

    private final PDFFunction fkt;
    private final ColorSpace origCs;

    /**
     * Create a new CMYKColorSpace Instance.
     *
     * @param fkt    a {@link org.loboevolution.pdfview.function.PDFFunction} object.
     * @param origCs a {@link java.awt.color.ColorSpace} object.
     */
    public AltColorSpace(final PDFFunction fkt, final ColorSpace origCs) {
        super(origCs.getType(), fkt.getNumInputs());
        this.fkt = fkt;
        this.origCs = origCs;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts from CIEXYZ.
     *
     * @see java.awt.color.ColorSpace#fromCIEXYZ(float[])
     */
    @Override
    public float[] fromCIEXYZ(final float[] p_colorvalue) {
        return this.origCs.fromCIEXYZ(this.fkt.calculate(p_colorvalue));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts a given RGB.
     *
     * @see java.awt.color.ColorSpace#fromRGB(float[])
     */
    @Override
    public float[] fromRGB(final float[] p_rgbvalue) {
        return this.origCs.fromCIEXYZ(this.fkt.calculate(p_rgbvalue));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts to CIEXYZ.
     *
     * @see java.awt.color.ColorSpace#toCIEXYZ(float[])
     */
    @Override
    public float[] toCIEXYZ(final float[] p_colorvalue) {
        final float[] colorvalue = this.fkt.calculate(p_colorvalue);
        return this.origCs.toCIEXYZ(colorvalue);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts to RGB.
     *
     * @see java.awt.color.ColorSpace#toRGB(float[])
     */
    @Override
    public float[] toRGB(final float[] p_colorvalue) {
        final float[] colorvalue = this.fkt.calculate(p_colorvalue);
        return this.origCs.toRGB(colorvalue);
    }
}
