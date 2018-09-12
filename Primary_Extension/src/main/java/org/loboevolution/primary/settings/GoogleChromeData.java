/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.loboevolution.http.Cookie;
import org.loboevolution.primary.ext.history.NavigationHistory;
import org.loboevolution.primary.gui.bookmarks.BookmarkInfo;
import org.loboevolution.primary.gui.bookmarks.BookmarksHistory;
import org.loboevolution.request.CookieStore;

import com.loboevolution.store.SQLiteCommon;

public class GoogleChromeData {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(GoogleChromeData.class);

	public static void importCookie() {
		String pathToCookies = SQLiteCommon.getChromeDirectory();
		List<String> files = getFiles(pathToCookies, null, "Cookies");

		for (String path : files) {
			List<Cookie> cookies = getCookie(path);
			for (Cookie cookie : cookies) {
				Date expires = new Date();
				expires.setTime(Long.valueOf(cookie.getExpires()));
				CookieStore.saveCookie(cookie.getDomain(), cookie.getPath(), cookie.getName(), expires,
						cookie.getValue(), null, cookie.isSecure(), cookie.isHttpOnly());
			}
		}
	}
	
	public static void importHistory() {
		try {
			String pathToCookies = SQLiteCommon.getChromeDirectory();
			List<String> files = getFiles(pathToCookies, null, "History");
			for (String path : files) {
				List<String> hosts = getHostEntries(path);
				for (String host : hosts) {
					NavigationHistory.addAsRecent(new URL(host));
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void importBookmark() {
		String pathToCookies = SQLiteCommon.getChromeDirectory();
		List<String> files = getFiles(pathToCookies, null, "Bookmarks");
		for (String path : files) {
			List<BookmarkInfo> bookmarks = getBookmarkInfo(path);
			for (BookmarkInfo info : bookmarks) {
				BookmarksHistory.insertBookmark(info);
			}
		}
	}

	private static List<BookmarkInfo> getBookmarkInfo(String path) {
		List<BookmarkInfo> listInfo = new ArrayList<BookmarkInfo>();
		File f = new File(path);

		try (Scanner scan = new Scanner(f)) {
			String str = "";
			while (scan.hasNext()) {
				str += scan.nextLine();
			}
			JSONObject jsonObject = new JSONObject(str);
			JSONObject root = jsonObject.getJSONObject("roots");
			JSONObject bookmarks = root.getJSONObject("bookmark_bar");
			JSONArray childrens = bookmarks.getJSONArray("children");
			for (int i = 0; i < childrens.length(); i++) {
				JSONObject temp = childrens.getJSONObject(i);
				BookmarkInfo bookmark = new BookmarkInfo();
				if (temp.optString("url").contains("http")) {
					bookmark.setUrl(new URL(temp.optString("url")));
					bookmark.setTitle(temp.optString("name"));
					bookmark.setDescription(temp.optString("name"));
					listInfo.add(bookmark);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return listInfo;
	}
	
	private static List<String> getHostEntries(String path) {
		List<String> hostEntries = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CHROME_HISTORY);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				hostEntries.add(rs.getString(1));
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return hostEntries;
	}

	private static List<Cookie> getCookie(String path) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CHROME_COOKIES);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs != null && rs.next()) {
				Cookie cookie = new Cookie("", "");
				cookie.setDomain(rs.getString(2));
				cookie.setName(rs.getString(3));
				cookie.setValue(rs.getString(4));
				cookie.setPath(rs.getString(5));
				cookie.setExpires(rs.getString(6));
				cookie.setSecure(rs.getInt(7) > 0 ? true : false);
				cookie.setHttpOnly(rs.getInt(8) > 0 ? true : false);
				cookies.add(cookie);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return cookies;
	}

	private static List<String> getFiles(String directoryPath, List<String> cookieFilePaths, String fileName) {
		if (cookieFilePaths == null) {
			cookieFilePaths = new ArrayList<String>();
		}

		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath));
			for (Path filePath : stream) {
				if (filePath.getFileName().toString().equals(fileName)) {
					cookieFilePaths.add(filePath.toAbsolutePath().toString());
				} else if ((new File(filePath.toAbsolutePath().toString())).isDirectory()) {
					getFiles(filePath.toAbsolutePath().toString(), cookieFilePaths, fileName);
				}
			}
		} catch (IOException | DirectoryIteratorException x) {
			logger.error(x);
		}
		return cookieFilePaths;
	}
}
