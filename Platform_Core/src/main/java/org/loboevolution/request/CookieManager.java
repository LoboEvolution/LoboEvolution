/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.request;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.loboevolution.http.Cookie;

/**
 * The Class CookieManager.
 */
public class CookieManager extends CookieHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.CookieHandler#get(java.net.URI, java.util.Map)
	 */
	@Override
	public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
		Map<String, List<String>> resultHeaders = new HashMap<String, List<String>>(2);
		List<Cookie> cookies = CookieStore.getCookies(uri.getHost(), uri.getPath());
		StringBuilder cookieHeaderValue = null;
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			String assignment = cookieName + "=" + cookieValue;
			if (cookieHeaderValue == null) {
				cookieHeaderValue = new StringBuilder();
			} else {
				cookieHeaderValue.append("; ");
			}
			cookieHeaderValue.append(assignment);
		}
		if (cookieHeaderValue != null) {
			resultHeaders.put("Cookie", Collections.singletonList(cookieHeaderValue.toString()));
		}

		return resultHeaders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.CookieHandler#put(java.net.URI, java.util.Map)
	 */
	@Override
	public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
		for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
			String key = entry.getKey();
			for (String value : entry.getValue()) {
				if (key != null && value != null) {
					if ("Set-Cookie".equalsIgnoreCase(key) || "Set-Cookie2".equalsIgnoreCase(key)|| "Cookie".equalsIgnoreCase(key)|| "Cookie2".equalsIgnoreCase(key)) {
						CookieStore.saveCookie(uri.getHost(), value);
					}
				}
			}
		}
	}

	public static void install() {
		CookieHandler.setDefault(new CookieManager());
	}
}
