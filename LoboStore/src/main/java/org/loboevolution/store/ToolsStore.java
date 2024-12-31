/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
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
@Slf4j
public class ToolsStore implements QueryStore, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	/**
	 * <p>deleteSearchEngine.</p>
	 */
	public void deleteSearchEngine() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.DELETE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>getSearchEngines.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<SearchEngineStore> getSearchEngines() {
		final List<SearchEngineStore> searchEngineStores = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(this.SEARCH2)) {
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
			log.error(e.getMessage(), e);
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
	public void insertSearch(final String name, final String description, final String baseUrl, final String queryParameter, final boolean selected) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.INSERT_SEARCH)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setString(3, "SEARCH_ENGINE");
			pstmt.setString(4, baseUrl);
			pstmt.setString(5, queryParameter);
			pstmt.setInt(6, selected ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
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
	public void selectedSearch(final String name) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH2)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>unselectedSearch.</p>
	 */
	public void unselectedSearch() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.UPDATE_SEARCH)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
