/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.store;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class ToolsStore.
 *
 *
 *
 */
public class ToolsStore implements Serializable {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ToolsStore.class.getName());

	private static final long serialVersionUID = 1L;

	private final String DELETE_SEARCH = "DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'";

	private final String INSERT_SEARCH = "INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)";

	private final String SEARCH2 = "SELECT DISTINCT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC";

	private final String UPDATE_SEARCH = "UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'";

	private final String UPDATE_SEARCH2 = "UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'";

	/**
	 * <p>deleteSearchEngine.</p>
	 */
	public void deleteSearchEngine() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getSearchEngines.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<SearchEngineStore> getSearchEngines() {
		final List<SearchEngineStore> searchEngineStores = new ArrayList<>();
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
				se.setSelected(rs.getInt(6) == 1);
				searchEngineStores.add(se);
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
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

	/**
	 * <p>insertSearch.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param description a {@link java.lang.String} object.
	 * @param baseUrl a {@link java.lang.String} object.
	 * @param queryParameter a {@link java.lang.String} object.
	 * @param selected a boolean.
	 */
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>restoreDefaults.</p>
	 */
	public void restoreDefaults() {
		unselectedSearch();
		selectedSearch("Google Web Search");
	}

	/**
	 * <p>selectedSearch.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void selectedSearch(String name) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH2)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>unselectedSearch.</p>
	 */
	public void unselectedSearch() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
