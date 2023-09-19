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
 * Created on Nov 13, 2005
 */
package org.loboevolution.html.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * <p>WritableLineReader class.</p>
 *
 *
 *
 */
public class WritableLineReader extends LineNumberReader {
	private StringBuilder writeBuffer = null;

	/**
	 * <p>Constructor for WritableLineReader.</p>
	 *
	 * @param reader a {@link java.io.Reader} object.
	 */
	public WritableLineReader(Reader reader) {
		super(reader);
	}

	/**
	 * <p>Constructor for WritableLineReader.</p>
	 *
	 * @param reader a {@link java.io.Reader} object.
	 * @param bufferSize a int.
	 */
	public WritableLineReader(Reader reader, int bufferSize) {
		super(reader, bufferSize);
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		this.writeBuffer = null;
		super.close();
	}

	/*
	 * Note: Not implicitly thread safe.
	 */
	/** {@inheritDoc} */
	@Override
	public int read() throws IOException {
		final StringBuilder sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			final char ch = sb.charAt(0);
			sb.deleteCharAt(0);
			if (sb.length() == 0) {
				this.writeBuffer = null;
			}
			return ch;
		}
		return super.read();
	}

	/** {@inheritDoc} */
	@Override
	public int read(char[] b, int off, int len) throws IOException {
		final StringBuilder sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			final int srcEnd = Math.min(sb.length(), len);
			sb.getChars(0, srcEnd, b, off);
			sb.delete(0, srcEnd);
			if (sb.length() == 0) {
				this.writeBuffer = null;
			}
			return srcEnd;
		}
		return super.read(b, off, len);
	}

	/** {@inheritDoc} */
	@Override
	public boolean ready() throws IOException {
		final StringBuilder sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			return true;
		}
		return super.ready();
	}

	/**
	 * Note: Not implicitly thread safe.
	 *
	 * @param text a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public void write(String text) throws IOException {
		// Document overrides this to know that new data is coming.
		StringBuilder sb = this.writeBuffer;
		if (sb == null) {
			sb = new StringBuilder();
			this.writeBuffer = sb;
		}
		sb.append(text);
	}
}
