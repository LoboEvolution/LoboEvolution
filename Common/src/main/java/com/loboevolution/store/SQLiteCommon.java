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
package com.loboevolution.store;

import java.io.File;

public class SQLiteCommon {

	/** The Constant SETTINGS_DIR. */
	private static final String JDBC_SQLITE = "jdbc:sqlite:";

	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE.sqlite";
	
	private SQLiteCommon() {}

	/**
	 * Gets the settings directory.
	 *
	 * @return the settings directory
	 */
	public static String getSettingsDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		File store = new File(storeDir, "store");
		return JDBC_SQLITE + store + "\\" + LOBO_DB;
	}
	
	/**
	 * Gets the directory.
	 *
	 * @return the directory
	 */
	public static String getDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		File store = new File(storeDir, "store");
		return store + "\\" + LOBO_DB;
	}
}