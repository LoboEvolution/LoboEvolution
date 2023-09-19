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

package org.loboevolution.menu.tools.pref.data;

import org.loboevolution.http.CookieManager;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.store.BookmarksStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.DatabseSQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>MozilaFirefoxData class.</p>
 */
public class MozilaFirefoxData extends BrowserData {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MozilaFirefoxData.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private static final String MOZ_BOOKMARKS = "SELECT DISTINCT places.url, book.title, places.description FROM moz_bookmarks book, moz_places places WHERE book.fk = places.id AND instr(places.url, 'http') > 0";

	private static final String MOZ_COOKIES = "SELECT * from moz_cookies";

	private static final String MOZ_HISTORY = "SELECT DISTINCT places.url, places.title FROM moz_historyvisits vis, moz_places places WHERE vis.place_id = places.id";

	private static List<BookmarkInfo> getBookmarkInfo(String path) {
		final List<BookmarkInfo> bookmarks = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DatabseSQLite.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(MOZ_BOOKMARKS);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				final BookmarkInfo bookmark = new BookmarkInfo();
				bookmark.setUrl(rs.getString(1));
				bookmark.setTitle(rs.getString(2));
				bookmark.setDescription(rs.getString(3));
				bookmarks.add(bookmark);
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return bookmarks;
	}

	private static List<CookieInfo> getCookieInfo(String path) {
		final List<CookieInfo> cookies = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DatabseSQLite.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(MOZ_COOKIES);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				cookies.add(CookieInfo.builder()
						.domain(rs.getString(2))
						.name(rs.getString(4))
						.value(rs.getString(5))
						.path(rs.getString(7))
						.expires(rs.getString(8))
						.secure(rs.getInt(11) > 0)
						.httpOnly(rs.getInt(12) > 0)
						.build());
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return cookies;
	}

	private static List<BookmarkInfo> getHostEntries(String path) {
		final List<BookmarkInfo> hostEntries = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DatabseSQLite.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(MOZ_HISTORY);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				BookmarkInfo info = new BookmarkInfo();
				info.setUrl(rs.getString(1));
				info.setTitle(rs.getString(2));
				hostEntries.add(info);
			}

		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return hostEntries;
	}

	/**
	 * <p>importBookmark.</p>
	 */
	public static void importBookmark() {
		final String pathToCookieInfos = getMozillaDirectory();
		final List<String> files = getFiles(pathToCookieInfos, null, "places.sqlite");

		for (final String path : files) {
			final List<BookmarkInfo> bookmarks = getBookmarkInfo(path);
			for (final BookmarkInfo info : bookmarks) {
				final BookmarksStore hist = new BookmarksStore();
				hist.insertBookmark(info);
			}
		}
	}

	/**
	 * <p>importCookie.</p>
	 */
	public static void importCookie() {
		final String pathToCookieInfos = getMozillaDirectory();
		final List<String> files = getFiles(pathToCookieInfos, null, "cookies.sqlite");

		for (final String path : files) {
			final List<CookieInfo> cookies = getCookieInfo(path);
			for (final CookieInfo cookie : cookies) {
				final Date expires = new Date();
				expires.setTime(Long.parseLong(cookie.getExpires()));
				CookieManager.saveCookie(cookie.getDomain(), cookie.getPath(), cookie.getName(), expires,
						cookie.getValue(), null, cookie.isSecure(), cookie.isHttpOnly());
			}
		}
	}

	/**
	 * <p>importHistory.</p>
	 */
	public static void importHistory() {
		try {
			final String pathToCookieInfos = getMozillaDirectory();
			final List<String> files = getFiles(pathToCookieInfos, null, "places.sqlite");
			for (final String path : files) {
				final List<BookmarkInfo> hosts = getHostEntries(path);
				for (final BookmarkInfo info : hosts) {
					final NavigationStore nav = new NavigationStore();
					nav.addAsRecent(info.getUrl(), info.getTitle(), -1);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
