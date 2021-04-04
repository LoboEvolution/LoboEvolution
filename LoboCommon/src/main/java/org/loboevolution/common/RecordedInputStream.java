/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
	private boolean hasReachedEOF = false;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#available()
	 */
	/** {@inheritDoc} */
	@Override
	public int available() throws IOException {
		return this.delegate.available();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#close()
	 */
	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		this.delegate.close();
	}

	/**
	 * <p>consumeToEOF.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public void consumeToEOF() throws IOException {
		final byte[] buffer = new byte[8192];
		while (this.read(buffer) != -1) {
        }
	}

	/**
	 * <p>getBytesRead.</p>
	 *
	 * @return an array of {@link byte} objects.
	 * @throws org.loboevolution.common.BufferExceededException if any.
	 */
	public byte[] getBytesRead() throws BufferExceededException {
		if (this.hasReachedMaxBufferSize) {
			throw new BufferExceededException();
		}
		return this.store.toByteArray();
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
		final byte[] bytes = this.store.toByteArray();
		return new String(bytes, encoding);
	}

	/**
	 * <p>hasReachedEOF.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasReachedEOF() {
		return this.hasReachedEOF;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void mark(int readlimit) {
		if (this.hasReachedMaxBufferSize) {
			throw new java.lang.IllegalStateException("Maximum buffer size was already reached.");
		}
		this.markPosition = this.store.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#markSupported()
	 */
	/** {@inheritDoc} */
	@Override
	public boolean markSupported() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#read()
	 */
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
			} else {
				this.hasReachedEOF = true;
			}
			return b;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
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
			} else {
				this.hasReachedEOF = true;
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
