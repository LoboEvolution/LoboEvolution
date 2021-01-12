/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Mar 19, 2005
 */
package org.loboevolution.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * <p>IORoutines class.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class IORoutines {
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
