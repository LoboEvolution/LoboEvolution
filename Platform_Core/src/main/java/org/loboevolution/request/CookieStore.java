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
 * Created on Jun 1, 2005
 */
package org.loboevolution.request;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.http.Cookie;
import org.loboevolution.http.Domains;
import org.loboevolution.util.DateUtil;
import org.loboevolution.util.Strings;

import com.loboevolution.store.SQLiteCommon;

/**
 * The Class CookieStore.
 *
 * @author J. H. S.
 */
public class CookieStore {

	/** The date pattern. */
	private static final String PATTERN = "dd/MM/yyyy";


	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CookieStore.class);

	/**
	 * Instantiates a new cookie store.
	 */
	public CookieStore() {
	}
	/**
	 * Save cookie.
	 *
	 * @param url
	 *            the url
	 * @param cookieSpec
	 *            the cookie spec
	 */
	public static void saveCookie(URL url, String cookieSpec) {
		saveCookie(url.getHost(), cookieSpec);
	}

	/**
	 * Save cookie.
	 *
	 * @param urlHostName
	 *            the url host name
	 * @param cookieSpec
	 *            the cookie spec
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
				logger.error("name.toLowerCase(): " + name.toLowerCase());
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
			logger.error( "saveCookie(): Invalid cookie spec from '" + urlHostName + "'");
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
				expiresDate = new Date(System.currentTimeMillis() + Integer.parseInt(maxAge) * 1000);
			} catch (NumberFormatException nfe) {
				logger.error("saveCookie(): Max-age is not formatted correctly: " + maxAge + ".");
			}
		} else if (expires != null) {
			DateUtil du = new DateUtil();
			expiresDate = du.determineDateFormat(expires, Locale.US);
		}	
		saveCookie(domain, path, cookieName, expiresDate, cookieValue,maxAge, !Strings.isBlank(secure), !Strings.isBlank(httpOnly));
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
	 */
	private static void saveCookie(String domain, String path, String name, Date expires, String value, String maxAge, boolean secure, boolean httponly) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)")) {
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
			logger.error("saveCookie(): Unable to save cookie named '" + name + "' with domain '" + domain + "'", e);
		}
	}

	/**
	 * Gets the cookies.
	 *
	 * @param hostName
	 *            the host name
	 * @param path
	 *            the path
	 * @return the cookies
	 */
	public static List<Cookie> getCookies(String hostName, String path) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory())) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly "
										   + " FROM COOKIE WHERE domain = ? AND path = ?");

			while (rs!= null && rs.next()) {
				Cookie cookie = new Cookie();
				cookie.setName((rs.getString(1)));
				cookie.setValue((rs.getString(2)));
				cookie.setDomain((rs.getString(3)));
				cookie.setPath((rs.getString(4)));
				cookie.setExpires((rs.getString(5)));
				cookie.setMaxAge((rs.getInt(6)));
				cookie.setSecure(rs.getInt(7) > 0 ? true : false);
				cookie.setHttpOnly(rs.getInt(8) > 0 ? true : false);
				cookies.add(cookie);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return cookies;
	}
}
