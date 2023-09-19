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

import org.loboevolution.info.GeneralInfo;

import java.awt.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * General browser settings.
 */
public class GeneralStore implements Serializable {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GeneralStore.class.getName());

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

	private static final String DELETE_NETWORK = "DELETE FROM NETWORK";

	private static final String DELETE_SIZE = "DELETE FROM SIZE";

	private static final String DELETE_STARTUP = "DELETE FROM STARTUP";

	private static final String INSERT_NETWORK = "INSERT INTO NETWORK (js, css, cookie, cache, navigation, image) VALUES(?, ?,?,?,?,?)";

	private static final String INSERT_SIZE = "INSERT INTO SIZE (width, height) VALUES(?,?)";

	private static final String INSERT_STARTUP = "INSERT INTO STARTUP (baseUrl) VALUES(?)";

	private static final String NETWORK = "SELECT DISTINCT js, css, cookie, cache, navigation, image FROM NETWORK";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500070000402L;

	private static final String SIZE = "SELECT DISTINCT width, height FROM SIZE";

	private static final String STARTUP = "SELECT DISTINCT baseUrl FROM STARTUP";

	/**
	 * <p>deleteBounds.</p>
	 */
	public static void deleteBounds() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_SIZE)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteNetwork.</p>
	 */
	public static void deleteNetwork() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_NETWORK)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteStartUpUrl.</p>
	 */
	public static void deleteStartUpUrl() {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_STARTUP)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getInitialWindowBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	public static Rectangle getInitialWindowBounds() {
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		if (DatabseSQLite.storeExist()) {
			int width = -1;
			int height = -1;
			try (Connection conn = DriverManager.getConnection(DB_PATH);
				 PreparedStatement pstmt = conn.prepareStatement(SIZE)) {
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						width = rs.getInt(1);
						height = rs.getInt(2);
					}
				}
			} catch (final Exception e) {
				//logger.log(Level.SEVERE, e.getMessage(), e);
			}
			if (width > -1 && height > -1) {
				bounds = new Rectangle(width, height);
			}
		}
		return bounds;
	}

	/**
	 * <p>getGeneralInfo.</p>
	 *
	 * @return a {@link org.loboevolution.info.GeneralInfo} object.
	 */
	public static GeneralInfo getGeneralInfo() {
		final GeneralInfo setting = new GeneralInfo();
		if (DatabseSQLite.storeExist()) {
			try (Connection conn = DriverManager.getConnection(DB_PATH);
				 PreparedStatement pstmt = conn.prepareStatement(NETWORK)) {
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						setting.setJs(rs.getInt(1) == 1);
						setting.setCss(rs.getInt(2) == 1);
						setting.setCookie(rs.getInt(3) == 1);
						setting.setCache(rs.getInt(4) == 1);
						setting.setNavigation(rs.getInt(5) == 1);
						setting.setImage(rs.getInt(6) == 1);
					}
				}
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return setting;
	}

	/**
	 * <p>getStartupURLs.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> getStartupURLs() {
		final List<String> urls = new ArrayList<>();
		if (DatabseSQLite.storeExist()) {
			try (Connection conn = DriverManager.getConnection(DB_PATH);
				 PreparedStatement pstmt = conn.prepareStatement(STARTUP)) {
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs != null && rs.next()) {
						urls.add(rs.getString(1));
					}
				}
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return urls;
	}

	/**
	 * <p>insertBounds.</p>
	 *
	 * @param rect a {@link java.awt.Rectangle} object.
	 */
	public static void insertBounds(Rectangle rect) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_SIZE)) {
			pstmt.setInt(1, rect.width);
			pstmt.setInt(2, rect.height);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>insertNetwork.</p>
	 *
	 * @param js a {@link java.lang.Boolean} object.
	 * @param css a {@link java.lang.Boolean} object.
	 * @param cookie a {@link java.lang.Boolean} object.
	 * @param cache a {@link java.lang.Boolean} object.
	 * @param navigation a {@link java.lang.Boolean} object.
	 */
	public static void insertNetwork(boolean js, boolean css, boolean image, boolean cookie, boolean cache, boolean navigation) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_NETWORK)) {
			pstmt.setInt(1, js ? 1 : 0);
			pstmt.setInt(2, css ? 1 : 0);
			pstmt.setInt(3, cookie ? 1 : 0);
			pstmt.setInt(4, cache ? 1 : 0);
			pstmt.setInt(5, navigation ? 1 : 0);
			pstmt.setInt(6, image ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>insertStartupUrl.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public static void insertStartupUrl(String url) {
		try (Connection conn = DriverManager.getConnection(DB_PATH);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_STARTUP)) {
			pstmt.setString(1, url);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>restoreDefaults.</p>
	 */
	public static void restoreDefaults() {
		deleteNetwork();
		deleteBounds();
		deleteStartUpUrl();
		insertNetwork(true, true, true,true, true, true);
		insertBounds(new Rectangle(800, 400));
	}
}
