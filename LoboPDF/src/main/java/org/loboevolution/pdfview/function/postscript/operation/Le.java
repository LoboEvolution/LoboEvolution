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

package org.loboevolution.pdfview.function.postscript.operation;

import java.util.Deque;

final class Le implements PostScriptOperation {
    @Override
    /**
     * {@inheritDoc}
     *
     * <i>num1 num2</i> <b>le</b> <i>bool</i> <p>
     *
     * pops two objects from the operand stack and pushes true
     * if the first operand is less than or equal to the second,
     * or false otherwise. If both operands are numbers, le
     * compares their mathematical values. If both operands are
     * strings, le compares them element by element, treating
     * the elements as integers in the range 0 to 255,
     * to determine whether the first string is lexically less
     * than or equal to the second. If the operands are of other
     * types or one is a string and the other is a number, a
     * typecheck error occurs.<p>
     *
     * errors: invalidaccess, stackunderflow, typecheck
     */
    public void eval(final Deque<Object> environment) {
        final double num2 = (Double) environment.pop();
        final double num1 = (Double) environment.pop();
        environment.push(num1 <= num2);
    }
}

