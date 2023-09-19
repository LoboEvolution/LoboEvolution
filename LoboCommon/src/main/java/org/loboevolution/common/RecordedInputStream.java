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
/*
 * Created on Apr 15, 2005
 */
package org.loboevolution.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wraps an InputStream and records all of the bytes read. This stream supports
 * mark() and reset().
 * <p>
 * Note: Buffered streams should wrap this class as opposed to the other way
 * around.
 *
 * Author J. H. S.
 *
 */
public class RecordedInputStream extends InputStream {
	private final InputStream delegate;
	private boolean hasReachedMaxBufferSize = false;
	private int markPosition = -1;
	private final int maxBufferSize;
	private int readPosition = -1;
	private byte[] resetBuffer = null;
	private final ByteArrayOutputStream store = new ByteArrayOutputStream();

	/**
	 * <p>Constructor for RecordedInputStream.</p>
	 *
	 * @param delegate a {@link java.io.InputStream} object.
	 * @param maxBufferSize a int.
	 */
	public RecordedInputStream(InputStream delegate, int maxBufferSize) {
		super();
		this.delegate = delegate;
		this.maxBufferSize = maxBufferSize;
	}

	/** {@inheritDoc} */
	@Override
	public int available() throws IOException {
		return this.delegate.available();
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		this.delegate.close();
	}

	/**
	 * <p>getString.</p>
	 *
	 * @param encoding a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.UnsupportedEncodingException if any.
	 * @throws org.loboevolution.common.BufferExceededException if any.
	 */
	public String getString(String encoding) throws java.io.UnsupportedEncodingException, BufferExceededException {
		if (this.hasReachedMaxBufferSize) {
			throw new BufferExceededException();
		}
		return this.store.toString(encoding);
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void mark(int readlimit) {
		if (this.hasReachedMaxBufferSize) {
			throw new java.lang.IllegalStateException("Maximum buffer size was already reached.");
		}
		this.markPosition = this.store.size();
	}

	/** {@inheritDoc} */
	@Override
	public boolean markSupported() {
		return true;
	}


	/** {@inheritDoc} */
	@Override
	public int read() throws IOException {
		if (this.readPosition != -1 && this.readPosition < this.resetBuffer.length) {
			final int b = this.resetBuffer[this.readPosition];
			this.readPosition++;
			return b;
		} else {
			final int b = this.delegate.read();
			if (b != -1) {
				if (!this.hasReachedMaxBufferSize) {
					this.store.write(b);
					if (this.store.size() > this.maxBufferSize) {
						this.hasReachedMaxBufferSize = true;
					}
				}
			}
			return b;
		}
	}

	/** {@inheritDoc} */
	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException {
		if (this.readPosition != -1 && this.readPosition < this.resetBuffer.length) {
			final int minLength = Math.min(this.resetBuffer.length - this.readPosition, length);
			System.arraycopy(this.resetBuffer, this.readPosition, buffer, offset, minLength);
			this.readPosition += minLength;
			return minLength;
		} else {
			final int numRead = this.delegate.read(buffer, offset, length);
			if (numRead != -1) {
				if (!this.hasReachedMaxBufferSize) {
					this.store.write(buffer, offset, numRead);
					if (this.store.size() > this.maxBufferSize) {
						this.hasReachedMaxBufferSize = true;
					}
				}
			}
			return numRead;
		}
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void reset() throws IOException {
		if (this.hasReachedMaxBufferSize) {
			throw new java.lang.IllegalStateException("Maximum buffer size was already reached.");
		}
		final int mp = this.markPosition;
		final byte[] wholeBuffer = this.store.toByteArray();
		final byte[] resetBuffer = new byte[wholeBuffer.length - mp];
		System.arraycopy(wholeBuffer, mp, resetBuffer, 0, resetBuffer.length);
		this.resetBuffer = resetBuffer;
		this.readPosition = 0;
	}
}
