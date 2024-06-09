/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.common;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * <p>Urls class.</p>
 */
@Slf4j
public class Urls {
	
	/** The Constant PATTERN_RFC1123. */
	public static final DateFormat PATTERN_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	/**
	 * <p>createURL.</p>
	 *
	 * @param baseUrl a {@link java.net.URL} object.
	 * @param relativeUrl a {@link java.lang.String} object.
	 * @return a {@link java.net.URL} object.
	 * @throws java.lang.Exception if any.
	 */
	public static URL createURL(final URL baseUrl, final String relativeUrl) throws Exception {
		if (relativeUrl.contains("javascript:void")) {
			return null;
		}

		return baseUrl.toURI().resolve(encodeIllegalCharacters(relativeUrl)).toURL();
	}

	/**
	 * Converts the given URL into a valid URL by encoding illegal characters. Right
	 * now it is implemented like in IE7: only spaces are replaced with "%20".
	 * (Firefox 3 also encodes other non-ASCII and some ASCII characters).
	 *
	 * @return the encoded URL
	 * @param url a {@link java.lang.String} object.
	 */
	public static String encodeIllegalCharacters(final String url) {
		return url.replace(" ", "%20");
	}

	/**
	 * <p>getCharset.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getCharset(final URLConnection connection) {
		final String contentType = connection.getContentType();
		if (contentType == null) {
			return getDefaultCharset(connection);
		}
		final StringTokenizer tok = new StringTokenizer(contentType, ";");
		if (tok.hasMoreTokens()) {
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				final String assignment = tok.nextToken().trim();
				final int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					final String varName = assignment.substring(0, eqIdx).trim();
					if ("charset".equalsIgnoreCase(varName)) {
						final String varValue = assignment.substring(eqIdx + 1);
						return Strings.unquote(varValue.trim());
					}
				}
			}
		}
		return getDefaultCharset(connection);
	}

	private static String getDefaultCharset(final URLConnection connection) {
		final URL url = connection.getURL();
		if (Urls.isLocalFile(url)) {
			final String charset = Charset.defaultCharset().displayName();
			return charset == null ? "UTF-8" : charset;
		} else {
			return "UTF-8";
		}
	}

	/**
	 * <p>isAbsolute.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isAbsolute(final String url) {
		boolean result = false;

		if (url.startsWith("//")) {
			return true;
		}

		if (url.startsWith("/")) {
			return false;
		}

		try {
			final URI uri = new URI(url);
			result = uri.isAbsolute();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <p>isLocalFile.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return a boolean.
	 */
	public static boolean isLocalFile(final URL url) {
		final String scheme = url.getProtocol();
		return "file".equalsIgnoreCase(scheme);
	}

	/**
	 * <p>getExpiration.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @param baseTime a long.
	 * @return a {@link java.lang.Long} object.
	 */
	public static Long getExpiration(final URLConnection connection, final long baseTime) {
		final String cacheControl = connection.getHeaderField("Cache-Control");
		if (cacheControl != null) {
			final StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				final String token = tok.nextToken().trim().toLowerCase();
				if ("must-revalidate".equals(token)) {
					return 0L;
				} else if (token.startsWith("max-age")) {
					final int eqIdx = token.indexOf('=');
					if (eqIdx != -1) {
						final String value = token.substring(eqIdx + 1).trim();
						try {
							return baseTime + Integer.parseInt(value);
						} catch (final NumberFormatException e) {
							log.error(e.getMessage(), e);
						}
					}
				}
			}
		}
		final String expires = connection.getHeaderField("Expires");
		if (expires != null) {
			try {
				synchronized (PATTERN_RFC1123) {
					final Date expDate = PATTERN_RFC1123.parse(expires);
					return expDate.getTime();
				}
			} catch (final ParseException pe) {
				try {
					return baseTime + Integer.parseInt(expires);
				} catch (final NumberFormatException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return 0L;
	}

	public static boolean exists(URL url) {
		try {
			url.openConnection();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
