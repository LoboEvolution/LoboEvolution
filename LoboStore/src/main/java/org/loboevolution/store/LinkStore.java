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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>LinkStore class.</p>
 */
public class LinkStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LinkStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();
	
	/**
	 * <p>isVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isVisited(final String link) {
        boolean vis = false;
    	try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.LINK)) {
    		pstmt.setString(1, link);
			try (final ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					vis = rs.getInt(1) > 0;
				}
			}
        } catch (final Exception e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return vis;
    }	

	/**
	 * <p>insertLinkVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 */
	public static void insertLinkVisited(final String link) {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_LINK)) {
			pstmt.setString(1, link);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteLinks.</p>
	 */
	public static void deleteLinks() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_LINK)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
