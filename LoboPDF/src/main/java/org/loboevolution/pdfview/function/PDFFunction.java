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
 * <p>PDF Functions are defined in the reference as Section 3.9.</p>
 *
 * <p>A PDF function maps some set of <i>m</i> inputs into some set
 * of <i>n</i> outputs.  There are 4 types of functions:
 * <ul><li>Type 0: Sampled functions. (PDF 1.2)<br>
 *                  A sampled function (type 0) uses a table of sample values
 *                  to define the function. Various techniques are used to
 *                  interpolate values between the sample values
 *                  (see Section 3.9.1, "Type 0 (Sampled) Functions").</li>
 *     <li>Type 2: Exponential Interpolation. (PDF 1.3)<br>
 *                  An exponential interpolation function (type 2)
 *                  defines a set of coefficients for an exponential function
 *                  (see Section 3.9.2,
 *                  "Type 2 (Exponential Interpolation) Functions").</li>
 *     <li>Type 3: Stitching functions. (PDF 1.3)<br>
 *                  A stitching function (type 3) is a combination of
 *                  other functions, partitioned across a domain
 *                  (see Section 3.9.3, "Type 3 (Stitching) Functions").</li>
 *     <li>Type 4: Postscript calculations. (PDF 1.3)<br>
 *                  A PostScript calculator function (type 4) uses operators
 *                  from the PostScript language to describe an arithmetic
 *                  expression (see Section 3.9.4,
 *                  "Type 4 (PostScript Calculator) Functions").</li>
 * </ul>
 * <p>
 * The function interface contains a single method, <i>calculate</i> which
 * takes an array of <i>m</i> floats an interprets them into an array of
 * <i>n</i> floats.
 * PDFFunctions do not have accessible constructors.  Instead, use the
 * static <i>getFunction()</i> method to read a functions from a PDF Object.
 */
public abstract class PDFFunction {

    /**
     * Sampled function
     */
    public static final int TYPE_0 = 0;

    /**
     * Exponential interpolation function
     */
    public static final int TYPE_2 = 2;

    /**
     * Stitching function.
     */
    public static final int TYPE_3 = 3;

    /**
     * PostScript calculator function.
     */
    public static final int TYPE_4 = 4;

    /**
     * the type of this function from the list of known types
     */
    @Getter
    private final int type;

    /**
     * the input domain of this function, an array of 2 * <i>m</i> floats
     */
    @Setter
    private float[] domain;

    /**
     * the output range of this functions, and array of 2 * <i>n</i> floats.
     * required for type 0 and 4 functions
     */
    @Setter
    private float[] range;

    /**
     * Creates a new instance of PDFFunction
     *
     * @param type a {@link java.lang.Integer} object.
     */
    protected PDFFunction(final int type) {
        this.type = type;
    }

    /**
     * Get a PDFFunction from a PDFObject
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return a {@link org.loboevolution.pdfview.function.PDFFunction} object.
     * @throws java.io.IOException if any.
     */
    public static PDFFunction getFunction(final PDFObject obj)
            throws IOException {
        final PDFFunction function;
        final int type;
        float[] domain;
        float[] range = null;

        // read the function type (required)
        final PDFObject typeObj = obj.getDictRef("FunctionType");
        if (typeObj == null) {
            throw new PDFParseException(
                    "No FunctionType specified in function!");
        }
        type = typeObj.getIntValue();

        // read the function's domain (required)
        final PDFObject domainObj = obj.getDictRef("Domain");
        if (domainObj == null) {
            throw new PDFParseException("No Domain specified in function!");
        }

        final PDFObject[] domainAry = domainObj.getArray();
        domain = new float[domainAry.length];
        for (int i = 0; i < domainAry.length; i++) {
            domain[i] = domainAry[i].getFloatValue();
        }

        // read the function's range (optional)
        final PDFObject rangeObj = obj.getDictRef("Range");
        if (rangeObj != null) {
            final PDFObject[] rangeAry = rangeObj.getArray();
            range = new float[rangeAry.length];
            for (int i = 0; i < rangeAry.length; i++) {
                range[i] = rangeAry[i].getFloatValue();
            }
        }

        // now create the acual function object
        function = switch (type) {
            case TYPE_0 -> {
                if (rangeObj == null) {
                    throw new PDFParseException(
                            "No Range specified in Type 0 Function!");
                }
                yield new FunctionType0();
            }
            case TYPE_2 -> new FunctionType2();
            case TYPE_3 -> new FunctionType3();
            case TYPE_4 -> {
                if (rangeObj == null) {
                    throw new PDFParseException(
                            "No Range specified in Type 4 Function!");
                }
                yield new FunctionType4();
            }
            default -> throw new PDFParseException(
                    "Unsupported function type: " + type);
        };

        // fill in the domain and optionally the range
        function.setDomain(domain);
        if (range != null) {
            function.setRange(range);
        }

        // now initialize the function
        function.parse(obj);

        return function;
    }

