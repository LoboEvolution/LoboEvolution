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

package org.loboevolution.pdfview.colorspace;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.function.FunctionType0;

import java.awt.color.ColorSpace;
import java.io.IOException;

/**
 * A ColorSpace for calibrated RGB
 * <p>
 * Author Mike Wessler
 */
public class CalRGBColor extends ColorSpace {
    static final ColorSpace rgbCS = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    private static final float[] vonKriesM = {0.40024f, -0.22630f, 0.00000f,
            0.70760f, 1.16532f, 0.00000f,
            -0.08081f, 0.04570f, 0.91822f};
    private static final float[] vonKriesMinv = {1.859936f, 0.361191f, 0.000000f,
            -1.129382f, 0.638812f, 0.000000f,
            0.219897f, -0.000006f, 1.089064f};
    private static final float[] xyzToSRGB = {3.24071f, -0.969258f, 0.0556352f,
            -1.53726f, 1.87599f, -0.203996f,
            -0.498571f, 0.0415557f, 1.05707f};
    private static final float[] xyzToRGB = {2.04148f, -0.969258f, 0.0134455f,
            -0.564977f, 1.87599f, -0.118373f,
            -0.344713f, 0.0415557f, 1.01527f};
    static ColorSpace cieCS = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
    final float[] white = {1f, 1f, 1f};
    final float[] black = {0, 0, 0};
    final float[] matrix = {1f, 0, 0, 0, 1f, 0, 0, 0, 1f};
    final float[] gamma = {1f, 1f, 1f};
    float[] scale;
    float[] max;

    /**
     * Create a new Calibrated RGB color space object, given the
     * description in a PDF dictionary.
     *
     * @param obj a dictionary that contains an array of 3 Numbers
     *            for "WhitePoint" and "BlackPoint", a Number for "Gamma", and
     *            an array of 9 Numbers for "Matrix".
     * @throws java.io.IOException if any.
     */
    public CalRGBColor(final PDFObject obj) throws IOException {
        // obj is a dictionary that has the following parts:
        // WhitePoint [a b c]
        // BlackPoint [a b c]
        // Gamma a
        super(CS_sRGB, 3);

        // find out what what is according to the CIE color space
        // note that this is not reflexive (i.e. passing this value
        // into toRGB does not get you (1.0, 1.0, 1.0) back)
        // cieWhite = cieCS.fromRGB(new float[] { 1.0f, 1.0f, 1.0f } );

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
        ary = obj.getDictRef("Gamma");
        if (ary != null) {
            for (int i = 0; i < 3; i++) {
                this.gamma[i] = ary.getAt(i).getFloatValue();
            }
        }
        ary = obj.getDictRef("Matrix");
        if (ary != null) {
            for (int i = 0; i < 9; i++) {
                this.matrix[i] = ary.getAt(i).getFloatValue();
            }
        }

        // create a scale matrix relative to the 50 CIE color space.
        // see http://www.brucelindbloom.com/Eqn_RGB_XYZ_Matrix.html
        // we use the Von Kries cone response domain
        final float[] cieWhite = rgbCS.toCIEXYZ(new float[]{1f, 1f, 1f});

        final float[] sourceWhite = matrixMult(this.white, vonKriesM, 3);
        final float[] destWhite = matrixMult(cieWhite, vonKriesM, 3);

        this.scale = new float[]{destWhite[0] / sourceWhite[0], 0, 0,
                0, destWhite[1] / sourceWhite[1], 0,
                0, 0, destWhite[2] / sourceWhite[2]};
        this.scale = matrixMult(vonKriesM, this.scale, 3);
        this.scale = matrixMult(this.scale, vonKriesMinv, 3);

        this.max = matrixMult(this.white, this.scale, 3);
        this.max = ciexyzToSRGB(this.max);
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the number of components (3)
     */
    @Override
    public int getNumComponents() {
        return 3;
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from Calibrated RGB to standard RGB
     */
    @Override
    public float[] toRGB(final float[] comp) {
        if (comp.length == 3) {
            // compute r', g' and b' by raising the given values to the
            // correct gamma
            final float a = (float) Math.pow(comp[0], this.gamma[0]);
            final float b = (float) Math.pow(comp[1], this.gamma[1]);
            final float c = (float) Math.pow(comp[2], this.gamma[2]);

            // now multiply by the matrix to get X, Y and Z values
            float[] xyz = new float[]{
                    this.matrix[0] * a + this.matrix[3] * b + this.matrix[6] * c,
                    this.matrix[1] * a + this.matrix[4] * b + this.matrix[7] * c,
                    this.matrix[2] * a + this.matrix[5] * b + this.matrix[8] * c};

            // now scale the xyz values
            xyz = matrixMult(xyz, this.scale, 3);

            // convert to RGB
            final float[] rgb = ciexyzToSRGB(xyz);

            // cheat -- scale based on max
            for (int i = 0; i < rgb.length; i++) {
                rgb[i] = FunctionType0.interpolate(rgb[i], 0, this.max[i], 0, 1);

                // sometimes we get off a little bit due to precision loss
                if (rgb[i] > 1.0) {
                    rgb[i] = 1.0f;
                }
            }

            return rgb;
        } else {
            return this.black;
        }
    }

    /**
     * Convert from CIEXYZ, with scale and gamma calculated to sRGB
     */
    private float[] ciexyzToSRGB(final float[] xyz) {
        final float[] rgb = matrixMult(xyz, xyzToSRGB, 3);

        for (int i = 0; i < rgb.length; i++) {
            if (rgb[i] < 0.0) {
                rgb[i] = 0f;
            } else if (rgb[i] > 1.0) {
                rgb[i] = 1f;
            }

            if (rgb[i] < 0.003928) {
                rgb[i] = (float) (rgb[i] * 12.92);
            } else {
                rgb[i] = (float) ((Math.pow(rgb[i], 1.0 / 2.4) * 1.055) - 0.055);
            }
        }

        //float[] rgb = cieCS.toRGB(xyz);
        return rgb;
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from RGB to Calibrated RGB.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromRGB(final float[] rgbvalue) {
        return new float[3];
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from CIEXYZ to Calibrated RGB.  NOT IMPLEMENTED
     */
    @Override
    public float[] fromCIEXYZ(final float[] colorvalue) {
        return new float[3];
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the type of this color space (TYPE_RGB)
     */
    @Override
    public int getType() {
        return TYPE_RGB;
    }

    /**
     * {@inheritDoc}
     * <p>
     * convert from Calibrated RGB to CIEXYZ.  NOT IMPLEMENTED
     */
    @Override
    public float[] toCIEXYZ(final float[] colorvalue) {
        return new float[3];
    }

    /**
     * Slowly multiply two matrices
     *
     * @param a   the first matrix
     * @param b   the second matrix
     * @param len the size of each row.  All matrix lengths must be a
     *            multiple of len.
     */
    float[] matrixMult(final float[] a, final float[] b, final int len) {
        final int rows = a.length / len;
        final int cols = b.length / len;

        final float[] out = new float[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                for (int j = 0; j < len; j++) {
                    out[(i * cols) + k] += a[(i * len) + j] * b[(j * cols) + k];
                }
            }
        }

        return out;
    }
}
