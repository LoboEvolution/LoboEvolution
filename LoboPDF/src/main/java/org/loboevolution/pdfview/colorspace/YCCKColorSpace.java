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

import java.awt.color.ColorSpace;

/**
 * A ColorSpace for the YCCK color space.  This color space converts to CMYK and then
 * uses an existing CMYK color space to convert from CMYK to RGB.  This allows embedded
 * CMYK color profiles to be used with YCCK images.  If no CMYK color space is
 * provided then by default it uses a CMYKColorSpace. Only toRGB is supported.
 * <p>
 * Author Ben Day
 */
public class YCCKColorSpace extends ColorSpace {

    private final ColorSpace cmykColorSpace;

    /**
     * create a new YCCK color space:  a ColorSpace with 4 components
     *
     * @param existingCmykColorSpace a {@link java.awt.color.ColorSpace} object.
     */
    public YCCKColorSpace(final ColorSpace existingCmykColorSpace) {
        super(TYPE_4CLR, 4);
        cmykColorSpace = existingCmykColorSpace;
    }

    /**
     * <p>Constructor for YCCKColorSpace.</p>
     */
    public YCCKColorSpace() {
        this(new CMYKColorSpace());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Convert from CIEXYZ to RGB.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromCIEXYZ(final float[] colorvalue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     * <p>
     * Convert from RGB to YCCK.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromRGB(final float[] rgbvalue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     * <p>
     * the number of components
     */
    @Override
    public int getNumComponents() {
        return 4;
    }

    /**
     * {@inheritDoc}
     * <p>
     * the name of this color space
     */
    @Override
    public String getName(final int idx) {
        return "YCCK";
    }

    /**
     * {@inheritDoc}
     * <p>
     * the type of this color space (TYPE_4CLR)
     */
    @Override
    public int getType() {
        return TYPE_4CLR;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Convert from YCCK to CIEXYZ.  NOT IMPLEMENTED
     */
    @Override
    public float[] toCIEXYZ(final float[] colorvalue) {
        return cmykColorSpace.toCIEXYZ(toCmyk(colorvalue));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Convert from YCCK to RGB.
     */
    @Override
    public float[] toRGB(final float[] colorvalue) {
        return cmykColorSpace.toRGB(toCmyk(colorvalue));
    }

    private float[] toCmyk(final float[] colorvalue) {
        final float y = colorvalue[0];
        final float cb = colorvalue[1];
        final float cr = colorvalue[2];
        final float k = colorvalue[3];
        final float[] cmyk = new float[4];
        float v;
        v = (float) (1.0 - (y + 1.402 * (cr - 0.5)));
        cmyk[0] = v < 0.0f ? 0.0f : (Math.min(v, 1.0f));
        v = (float) (1.0 - (y - 0.34414 * (cb - 0.5) - 0.71414 * (cr - 0.5)));
        cmyk[1] = v < 0.0f ? 0.0f : (Math.min(v, 1.0f));
        v = (float) (1.0 - (y + 1.772 * (cb - 0.5)));
        cmyk[2] = v < 0.0f ? 0.0f : (Math.min(v, 1.0f));
        cmyk[3] = k;
        return cmyk;
    }
}
