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
package org.loboevolution.font;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.color.ColorFactory;

public class FontSqlLiteRetrive {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FontSqlLiteRetrive.class);

	/** The Constant SETTINGS_DIR. */
	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE";

	public LAFSettings retriveFontDate() {
		String urlDatabase = JDBC_SQLITE + getSettingsDirectory() + "\\" + LOBO_DB;
		LAFSettings laf = null;
		try (Connection conn = DriverManager.getConnection(urlDatabase)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT acryl, aero, aluminium, bernstein, fast, graphite," + 
											 " 	      hiFi,luna, mcWin, mint, noire, smart, texture," + 
											 "	 	  subscript, superscript, underline, italic, strikethrough," + 
											 "		  fontSize, font, color, bold" +
											 " FROM LOOK_AND_FEEL");

			while (rs!= null && rs.next()) {
				laf = new LAFSettings();
				laf.setAcryl(rs.getInt(1) == 1 ? true : false);
				laf.setAero(rs.getInt(2) == 1 ? true : false);
				laf.setAluminium(rs.getInt(3) == 1 ? true : false);
				laf.setBernstein(rs.getInt(4) == 1 ? true : false);
				laf.setFast(rs.getInt(5) == 1 ? true : false);
				laf.setGraphite(rs.getInt(6) == 1 ? true : false);
				laf.setHiFi(rs.getInt(7) == 1 ? true : false);
				laf.setLuna(rs.getInt(8) == 1 ? true : false);
				laf.setMcWin(rs.getInt(9) == 1 ? true : false);
				laf.setMint(rs.getInt(10) == 1 ? true : false);
				laf.setNoire(rs.getInt(11) == 1 ? true : false);
				laf.setSmart(rs.getInt(12) == 1 ? true : false);
				laf.setTexture(rs.getInt(13) == 1 ? true : false);
				laf.setSubscript(rs.getInt(14) == 1 ? true : false);
				laf.setSuperscript(rs.getInt(15) == 1 ? true : false);
				laf.setUnderline(rs.getInt(16) == 1 ? true : false);
				laf.setItalic(rs.getInt(17) == 1 ? true : false);
				laf.setStrikethrough(rs.getInt(18) == 1 ? true : false);
				laf.setFontSize(Float.parseFloat(rs.getString(19)));
				laf.setFont(rs.getString(20));
				laf.setColor(ColorFactory.getInstance().getColor(rs.getString(21)));
				laf.setBold(rs.getInt(22) == 1 ? true : false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return laf;
	}

	/**
	 * Gets the settings directory.
	 *
	 * @return the settings directory
	 */
	private File getSettingsDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		return new File(storeDir, "store");
	}
}