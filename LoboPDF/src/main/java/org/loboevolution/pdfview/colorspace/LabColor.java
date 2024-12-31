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

import org.loboevolution.pdfview.PDFObject;

import java.awt.color.ColorSpace;
import java.io.IOException;

/**
 * A ColorSpace for Lab color
 * <p>
 * Author Mike Wessler
 */
public class LabColor extends ColorSpace {

    static final ColorSpace cie = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    final float[] white = {1f, 1f, 1f};
    final float[] black = {0, 0, 0};
    final float[] range = {-100f, 100f, -100f, 100f};

    /**
     * Create a new Lab color space object, given the description in
     * a PDF dictionary.
     *
     * @param obj a dictionary that contains an Array of 3 Numbers for
     *            "WhitePoint" and "BlackPoint", and an array of 4 Numbers for
     *            "Range".
     * @throws java.io.IOException if any.
     */
    public LabColor(final PDFObject obj) throws IOException {
        // obj is a dictionary that has the following parts:
        // WhitePoint [a b c]
        // BlackPoint [a b c]
        // Gamma a
        super(TYPE_Lab, 3);
        PDFObject ary = obj.getDictRef("WhitePoint");
        if (ary != null) {
            for (int i = 0; i < 3; i++) {
                this.white[i] = ary.getAt(i).getFloatValue();
            }
        }
        ary = obj.getDictRef("BlackPoint");
        if (ary != null) {
            for (int i = 0; i < 3; i++) {
                this.black[i] = ary.getAt(i).getFloatValue();
            }
        }
        ary = obj.getDictRef("Range");
        if (ary != null) {
            for (int i = 0; i < 4; i++) {
                this.range[i] = ary.getAt(i).getFloatValue();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the number of components for this color space (3)
     */
    @Override
    public int getNumComponents() {
        return 3;
    }

    /**
     * Stage 2 of the conversion algorithm.  Pulled out because
     * it gets invoked for each component
     *
     * @param s1 a float.
     * @return a float.
     */
    public final float stage2(final float s1) {
        return (s1 >= 6f / 29f) ? s1 * s1 * s1 : 108f / 841f * (s1 - 4f / 29f);
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from Lab to RGB
     */
    @Override
    public float[] toRGB(final float[] comp) {
        if (comp.length == 3) {
            final float l = (comp[0] + 16) / 116 + comp[1] / 500;
            final float m = (comp[0] + 16) / 116;
            final float n = (comp[0] + 16) / 116 - comp[2] / 200;
            final float[] xyz = {
                    this.white[0] * stage2(l),
                    this.white[0] * stage2(m),
                    this.white[0] * stage2(n)};
            final float[] rgb = cie.fromCIEXYZ(xyz);
            return rgb;
        } else {
            return this.black;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from RGB to Lab.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromRGB(final float[] rgbvalue) {
        return new float[3];
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from CIEXYZ to Lab.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromCIEXYZ(final float[] colorvalue) {
        return new float[3];
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the type of this colorspace (TYPE_Lab)
     */
    @Override
    public int getType() {
        return TYPE_Lab;
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from Lab to CIEXYZ.   NOT IMPLEMENTED
     */
    @Override
    public float[] toCIEXYZ(final float[] colorvalue) {
        return new float[3];
    }

}
