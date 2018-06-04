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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.http.Cookie;
import org.loboevolution.request.CookieStore;

import com.loboevolution.store.SQLiteCommon;

public class MozilaFirefoxData {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(MozilaFirefoxData.class);

	public static void importCookie() {
		String pathToCookies = SQLiteCommon.getMozillaDirectory();
		List<String> files = getFiles(pathToCookies, null);

		for (String path : files) {
			List<Cookie> cookies = getCookie(path);
			for (Cookie cookie : cookies) {
				Date expires = new Date();
				expires.setTime(Long.valueOf(cookie.getExpires()));
				CookieStore.saveCookie(cookie.getDomain(), cookie.getPath(), cookie.getName(), expires, cookie.getValue(), null, cookie.isSecure(), cookie.isHttpOnly());
			}
		}
	}

	private static List<Cookie> getCookie(String path) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.JDBC_SQLITE + path);
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.MOZ_COOKIES)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				Cookie cookie = new Cookie();
				cookie.setDomain(rs.getString(2));
				cookie.setName(rs.getString(4));
				cookie.setValue(rs.getString(5));
				cookie.setPath(rs.getString(7));
				cookie.setExpires(rs.getString(8));
				cookie.setSecure(rs.getInt(11) > 0 ? true : false);
				cookie.setHttpOnly(rs.getInt(12) > 0 ? true : false);
				cookies.add(cookie);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return cookies;
	}

	private static List<String> getFiles(String directoryPath, List<String> cookieFilePaths) {
		if (cookieFilePaths == null) {
			cookieFilePaths = new ArrayList<String>();
		}

		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath));
			for (Path filePath : stream) {
				if (filePath.getFileName().toString().equals("cookies.sqlite")) {
					cookieFilePaths.add(filePath.toAbsolutePath().toString());
				} else if ((new File(filePath.toAbsolutePath().toString())).isDirectory()) {
					getFiles(filePath.toAbsolutePath().toString(), cookieFilePaths);
				}
			}
		} catch (IOException | DirectoryIteratorException x) {
			logger.error(x);
		}
		return cookieFilePaths;
	}
}