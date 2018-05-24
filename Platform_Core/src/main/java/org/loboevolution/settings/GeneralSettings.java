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

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.request.UserAgentImpl;
import com.loboevolution.store.SQLiteCommon;

/**
 * General browser settings. This is a singleton class with an instance obtained
 * by calling {@link #getInstance()}.
 */
public class GeneralSettings implements Serializable {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(GeneralSettings.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500070000402L;

	/** The ie version. */
	private String ieVersion;

	/** The moz version. */
	private String mozVersion;
	
	/** The ie. */
	private boolean ie;
	
	/** The includeIE. */
	private boolean includeIE;

	/** The js. */
	private boolean js;

	/** The css. */
	private boolean css;
	
	/** The cookie. */
	private boolean cookie;

	/** The cache. */
	private boolean cache;
	
	/** The navigation. */
	private boolean navigation;
	
	public static GeneralSettings getNetwork() {
		GeneralSettings setting = new GeneralSettings();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT js, css, cookie, cache, navigation FROM NETWORK")) {
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				setting.setJs(rs.getInt(1) == 1 ? true : false);
				setting.setCss(rs.getInt(2) == 1 ? true : false);
				setting.setCookie(rs.getInt(3) == 1 ? true : false);
				setting.setCache(rs.getInt(4) == 1 ? true : false);
				setting.setNavigation(rs.getInt(5) == 1 ? true : false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return setting;
	}
	
	public static GeneralSettings getUserAgent() {
		GeneralSettings setting = new GeneralSettings();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT msie, mozilla, include_msie FROM USER_AGENT")) {
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				setting.setIeVersion(rs.getString(1));
				setting.setMozVersion(rs.getString(2));
				setting.setIncludeIE(rs.getInt(3) == 1 ? true : false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return setting;
	}
	
	public static List<String> getStartupURLs() {
		List<String> urls = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				PreparedStatement pstmt = conn.prepareStatement("SELECT baseUrl FROM STARTUP")) {
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				urls.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		if(urls.isEmpty()) {
			urls.add("");
		}
		
		return urls;
	}
	
	public static void insertNetwork(boolean js, boolean css, boolean cookie, boolean cache, boolean navigation) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO NETWORK (js, css, cookie, cache, navigation) VALUES(?,?,?,?,?)")) {
			pstmt.setInt(1, js ? 1 : 0);
			pstmt.setInt(2, css ? 1 : 0);
			pstmt.setInt(3, cookie ? 1 : 0);
			pstmt.setInt(4, cache ? 1 : 0);
			pstmt.setInt(5, navigation ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void insertUserAgent(String msie,String mozilla, boolean incluedeMsie) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USER_AGENT (msie, mozilla, include_msie) VALUES(?,?,?)")) {
			pstmt.setString(1, msie);
			pstmt.setString(2, mozilla);
			pstmt.setInt(3, incluedeMsie ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void insertBounds(Rectangle rect) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SIZE (width, height) VALUES(?,?)")) {
			pstmt.setInt(1, rect.width);
			pstmt.setInt(2, rect.height);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void deleteNetwork() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NETWORK")) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void deleteUserAgent() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getSettingsDirectory());
				 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM USER_AGENT")) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void restoreDefaults() {
		deleteNetwork();
		deleteUserAgent();
		insertNetwork(true, true, true, true, true);
		insertUserAgent("11.0", "5.0", true);
		//TODO this.startupURLs = Collections.singletonList(DEFAULT_STARTUP);
	}

	public static Rectangle getInitialWindowBounds() {
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		/*if (bounds == null) {
			return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		}
		if (bounds.width < 100) {
			bounds.width = 100;
		}
		if (bounds.height < 100) {
			bounds.height = 100;
		}*/
		return bounds;
	}

	/**
	 * Gets the ie version.
	 *
	 * @return the ie version
	 */
	public String getIeVersion() {
		return ieVersion;
	}

	/**
	 * Sets the ie version.
	 *
	 * @param ieVersion
	 *            the new ie version
	 */
	public void setIeVersion(String ieVersion) {
		this.ieVersion = ieVersion;
		UserAgentImpl.getInstance().invalidateUserAgent();
	}

	/**
	 * Gets the moz version.
	 *
	 * @return the moz version
	 */
	public String getMozVersion() {
		return mozVersion;
	}

	/**
	 * Sets the moz version.
	 *
	 * @param mozVersion
	 *            the new moz version
	 */
	public void setMozVersion(String mozVersion) {
		this.mozVersion = mozVersion;
		UserAgentImpl.getInstance().invalidateUserAgent();
	}

	/**
	 * @return the ie
	 */
	public boolean isIe() {
		return ie;
	}


	/**
	 * @param ie the ie to set
	 */
	public void setIe(boolean ie) {
		this.ie = ie;
		UserAgentImpl.getInstance().invalidateUserAgent();
	}


	/**
	 * @return the includeIE
	 */
	public boolean isIncludeIE() {
		return includeIE;
	}


	/**
	 * @param includeIE the includeIE to set
	 */
	public void setIncludeIE(boolean includeIE) {
		this.includeIE = includeIE;
	}


	/**
	 * @return the js
	 */
	public boolean isJs() {
		return js;
	}


	/**
	 * @param js the js to set
	 */
	public void setJs(boolean js) {
		this.js = js;
	}


	/**
	 * @return the css
	 */
	public boolean isCss() {
		return css;
	}


	/**
	 * @param css the css to set
	 */
	public void setCss(boolean css) {
		this.css = css;
	}


	/**
	 * @return the cookie
	 */
	public boolean isCookie() {
		return cookie;
	}


	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(boolean cookie) {
		this.cookie = cookie;
	}


	/**
	 * @return the cache
	 */
	public boolean isCache() {
		return cache;
	}


	/**
	 * @param cache the cache to set
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}


	/**
	 * @return the navigation
	 */
	public boolean isNavigation() {
		return navigation;
	}


	/**
	 * @param navigation the navigation to set
	 */
	public void setNavigation(boolean navigation) {
		this.navigation = navigation;
	}
}
