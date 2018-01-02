/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The Class MultipartFormDataWriter.
 */
public class MultipartFormDataWriter {

	/** The Constant LINE_BREAK_BYTES. */
	private static final byte[] LINE_BREAK_BYTES;

	/** The out. */
	private final OutputStream out;

	/** The boundary. */
	private final String boundary;

	static {
		LINE_BREAK_BYTES = "\r\n".getBytes();
	}

	/**
	 * Instantiates a new multipart form data writer.
	 *
	 * @param out
	 *            the out
	 * @param boundary
	 *            the boundary
	 */
	public MultipartFormDataWriter(final OutputStream out, final String boundary) {
		super();
		this.out = out;
		this.boundary = boundary;
	}

	/**
	 * Write file data.
	 *
	 * @param name
	 *            the name
	 * @param fileName
	 *            the file name
	 * @param contentType
	 *            the content type
	 * @param in
	 *            Data stream. The caller is responsible for closing it.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final void writeFileData(String name, String fileName, String contentType, InputStream in)
			throws IOException {
		String headers = "--" + this.boundary + "\r\n" + "Content-Disposition: form-data; name=\"" + name
				+ "\"; filename=\"" + fileName + "\"\r\n" + "Content-Type: " + contentType + "\r\n" + "\r\n";
		OutputStream out = this.out;
		out.write(headers.getBytes("UTF-8"));
		byte[] buffer = new byte[4096];
		int numRead;
		while ((numRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numRead);
		}
		out.write(LINE_BREAK_BYTES);
	}

	/**
	 * Write text.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param charset
	 *            the charset
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final void writeText(String name, String value, String charset) throws IOException {
		String headers = "--" + this.boundary + "\r\n" + "Content-Disposition: form-data; name=\"" + name + "\"\r\n"
				+ "Content-Type: text/plain; charset=\"" + charset + "\"\r\n" + "\r\n";
		OutputStream out = this.out;
		out.write(headers.getBytes("UTF-8"));
		out.write(value.getBytes(charset));
		out.write(LINE_BREAK_BYTES);
	}

	/**
	 * Send.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final void send() throws IOException {
		String finalDelimiter = "--" + this.boundary + "--\r\n";
		OutputStream out = this.out;
		out.write(finalDelimiter.getBytes("UTF-8"));
		out.flush();
	}
}
