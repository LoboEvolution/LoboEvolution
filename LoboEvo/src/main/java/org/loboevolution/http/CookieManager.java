package org.loboevolution.http;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Domains;
import org.loboevolution.common.Strings;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.SQLiteCommon;
import org.loboevolution.util.DateUtil;

/**
 * <p>CookieManager class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CookieManager {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CookieManager.class.getName());

	private static String DELETE_COOKIES = "DELETE FROM COOKIE";

	/** The date pattern. */
	private static String PATTERN = "dd/MM/yyyy";

	/**
	 * <p>deleteCookies.</p>
	 */
	public static void deleteCookies() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_COOKIES)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getCookieList.</p>
	 *
	 * @param address a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<CookieInfo> getCookieList(String address) {
		URL url;
		List<CookieInfo> cookies = new ArrayList<CookieInfo>();
		try {
			url = new URL(address);
			final String domain = url.getHost().replaceFirst("^www.*?\\.", "");
			cookies = getCookies(domain, "/");
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return cookies;
	}

	private static List<CookieInfo> getCookies(String hostName, String path) {
		final List<CookieInfo> cookies = new ArrayList<CookieInfo>();
		final GeneralStore settings = GeneralStore.getNetwork();
		if (settings.isCookie()) {
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
					PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.COOKIES)) {
				pstmt.setString(1, hostName);
				pstmt.setString(2, path);
				try (ResultSet rs = pstmt.executeQuery()) {
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
				logger.log(Level.SEVERE, e.getMessage(), e);
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
	public static void putCookies(String uri) {
		try {
			final URL url = new URL(uri);
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void saveCookie(String urlHostName, String cookieSpec) {
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
				expiresDate = new Date(System.currentTimeMillis() + Integer.parseInt(maxAge) * 1000);
			} catch (final NumberFormatException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		} else if (expires != null) {
			final DateUtil du = new DateUtil();
			expiresDate = du.determineDateFormat(expires, Locale.US);
		}

		final GeneralStore settings = GeneralStore.getNetwork();
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
	public static void saveCookie(String domain, String path, String name, Date expires, String value, String maxAge,
			boolean secure, boolean httponly) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_COOKIES)) {
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>saveCookie.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 * @param cookieSpec a {@link java.lang.String} object.
	 */
	public static void saveCookie(URL url, String cookieSpec) {
		saveCookie(url.getHost(), cookieSpec);
	}

}
