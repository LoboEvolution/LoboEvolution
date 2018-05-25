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

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.font.LAFSettings;

import com.loboevolution.store.SQLiteCommon;

public class LookAndFeelsSettings {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LookAndFeelsSettings.class);
	
	public LookAndFeelsSettings() {
	}	

	/**
	 * Insert a new row into the look_and_feel table
	 *
	 * @param laf
	 */
	public void insertLAF(LAFSettings laf) {
		try (Connection conn = connect(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_LAF)) {

			pstmt.setInt(1, laf.isAcryl() ? 1 : 0);
			pstmt.setInt(2, laf.isAero() ? 1 : 0);
			pstmt.setInt(3, laf.isAluminium() ? 1 : 0);
			pstmt.setInt(4, laf.isBernstein() ? 1 : 0);
			pstmt.setInt(5, laf.isFast() ? 1 : 0);
			pstmt.setInt(6, laf.isGraphite() ? 1 : 0);
			pstmt.setInt(7, laf.isHiFi() ? 1 : 0);
			pstmt.setInt(8, laf.isLuna() ? 1 : 0);
			pstmt.setInt(9, laf.isMcWin() ? 1 : 0);
			pstmt.setInt(10, laf.isMint() ? 1 : 0);
			pstmt.setInt(11, laf.isNoire() ? 1 : 0);
			pstmt.setInt(12, laf.isSmart() ? 1 : 0);
			pstmt.setInt(13, laf.isTexture() ? 1 : 0);
			pstmt.setInt(14, laf.isSubscript() ? 1 : 0);
			pstmt.setInt(15, laf.isSuperscript() ? 1 : 0);
			pstmt.setInt(16, laf.isUnderline() ? 1 : 0);
			pstmt.setInt(17, laf.isItalic() ? 1 : 0);
			pstmt.setInt(18, laf.isStrikethrough() ? 1 : 0);
			pstmt.setFloat(19, laf.getFontSize());
			pstmt.setString(20, laf.getFont());
			
			Color c = laf.getColor();
			pstmt.setString(21, "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
			pstmt.setInt(22, laf.isBold() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void deleteLAF() {
		try (Connection conn = connect(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_LAF)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Insert a new row into the search selected table
	 *
	 * @param search
	 */
	public void insertFileSelected(SearchEngine search) {
		try (Connection conn = connect(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_SEARCH2)) {
			pstmt.setString(1, search.getName());
			pstmt.setString(2, search.getDescription());
			pstmt.setString(3, search.getType());
			pstmt.setInt(4, search.isSelected() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Connect to the test.db database
	 *
	 * @return the Connection object
	 */
	private Connection connect(String dbPath) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbPath);
		} catch (Exception e) {
			logger.error(e);
		}
		return conn;
	} 
}