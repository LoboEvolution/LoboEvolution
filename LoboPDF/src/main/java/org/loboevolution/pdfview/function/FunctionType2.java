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

package org.loboevolution.pdfview.function;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * A type 2 function is an exponential interpolation function, which maps
 * from one input value to n output values using a simple exponential
 * formula.
 */
public class FunctionType2 extends PDFFunction {
    /**
     * the function's value at zero for the n outputs
     */
    @Setter
    private float[] c0 = new float[]{0f};

    /**
     * the function's value at one for the n outputs
     */
    @Setter
    private float[] c1 = new float[]{1f};

    /**
     * the exponent
     */
    @Getter
    @Setter
    private float n;

    /**
     * Creates a new instance of FunctionType2
     */
    public FunctionType2() {
        super(TYPE_2);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Read the zeros, ones and exponent
     */
    @Override
    protected void parse(final PDFObject obj) throws IOException {
        // read the exponent (required)
        final PDFObject nObj = obj.getDictRef("N");
        if (nObj == null) {
            throw new PDFParseException("Exponent required for function type 2!");
        }
        setN(nObj.getFloatValue());

        // read the zeros array (optional)
        final PDFObject cZeroObj = obj.getDictRef("C0");
        if (cZeroObj != null) {
            final PDFObject[] cZeroAry = cZeroObj.getArray();
            final float[] cZero = new float[cZeroAry.length];
            for (int i = 0; i < cZeroAry.length; i++) {
                cZero[i] = cZeroAry[i].getFloatValue();
            }
            setC0(cZero);
        }

        // read the ones array (optional)
        final PDFObject cOneObj = obj.getDictRef("C1");
        if (cOneObj != null) {
            final PDFObject[] cOneAry = cOneObj.getArray();
            final float[] cOne = new float[cOneAry.length];
            for (int i = 0; i < cOneAry.length; i++) {
                cOne[i] = cOneAry[i].getFloatValue();
            }
            setC1(cOne);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Calculate the function value for the input.  For each output (j),
     * the function value is:
     * C0(j) + x^N * (C1(j) - C0(j))
     */
    @Override
    protected void doFunction(final float[] inputs, final int inputOffset,
                              final float[] outputs, final int outputOffset) {
        // read the input value
        final float input = inputs[inputOffset];

        // calculate the output values
        for (int i = 0; i < getNumOutputs(); i++) {
            outputs[i + outputOffset] = getC0(i) +
                    (float) (Math.pow(input, getN()) * (getC1(i) - getC0(i)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumOutputs() {
        // For Type 2 functions, the number of outputs is determined by the size of C0 (or C1).
        return c0.length;
    }

    /**
     * Get the values at zero
     *
     * @param index a {@link java.lang.Integer} object.
     * @return a float.
     */
    public float getC0(final int index) {
        return this.c0[index];
    }

    /**
     * Get the values at one
     *
     * @param index a {@link java.lang.Integer} object.
     * @return a float.
     */
    public float getC1(final int index) {
        return this.c1[index];
    }
}
