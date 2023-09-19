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
 * Created on Mar 19, 2005
 */
package org.loboevolution.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * <p>IORoutines class.</p>
 *
 * Author J. H. S.
 *
 */
public class IORoutines {



	/**
	 * Load as text.
	 *
	 * @param in
	 *            the in
	 * @param encoding
	 *            the encoding
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String loadAsText(InputStream in, String encoding) throws IOException {
		return loadAsText(in, encoding, 4096);
	}

	/**
	 * Load as text.
	 *
	 * @param in
	 *            the in
	 * @param encoding
	 *            the encoding
	 * @param bufferSize
	 *            the buffer size
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String loadAsText(InputStream in, String encoding, int bufferSize) throws IOException {
		InputStreamReader reader = new InputStreamReader(in, encoding);
		char[] buffer = new char[bufferSize];
		int offset = 0;
		while (true) {
			int remain = buffer.length - offset;
			if (remain <= 0) {
				char[] newBuffer = new char[buffer.length * 2];
				System.arraycopy(buffer, 0, newBuffer, 0, offset);
				buffer = newBuffer;
				remain = buffer.length - offset;
			}
			int numRead = reader.read(buffer, offset, remain);
			if (numRead == -1) {
				break;
			}
			offset += numRead;
		}
		return String.valueOf(buffer, 0, offset);
	}



	/**
	 * <p>load.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @param initialBufferSize a int.
	 * @return an array of {@link byte} objects.
	 * @throws java.io.IOException if any.
	 */
	public static byte[] load(InputStream in, int initialBufferSize) throws IOException {
		if (initialBufferSize == 0) {
			initialBufferSize = 1;
		}
		byte[] buffer = new byte[initialBufferSize];
		int offset = 0;
		while (true) {
			int remain = buffer.length - offset;
			if (remain <= 0) {
				final int newSize = buffer.length * 2;
				final byte[] newBuffer = new byte[newSize];
				System.arraycopy(buffer, 0, newBuffer, 0, offset);
				buffer = newBuffer;
				remain = buffer.length - offset;
			}
			final int numRead = in.read(buffer, offset, remain);
			if (numRead == -1) {
				break;
			}
			offset += numRead;
		}
		if (offset < buffer.length) {
			final byte[] newBuffer = new byte[offset];
			System.arraycopy(buffer, 0, newBuffer, 0, offset);
			buffer = newBuffer;
		}
		return buffer;
	}

	/**
	 * <p>getInputStream.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.io.IOException if any.
	 */
	public static InputStream getInputStream(URLConnection connection) throws IOException {
		InputStream in;
		if (connection instanceof HttpURLConnection) {
			in = IORoutines.getGzipStreamError(((HttpURLConnection) connection));
			if (in == null) {
				in = IORoutines.getGzipStream(connection);
			}
		} else {
			in = connection.getInputStream();
		}
		return in;
	}
	
	private static InputStream getGzipStream(URLConnection con) throws IOException {
		InputStream cis = con.getInputStream();
		if (cis != null) {
			if ("gzip".equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getInputStream());
			} else {
				return con.getInputStream();
			}
		} else {
			return null;
		}
	}

	private static InputStream getGzipStreamError(HttpURLConnection con) throws IOException {
		InputStream cis = con.getErrorStream();
		if (cis != null) {
			if ("gzip".equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getErrorStream());
			} else {
				return con.getErrorStream();
			}
		} else {
			return null;
		}
	}
}
