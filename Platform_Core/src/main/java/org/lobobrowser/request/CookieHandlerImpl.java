/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieHandlerImpl extends CookieHandler {
	private static final Logger logger = Logger
			.getLogger(CookieHandlerImpl.class.getName());
	private final CookieStore cookieStore = CookieStore.getInstance();

	private void printHeaders(Map<String, List<String>> headers) {
		StringWriter swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			for (String value : entry.getValue()) {
				writer.println("Header: " + entry.getKey() + "=" + value);
			}
		}
		writer.println();
		writer.flush();
		logger.info(swriter.toString());
	}

	@Override
	public Map<String, List<String>> get(URI uri,
			Map<String, List<String>> requestHeaders) throws IOException {
		Map<String, List<String>> resultHeaders = new java.util.HashMap<String, List<String>>(
				2);
		java.util.Collection<Cookie> cookies = this.cookieStore.getCookies(
				uri.getHost(), uri.getPath());
		if (cookies != null) {
			StringBuffer cookieHeaderValue = null;
			for (Cookie cookie : cookies) {
				// We should not decode values. Servers
				// expect to receive what they set the
				// values to.
				String cookieName = cookie.getName();
				String cookieValue = cookie.getValue();
				String assignment = cookieName + "=" + cookieValue;
				// if(logger.isLoggable(Level.INFO)) {
				// logger.info("get(): found cookie: [" + assignment + "]; uri="
				// + uri);
				// }
				if (cookieHeaderValue == null) {
					cookieHeaderValue = new StringBuffer();
				} else {
					cookieHeaderValue.append("; ");
				}
				cookieHeaderValue.append(assignment);
			}
			if (cookieHeaderValue != null) {
				resultHeaders.put("Cookie", java.util.Collections
						.singletonList(cookieHeaderValue.toString()));
			}
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.info("get(): ---- Cookie headers for uri=[" + uri + "].");
			this.printHeaders(resultHeaders);
		}
		return resultHeaders;
	}

	@Override
	public void put(URI uri, Map<String, List<String>> responseHeaders)
			throws IOException {
		if (logger.isLoggable(Level.FINE)) {
			logger.info("put(): ---- Response headers for uri=[" + uri + "].");
			this.printHeaders(responseHeaders);
		}
		CookieStore store = this.cookieStore;
		for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
			String key = entry.getKey();
			for (String value : entry.getValue()) {
				if (key != null && value != null) {
					if ("Set-Cookie".equalsIgnoreCase(key)) {
						store.saveCookie(uri.getHost(), value);
					} else if ("Set-Cookie2".equalsIgnoreCase(key)) {
						store.saveCookie(uri.getHost(), value);
					}
				}
			}
		}
	}
}
