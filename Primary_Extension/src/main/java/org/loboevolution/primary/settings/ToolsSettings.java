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
package org.loboevolution.primary.settings;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.loboevolution.store.SQLiteCommon;

/**
 * The Class ToolsSettings.
 */
public class ToolsSettings implements Serializable {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ToolsSettings.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500006000800L;

	/**
	 * Instantiates a new tools settings.
	 */
	public ToolsSettings() {
	}

	public List<SearchEngine> getSearchEngines() {
		List<SearchEngine> searchEngines = new ArrayList<SearchEngine>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory())) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC");
			while (rs != null && rs.next()) {
				SearchEngine se = new SearchEngine();
				se.setName(rs.getString(1));
				se.setDescription(rs.getString(2));
				se.setBaseUrl(rs.getString(3));
				se.setQueryParameter(rs.getString(4));
				se.setType(rs.getString(5));
				se.setSelected((rs.getInt(6) == 1 ? true : false));
				searchEngines.add(se);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return searchEngines;
	}

	/**
	 * Gets the selected search engine.
	 *
	 * @return the selected search engine
	 */
	public SearchEngine getSelectedSearchEngine() {
		List<SearchEngine> searchEngines = getSearchEngines();
		for (SearchEngine searchEngine : searchEngines) {
			if (searchEngine.isSelected()) {
				return searchEngine;
			}
		}
		return null;
	}

	public void restoreDefaults() {
		unselectedSearch();
		selectedSearch("Google Web Search");
	}

	public void deleteSearchEngine() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'")) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void insertSearch(String name, String description, String baseUrl, String queryParameter, boolean selected) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)")) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setString(3, "SEARCH_ENGINE");
			pstmt.setString(4, baseUrl);
			pstmt.setString(5, queryParameter);
			pstmt.setInt(6, selected ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void unselectedSearch() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'")) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void selectedSearch(String name) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'")) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
