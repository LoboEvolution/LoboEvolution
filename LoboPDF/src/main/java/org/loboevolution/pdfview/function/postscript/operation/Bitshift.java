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

package org.loboevolution.pdfview.function.postscript.operation;

import java.util.Deque;

final class Bitshift implements PostScriptOperation {
    @Override
    /**
     * {@inheritDoc}
     *
     * <i>int1 <i>shift</i> <b>bitshift</b> <i>int2</i> <p>
     *
     * shifts the binary representation of int1 left by
     * shift bits and returns the result. Bits shifted out
     * are lost; bits shifted in are 0. If shift is negative,
     * a right shift by –shift bits is performed.
     * This PostScriptOperation produces an arithmetically correct
     * result only for positive values of int1.
     * Both int1 and shift must be integers. <p>
     *
     * errors: stackunderflow, typecheck
     */
    public void eval(final Deque<Object> environment) {
        final long shift = (Long) environment.pop();
        final long int1 = (Long) environment.pop();
        environment.push(int1 << shift);
    }
}
