package org.loboevolution.store;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * General browser settings. This is a singleton class with an instance obtained
 * by calling {@link #getInstance()}.
 */
public class GeneralStore implements Serializable {

	private static String DELETE_NETWORK = "DELETE FROM NETWORK";

	private static String DELETE_SIZE = "DELETE FROM SIZE";

	private static String DELETE_STARTUP = "DELETE FROM STARTUP";

	private static String DELETE_USER_AGENT = "DELETE FROM USER_AGENT";

	private static String INSERT_NETWORK = "INSERT INTO NETWORK (js, css, cookie, cache, navigation) VALUES(?,?,?,?,?)";

	private static String INSERT_SIZE = "INSERT INTO SIZE (width, height) VALUES(?,?)";

	private static String INSERT_STARTUP = "INSERT INTO STARTUP (baseUrl) VALUES(?)";

	private static String INSERT_USER_AGENT = "INSERT INTO USER_AGENT (description) VALUES(?)";

	private static String NETWORK = "SELECT DISTINCT js, css, cookie, cache, navigation FROM NETWORK";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500070000402L;

	private static String SIZE = "SELECT DISTINCT width, height FROM SIZE";

	private static String STARTUP = "SELECT DISTINCT baseUrl FROM STARTUP";

	public static void deleteBounds() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_SIZE)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteNetwork() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_NETWORK)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteStartUpUrl() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_STARTUP)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteUserAgent() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_USER_AGENT)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static Rectangle getInitialWindowBounds() {
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int width = -1;
		int height = -1;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SIZE)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					width = rs.getInt(1);
					height = rs.getInt(2);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}	
		if (width > -1 && height > -1) {
			bounds = new Rectangle(width, height);
		}

		return bounds;
	}

	public static GeneralStore getNetwork() {
		final GeneralStore setting = new GeneralStore();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(NETWORK)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					setting.setJs(rs.getInt(1) == 1 ? true : false);
					setting.setCss(rs.getInt(2) == 1 ? true : false);
					setting.setCookie(rs.getInt(3) == 1 ? true : false);
					setting.setCache(rs.getInt(4) == 1 ? true : false);
					setting.setNavigation(rs.getInt(5) == 1 ? true : false);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return setting;
	}

	public static List<String> getStartupURLs() {
		final List<String> urls = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(STARTUP)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					urls.add(rs.getString(1));
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return urls;
	}

	public static void insertBounds(Rectangle rect) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_SIZE)) {
			pstmt.setInt(1, rect.width);
			pstmt.setInt(2, rect.height);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertNetwork(boolean js, boolean css, boolean cookie, boolean cache, boolean navigation) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_NETWORK)) {
			pstmt.setInt(1, js ? 1 : 0);
			pstmt.setInt(2, css ? 1 : 0);
			pstmt.setInt(3, cookie ? 1 : 0);
			pstmt.setInt(4, cache ? 1 : 0);
			pstmt.setInt(5, navigation ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertStartupUrl(String url) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_STARTUP)) {
			pstmt.setString(1, url);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertUserAgent(String userAgent) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_USER_AGENT)) {
			pstmt.setString(1, userAgent);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void restoreDefaults() {
		final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
		deleteNetwork();
		deleteUserAgent();
		deleteBounds();
		deleteStartUpUrl();
		insertNetwork(true, true, true, true, true);
		insertUserAgent(userAgent);
		insertBounds(new Rectangle(800, 400));
	}

	/** The cache. */
	private boolean cache;

	/** The cookie. */
	private boolean cookie;

	/** The css. */
	private boolean css;

	/** The js. */
	private boolean js;

	/** The navigation. */
	private boolean navigation;

	/** The ie version. */
	private String userAgent;

	/**
	 * @param the userAgent
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * @return the cache
	 */
	public boolean isCache() {
		return this.cache;
	}

	/**
	 * @return the cookie
	 */
	public boolean isCookie() {
		return this.cookie;
	}

	/**
	 * @return the css
	 */
	public boolean isCss() {
		return this.css;
	}

	/**
	 * @return the js
	 */
	public boolean isJs() {
		return this.js;
	}

	/**
	 * @return the navigation
	 */
	public boolean isNavigation() {
		return this.navigation;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(boolean cookie) {
		this.cookie = cookie;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(boolean css) {
		this.css = css;
	}

	/**
	 * @param js the js to set
	 */
	public void setJs(boolean js) {
		this.js = js;
	}

	/**
	 * @param navigation the navigation to set
	 */
	public void setNavigation(boolean navigation) {
		this.navigation = navigation;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
