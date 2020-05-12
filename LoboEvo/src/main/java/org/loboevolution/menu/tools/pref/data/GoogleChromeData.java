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

	private static String CHROME_COOKIES = "SELECT * from cookies";

	private static String CHROME_HISTORY = "SELECT DISTINCT url from urls";

	private static List<BookmarkInfo> getBookmarkInfo(String path) {
		final List<BookmarkInfo> listInfo = new ArrayList<BookmarkInfo>();
		final File f = new File(path);

		try (Scanner scan = new Scanner(f)) {
			String str = "";
			while (scan.hasNext()) {
				str += scan.nextLine();
			}
			final JSONObject jsonObject = new JSONObject(str);
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
			e.printStackTrace();
		}
		return listInfo;
	}

	private static List<CookieInfo> getCookieInfo(String path) {
		final List<CookieInfo> cookies = new ArrayList<CookieInfo>();
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
				cookie.setSecure(rs.getInt(7) > 0 ? true : false);
				cookie.setHttpOnly(rs.getInt(8) > 0 ? true : false);
				cookies.add(cookie);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return cookies;
	}

	private static List<String> getHostEntries(String path) {
		final List<String> hostEntries = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(CHROME_HISTORY);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				hostEntries.add(rs.getString(1));
			}

		} catch (final Exception e) {
			e.printStackTrace();
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
				expires.setTime(Long.valueOf(cookie.getExpires()));
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
				final List<String> hosts = getHostEntries(path);
				for (final String host : hosts) {
					final NavigationStore nav = new NavigationStore();
					nav.addAsRecent(host, -1);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
