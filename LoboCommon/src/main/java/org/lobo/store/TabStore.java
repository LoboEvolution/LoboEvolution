package org.lobo.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.lobo.info.TabInfo;

public class TabStore {

	private static String INSERT_TAB = "INSERT INTO TAB (index_tab, url) VALUES(?,?)";

	private static String DELETE_TAB = "DELETE FROM TAB WHERE index_tab = ?";
	
	private static String DELETE_TAB_ALL = "DELETE FROM TAB";

	private static String TAB = "SELECT url FROM TAB WHERE index_tab = ?";

	private static String TABS = "SELECT index_tab, url FROM TAB";
	
	public static void insertTab(Integer index, String url) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_TAB)) {
			pstmt.setInt(1, index);
			pstmt.setString(2, url);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteTab(Integer index) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB)) {
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAll() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB_ALL)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

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
			e.printStackTrace();
		}
		return url;
	}
	
	public static List<TabInfo> getTabs() {
		List<TabInfo> urls = new ArrayList<TabInfo>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(TABS)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					TabInfo ti = new TabInfo();
					ti.setIndexTab(rs.getString(1));
					ti.setUrl(rs.getString(2));
					urls.add(ti);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return urls;
	}
}
