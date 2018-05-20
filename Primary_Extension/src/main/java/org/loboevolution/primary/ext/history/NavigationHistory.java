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
/*
 * Created on Jun 6, 2005
 */
package org.loboevolution.primary.ext.history;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.loboevolution.store.SQLiteCommon;

/**
 * History of navigation locations. Not thread safe.
 */
public class NavigationHistory implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000600200100L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(NavigationHistory.class);

	/**
	 * Instantiates a new navigation history.
	 */
	public NavigationHistory() {}
		
	/**
	 * Gets the opened files.
	 *
	 * @return opened files
	 */
	public static List<String[]> getFiles(String type) {
		List<String[]> values = new ArrayList<String[]>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT description FROM SEARCH WHERE type = ?")) {
			pstmt.setString(1, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				values.add(new String[] { rs.getString(1) });
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return values;
	}
	
	/**
	 * Gets the recent host entries.
	 *
	 * @param maxNumItems
	 *            the max num items
	 * @return the recent host entries
	 */
	public static List<String[]> getRecentHostEntries(int maxNumItems) {
		List<String[]> recentHostEntries = new ArrayList<String[]>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT baseUrl FROM HOST LIMIT ?")) {
			pstmt.setInt(1, maxNumItems);
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				recentHostEntries.add(new String[] { rs.getString(1) });
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return recentHostEntries;
	}
	

	/**
	 * Gets the recent host entries.
	 *
	 * @param maxNumItems
	 *            the max num items
	 * @return the recent host entries
	 */
	public static List<String> getRecentItems(int maxNumItems) {
		List<String> recentHostEntries = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT baseUrl FROM HOST LIMIT ?")) {
			pstmt.setInt(1, maxNumItems);
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				recentHostEntries.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return recentHostEntries;
	}
	
	
	
	
	/**
	 * Gets the head match items.
	 *
	 * @param itemPrefix
	 *            the item prefix
	 * @param maxNumItems
	 *            the max num items
	 * @return the head match items
	 */
	public static List<String> getHeadMatchItems(String itemPrefix) {
		List<String> recentHostEntries = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT baseUrl FROM HOST WHERE baseUrl like ?")) {
			pstmt.setString(1, "%" + itemPrefix + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				recentHostEntries.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return recentHostEntries;
	}
	
	
	/**
	 * Adds the as recent.
	 *
	 * @param url
	 *            the url
	 * @param itemInfo
	 *            the item info
	 */
	public static void addAsRecent(URL url) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO HOST (baseUrl) VALUES(?)")) {
			pstmt.setString(1,  url.toExternalForm());
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
