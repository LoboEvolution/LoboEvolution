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
package org.loboevolution.settings;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.loboevolution.store.SQLiteCommon;

/**
 * Settings associated with host names. This is a singleton class with an
 * instance obtained by calling {@link #getInstance()}.
 */
public class AssociatedSettings implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500005000804L;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(AssociatedSettings.class);
	
	/**
	 * Gets the user name for host.
	 *
	 * @param hostName
	 *            the host name
	 * @return the user name for host
	 */
	public String getUserNameForHost(String hostName) {
		String userName = "";
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.AUTHENTICATION)) {
			pstmt.setString(1, "%" + hostName + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					userName = rs.getString(1);
				}
			}
        } catch (Exception e) {
            logger.error(e);
        }
        return userName;
    }	
	
	public void saveAuth(String name, String host) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_AUTH)) {
			pstmt.setString(1, name);
			pstmt.setString(2, host);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
