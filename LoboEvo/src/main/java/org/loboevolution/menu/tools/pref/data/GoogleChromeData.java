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

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.loboevolution.http.CookieManager;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.store.BookmarksStore;
import org.loboevolution.store.DatabseSQLite;
import org.loboevolution.store.NavigationStore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * <p>GoogleChromeData class.</p>
 */
@Slf4j
public class GoogleChromeData extends BrowserData {

	private static final String CHROME_COOKIES = "SELECT * from cookies";

	private static final String CHROME_HISTORY = "SELECT DISTINCT url, title from urls";

	private static List<BookmarkInfo> getBookmarkInfo(final String path) {
		final List<BookmarkInfo> listInfo = new ArrayList<>();
		final File f = new File(path);

		try (final Scanner scan = new Scanner(f)) {
			final StringBuilder str = new StringBuilder();
			while (scan.hasNext()) {
				str.append(scan.nextLine());
			}
			final JSONObject jsonObject = new JSONObject(str.toString());
			final JSONObject root = jsonObject.getJSONObject("roots");
			final JSONObject bookmarks = root.getJSONObject("bookmark_bar");
			final JSONArray childrens = bookmarks.getJSONArray("children");
			for (int i = 0; i < childrens.length(); i++) {
				final JSONObject temp = childrens.getJSONObject(i);
				final BookmarkInfo bookmark = new BookmarkInfo();
				if (temp.optString("url").contains("http")) {
					bookmark.setUrl(temp.optString("url"));
					bookmark.setTitle(temp.optString("name"));
					bookmark.setDescription(temp.optString("name"));
					listInfo.add(bookmark);
				}
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return listInfo;
	}

	private static List<CookieInfo> getCookieInfo(final String path) {
		final List<CookieInfo> cookies = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DatabseSQLite.JDBC_SQLITE + path);
             final PreparedStatement pstmt = conn.prepareStatement(CHROME_COOKIES);
             final ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				cookies.add(CookieInfo.builder()
						.domain(rs.getString(2))
						.name(rs.getString(3))
						.value(rs.getString(4))
						.path(rs.getString(5))
						.expires(rs.getString(6))
						.secure(rs.getInt(7) > 0)
						.httpOnly(rs.getInt(8) > 0)
						.build());
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return cookies;
	}

	private static List<BookmarkInfo> getHostEntries(final String path) {
		final List<BookmarkInfo> hostEntries = new ArrayList<>();
		try (final Connection conn = DriverManager.getConnection(DatabseSQLite.JDBC_SQLITE + path);
             final PreparedStatement pstmt = conn.prepareStatement(CHROME_HISTORY);
             final ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				final BookmarkInfo info = new BookmarkInfo();
				info.setUrl(rs.getString(1));
				info.setTitle(rs.getString(2));
				hostEntries.add(info);
			}

		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return hostEntries;
	}

	/**
	 * <p>importBookmark.</p>
	 */
	public static void importBookmark() {
		final String pathToCookieInfos = getChromeDirectory();
		final List<String> files = getFiles(pathToCookieInfos, null, "Bookmarks");
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
		final String pathToCookieInfos = getChromeDirectory();
		final List<String> files = getFiles(pathToCookieInfos, null, "CookieInfos");

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
			final String pathToCookieInfos = getChromeDirectory();
			final List<String> files = getFiles(pathToCookieInfos, null, "History");
			for (final String path : files) {
				final List<BookmarkInfo> hosts = getHostEntries(path);
				for (final BookmarkInfo info : hosts) {
					final NavigationStore nav = new NavigationStore();
					nav.addAsRecent(info.getUrl(), info.getTitle(), -1);
				}
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
