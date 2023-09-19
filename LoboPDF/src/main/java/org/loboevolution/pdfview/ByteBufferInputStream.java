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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Exposes a {@link java.nio.ByteBuffer} as an {@link java.io.InputStream}.
 *
 * Author Luke Kirby
  *
 */
public class ByteBufferInputStream extends InputStream {

    /** The underlying byte buffer */
    private final ByteBuffer buffer;

    /**
     * Class constructor
     *
     * @param buffer the buffer to present as an input stream, positioned
     *  at the current read position of the byte buffer
     */
    public ByteBufferInputStream(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    /** {@inheritDoc} */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        if (b == null) {
	    throw new NullPointerException();
	} else if (off < 0 || len < 0 || len > b.length - off) {
	    throw new IndexOutOfBoundsException();
	} else if (len == 0) {
	    return 0;
	}

        final int remaining = buffer.remaining();
        if (remaining == 0) {
            return -1;
        } else if (remaining < len) {
            buffer.get(b, off, remaining);
            return remaining;
        } else {
            buffer.get(b, off, len);
            return len;
        }
    }

    /** {@inheritDoc} */
    @Override
    public long skip(long n) throws IOException {
        if (n <= 0) {
            return 0;
        } else {
            final int remaining = buffer.remaining();
            if (n < remaining) {
                buffer.position(buffer.position() + remaining);
                return remaining;
            } else {
                buffer.position((int) (buffer.position() + n));
                return n;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int read() throws IOException {
        return buffer.get();
    }

    /** {@inheritDoc} */
    @Override
    public int available() throws IOException {
        return buffer.remaining();
    }

    /** {@inheritDoc} */
    @Override
    public void mark(int readlimit) {
        buffer.mark();
    }

    /** {@inheritDoc} */
    @Override
    public boolean markSupported() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void reset() throws IOException {
        buffer.reset();
    }
}
