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

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>DownloadStore class.</p>
 */
@Slf4j
public class DownloadStore {

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private final String DELETE_DOWNLOAD = "DELETE FROM DOWNLOAD";

	private final String DOWNLOAD_ORDERED = "SELECT baseUrl FROM DOWNLOAD ORDER BY dt ASC";

	private final String INSERT_DOWNLOAD = "INSERT INTO DOWNLOAD (baseUrl, dt) VALUES(?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

	/**
	 * <p>addAsRecent.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public void addAsRecent(final String uri) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(INSERT_DOWNLOAD)) {
			pstmt.setString(1, new URL(uri).toExternalForm());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteDownload.</p>
	 */
	public void deleteDownload() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_DOWNLOAD)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>getDownload.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getDownload() {
		final List<String> recent = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DOWNLOAD_ORDERED)) {
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					recent.add(rs.getString(1));
				}
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return recent;
	}
}
