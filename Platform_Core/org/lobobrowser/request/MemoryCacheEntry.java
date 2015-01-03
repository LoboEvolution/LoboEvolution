/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.lobobrowser.util.NameValuePair;
import org.lobobrowser.util.io.IORoutines;

public class MemoryCacheEntry {
	public final byte[] content;
	public final List<NameValuePair> headers;
	public final Long expiration;
	public final Object altObject;
	public final long requestTime;
	public final int altObjectSize;

	/**
	 * 
	 * @param content
	 *            The content of the document without headers.
	 * @param headers
	 * @param expiration
	 * @param altObject
	 * @param altObjectSize
	 */
	public MemoryCacheEntry(final byte[] content,
			final List<NameValuePair> headers, final Long expiration,
			final Object altObject, final int altObjectSize) {
		this.content = content;
		this.headers = headers;
		this.expiration = expiration;
		this.altObject = altObject;
		this.altObjectSize = altObjectSize;
		this.requestTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param rawContent
	 *            The content of the document, including headers.
	 * @param altObject
	 * @param altObjectSize
	 */
	public MemoryCacheEntry(final byte[] rawContent, final Long expires,
			final long requestTime, final Object altObject,
			final int altObjectSize) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(rawContent);
		String line;
		List<NameValuePair> headersList = new LinkedList<NameValuePair>();
		while ((line = IORoutines.readLine(in)) != null) {
			if ("".equals(line)) {
				break;
			}
			int colonIdx = line.indexOf(':');
			String name = colonIdx == -1 ? "" : line.substring(0, colonIdx)
					.trim().toLowerCase();
			String value = colonIdx == -1 ? line.trim() : line.substring(
					colonIdx + 1).trim();
			headersList.add(new NameValuePair(name, value));
		}
		// Note: This works with a ByteArrayInputStream.
		int remainingLength = in.available();
		int offset = rawContent.length - remainingLength;
		byte[] remainingContent = new byte[remainingLength];
		System.arraycopy(rawContent, offset, remainingContent, 0,
				remainingLength);
		this.content = remainingContent;
		this.headers = headersList;
		this.expiration = expires;
		this.requestTime = requestTime;
		this.altObject = altObject;
		this.altObjectSize = altObjectSize;
	}

}
