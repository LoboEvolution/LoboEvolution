package org.loboevolution.store;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.info.BookmarkInfo;

/**
 * The Class BookmarksStore.
 *
 * @author utente
 * @version $Id: $Id
 */
public class BookmarksStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(BookmarksStore.class.getName());

	private final String BOOKMARKS = "SELECT DISTINCT name, description, baseUrl, tags FROM BOOKMARKS WHERE baseUrl = ?";

	private final String DELETE_BOOKMARKS = "DELETE FROM BOOKMARKS";
	
	private final String DELETE_BOOKMARKS_BY_URL = "DELETE FROM BOOKMARKS WHERE baseUrl = ?";

	private final String INSERT_BOOKMARKS = "INSERT INTO BOOKMARKS (name, description, baseUrl, tags) VALUES(?,?,?,?)";

	/**
	 * <p>deleteBookmarks.</p>
	 */
	public void deleteBookmarks() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_BOOKMARKS)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteBookmarks.</p>
	 */
	public void deleteBookmark(String url) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_BOOKMARKS_BY_URL)) {
			pstmt.setString(1, url.trim());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Gets the bookmarks
	 *
	 * @return bookmarks
	 * @param num a {@link java.lang.Integer} object.
	 */
	public List<BookmarkInfo> getBookmarks(Integer num) {
		synchronized (this) {
			final List<BookmarkInfo> values = new ArrayList<>();
			String query = "SELECT name, description, baseUrl, tags FROM BOOKMARKS";
			if (num != null) {
				query = query + " LIMIT " + num;
			}
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query)) {
				while (rs != null && rs.next()) {
					final BookmarkInfo info = new BookmarkInfo();
					info.setTitle(rs.getString(1));
					info.setDescription(rs.getString(2));
					info.setUrl(rs.getString(3));
					info.setTags(Strings.splitUsingTokenizer(rs.getString(4), " "));
					values.add(info);
				}
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
			return values;
		}
	}

	/**
	 * Gets the existing info.
	 *
	 * @param item the item
	 * @return the existing info
	 */
	public BookmarkInfo getExistingInfo(String item) {
		BookmarkInfo info = null;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.BOOKMARKS)) {
			pstmt.setString(1, item);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					info = new BookmarkInfo();
					info.setTitle(rs.getString(1));
					info.setDescription(rs.getString(2));
					info.setUrl(rs.getString(3));
					info.setTags(Strings.splitUsingTokenizer(rs.getString(4), " "));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * <p>insertBookmark.</p>
	 *
	 * @param info a {@link org.loboevolution.info.BookmarkInfo} object.
	 */
	public void insertBookmark(BookmarkInfo info) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_BOOKMARKS)) {
			pstmt.setString(1, info.getTitle());
			pstmt.setString(2, info.getDescription());
			pstmt.setString(3, info.getUrl());
			pstmt.setString(4, info.getTagsText());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
