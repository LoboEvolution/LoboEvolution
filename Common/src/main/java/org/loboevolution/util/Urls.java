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
/*
 * Created on Jun 12, 2005
 */
package org.loboevolution.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.http.NameValuePair;

/**
 * The Class Urls.
 */
public final class Urls {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Urls.class);
	
	/** The Constant PATTERN_RFC1123. */
	public static final DateFormat PATTERN_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	static {
		DateFormat df = PATTERN_RFC1123;
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Instantiates a new urls.
	 */
	private Urls() {
		super();
	}

	/**
	 * Whether the URL refers to a resource in the local file system.
	 *
	 * @param url
	 *            the url
	 * @return true, if is local
	 */
	public static boolean isLocal(URL url) {
		if (isLocalFile(url)) {
			return true;
		}
		String protocol = url.getProtocol();
		if ("jar".equalsIgnoreCase(protocol)) {
			String path = url.getPath();
			int emIdx = path.lastIndexOf('!');
			String subUrlString = emIdx == -1 ? path : path.substring(0, emIdx);
			try {
				URL subUrl = new URL(subUrlString);
				return isLocal(subUrl);
			} catch (MalformedURLException mfu) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Whether the URL is a file in the local file system.
	 *
	 * @param url
	 *            the url
	 * @return true, if is local file
	 */
	public static boolean isLocalFile(URL url) {
		String scheme = url.getProtocol();
		return "file".equalsIgnoreCase(scheme) && !hasHost(url);
	}

	/**
	 * Checks for host.
	 *
	 * @param url
	 *            the url
	 * @return true, if successful
	 */
	public static boolean hasHost(URL url) {
		String host = url.getHost();
		return host != null && !"".equals(host);
	}

	/**
	 * Creates an absolute URL in a manner equivalent to major browsers.
	 *
	 * @param baseUrl
	 *            the base url
	 * @param relativeUrl
	 *            the relative url
	 * @return the url
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 */
	public static URL createURL(URL baseUrl, String relativeUrl)
			throws MalformedURLException, UnsupportedEncodingException {
		if (relativeUrl.contains("javascript:void")) {
			return null;
		}	
		return new URL(baseUrl, relativeUrl);
	}

	/**
	 * Returns the time when the document should be considered expired. The time
	 * will be zero if the document always needs to be revalidated. It will be
	 * <code>null</code> if no expiration time is specified.
	 *
	 * @param connection
	 *            the connection
	 * @param baseTime
	 *            the base time
	 * @return the expiration
	 */
	public static Long getExpiration(URLConnection connection, long baseTime) {
		String cacheControl = connection.getHeaderField("Cache-Control");
		if (cacheControl != null) {
			StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken().trim().toLowerCase();
				if ("must-revalidate".equals(token)) {
					return Long.valueOf(0);
				} else if (token.startsWith("max-age")) {
					int eqIdx = token.indexOf('=');
					if (eqIdx != -1) {
						String value = token.substring(eqIdx + 1).trim();
						int seconds;
						try {
							seconds = Integer.parseInt(value);
							return Long.valueOf(baseTime + seconds * 1000);
						} catch (NumberFormatException nfe) {
							logger.warn("getExpiration(): Bad Cache-Control max-age value: " + value);
							// ignore
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
					return Long.valueOf(expDate.getTime());
				}
			} catch (java.text.ParseException pe) {
				int seconds;
				try {
					seconds = Integer.parseInt(expires);
					return Long.valueOf(baseTime + seconds * 1000);
				} catch (NumberFormatException nfe) {
					logger.warn("getExpiration(): Bad Expires header value: " + expires);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the headers.
	 *
	 * @param connection
	 *            the connection
	 * @return the headers
	 */
	public static List<NameValuePair> getHeaders(URLConnection connection) {
		// Random access index recommended.
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		for (int n = 0;; n++) {
			String value = connection.getHeaderField(n);
			if (value == null) {
				break;
			}
			// Key may be null for n == 0.
			String key = connection.getHeaderFieldKey(n);
			if (key != null) {
				headers.add(new NameValuePair(key, value));
			}
		}
		return headers;
	}

	/**
	 * Guess url.
	 *
	 * @param baseURL
	 *            the base url
	 * @param spec
	 *            the spec
	 * @return the url
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public static URL guessURL(URL baseURL, String spec) throws MalformedURLException {
		URL finalURL = null;
		try {
			if (baseURL != null) {
				int colonIdx = spec.indexOf(':');
				String newProtocol = colonIdx == -1 ? null : spec.substring(0, colonIdx);
				if (newProtocol != null && !newProtocol.equalsIgnoreCase(baseURL.getProtocol())) {
					baseURL = null;
				}
			}
			finalURL = createURL(baseURL, spec);
		} catch (MalformedURLException | UnsupportedEncodingException mfu) {
			spec = spec.trim();
			int idx = spec.indexOf(':');
			if (idx == -1) {
				int slashIdx = spec.indexOf('/');
				if (slashIdx == 0) {
					// A file, absolute
					finalURL = new URL("file:" + spec);
				} else {
					if (slashIdx == -1) {
						// No slash, no colon, must be host.
						finalURL = new URL(baseURL, "http://" + spec);
					} else {
						String possibleHost = spec.substring(0, slashIdx).toLowerCase();
						if (Domains.isLikelyHostName(possibleHost)) {
							finalURL = new URL(baseURL, "http://" + spec);
						} else {
							finalURL = new URL(baseURL, "file:" + spec);
						}
					}
				}
			} else {
				if (idx == 1) {
					// Likely a drive
					finalURL = new URL(baseURL, "file:" + spec);
				} else {
					try {
						throw mfu;
					} catch (IOException e) {
						logger.error(e);
					}
				}
			}
		}
		if (!"".equals(finalURL.getHost()) && finalURL.toExternalForm().indexOf(' ') != -1) {
			throw new MalformedURLException("There are blanks in the URL: " + finalURL.toExternalForm());
		}
		return finalURL;
	}

	/**
	 * Guess url.
	 *
	 * @param spec
	 *            the spec
	 * @return the url
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public static URL guessURL(String spec) throws MalformedURLException {
		return guessURL(null, spec);
	}

	/**
	 * Gets the charset.
	 *
	 * @param connection
	 *            the connection
	 * @return the charset
	 */
	public static String getCharset(URLConnection connection) {
		String contentType = connection.getContentType();
		if (contentType == null) {
			return getDefaultCharset(connection);
		}
		StringTokenizer tok = new StringTokenizer(contentType, ";");
		if (tok.hasMoreTokens()) {
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				String assignment = tok.nextToken().trim();
				int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					String varName = assignment.substring(0, eqIdx).trim();
					if ("charset".equalsIgnoreCase(varName)) {
						String varValue = assignment.substring(eqIdx + 1);
						return Strings.unquote(varValue.trim());
					}
				}
			}
		}
		return getDefaultCharset(connection);
	}

	/**
	 * Gets the default charset.
	 *
	 * @param connection
	 *            the connection
	 * @return the default charset
	 */
	private static String getDefaultCharset(URLConnection connection) {
		URL url = connection.getURL();
		if (Urls.isLocalFile(url)) {
			String charset = System.getProperty("file.encoding");
			return charset == null ? "UTF-8" : charset;
		} else {
			return "UTF-8";
		}
	}

	/**
	 * Gets the no ref form.
	 *
	 * @param url
	 *            the url
	 * @return the no ref form
	 */
	public static String getNoRefForm(URL url) {
		String host = url.getHost();
		int port = url.getPort();
		String portText = port == -1 ? "" : ":" + port;
		String userInfo = url.getUserInfo();
		String userInfoText = userInfo == null || userInfo.length() == 0 ? "" : userInfo + "@";
		String hostPort = host == null || host.length() == 0 ? "" : "//" + userInfoText + host + portText;
		return url.getProtocol() + ":" + hostPort + url.getFile();
	}

	/**
	 * Comparison that does not consider Ref.
	 *
	 * @param url1
	 *            the url1
	 * @param url2
	 *            the url2
	 * @return true, if successful
	 */
	public static boolean sameNoRefURL(URL url1, URL url2) {
		return Objects.equals(url1.getHost(), url2.getHost()) && Objects.equals(url1.getProtocol(), url2.getProtocol())
				&& url1.getPort() == url2.getPort() && Objects.equals(url1.getFile(), url2.getFile())
				&& Objects.equals(url1.getUserInfo(), url2.getUserInfo());
	}

	/**
	 * Converts the given URL into a valid URL by encoding illegal characters.
	 * Right now it is implemented like in IE7: only spaces are replaced with
	 * "%20". (Firefox 3 also encodes other non-ASCII and some ASCII
	 * characters).
	 *
	 * @param url
	 *            URL to convert
	 * @return the encoded URL
	 */
	public static String encodeIllegalCharacters(String url) {
		return url.replace(" ", "%20");
	}

	/**
	 * Converts the given URL into a valid URL by removing control characters
	 * <code>(ASCII code &lt; 32)</code>.
	 *
	 * @param url
	 *            URL to convert
	 * @return the encoded URL
	 */
	public static String removeControlCharacters(String url) {
		StringBuilder sb = new StringBuilder(url.length());
		for (int i = 0; i < url.length(); i++) {
			char c = url.charAt(i);
			if (c >= 32) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static boolean isAbsolute(String url){
		boolean result = false;
		
		if (url.startsWith("//")) {
	        return true;
	    }

	    if (url.startsWith("/")){
	        return false;
	    }

	    try {
	        URI uri = new URI(url);
	        result = uri.isAbsolute();
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return result;
	}
}
