package org.lobo.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TabStore {

	private static String INSERT_TAB = "INSERT INTO TAB (index_tab, url) VALUES(?,?)";

	private static String DELETE_TAB = "DELETE FROM TAB WHERE index_tab = ?";

	private static String TAB = "SELECT url FROM TAB WHERE index_tab = ?";

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
}
