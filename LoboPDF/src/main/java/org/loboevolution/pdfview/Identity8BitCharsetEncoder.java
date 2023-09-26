/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.pdfview;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * A {@link java.nio.charset.CharsetEncoder} that attempts to write out the lower 8 bits
 * of any character. Characters &gt;= 256 in value are regarded
 * as unmappable.
 *
 * Author Luke Kirby
  *
 */
public class Identity8BitCharsetEncoder extends CharsetEncoder {

    /**
     * <p>Constructor for Identity8BitCharsetEncoder.</p>
     */
    public Identity8BitCharsetEncoder() {
        super(null, 1, 1);
    }

	/** {@inheritDoc} */
    @Override
	protected CoderResult encodeLoop(final CharBuffer in, final ByteBuffer out) {
        while (in.remaining() > 0) {
            if (out.remaining() < 1) {
                return CoderResult.OVERFLOW;
            }
            final char c = in.get();
            if (c >= 0 && c < 256) {
                out.put((byte) c);
            } else {
                return CoderResult.unmappableForLength(1);
            }
        }
        return CoderResult.UNDERFLOW;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLegalReplacement(final byte[] repl) {
        // avoid referencing the non-existent character set
        return true;
    }
}
