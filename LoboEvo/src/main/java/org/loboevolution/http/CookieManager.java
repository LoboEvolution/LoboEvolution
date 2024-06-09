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

package org.loboevolution.http;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Domains;
import org.loboevolution.common.Strings;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.store.DatabseSQLite;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.SQLiteCommon;
import org.loboevolution.util.DateUtil;

/**
 * <p>CookieManager class.</p>
 */
@Slf4j
public class CookieManager {

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private static final String DELETE_COOKIES = "DELETE FROM COOKIE";

	/** The date pattern. */
	private static final String PATTERN = "dd/MM/yyyy";

	static final GeneralInfo settings = GeneralStore.getGeneralInfo();

	/**
	 * <p>deleteCookies.</p>
	 */
	public static void deleteCookies() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_COOKIES)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>getCookieList.</p>
	 *
	 * @param address a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<CookieInfo> getCookieList(final String address) {
		final URI url;
		List<CookieInfo> cookies = new ArrayList<>();
		try {
			url = new URI(address);
			final String domain = url.getHost().replaceFirst("^www.*?\\.", "");
			cookies = getCookies(domain, "/");
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return cookies;
	}

	private static List<CookieInfo> getCookies(final String hostName, final String path) {
		final List<CookieInfo> cookies = new ArrayList<>();
		if (settings.isCookie()) {
			try (final Connection conn = DriverManager.getConnection(DB_PATH);
                 final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.COOKIES)) {
				pstmt.setString(1, hostName);
				pstmt.setString(2, path);
				try (final ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						final CookieInfo cookie = new CookieInfo();
						cookie.setName(rs.getString(1));
						cookie.setValue(rs.getString(2));
						cookie.setDomain(rs.getString(3));
						cookie.setPath(rs.getString(4));
						cookie.setExpires(rs.getString(5));
						cookie.setMaxAge(rs.getInt(6));
						cookie.setSecure(rs.getInt(7) > 0);
						cookie.setHttpOnly(rs.getInt(8) > 0);
						cookies.add(cookie);
					}
				}
			} catch (final Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		}
		return cookies;
	}

	/**
	 * <p>putCookies.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public static void putCookies(final String uri) {
		try {
			final URL url = new URI(uri).toURL();
			final URLConnection connection = url.openConnection();
			final Map<String, List<String>> headerFields = connection.getHeaderFields();
			for (final Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
				final String key = entry.getKey();
				for (final String value : entry.getValue()) {
					if (value != null
							&& ("Set-Cookie".equalsIgnoreCase(key) || "Set-Cookie2".equalsIgnoreCase(key)
							|| "Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key))) {
						saveCookie(url.getHost(), value);
					}
				}
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static void saveCookie(final String urlHostName, final String cookieSpec) {
		String cookieName = null;
		String cookieValue = null;
		String domain = null;
		String path = "/";
		String expires = null;
		String maxAge = null;
		String secure = null;
		String httpOnly = null;
		boolean hasCookieName = false;
		final String[] splitUsingTokenizer = Strings.splitUsingTokenizer(cookieSpec, ";");
		for (final String token : splitUsingTokenizer) {
			final int idx = token.indexOf('=');
			final String name = idx == -1 ? token.trim() : token.substring(0, idx).trim();
			final String value = idx == -1 ? "" : Strings.unquote(token.substring(idx + 1).trim());
			if (!hasCookieName) {
				cookieName = name;
				cookieValue = value;
				hasCookieName = true;
			} else {
				switch (name.toLowerCase()) {
				case "max-age":
					maxAge = value;
					break;
				case "path":
					path = value;
					break;
				case "domain":
					domain = value;
					break;
				case "expires":
					expires = value;
					break;
				case "secure":
					secure = "secure";
					break;
				case "httponly":
					httpOnly = "httpOnly";
					break;
				default:
					break;
				}
			}
		}

		if (cookieName == null) {
			return;
		}

		if (domain != null) {
			if (!Domains.isValidCookieDomain(domain, urlHostName)) {
				return;
			} else {
				if (domain.startsWith(".")) {
					domain = domain.substring(1);
				}

			}
		} else {
			domain = urlHostName;
		}

		Date expiresDate = null;
		if (maxAge != null) {
			try {
				expiresDate = new Date(System.currentTimeMillis() + Long.parseLong(maxAge) * 1000);
			} catch (final NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		} else if (expires != null) {
			final DateUtil du = new DateUtil();
			expiresDate = du.determineDateFormat(expires, Locale.US);
		}

		if (settings.isCookie()) {
			saveCookie(domain, path, cookieName, expiresDate, cookieValue, maxAge, Strings.isNotBlank(secure),
					Strings.isNotBlank(httpOnly));
		}
	}

	/**
	 * <p>saveCookie.</p>
	 *
	 * @param domain a {@link java.lang.String} object.
	 * @param path a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param expires a {@link java.util.Date} object.
	 * @param value a {@link java.lang.String} object.
	 * @param maxAge a {@link java.lang.String} object.
	 * @param secure a boolean.
	 * @param httponly a boolean.
	 */
	public static void saveCookie(final String domain, final String path, final String name, final Date expires, final String value, final String maxAge,
                                  final boolean secure, final boolean httponly) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_COOKIES)) {
			final SimpleDateFormat dateFormatter = new SimpleDateFormat(PATTERN);
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.setString(3, domain);
			pstmt.setString(4, path);
			pstmt.setString(5, expires != null ? dateFormatter.format(expires) : null);
			pstmt.setString(6, maxAge);
			pstmt.setInt(7, secure ? 1 : 0);
			pstmt.setInt(8, httponly ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>saveCookie.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @param cookieSpec a {@link java.lang.String} object.
	 */
	public static void saveCookie(final URL url, final String cookieSpec) {
		saveCookie(url.getHost(), cookieSpec);
	}

}
