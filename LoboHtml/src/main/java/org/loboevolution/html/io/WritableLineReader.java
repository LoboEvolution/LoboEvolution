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

	/*
	 * (non-Javadoc) Note: Not implicitly thread safe.
	 * 
	 * @see java.io.Reader#close()
	 */
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

	/*
	 * (non-Javadoc) Note: Not implicitly thread safe.
	 * 
	 * @see java.io.Reader#read(byte[], int, int)
	 */
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
