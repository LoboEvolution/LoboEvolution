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
import org.loboevolution.pdfview.function.postscript.PostScriptParser;
import org.loboevolution.pdfview.function.postscript.operation.OperationSet;
import org.loboevolution.pdfview.function.postscript.operation.PostScriptOperation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <p>A PostScript function is represented as a stream containing code
 * written in a small subset of the PostScript language.
 * This reference is taken from the (3200-1:2008:7.10.5)<p>
 * <p>
 * http://www.adobe.com/devnet/acrobat/pdfs/adobe_supplement_iso32000.pdf
 * </p>
 */
public class FunctionType4 extends PDFFunction {

    /**
     * the list of tokens and sub-expressions.
     */
    private List<String> tokens;

    /**
     * the stack of operations. The stack contents should all be Comparable.
     */
    private Deque<Object> stack;

    /**
     * Creates a new instance of FunctionType4
     */
    protected FunctionType4() {
        super(TYPE_4);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Read the function information from a PDF Object
     */
    @Override
    protected void parse(final PDFObject obj) throws IOException {
        final ByteBuffer buf = obj.getStreamBuffer();

        final byte[] byteA = new byte[buf.remaining()];
        buf.get(byteA);
        final String scriptContent = new String(byteA, "UTF-8");
        this.tokens = new PostScriptParser().parse(scriptContent);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Map from <i>m</i> input values to <i>n</i> output values.
     * The number of inputs <i>m</i> must be exactly one half the size of the
     * domain.  The number of outputs should match one half the size of the
     * range.
     */
    @Override
    protected void doFunction(final float[] inputs, final int inputOffset, final float[] outputs, final int outputOffset) {
        prepareInitialStack(inputs, inputOffset);
        for (final String token : this.tokens) {
            final PostScriptOperation op = OperationSet.getInstance().getOperation(token);
            op.eval(this.stack);
        }
        assertResultIsCorrect(outputs, outputOffset);
        prepareResult(outputs, outputOffset);
    }

    /*************************************************************************
     * @param outputs a {@link Float} object.
     * @param outputOffset a {@link Float} object.
     ************************************************************************/
    private void prepareResult(final float[] outputs, final int outputOffset) {
        for (int i = outputOffset; i < outputs.length; i++) {
            outputs[outputs.length - i - 1] = ((Double) this.stack.pop()).floatValue();
        }
    }

    /*************************************************************************
     * Put all input values on the initial stack.
     * All values are pushed as Double because we calculate internally with double.
     * @param inputs a {@link Float} object.
     * @param inputOffset a {@link Integer} object.
     ************************************************************************/

    private void prepareInitialStack(final float[] inputs, final int inputOffset) {
        this.stack = new ArrayDeque<>();
        for (int i = inputOffset; i < inputs.length; i++) {
            this.stack.push((double) inputs[i]);
        }
    }

    /*************************************************************************
     * @param outputs a {@link Float} object.
     * @param outputOffset a {@link Integer} object.
     ************************************************************************/

    private void assertResultIsCorrect(final float[] outputs, final int outputOffset) {
        final int expectedResults = outputs.length - outputOffset;
        if (this.stack.size() != expectedResults) {
            throw new IllegalStateException("Output does not match result " + expectedResults + "/" + this.stack);
        }
    }
}
