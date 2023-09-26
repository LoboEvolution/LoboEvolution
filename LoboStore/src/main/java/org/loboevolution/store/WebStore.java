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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>WebStore class.</p>
 */
public class WebStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(WebStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();
	
	/**
	 * <p>getValue.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @param session a {@link java.lang.Integer} object.
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getValue(final String key, final int session, final int index) {
		String name = null;
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_VALUE)) {
			pstmt.setString(1, key);
			pstmt.setInt(2, session);
			pstmt.setInt(3, index);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					name = rs.getString(1);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return name;
	}
	
	/**
	 * <p>getMapStorage.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 * @param index a {@link java.lang.Integer} object.
	 * @param session a {@link java.lang.Integer} object.
	 */
	public static Map<String, String> getMapStorage(final int index, final int session) {
		final Map<String, String> map = new HashMap<>();
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_MAP)) {
			pstmt.setInt(1, index);
			pstmt.setInt(2, session);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					map.put(rs.getString(1), rs.getString(2));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * <p>insertStorage.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param session a int.
	 * @param tabIndex a int.
	 */
	public static void insertStorage(final String name, final String value, final int session, final int tabIndex) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE)) {
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.setInt(3, session);
			pstmt.setInt(4, tabIndex);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteStorage.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param session int object
	 * @param index int object.
	 */
	public static void deleteStorage(final String name, final int session, final int index) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_DELETE_KEY)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, session);
			pstmt.setInt(3, index);
			pstmt.executeUpdate();
		} catch (final Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>deleteStorage.</p>
	 *
	 * @param session int object.
	 * @param index int object.
	 */
	public static void deleteStorage(final int session, final int index) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_WEBSTORAGE)) {
			pstmt.setInt(1, session);
			pstmt.setInt(2, index);
			pstmt.executeUpdate();
		} catch (final Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>deleteSessionStorage.</p>
	 */
	public static void deleteSessionStorage() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_ALL_WEBSTORAGE)) {
			pstmt.setInt(1, 1);
			pstmt.executeUpdate();
		} catch (final Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>countStorage.</p>
	 *
	 * @param index int object.
	 * @return a int object.
	 */
	public static int countStorage(final int index) {
		int check = 0;
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_SIZE)) {
			pstmt.setInt(1, index);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					check = rs.getInt(1);
				}
			}
		} catch (final Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
		return check;
	}
}
