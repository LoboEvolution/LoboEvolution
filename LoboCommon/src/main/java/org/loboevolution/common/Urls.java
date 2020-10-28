package org.loboevolution.common;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Urls class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Urls {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Urls.class.getName());
	
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

	/**
	 * <p>hasHost.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return a boolean.
	 */
	public static boolean hasHost(URL url) {
		final String host = url.getHost();
		return Strings.isNotBlank(host);
	}

	/**
	 * <p>isAbsolute.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a boolean.
	 */
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return result;
	}

	/**
	 * <p>isLocalFile.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return a boolean.
	 */
	public static boolean isLocalFile(URL url) {
		final String scheme = url.getProtocol();
		return "file".equalsIgnoreCase(scheme) && !hasHost(url);
	}

	/**
	 * Converts the given URL into a valid URL by removing control characters (ASCII code).
	 *
	 * @return the encoded URL
	 * @param url a {@link java.lang.String} object.
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
	
	/**
	 * <p>getExpiration.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @param baseTime a long.
	 * @return a {@link java.lang.Long} object.
	 */
	public static Long getExpiration(URLConnection connection, long baseTime) {
		String cacheControl = connection.getHeaderField("Cache-Control");
		if (cacheControl != null) {
			StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken().trim().toLowerCase();
				if ("must-revalidate".equals(token)) {
					return 0L;
				} else if (token.startsWith("max-age")) {
					int eqIdx = token.indexOf('=');
					if (eqIdx != -1) {
						String value = token.substring(eqIdx + 1).trim();
						try {
							return baseTime + Integer.parseInt(value);
						} catch (NumberFormatException e) {
							logger.log(Level.SEVERE, e.getMessage(), e);
						}
					}
				}
			}
		}
		String expires = connection.getHeaderField("Expires");
		if (expires != null) {
			try {
				synchronized (PATTERN_RFC1123) {
					Date expDate = PATTERN_RFC1123.parse(expires);
					return expDate.getTime();
				}
			} catch (ParseException pe) {
				try {
					return baseTime + Integer.parseInt(expires);
				} catch (NumberFormatException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		return 0L;
	}
}
