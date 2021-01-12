/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.store;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>NavigationStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class NavigationStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NavigationStore.class.getName());

	private final String DELETE_HOST = "DELETE FROM HOST";
	
	private final String DELETE_HOST_BY_URL = "DELETE FROM HOST where baseUrl = ?";

	private final String HOST = "SELECT DISTINCT baseUrl FROM HOST LIMIT ?";
	
	private final String HOST_ORDERED = "SELECT baseUrl FROM HOST where tab = ? ORDER BY dt ASC";

	private final String INSERT_HOST = "INSERT INTO HOST (baseUrl, tab, dt) VALUES(?,?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

	/**
	 * <p>addAsRecent.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param index a int.
	 */
	public void addAsRecent(String uri, int index) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_HOST)) {
			pstmt.setString(1, new URL(uri).toExternalForm());
			pstmt.setInt(2, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteHost.</p>
	 */
	public void deleteHost() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_HOST)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteHost.</p>
	 */
	public void deleteHost(String host) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_HOST_BY_URL)) {
			pstmt.setString(1, host);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>getHostOrdered.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getHostOrdered(int index) {
		final List<String> recentHostEntries = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(HOST_ORDERED)) {
			pstmt.setInt(1, index);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					recentHostEntries.add(rs.getString(1));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return recentHostEntries;
	}

	/**
	 * <p>getRecentHostEntries.</p>
	 *
	 * @param maxNumItems a int.
	 * @return a {@link java.util.List} object.
	 */
	public List<String[]> getRecentHostEntries(int maxNumItems) {
		final List<String[]> recentHostEntries = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(HOST)) {
			pstmt.setInt(1, maxNumItems);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					recentHostEntries.add(new String[] { rs.getString(1) });
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return recentHostEntries;
	}
}
