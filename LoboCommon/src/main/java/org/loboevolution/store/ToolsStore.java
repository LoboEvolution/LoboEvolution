package org.loboevolution.store;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ToolsStore.
 */
public class ToolsStore implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String DELETE_SEARCH = "DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'";

	private final String INSERT_SEARCH = "INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)";

	private final String SEARCH2 = "SELECT DISTINCT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC";

	private final String UPDATE_SEARCH = "UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'";

	private final String UPDATE_SEARCH2 = "UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'";

	public void deleteSearchEngine() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public List<SearchEngineStore> getSearchEngines() {
		final List<SearchEngineStore> searchEngineStores = new ArrayList<SearchEngineStore>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(this.SEARCH2)) {
			while (rs != null && rs.next()) {
				final SearchEngineStore se = new SearchEngineStore();
				se.setName(rs.getString(1));
				se.setDescription(rs.getString(2));
				se.setBaseUrl(rs.getString(3));
				se.setQueryParameter(rs.getString(4));
				se.setType(rs.getString(5));
				se.setSelected(rs.getInt(6) == 1 ? true : false);
				searchEngineStores.add(se);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return searchEngineStores;
	}

	/**
	 * Gets the selected search engine.
	 *
	 * @return the selected search engine
	 */
	public SearchEngineStore getSelectedSearchEngine() {
		final List<SearchEngineStore> searchEngineStores = getSearchEngines();
		for (final SearchEngineStore searchEngineStore : searchEngineStores) {
			if (searchEngineStore.isSelected()) {
				return searchEngineStore;
			}
		}
		return null;
	}

	public void insertSearch(String name, String description, String baseUrl, String queryParameter, boolean selected) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_SEARCH)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setString(3, "SEARCH_ENGINE");
			pstmt.setString(4, baseUrl);
			pstmt.setString(5, queryParameter);
			pstmt.setInt(6, selected ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreDefaults() {
		unselectedSearch();
		selectedSearch("Google Web Search");
	}

	public void selectedSearch(String name) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH2)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void unselectedSearch() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
