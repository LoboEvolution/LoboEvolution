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


final class PushAsNumber implements PostScriptOperation {

    private final String token;

    /**
     * **********************************************************************
     * Constructor
     *
     * @param numberToken **********************************************************************
     */
    public PushAsNumber(final String numberToken) {
        this.token = numberToken;
    }

    /**
     * {@inheritDoc}
     * <p>
     * **********************************************************************
     * eval
     *
     * @see org.loboevolution.pdfview.function.postscript.operation.PostScriptOperation#eval(java.util.Deque)
     * **********************************************************************
     */
    @Override
    public void eval(final Deque<Object> environment) {
        try {
            final double number = Double.parseDouble(this.token);
            environment.push(number);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("PS token is not supported " + this.token);
        }
    }

}

