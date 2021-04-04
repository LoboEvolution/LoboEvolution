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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>LinkStore class.</p>
 *
 *
 *
 */
public class LinkStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LinkStore.class.getName());
	
	/**
	 * <p>isVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isVisited(String link) {
        boolean vis = false;
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
			PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.LINK)) {
    		pstmt.setString(1, link);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					vis = rs.getInt(1) > 0;
				}
			}
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return vis;
    }	

	/**
	 * <p>insertLinkVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 */
	public static void insertLinkVisited(String link) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_LINK)) {
			pstmt.setString(1, link);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteLinks.</p>
	 */
	public static void deleteLinks() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_LINK)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
