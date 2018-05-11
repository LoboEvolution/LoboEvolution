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
package org.loboevolution.store;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.font.LAFSettings;

public class SQLiteManager {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SQLiteManager.class);
	
	/** The Constant SETTINGS_DIR. */
	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE";
			
	/*The url database*/
	private String urlDatabase;
	
	public SQLiteManager() {
		urlDatabase = JDBC_SQLITE + getSettingsDirectory() + "\\" + LOBO_DB;
	}	

	/**
	 * Insert a new row into the settings table
	 *
	 * @param laf
	 */
	public void insertLAF(LAFSettings laf) {
		try (Connection conn = connect(urlDatabase);
				PreparedStatement pstmt = conn.prepareStatement(
						" INSERT INTO LOOK_AND_FEEL (acryl, aero, aluminium, bernstein, fast, graphite,"
								+ "hiFi,luna, mcWin, mint, noire, smart, texture,"
								+ "subscript, superscript, underline, italic, strikethrough, "
								+ "fontSize, font, color, bold) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {

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
			int a = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void deleteLAF() {
		try (Connection conn = connect(urlDatabase); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM LOOK_AND_FEEL")) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
		return conn;
	} 

    /**
	 * Gets the settings directory.
	 *
	 * @return the settings directory
	 */
	public File getSettingsDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		return new File(storeDir, "store");
	}
}