    /**
     * Perform a linear interpolation.  Given a value x, and two points,
     * (xmin, ymin), (xmax, ymax), where xmin {@literal <}= x {@literal <}= xmax, calculate a value
     * y on the line from (xmin, ymin) to (xmax, ymax).
     *
     * @param x    the x value of the input
     * @param xmin the minimum x value
     * @param ymin the minimum y value
     * @param xmax the maximum x value
     * @param ymax the maximum y value
     * @return the y value interpolated from the given x
     */
    public static float interpolate(final float x, final float xmin, final float xmax,
                                    final float ymin, final float ymax) {
        float value = (ymax - ymin) / (xmax - xmin);
        value *= x - xmin;
        value += ymin;

        return value;
    }

    /**
     * Get the number of inputs, <i>m</i>, required by this function
     *
     * @return the number of input values expected by this function
     */
    public int getNumInputs() {
        return (this.domain.length / 2);
    }

    /**
     * Get the number of outputs, <i>n</i>, returned by this function
     *
     * @return the number of output values this function will return
     */
    public int getNumOutputs() {
        if (this.range == null) {
            return 0;
        }
        return (this.range.length / 2);
    }

    /**
     * Get a component of the domain of this function
     *
     * @param i the index into the domain array, which has size 2 * <i>m</i>.
     *          the <i>i</i>th entry in the array has index 2<i>i</i>,
     *          2<i>i</i> + 1
     * @return the <i>i</i>th entry in the domain array
     */
    protected float getDomain(final int i) {
        return this.domain[i];
    }

    /**
     * Get a component of the range of this function
     *
     * @param i the index into the range array, which has size 2 * <i>n</i>.
     *          the <i>i</i>th entry in the array has index 2<i>i</i>,
     *          2<i>i</i> + 1
     * @return the <i>i</i>th entry in the range array
     */
    protected float getRange(final int i) {
        if (this.range == null) {
            if ((i % 2) == 0) {
                return Float.MIN_VALUE;
            } else {
                return Float.MAX_VALUE;
            }
        }
        return this.range[i];
    }

    /**
     * Map from <i>m</i> input values to <i>n</i> output values.
     * The number of inputs <i>m</i> must be exactly one half the size of the
     * domain.  The number of outputs should match one half the size of the
     * range.
     *
     * @param inputs an array of {@literal >}= <i>m</i> input values
     * @return the array of <i>n</i> output values
     */
    public float[] calculate(final float[] inputs) {
        final float[] outputs = new float[getNumOutputs()];
        calculate(inputs, 0, outputs, 0);
        return outputs;
    }

    /**
     * Map from <i>m</i> input values to <i>n</i> output values.
     * The number of inputs <i>m</i> must be exactly one half the size of the
     * domain.  The number of outputs should match one half the size of the
     * range.
     *
     * @param inputs       an array of {@literal >}= <i>m</i> input values
     * @param inputOffset  the offset into the input array to read from
     * @param outputs      an array of size {@literal >}= <i>n</i> which will be filled
     *                     with the output values
     * @param outputOffset the offset into the output array to write to
     * @return the array of <i>n</i> output values
     */
    public float[] calculate(final float[] inputs, final int inputOffset,
                             final float[] outputs, final int outputOffset) {
        // check the inputs
        if (inputs.length - inputOffset < getNumInputs()) {
            throw new IllegalArgumentException(
                    "Wrong number of inputs to function!");
        }

        // check the outputs
        if (this.range != null && outputs.length - outputOffset < getNumOutputs()) {
            throw new IllegalArgumentException(
                    "Wrong number of outputs for function!");
        }

        // clip the inputs to domain
        for (int i = 0; i < inputs.length; i++) {
            // clip to the domain -- min(max(x<i>, domain<2i>), domain<2i+1>)
            inputs[i] = Math.max(inputs[i], getDomain(2 * i));
            inputs[i] = Math.min(inputs[i], getDomain((2 * i) + 1));
        }

        // do the actual calculation
        doFunction(inputs, inputOffset, outputs, outputOffset);

        // clip the outputs to range
        for (int i = 0; this.range != null && i < outputs.length; i++) {
            // clip to range -- min(max(r<i>, range<2i>), range<2i + 1>)
            outputs[i] = Math.max(outputs[i], getRange(2 * i));
            outputs[i] = Math.min(outputs[i], getRange((2 * i) + 1));
        }

        return outputs;
    }

    /**
     * Subclasses must implement this method to perform the actual function
     * on the given set of data.  Note that the inputs are guaranteed to be
     * clipped to the domain, while the outputs will be automatically clipped
     * to the range after being returned from this function.
     *
     * @param inputOffset  the offset into the inputs array to read from
     * @param outputs      guaranteed to be at least as big as
     *                     <code>getNumOutputs()</code>, but not yet clipped to domain
     * @param outputOffset the offset into the output array to write to
     * @param inputs       an array of {@link float} objects.
     */
    protected abstract void doFunction(float[] inputs, final int inputOffset,
                                       float[] outputs, final int outputOffset);

    /**
     * Read the function information from a PDF Object
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    protected abstract void parse(PDFObject obj) throws IOException;
}
