/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.info.BookmarkInfo;

/**
 * <p>NavigationStore class.</p>
 */
public class NavigationStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NavigationStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private final String DELETE_HOST = "DELETE FROM HOST";
	
	private final String DELETE_HOST_BY_URL = "DELETE FROM HOST where baseUrl = ?";

	private final String HOST = "SELECT DISTINCT baseUrl, name FROM HOST ORDER BY dt DESC LIMIT ?";
	
	private final String HOST_TAB = "SELECT baseUrl, name FROM HOST where tab = ? ORDER BY dt DESC";

	private final String INSERT_HOST = "INSERT INTO HOST (baseUrl, name, tab, dt) VALUES(?,?,?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

	/**
	 * <p>addAsRecent.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param index a int.
	 * @param title a {@link java.lang.String} object.
	 */
	public void addAsRecent(String uri, String title, int index) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_HOST)) {
			pstmt.setString(1, new URL(uri).toExternalForm());
			pstmt.setString(2, title);
			pstmt.setInt(3, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteHost.</p>
	 */
	public void deleteHost() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_HOST)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteHost.</p>
	 *
	 * @param host a {@link java.lang.String} object.
	 */
	public void deleteHost(String host) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_HOST_BY_URL)) {
			pstmt.setString(1, host);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>getRecentHost.</p>
	 *
	 * @param index a int.
	 * @param isTab a boolean.
	 * @return a {@link java.util.List} object.
	 */
	public List<BookmarkInfo> getRecentHost(int index, boolean isTab) {
		final List<BookmarkInfo> recentHostEntries = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(isTab ? HOST_TAB : HOST)) {
			pstmt.setInt(1, index);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					BookmarkInfo info = new BookmarkInfo();
					info.setUrl(rs.getString(1));
					info.setTitle(rs.getString(2));
					recentHostEntries.add(info);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return recentHostEntries;
	}
}
