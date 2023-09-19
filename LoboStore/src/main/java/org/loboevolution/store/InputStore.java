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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.info.BookmarkInfo;

/**
 * <p>InputStore class.</p>
 */
public class InputStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InputStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();
	
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
    	try (Connection conn = DriverManager.getConnection(DB_PATH);
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
    	try (Connection conn = DriverManager.getConnection(DB_PATH);
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
			try (Connection conn = DriverManager.getConnection(DB_PATH);
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
		try (Connection conn = DriverManager.getConnection(DB_PATH);
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
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_INPUT2)) {
			pstmt.setString(1, value);
			pstmt.setString(2, baseUrl);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
