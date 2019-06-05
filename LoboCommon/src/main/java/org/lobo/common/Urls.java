package org.lobo.common;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class Urls {

	public static URL createURL(URL baseUrl, String relativeUrl) throws Exception {
		if (relativeUrl.contains("javascript:void")) {
			return null;
		}
		return new URL(baseUrl, relativeUrl);
	}

	/**
	 * Converts the given URL into a valid URL by encoding illegal characters. Right
	 * now it is implemented like in IE7: only spaces are replaced with "%20".
	 * (Firefox 3 also encodes other non-ASCII and some ASCII characters).
	 * 
	 * @param the URL to convert
	 * @return the encoded URL
	 */
	public static String encodeIllegalCharacters(final String url) {
		return url.replace(" ", "%20");
	}

	public static String getCharset(URLConnection connection) {
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

	private static String getDefaultCharset(URLConnection connection) {
		final URL url = connection.getURL();
		if (Urls.isLocalFile(url)) {
			final String charset = System.getProperty("file.encoding");
			return charset == null ? "UTF-8" : charset;
		} else {
			return "UTF-8";
		}
	}

	public static boolean hasHost(URL url) {
		final String host = url.getHost();
		return Strings.isNotBlank(host);
	}

	public static boolean isAbsolute(String url) {
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
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isLocalFile(URL url) {
		final String scheme = url.getProtocol();
		return "file".equalsIgnoreCase(scheme) && !hasHost(url);
	}

	/**
	 * Converts the given URL into a valid URL by removing control characters (ASCII
	 * code < 32).
	 * 
	 * @param the URL to convert
	 * @return the encoded URL
	 */
	public static String removeControlCharacters(final String url) {
		final StringBuilder sb = new StringBuilder(url.length());
		for (int i = 0; i < url.length(); i++) {
			final char c = url.charAt(i);
			if (c >= 32) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
