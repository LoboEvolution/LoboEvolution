/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 *
 *
 *
 */
public class WebStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(WebStore.class.getName());
	
	
	/**
	 * <p>getValue.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getValue(String key, int index) {
		String name = null;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_VALUE)) {
			pstmt.setString(1, key);
			pstmt.setInt(2, index);
			try (ResultSet rs = pstmt.executeQuery()) {
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
	 * @param index a int.
	 */
	public static Map<String, String> getMapStorage(int index) {
		Map<String, String> map = new HashMap<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_MAP)) {
			pstmt.setInt(1, index);
			try (ResultSet rs = pstmt.executeQuery()) {
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
	public static void insertStorage(String name, String value, int session, int tabIndex) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE)) {
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.setInt(3, session);
			pstmt.setInt(4, tabIndex);
			pstmt.executeUpdate();
		} catch (Exception e) {
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
	public static void deleteStorage(String name, int session, int index) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_DELETE_KEY)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, session);
			pstmt.setInt(3, index);
			pstmt.executeUpdate();
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>deleteStorage.</p>
	 *
	 * @param session int object.
	 * @param index int object.
	 */
	public static void deleteStorage(int session, int index) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_WEBSTORAGE)) {
			pstmt.setInt(1, session);
			pstmt.setInt(2, index);
			pstmt.executeUpdate();
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>deleteSessionStorage.</p>
	 */
	public static void deleteSessionStorage() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_ALL_WEBSTORAGE)) {
			pstmt.setInt(1, 1);
			pstmt.executeUpdate();
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
	
	/**
	 * <p>countStorage.</p>
	 *
	 * @param index int object.
	 * @return a int object.
	 */
	public static int countStorage(int index) {
		int check = 0;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.WEBSTORAGE_SIZE)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				pstmt.setInt(1, index);
				while (rs != null && rs.next()) {
					check = rs.getInt(1);
				}
			}
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
		return check;
	}
}
