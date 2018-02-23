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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.loboevolution.http.Cookie;

/**
 * The Class CookieManager.
 */
public class CookieManager extends CookieHandler {
	
	/** The cookie store. */
	private final CookieStore cookieStore = CookieStore.getInstance();

	/** The cache. */
	private static Map<String, Set<Wrapper>> cache = new HashMap<String, Set<Wrapper>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.CookieHandler#get(java.net.URI, java.util.Map)
	 */
	@Override
	public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
		Map<String, List<String>> resultHeaders = new HashMap<String, List<String>>(2);
		Collection<Cookie> cookies = this.cookieStore.getCookies(uri.getHost(), uri.getPath());
		if (cookies != null) {
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

	public static Cookie[] getCookies(URI uri) {
		return getCookies(uri.getHost());
	}

	public static Cookie[] getCookies(String host) {
		Set<Wrapper> cookies = cache.get(host);
		if (cookies == null) {
			return new Cookie[0];
		} else {
			Cookie[] c = new Cookie[cookies.size()];
			int index = 0;
			for (Wrapper w : cookies) {
				c[index++] = w.cookie;
			}
			return c;
		}
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public static Cookie[] getCookies() {
		Set<Cookie> cookies = new HashSet<Cookie>();
		for (Map.Entry<String, Set<Wrapper>> entry : cache.entrySet()) {
			if (entry.getValue() != null) {
				for (Wrapper w : entry.getValue()) {
					cookies.add(w.cookie);
				}
			}
		}
		return cookies.toArray(new Cookie[0]);
	}

	public static void install() {
		CookieHandler.setDefault(new CookieManager());
	}

	/**
	 * A cookie wrapper. How cute.
	 * 
	 * Wraps cookies that are placed in the in-memory cache, such that cookies
	 * are compared based on their getName() property for equality.
	 */
	private static final class Wrapper {

		/** The cookie. */
		private Cookie cookie;

		private Wrapper(Cookie c) {
			if (c == null) {
				throw new IllegalArgumentException ();
			}
			this.cookie = c;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Wrapper) {
				return cookie.getName().equalsIgnoreCase(((Wrapper) o).cookie.getName());
			}
			return false;
		}
	}
}
