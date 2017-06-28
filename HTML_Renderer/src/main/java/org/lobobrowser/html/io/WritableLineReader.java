/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 13, 2005
 */
package org.lobobrowser.html.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * The Class WritableLineReader.
 */
public class WritableLineReader extends LineNumberReader {

	/**
	 * Instantiates a new writable line reader.
	 *
	 * @param reader
	 *            the reader
	 * @param bufferSize
	 *            the buffer size
	 */
	public WritableLineReader(Reader reader, int bufferSize) {
		super(reader, bufferSize);
	}

	/**
	 * Instantiates a new writable line reader.
	 *
	 * @param reader
	 *            the reader
	 */
	public WritableLineReader(Reader reader) {
		super(reader);
	}

	/*
	 * Note: Not implicitly thread safe.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.LineNumberReader#read()
	 */
	@Override
	public int read() throws IOException {
		StringBuffer sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			char ch = sb.charAt(0);
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
	@Override
	public int read(char[] b, int off, int len) throws IOException {
		StringBuffer sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			int srcEnd = Math.min(sb.length(), len);
			sb.getChars(0, srcEnd, b, off);
			sb.delete(0, srcEnd);
			if (sb.length() == 0) {
				this.writeBuffer = null;
			}
			return srcEnd;
		}
		return super.read(b, off, len);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.BufferedReader#ready()
	 */
	@Override
	public boolean ready() throws IOException {
		StringBuffer sb = this.writeBuffer;
		if (sb != null && sb.length() > 0) {
			return true;
		}
		return super.ready();
	}

	/*
	 * (non-Javadoc) Note: Not implicitly thread safe.
	 * 
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() throws IOException {
		this.writeBuffer = null;
		super.close();
	}

	/** The write buffer. */
	private StringBuffer writeBuffer = null;

	/**
	 * Note: Not implicitly thread safe.
	 *
	 * @param text
	 *            the text
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void write(String text) throws IOException {
		// Document overrides this to know that new data is coming.
		StringBuffer sb = this.writeBuffer;
		if (sb == null) {
			sb = new StringBuffer();
			this.writeBuffer = sb;
		}
		sb.append(text);
	}
}
