/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Domains;
import org.loboevolution.common.Strings;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.net.Cookie;
import org.loboevolution.util.DateUtil;

/**
 * <p>CookieStore class.</p>
 */
public class CookieStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CookieStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();
	
	/** The date pattern. */
	private static final String PATTERN = "dd/MM/yyyy";

	static final GeneralInfo settings = GeneralStore.getGeneralInfo();
	
	/**
	 * <p>saveCookie.</p>
	 *
	 * @param urlHostName a {@link java.lang.String} object.
	 * @param cookieSpec a {@link java.lang.String} object.
	 */
	public static void saveCookie(String urlHostName, String cookieSpec) {
		String cookieName = null;
		String cookieValue = null;
		String domain = null;
		String path = "/";
		String expires = null;
		String maxAge = null;
		String secure = null;
		String httpOnly = null;
		boolean hasCookieName = false;
		String[] splitUsingTokenizer = Strings.splitUsingTokenizer(cookieSpec, ";");
		for (String token : splitUsingTokenizer) {
			int idx = token.indexOf('=');
			String name = idx == -1 ? token.trim() : token.substring(0, idx).trim();
			String value = idx == -1 ? "" : Strings.unquote(token.substring(idx + 1).trim());
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

		if (Strings.isNotBlank(cookieName)) {
			if (domain != null) {
				if (Domains.isValidCookieDomain(domain, urlHostName)) {
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
					expiresDate = new Date(System.currentTimeMillis() + Integer.parseInt(maxAge) * 1000);
				} catch (NumberFormatException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			} else if (expires != null) {
				DateUtil du = new DateUtil();
				expiresDate = du.determineDateFormat(expires, Locale.US);
			}

			if (settings.isCookie()) {
				saveCookie(domain, path, cookieName, expiresDate, cookieValue, maxAge, Strings.isNotBlank(secure), Strings.isNotBlank(httpOnly));
			}
		}
	}

	/**
	 * Save cookie.
	 *
	 * @param domain
	 *            the domain
	 * @param path
	 *            the path
	 * @param name
	 *            the name
	 * @param expires
	 *            the expires
	 * @param value
	 *            the value
	 * @param maxAge a {@link java.lang.String} object.
	 * @param secure a boolean.
	 * @param httponly a boolean.
	 */
	public static void saveCookie(String domain, String path, String name, Date expires, String value, String maxAge, boolean secure, boolean httponly) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_COOKIES)) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(PATTERN);
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.setString(3, domain);
			pstmt.setString(4, path);
			pstmt.setString(5, expires != null ? dateFormatter.format(expires) : null);
			pstmt.setString(6, maxAge);
			pstmt.setInt(7, secure ? 1 : 0);
			pstmt.setInt(8, httponly ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getCookies.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param path a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<Cookie> getCookies(String hostName, String path) {
		List<Cookie> cookies = new ArrayList<>();
		if (settings.isCookie()) {
			try (Connection conn = DriverManager.getConnection(DB_PATH);
					PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.COOKIES)) {
				pstmt.setString(1, hostName);
				pstmt.setString(2, path);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						Cookie cookie = new Cookie("", "");
						cookie.setName((rs.getString(1)));
						cookie.setValue((rs.getString(2)));
						cookie.setDomain((rs.getString(3)));
						cookie.setPath((rs.getString(4)));
						cookie.setExpires((rs.getString(5)));
						cookie.setMaxAge((rs.getInt(6)));
						cookie.setSecure(rs.getInt(7) > 0);
						cookie.setHttpOnly(rs.getInt(8) > 0);
						cookies.add(cookie);
					}
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				return null;
			}
		}
		return cookies;
	}
}
