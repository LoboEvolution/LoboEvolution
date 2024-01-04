/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;

/**
 * <p>ExternalResourcesStore class.</p>
 */
@Slf4j
public class ExternalResourcesStore {

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	/** The date pattern. */
	private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

	/**
	 * <p>getSourceCache.</p>
	 *
	 * @param baseUrl a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getSourceCache(final String baseUrl, final String type, final boolean test) {
		String source = "";
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					source = rs.getString(1);
				}
			}

			if (Strings.isBlank(source)) {
				if (!test) deleteCache(baseUrl, type);
			}

		} catch (final Exception err) {
			log.error(err.getMessage(), err);
		}
		return source;
	}

	public void saveCache(final String baseUrl, final String source, final String type) throws Exception {
		final URL url = new URL(baseUrl);
		final URLConnection con = url.openConnection();
		final String eTag = con.getHeaderField("Etag");
		final long lastModified = Urls.getExpiration(con, System.currentTimeMillis());
		final int contentLenght = Integer.parseInt(con.getHeaderField("Content-Length") == null ? "0" : con.getHeaderField("Content-Length"));
		final String cacheControl = con.getHeaderField("Cache-Control");
		boolean isNoStore = false;
		if (cacheControl != null) {
			final StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				final String token = tok.nextToken().trim().toLowerCase();
				if ("no-store".equals(token)) {
					isNoStore = true;
				}
			}
		}

		final int check = checkCache(baseUrl, contentLenght, eTag, type);
		if (!isNoStore && check == 0) {
			insertCache(baseUrl, source, contentLenght, eTag, new Date(lastModified), type);
		}
	}

	private static int checkCache(final String baseUrl, final int contentLenght, final String eTag, final String type) {
		int check = 0;
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CHECK_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setInt(2, contentLenght);
			pstmt.setString(3, eTag);
			pstmt.setString(3, type);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					check = rs.getInt(1);
				}
			}
		} catch (final Exception err) {
			log.error(err.getMessage(), err);
		}
		return check;
	}

	private static void insertCache(final String baseUrl, final String source, final int contentLenght, final String eTag, final Date lastModified,
                                    final String type) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_CACHE)) {
			final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, source);
			pstmt.setInt(3, contentLenght);
			pstmt.setString(4, eTag);
			pstmt.setString(5, lastModified != null ? dateFormatter.format(lastModified) : null);
			pstmt.setString(6, type);
			pstmt.executeUpdate();
		} catch (final Exception err) {
			log.error(err.getMessage(), err);
		}
	}

	public static void deleteAllCache() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_ALL_CACHE)) {
			pstmt.executeUpdate();
		} catch (final Exception err) {
			log.error(err.getMessage(), err);
		}
	}

	private static void deleteCache(final String baseUrl, final String type) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			pstmt.executeUpdate();
		} catch (final Exception err) {
			log.error(err.getMessage(), err);
		}
	}
}
