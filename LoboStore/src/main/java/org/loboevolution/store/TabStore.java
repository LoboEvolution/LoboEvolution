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

import org.loboevolution.info.TabInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>TabStore class.</p>
 */
public class TabStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TabStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private static final String INSERT_TAB = "INSERT INTO TAB (index_tab, url, title) VALUES(?,?,?)";

	private static final String DELETE_TAB = "DELETE FROM TAB WHERE index_tab = ?";
	
	private static final String DELETE_TAB_ALL = "DELETE FROM TAB";

	private static final String TAB = "SELECT url FROM TAB WHERE index_tab = ?";
	
	private static final String TAB_URL = "SELECT DISTINCT url FROM TAB";

	private static final String TABS = "SELECT index_tab, url, title FROM TAB";
	
	/**
	 * <p>insertTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @param url a {@link java.lang.String} object.
	 */
	public static void insertTab(Integer index, String url) {
		insertTab(index, url, null);
	}
	
	/**
	 * <p>insertTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @param url a {@link java.lang.String} object.
	 * @param title a {@link java.lang.String} object.
	 */
	public static void insertTab(Integer index, String url, String title) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_TAB)) {
			pstmt.setInt(1, index);
			pstmt.setString(2, url);
			pstmt.setString(3, title);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 */
	public static void deleteTab(Integer index) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB)) {
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteAll.</p>
	 */
	public static void deleteAll() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB_ALL)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getTab(Integer index) {
		String url = "";
		if (DatabseSQLite.storeExist()) {
			try (Connection conn = DriverManager.getConnection(DB_PATH);
				 PreparedStatement pstmt = conn.prepareStatement(TAB)) {
				pstmt.setInt(1, index);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						url = rs.getString(1);
					}
				}
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return url;
	}
	
	/**
	 * <p>getTabs.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<TabInfo> getTabs() {
		List<TabInfo> urls = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_PATH);
			 PreparedStatement pstmt = conn.prepareStatement(TABS);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				urls.add(TabInfo.builder()
						.indexTab(rs.getString(1))
						.url(rs.getString(2))
						.title(rs.getString(3))
						.build());
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return urls;
	}
	
	/**
	 * <p>getUrls.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getUrls() {
		List<String> urls = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_PATH);
			 PreparedStatement pstmt = conn.prepareStatement(TAB_URL);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				urls.add(rs.getString(1));
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return urls;
	}
}
