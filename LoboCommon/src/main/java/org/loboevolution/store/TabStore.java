package org.loboevolution.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.info.TabInfo;

/**
 * <p>TabStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TabStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TabStore.class.getName());

	private static final String INSERT_TAB = "INSERT INTO TAB (index_tab, url, title) VALUES(?,?,?)";

	private static final String DELETE_TAB = "DELETE FROM TAB WHERE index_tab = ?";
	
	private static final String DELETE_TAB_ALL = "DELETE FROM TAB";

	private static final String TAB = "SELECT url FROM TAB WHERE index_tab = ?";
	
	private static final String TAB_URL = "SELECT DISTINCT url FROM TAB";

	private static final String TABS = "SELECT index_tab, url, title FROM TAB";
	
	/**
	 * <p>insertTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @param url a {@link java.lang.String} object.
	 */
	public static void insertTab(Integer index, String url) {
		insertTab(index, url, null);
	}
	
	/**
	 * <p>insertTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @param url a {@link java.lang.String} object.
	 * @param title a {@link java.lang.String} object.
	 */
	public static void insertTab(Integer index, String url, String title) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_TAB)) {
			pstmt.setInt(1, index);
			pstmt.setString(2, url);
			pstmt.setString(3, title);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 */
	public static void deleteTab(Integer index) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB)) {
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteAll.</p>
	 */
	public static void deleteAll() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB_ALL)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getTab(Integer index) {
		String url = "";
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(TAB)) {
			pstmt.setInt(1, index);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					url = rs.getString(1);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return url;
	}
	
	/**
	 * <p>getTabs.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<TabInfo> getTabs() {
		List<TabInfo> urls = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(TABS)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					TabInfo ti = new TabInfo();
					ti.setIndexTab(rs.getString(1));
					ti.setUrl(rs.getString(2));
					ti.setTitle(rs.getString(3));
					urls.add(ti);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return urls;
	}
	
	/**
	 * <p>getUrls.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getUrls() {
		List<String> urls = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(TAB_URL)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					urls.add(rs.getString(1));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return urls;
	}
}
