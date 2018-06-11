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
package org.loboevolution.primary.gui.bookmarks;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.primary.gui.bookmarks.BookmarkInfo;
import org.loboevolution.util.Strings;

import com.loboevolution.store.SQLiteCommon;

/**
 * The Class BookmarksHistory.
 */
public class BookmarksHistory implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000200000300L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(BookmarksHistory.class);

	/**
	 * Instantiates a new bookmarks history.
	 */
	public BookmarksHistory() {}
	
	
	/**
	 * Gets the bookmarks
	 *
	 * @return bookmarks
	 */
	public List<BookmarkInfo> getBookmarks(Integer num){
		synchronized (this) {
			List<BookmarkInfo> values = new ArrayList<BookmarkInfo>();
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory())) {
				Statement stmt = conn.createStatement();
				String query  = "SELECT name, description, baseUrl, tags FROM BOOKMARKS";
				if(num != null) { query = query + " and rownum = " + num; }
				ResultSet rs = stmt.executeQuery(query);
				while (rs!= null && rs.next()) {
					BookmarkInfo info = new BookmarkInfo();
					info.setTitle(rs.getString(1));
					info.setDescription(rs.getString(2));
					info.setUrl(new URL(rs.getString(3)));
					info.setTags(Strings.splitUsingTokenizer(rs.getString(4), " "));
					values.add(info);
				}
			} catch (Exception e) {
				logger.error(e);
			}
			return values;
		}
	}
	
	/**
	 * Gets the existing info.
	 *
	 * @param item
	 *            the item
	 * @return the existing info
	 */
	public BookmarkInfo getExistingInfo(String item) {
		BookmarkInfo info = null;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.BOOKMARKS)) {
			pstmt.setString(1, item);
			ResultSet rs = pstmt.executeQuery();
			while (rs!= null && rs.next()) {
				info = new BookmarkInfo();
				info.setTitle(rs.getString(1));
				info.setDescription(rs.getString(2));
				info.setUrl(new URL(rs.getString(3)));
				info.setTags(Strings.splitUsingTokenizer(rs.getString(4), " "));
			}
		} catch (Exception e) {
			logger.error(e);
		} 
		return info;
	}
	
	public static void insertBookmark(BookmarkInfo info) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_BOOKMARKS)) {
			pstmt.setString(1, info.getTitle());
			pstmt.setString(2, info.getDescription());
			pstmt.setString(3, info.getUrl().toExternalForm());
			pstmt.setString(4, info.getTagsText());
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static void deleteBookmarks() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_BOOKMARKS)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
}