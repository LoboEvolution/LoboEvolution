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

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.info.TabInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TabStore class.</p>
 */
@Slf4j
public class TabStore {

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
	public static void insertTab(final Integer index, final String url) {
		insertTab(index, url, null);
	}
	
	/**
	 * <p>insertTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @param url a {@link java.lang.String} object.
	 * @param title a {@link java.lang.String} object.
	 */
	public static void insertTab(final Integer index, final String url, final String title) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(INSERT_TAB)) {
			pstmt.setInt(1, index);
			pstmt.setString(2, url);
			pstmt.setString(3, title);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 */
	public static void deleteTab(final Integer index) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB)) {
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteAll.</p>
	 */
	public static void deleteAll() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_TAB_ALL)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>getTab.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getTab(final Integer index) {
		String url = "";
		if (DatabseSQLite.storeExist()) {
			try (final Connection conn = DriverManager.getConnection(DB_PATH);
                 final PreparedStatement pstmt = conn.prepareStatement(TAB)) {
				pstmt.setInt(1, index);
				try (final ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						url = rs.getString(1);
					}
				}
			} catch (final Exception e) {
				log.error(e.getMessage(), e);
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
		final List<TabInfo> urls = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(TABS);
             final ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				urls.add(TabInfo.builder()
						.indexTab(rs.getString(1))
						.url(rs.getString(2))
						.title(rs.getString(3))
						.build());
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return urls;
	}
	
	/**
	 * <p>getUrls.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getUrls() {
		final List<String> urls = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(TAB_URL);
             final ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				urls.add(rs.getString(1));
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return urls;
	}
}
