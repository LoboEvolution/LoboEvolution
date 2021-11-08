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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.info.BookmarkInfo;

/**
 * <p>InputStore class.</p>
 *
 *
 *
 */
public class InputStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InputStore.class.getName());
	
	/**
	 * <p>autocomplete.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @param type a {@link java.lang.String} object.
	 * @param baseUrl a {@link java.lang.String} object.
	 */
	public static List<String> autocomplete(String type, String value, String baseUrl) {
        List<String> autoList = new ArrayList<>();
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
			PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INPUT)) {
    		pstmt.setString(1, type);
			pstmt.setString(2, "%"+value+"%");
			pstmt.setString(3, baseUrl);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					autoList.add(rs.getString(1));
				}
			}
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return autoList;
    }
	
	/**
	 * <p>getPassword.</p>
	 *
	 * @param maxNumItems a int.
	 * @return a {@link java.util.List} object.
	 */
	public List<BookmarkInfo> getPassword(int maxNumItems) {
        List<BookmarkInfo> autoList = new ArrayList<>();
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
			PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INPUT_LIMIT)) {
    		pstmt.setInt(1, maxNumItems);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					BookmarkInfo info = new BookmarkInfo();
					info.setDescription(rs.getString(1));
					info.setTitle(rs.getString(2));
					info.setUrl(rs.getString(3));
					autoList.add(info);
				}
			}
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return autoList;
    }

	/**
	 * <p>insertLogin.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param navigationEnabled a boolean.
	 * @param baseUrl a {@link java.lang.String} object.
	 */
	public static void insertLogin(String type, String value, String baseUrl, boolean navigationEnabled) {
		if (navigationEnabled) {
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
					PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_INPUT)) {
				pstmt.setString(1, type);
				pstmt.setString(2, value);
				pstmt.setString(3, baseUrl);
				pstmt.executeUpdate();
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * <p>deleteInput.</p>
	 */
	public static void deleteInput() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_INPUT)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteInput.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @param baseUrl a {@link java.lang.String} object.
	 */
	public static void deleteInput(String value, String baseUrl) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_INPUT2)) {
			pstmt.setString(1, value);
			pstmt.setString(2, baseUrl);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
