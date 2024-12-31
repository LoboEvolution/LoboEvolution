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

final class Roll implements PostScriptOperation {

    /**
     * <p>popAsInteger.</p>
     *
     * @param st a {@link java.util.Stack} object.
     * @return a {@link java.lang.Integer} object.
     */
    public static int popAsInteger(final Deque<Object> st) {
        final Object e = st.pop();
        if (e instanceof Double) {
            final double doubleVal = (Double) e;
            return (int) doubleVal;
        } else {
            // error
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eval(final Deque<Object> environment) {
        // <i>anyn-1 ... any0 n j</i> <b>roll</b> <i>any(j-1)mod n ... anyn-1 ... any</i>
        // Roll n elements up j times
        int j = popAsInteger(environment);
        final int n = popAsInteger(environment);
        final Object[] temp = new Object[n];

        if (environment.size() < n) {
            // error, cause by non-standard PS cmd, do nothing for compatibility
            return;
        }

        if (j >= 0) {
            j %= n;
        } else {
            j = -j % n;
            if (j != 0)
                j = n - j;
        }
        for (int i = 0; i < n; ++i) {
            temp[i] = environment.pop();
        }

        for (int i = j - 1; i > -1; --i) {
            environment.push(temp[i]);
        }
        for (int i = n - 1; i > j - 1; --i) {
            environment.push(temp[i]);
        }
    }

}
