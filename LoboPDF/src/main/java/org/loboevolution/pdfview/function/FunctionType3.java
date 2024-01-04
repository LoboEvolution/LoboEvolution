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
package org.loboevolution.pdfview.function;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * 3.9.3 - A stitching function define a <i>stitching</i> of the subdomains of
 * several 1-input functions to produce a single new 1-input function.
 * Since the resulting stitching function is a 1-input function, the
 * domain is given by a two-element array, [ <b>Domain</b>0 <b>Domain</b>1 ].
 * <p>
 * Example 4.25
 * 5 0 obj                          % Shading dictionary
 * ShadingType 3
 * ColorSpace DeviceCMYK
 * Coords [ 0.0 0.0 0.096 0.0 0.0 1.0 00]% Concentric circles
 * Function 10 0 R
 * Extend [ true true ]
 * <p>
 * endobj
 * <p>
 * 10 0 obj                         % Color function
 * FunctionType 3
 * Domain [ 0.0 1.0 ]
 * Functions [ 11 0 R 12 0 R ]
 * Bounds [ 0.708 ]
 * Encode [ 1.0 0.0 0.0 1.0 ]
 * <p>
 * endobj
 * <p>
 * 11 0 obj                         % First subfunction
 * FunctionType 2
 * Domain [ 0.0 1.0 ]
 * C0 [ 0.929 0.357 1.000 0.298 ]
 * C1 [ 0.631 0.278 1.000 0.027 ]
 * N 1.048
 * <p>
 * endobj
 * <p>
 * 12 0 obj                         % Second subfunction
 * FunctionType 2
 * Domain [ 0.0 1.0 ]
 * C0 [ 0.929 0.357 1.000 0.298 ]
 * C1 [ 0.941 0.400 1.000 0.102 ]
 * N 1.374
 * <p>
 * endobj
 */
public class FunctionType3 extends PDFFunction {

    private PDFFunction[] functions;
    private float[] bounds;
    private float[] encode;

    /**
     * Creates a new instance of FunctionType3
     */
    protected FunctionType3() {
        super(TYPE_3);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Read the function information from a PDF Object.</p>
     * Required entries ( Table 3.38)  (3200-1:2008:7.10.4, table: 41)
     * are:
     *
     * <b>Functions</b> <i>array</i> (Required) An array of k 1-input functions making up
     * the stitching function. The output dimensionality of all functions
     * must be the same, and compatible with the value of <b>Range</b>
     * if <b>Range</b> is present.
     *
     * <b>Domain</b><i>array</i> (Required) A 2 element array where
     * <b>Domain</b>0 is less than <b>Domain</b>1. This is read by the
     * <code>PDFFunction</code> superclass.
     *
     * <b>Bounds</b> <i>array</i> (Required) An array of k-1 numbers that,
     * in combination with <b>Domain</b>, define the intervals to which each
     * function from the <b>Functions</b> array applies. <b>Bounds</b> elements
     * must be in order of increasing value, and each value must be within
     * the domain defined by <b>Domain</b>.
     *
     * <b>Encode</b> <i>array</i> (Required) An array of 2 * k numbers that,
     * taken in pairs, map each subset of the domain defined by <b>Domain</b>
     * and the <b>Bounds</b> array to the domain of the corresponding function.
     */
    @Override
    protected void parse(final PDFObject obj) throws IOException {
        // read the Functions array (required)
        final PDFObject functionsObj = obj.getDictRef("Functions");
        if (functionsObj == null) {
            throw new PDFParseException("Functions required for function type 3!");
        }
        final PDFObject[] functionsAry = functionsObj.getArray();
        functions = new PDFFunction[functionsAry.length];
        for (int i = 0; i < functionsAry.length; i++) {
            functions[i] = PDFFunction.getFunction(functionsAry[i]);
        }

        // read the Bounds array (required)
        final PDFObject boundsObj = obj.getDictRef("Bounds");
        if (boundsObj == null) {
            throw new PDFParseException("Bounds required for function type 3!");
        }
        final PDFObject[] boundsAry = boundsObj.getArray();
        bounds = new float[boundsAry.length + 2];
        if (bounds.length - 2 != functions.length - 1) {
            throw new PDFParseException("Bounds array must be of length " + (functions.length - 1));
        }

        for (int i = 0; i < boundsAry.length; i++) {
            bounds[i + 1] = boundsAry[i].getFloatValue();
        }
        bounds[0] = getDomain(0);
        bounds[bounds.length - 1] = getDomain(1);

        // read the encode array (required)
        final PDFObject encodeObj = obj.getDictRef("Encode");
        if (encodeObj == null) {
            throw new PDFParseException("Encode required for function type 3!");
        }
        final PDFObject[] encodeAry = encodeObj.getArray();
        encode = new float[encodeAry.length];
        if (encode.length != 2 * functions.length) {
            throw new PDFParseException("Encode array must be of length " + 2 * functions.length);
        }
        for (int i = 0; i < encodeAry.length; i++) {
            encode[i] = encodeAry[i].getFloatValue();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFunction(final float[] inputs, final int inputOffset,
                              final float[] outputs, final int outputOffset) {

        float x = inputs[inputOffset];

        // calculate the output values
        int p = bounds.length - 2;
        while (x < bounds[p]) p--;
        x = interpolate(x, bounds[p], bounds[p + 1], encode[2 * p], encode[2 * p + 1]);
        final float[] out = functions[p].calculate(new float[]{x});
        System.arraycopy(out, 0, outputs, outputOffset, out.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumInputs() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumOutputs() {
        return functions[0].getNumOutputs();
    }
}
