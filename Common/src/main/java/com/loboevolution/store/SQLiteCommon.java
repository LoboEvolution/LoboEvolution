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
	
	public static final String COLORS = "SELECT name, value FROM COLOR";
	
	public static final String FONTS = " SELECT acryl, aero, aluminium, bernstein, fast, graphite,"
			+ " 	      hiFi,luna, mcWin, mint, noire, smart, texture,"
			+ "	 	  subscript, superscript, underline, italic, strikethrough,"
			+ "		  fontSize, font, color, bold" + " FROM LOOK_AND_FEEL";

	public static final String COOKIES = " SELECT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly "
									   + " FROM COOKIE WHERE domain = ? AND path = ?";
	
	public static final String CONNECTIONS = "SELECT proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses FROM CONNECTION";
		
	public static final String USER_AGENT = "SELECT msie, mozilla, include_msie FROM USER_AGENT";
	
	public static final String STARTUP = "SELECT baseUrl FROM STARTUP";
	
	public static final String SIZE = "SELECT width, height FROM SIZE";
	
	public static final String SEARCH = "SELECT description FROM SEARCH WHERE type = ?";

	public static final String SEARCH2 = "SELECT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC";

	public static final String HOST = "SELECT baseUrl FROM HOST LIMIT ?";

	public static final String HOST2 = "SELECT baseUrl FROM HOST WHERE baseUrl like ?";
	
	public static final String BOOKMARKS = "SELECT name, description, baseUrl, tags FROM BOOKMARKS WHERE baseUrl = ?";
	
	public static final String  NETWORK = "SELECT js, css, cookie, cache, navigation FROM NETWORK";
	
	public static final String INSERT_COOKIES = "INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)";

	public static final String INSERT_CONNECTIONS = "INSERT INTO CONNECTION (proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses) VALUES(?,?,?,?,?,?,?)";
	
	public static final String INSERT_USER_AGENT = "INSERT INTO USER_AGENT (msie, mozilla, include_msie) VALUES(?,?,?)";
	
	public static final String INSERT_STARTUP = "INSERT INTO STARTUP (baseUrl) VALUES(?)";
	
	public static final String INSERT_NETWORK = "INSERT INTO NETWORK (js, css, cookie, cache, navigation) VALUES(?,?,?,?,?)";
	
	public static final String INSERT_SIZE = "INSERT INTO SIZE (width, height) VALUES(?,?)";
	
	public static final String INSERT_HOST = "INSERT INTO HOST (baseUrl) VALUES(?)";
	
	public static final String INSERT_COLOR = "INSERT INTO COLOR (name, value) VALUES(?,?)";
	
	public static final String INSERT_BOOKMARKS = "INSERT INTO BOOKMARKS (name, description, baseUrl, tags) VALUES(?,?,?,?)";
	
	public static final String INSERT_SEARCH = "INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)";
	
	public static final String INSERT_SEARCH2 = "INSERT INTO SEARCH (name, description, type, selected) VALUES(?,?,?,?)";
	
	public static final String INSERT_LAF = " INSERT INTO LOOK_AND_FEEL (acryl, aero, aluminium, bernstein, fast, graphite,"
										  + "hiFi,luna, mcWin, mint, noire, smart, texture, subscript, superscript, underline, italic, "
										  + "strikethrough, fontSize, font, color, bold) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String UPDATE_SEARCH = "UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'";
	
	public static final String UPDATE_SEARCH2 = "UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'";
	
	public static final String DELETE_CONNECTIONS = "DELETE FROM CONNECTION";
	
	public static final String DELETE_USER_AGENT = "DELETE FROM USER_AGENT";
	
	public static final String DELETE_STARTUP = "DELETE FROM STARTUP";
	
	public static final String DELETE_NETWORK = "DELETE FROM NETWORK";
	
	public static final String DELETE_SIZE = "DELETE FROM SIZE";
	
	public static final String DELETE_LAF = "DELETE FROM LOOK_AND_FEEL";
	
	public static final String DELETE_SEARCH = "DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'";
	
	
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