package org.lobo.http;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.lobo.store.SQLiteCommon;

public class NavigationHistory {

	private final String DELETE_HOST = "DELETE FROM HOST";

	private final String HOST = "SELECT DISTINCT baseUrl FROM HOST LIMIT ?";

	private final String INSERT_HOST = "INSERT INTO HOST (baseUrl) VALUES(?)";

	public void addAsRecent(String uri) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_HOST)) {
			pstmt.setString(1, new URL(uri).toExternalForm());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteHost() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_HOST)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public List<String[]> getRecentHostEntries(int maxNumItems) {
		final List<String[]> recentHostEntries = new ArrayList<String[]>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.HOST)) {
			pstmt.setInt(1, maxNumItems);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					recentHostEntries.add(new String[] { rs.getString(1) });
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return recentHostEntries;
	}
}
