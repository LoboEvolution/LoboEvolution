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

package org.loboevolution.menu.tools.pref.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.loboevolution.http.CookieManager;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.info.CookieInfo;
import org.loboevolution.store.BookmarksStore;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.SQLiteCommon;

/**
 * <p>GoogleChromeData class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class GoogleChromeData extends BrowserData {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GoogleChromeData.class.getName());

	private static final String CHROME_COOKIES = "SELECT * from cookies";

	private static final String CHROME_HISTORY = "SELECT DISTINCT url, title from urls";

	private static List<BookmarkInfo> getBookmarkInfo(String path) {
		final List<BookmarkInfo> listInfo = new ArrayList<>();
		final File f = new File(path);

		try (Scanner scan = new Scanner(f)) {
			StringBuilder str = new StringBuilder();
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return listInfo;
	}

	private static List<CookieInfo> getCookieInfo(String path) {
		final List<CookieInfo> cookies = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(CHROME_COOKIES);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				final CookieInfo cookie = new CookieInfo();
				cookie.setDomain(rs.getString(2));
				cookie.setName(rs.getString(3));
				cookie.setValue(rs.getString(4));
				cookie.setPath(rs.getString(5));
				cookie.setExpires(rs.getString(6));
				cookie.setSecure(rs.getInt(7) > 0);
				cookie.setHttpOnly(rs.getInt(8) > 0);
				cookies.add(cookie);
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return cookies;
	}

	private static List<BookmarkInfo> getHostEntries(String path) {
		final List<BookmarkInfo> hostEntries = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(CHROME_HISTORY);
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
