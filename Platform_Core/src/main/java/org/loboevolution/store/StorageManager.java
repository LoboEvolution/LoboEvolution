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
/*
 * Created on Mar 12, 2005
 */
package org.loboevolution.store;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.color.ColorCommon;
import org.loboevolution.security.GenericLocalPermission;
import org.loboevolution.security.LocalSecurityPolicy;

import com.loboevolution.store.SQLiteCommon;

/**
 * * @author J. H. S.
 */
public class StorageManager implements Runnable, ColorCommon {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(StorageManager.class);

	/** The Constant HOST_STORE_QUOTA. */
	private static final long HOST_STORE_QUOTA = 200 * 1024;

	/** The Constant HOST_STORE_DIR. */
	private static final String HOST_STORE_DIR = "HostStore";

	/** The Constant CACHE_DIR. */
	private static final String CACHE_DIR = "cache";

	/** The Constant CONTENT_DIR. */
	private static final String CONTENT_DIR = "content";

	/** The Constant LOOK_AND_FEEL. */
	private static final String LOOK_AND_FEEL = "CREATE TABLE LOOK_AND_FEEL (acryl integer, aero integer, aluminium integer, bernstein integer, fast integer, graphite integer, "
			+ "hiFi integer,luna integer, mcWin integer, mint integer, noire integer, smart integer, texture integer, "
			+ "bold integer, italic integer, underline integer, strikethrough integer, subscript integer, "
			+ "superscript integer, fontSize text, font text, color text)";
	
	/** The Constant SEARCH_SELECTED. */
	private static final String SEARCH = "CREATE TABLE SEARCH (name text, description text, baseUrl text, queryParameter text, selected integer, type text)";
	
	/** The Constant SEARCH_SELECTED. */
	private static final String BOOKMARKS = "CREATE TABLE BOOKMARKS (name text, description text, baseUrl text, tags text)";
	
	/** The Constant COLOR. */
	private static final String COLOR = "CREATE TABLE COLOR (name text, value text)";
	
	/** The Constant FONT. */
	private static final String FONT = "CREATE TABLE FONT (name text)";
	
	/** The Constant FONT_SIZE. */
	private static final String FONT_SIZE = "CREATE TABLE FONT_SIZE (name text)";
		
	/** The Constant CONNECTION. */
	private static final String CONNECTION = "CREATE TABLE CONNECTION (proxyType text, userName text, password text, authenticated integer, host text, port integer, disableProxyForLocalAddresses integer)";
	
	/** The Constant FONT. */
	private static final String HOST = "CREATE TABLE HOST (baseUrl text)";
	
	/** The Constant COOKIE. */
	private static final String COOKIE = "CREATE TABLE COOKIE (cookieName text, cookieValue text, domain text, path text, expires date, maxAge text, secure integer, httponly integer)";
	
	/** The Constant STARTUP. */
	private static final String STARTUP = "CREATE TABLE STARTUP (baseUrl text)";
	
	/** The Constant STARTUP. */
	private static final String SIZE = "CREATE TABLE SIZE (width integer, height integer)";
	
	/** The Constant USER_AGENT. */
	private static final String USER_AGENT = "CREATE TABLE USER_AGENT (msie text, mozilla text, include_msie integer)";
	
	/** The Constant NETWORK. */
	private static final String NETWORK = "CREATE TABLE NETWORK (js integer, css integer, cookie integer, cache integer, navigation integer)";
	
	/** The Constant LOGIN. */
	private static final String LOGIN = "CREATE TABLE INPUT (name text, value text)";
	
	/** The Constant AUTHENTICATION. */
	private static final String AUTHENTICATION = "CREATE TABLE AUTHENTICATION (name text, baseUrl text)";
	
	/** The Constant CACHE. */
	private static final String CACHE = "CREATE TABLE CACHE (baseUrl text, source blob, contenLenght num, etag text, lastModified date, type text)";
	
	private static final StorageManager instance = new StorageManager();

	/** The store directory. */
	private final File storeDirectory;

	/** The Constant NO_HOST. */
	private static final String NO_HOST = "$NO_HOST$";

	/** The restricted store cache. */
	private final Map<String, RestrictedStore> restrictedStoreCache = new HashMap<String, RestrictedStore>();

	/** The Constant MANAGED_STORE_UPDATE_DELAY. */
	private static final int MANAGED_STORE_UPDATE_DELAY = 1000 * 60 * 5;

	/** The thread started. */
	private boolean threadStarted = false;

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static StorageManager getInstance() throws IOException {
		return instance;
	}

	/**
	 * Instantiates a new storage manager.
	 */
	private StorageManager() {
		this.storeDirectory = LocalSecurityPolicy.STORE_DIRECTORY;
		if (!this.storeDirectory.exists()) {
			this.storeDirectory.mkdirs();
			File dbDir = new File(storeDirectory, "store");
			dbDir.mkdirs();
		}
	}

	/**
	 * Ensure thread started.
	 */
	private void ensureThreadStarted() {
		if (!this.threadStarted) {
			synchronized (this) {
				if (!this.threadStarted) {
					Thread t = new Thread(this, "StorageManager");
					t.setDaemon(true);
					t.setPriority(Thread.MIN_PRIORITY);
					t.start();
					this.threadStarted = true;
				}
			}
		}
	}

	/**
	 * Gets the app home.
	 *
	 * @return the app home
	 */
	public File getAppHome() {
		return this.storeDirectory;
	}

	/**
	 * Gets the cache host directory.
	 *
	 * @param hostName
	 *            the host name
	 * @return the cache host directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File getCacheHostDirectory(String hostName) throws IOException {
		CacheManager.getInstance();
		File cacheDir = this.getCacheRoot();
		if (hostName == null || "".equals(hostName)) {
			hostName = NO_HOST;
		}
		return new File(cacheDir, hostName);
	}

	/**
	 * Gets the content cache file.
	 *
	 * @param hostName
	 *            the host name
	 * @param fileName
	 *            the file name
	 * @return the content cache file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File getContentCacheFile(String hostName, String fileName) throws IOException {
		File domainDir = this.getCacheHostDirectory(hostName);
		File xamjDir = new File(domainDir, CONTENT_DIR);
		return new File(xamjDir, fileName);
	}

	/**
	 * Gets the cache root.
	 *
	 * @return the cache root
	 */
	public File getCacheRoot() {
		return new File(this.storeDirectory, CACHE_DIR);
	}

	/**
	 * Gets the restricted store.
	 *
	 * @param hostName
	 *            the host name
	 * @param createIfNotExists
	 *            the create if not exists
	 * @return the restricted store
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RestrictedStore getRestrictedStore(String hostName, final boolean createIfNotExists) throws IOException {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		if (hostName == null || "".equals(hostName)) {
			hostName = NO_HOST;
		}
		final String normHost = hostName;
		RestrictedStore store;
		synchronized (this) {
			store = this.restrictedStoreCache.get(normHost);
			if (store == null) {
				store = AccessController.doPrivileged((PrivilegedAction<RestrictedStore>) () -> {
					File hostStoreDir = new File(storeDirectory, HOST_STORE_DIR);
					File domainDir = new File(hostStoreDir, normHost);
					if (!createIfNotExists && !domainDir.exists()) {
						return null;
					}
					try {
						return new RestrictedStore(domainDir, HOST_STORE_QUOTA);
					} catch (IOException ioe) {
						throw new IllegalStateException(ioe);
					}
				});
				if (store != null) {
					this.restrictedStoreCache.put(normHost, store);
				}
			}
		}
		if (store != null) {
			this.ensureThreadStarted();
		}
		return store;
	}

	public void createDatabase() {
	
		File f = new File(SQLiteCommon.getDatabaseStore());
		if (!f.exists()) {
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory()); Statement stmt = conn.createStatement()) {
				createTable(LOOK_AND_FEEL);
				createTable(SEARCH);
				createTable(COLOR);
				createTable(FONT);
				createTable(FONT_SIZE);
				createTable(BOOKMARKS);
				createTable(CONNECTION);	
				createTable(HOST);
				createTable(COOKIE);
				createTable(STARTUP);
				createTable(USER_AGENT);
				createTable(NETWORK);
				createTable(SIZE);
				createTable(LOGIN);
				createTable(AUTHENTICATION);
				createTable(CACHE);
				populateColorTable();
				populateFontTable();
				populateFontSizeTable();
				populateSearchEngine();
				populateUserAgent();
				populateNetwork();
				populateSize();
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	private void createTable(String table) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory()); Statement stmt = conn.createStatement()) {
        	stmt.execute(table);
        } catch (Exception e) {
        	logger.error(e);
        }
    }
	
	private void populateColorTable() {
		insertColorTable("aliceblue", ALICEBLUE);
		insertColorTable("antiquewhite", ANTIQUEWHITE);
		insertColorTable("antiquewhite1", ANTIQUEWHITE1);
		insertColorTable("antiquewhite2", ANTIQUEWHITE2);
		insertColorTable("antiquewhite3", ANTIQUEWHITE3);
		insertColorTable("antiquewhite4", ANTIQUEWHITE4);
		insertColorTable("aqua", AQUA);
		insertColorTable("aquamarine", AQUAMARINE);
		insertColorTable("aquamarine1", AQUAMARINE1);
		insertColorTable("aquamarine2", AQUAMARINE2);
		insertColorTable("azure", AZURE);
		insertColorTable("azure1", AZURE1);
		insertColorTable("azure2", AZURE2);
		insertColorTable("azure3", AZURE3);
		insertColorTable("beige", BEIGE);
		insertColorTable("bisque", BISQUE);
		insertColorTable("bisque1", BISQUE1);
		insertColorTable("bisque2", BISQUE2);
		insertColorTable("bisque3", BISQUE3);
		insertColorTable("black", BLACK);
		insertColorTable("blanchedalmond", BLANCHEDALMOND);
		insertColorTable("blue", BLUE);
		insertColorTable("blue1", BLUE1);
		insertColorTable("blueviolet", BLUEVIOLET);
		insertColorTable("brown", BROWN);
		insertColorTable("brown1", BROWN1);
		insertColorTable("brown2", BROWN2);
		insertColorTable("brown3", BROWN3);
		insertColorTable("brown4", BROWN4);
		insertColorTable("burlywood", BURLYWOOD);
		insertColorTable("burlywood1", BURLYWOOD1);
		insertColorTable("burlywood2", BURLYWOOD2);
		insertColorTable("burlywood3", BURLYWOOD3);
		insertColorTable("burlywood4", BURLYWOOD4);
		insertColorTable("cadetblue", CADETBLUE);
		insertColorTable("cadetblue1", CADETBLUE1);
		insertColorTable("cadetblue2", CADETBLUE2);
		insertColorTable("cadetblue3", CADETBLUE3);
		insertColorTable("cadetblue4", CADETBLUE4);
		insertColorTable("chartreuse", CHARTREUSE);
		insertColorTable("chartreuse1", CHARTREUSE1);
		insertColorTable("chartreuse2", CHARTREUSE2);
		insertColorTable("chartreuse3", CHARTREUSE3);
		insertColorTable("chocolate", CHOCOLATE);
		insertColorTable("chocolate1", CHOCOLATE1);
		insertColorTable("chocolate2", CHOCOLATE2);
		insertColorTable("chocolate3", CHOCOLATE3);
		insertColorTable("coral", CORAL);
		insertColorTable("coral1", CORAL1);
		insertColorTable("coral2", CORAL2);
		insertColorTable("coral3", CORAL3);
		insertColorTable("coral4", CORAL4);
		insertColorTable("cornflowerblue", CORNFLOWERBLUE);
		insertColorTable("cornsilk", CORNSILK);
		insertColorTable("cornsilk1", CORNSILK1);
		insertColorTable("cornsilk2", CORNSILK2);
		insertColorTable("cornsilk3", CORNSILK3);
		insertColorTable("crimson", CRIMSON);
		insertColorTable("cyan", CYAN);
		insertColorTable("cyan1", CYAN1);
		insertColorTable("cyan2", CYAN2);
		insertColorTable("darkblue", DARKBLUE);
		insertColorTable("darkcyan", DARKCYAN);
		insertColorTable("darkgoldenrod", DARKGOLDENROD);
		insertColorTable("darkgoldenrod1", DARKGOLDENROD1);
		insertColorTable("darkgoldenrod2", DARKGOLDENROD2);
		insertColorTable("darkgoldenrod3", DARKGOLDENROD3);
		insertColorTable("darkgoldenrod4", DARKGOLDENROD4);
		insertColorTable("darkgray", DARKGRAY);
		insertColorTable("darkgreen", DARKGREEN);
		insertColorTable("darkkhaki", DARKKHAKI);
		insertColorTable("darkmagenta", DARKMAGENTA);
		insertColorTable("darkolivegreen", DARKOLIVEGREEN);
		insertColorTable("darkolivegreen1", DARKOLIVEGREEN1);
		insertColorTable("darkolivegreen2", DARKOLIVEGREEN2);
		insertColorTable("darkolivegreen3", DARKOLIVEGREEN3);
		insertColorTable("darkolivegreen4", DARKOLIVEGREEN4);
		insertColorTable("darkorange", DARKORANGE);
		insertColorTable("darkorange1", DARKORANGE1);
		insertColorTable("darkorange2", DARKORANGE2);
		insertColorTable("darkorange3", DARKORANGE3);
		insertColorTable("darkorange4", DARKORANGE4);
		insertColorTable("darkorchid", DARKORCHID);
		insertColorTable("darkorchid1", DARKORCHID1);
		insertColorTable("darkorchid2", DARKORCHID2);
		insertColorTable("darkorchid3", DARKORCHID3);
		insertColorTable("darkorchid4", DARKORCHID4);
		insertColorTable("darkred", DARKRED);
		insertColorTable("darksalmon", DARKSALMON);
		insertColorTable("darkseagreen", DARKSEAGREEN);
		insertColorTable("darkseagreen1", DARKSEAGREEN1);
		insertColorTable("darkseagreen2", DARKSEAGREEN2);
		insertColorTable("darkseagreen3", DARKSEAGREEN3);
		insertColorTable("darkseagreen4", DARKSEAGREEN4);
		insertColorTable("darkslateblue", DARKSLATEBLUE);
		insertColorTable("darkslategray", DARKSLATEGRAY);
		insertColorTable("darkslategray1", DARKSLATEGRAY1);
		insertColorTable("darkslategray2", DARKSLATEGRAY2);
		insertColorTable("darkslategray3", DARKSLATEGRAY3);
		insertColorTable("darkslategray4", DARKSLATEGRAY4);
		insertColorTable("darkturquoise", DARKTURQUOISE);
		insertColorTable("darkviolet", DARKVIOLET);
		insertColorTable("deeppink", DEEPPINK);
		insertColorTable("deeppink1", DEEPPINK1);
		insertColorTable("deeppink2", DEEPPINK2);
		insertColorTable("deeppink3", DEEPPINK3);
		insertColorTable("deepskyblue", DEEPSKYBLUE);
		insertColorTable("deepskyblue1", DEEPSKYBLUE1);
		insertColorTable("deepskyblue2", DEEPSKYBLUE2);
		insertColorTable("deepskyblue3", DEEPSKYBLUE3);
		insertColorTable("dimgray", DIMGRAY);
		insertColorTable("dodgerblue", DODGERBLUE);
		insertColorTable("dodgerblue1", DODGERBLUE1);
		insertColorTable("dodgerblue2", DODGERBLUE2);
		insertColorTable("dodgerblue3", DODGERBLUE3);
		insertColorTable("firebrick", FIREBRICK);
		insertColorTable("firebrick1", FIREBRICK1);
		insertColorTable("firebrick2", FIREBRICK2);
		insertColorTable("firebrick3", FIREBRICK3);
		insertColorTable("firebrick4", FIREBRICK4);
		insertColorTable("floralwhite", FLORALWHITE);
		insertColorTable("forestgreen", FORESTGREEN);
		insertColorTable("fractal", FRACTAL);
		insertColorTable("gainsboro", GAINSBORO);
		insertColorTable("ghostwhite", GHOSTWHITE);
		insertColorTable("gold", GOLD);
		insertColorTable("gold1", GOLD1);
		insertColorTable("gold2", GOLD2);
		insertColorTable("gold3", GOLD3);
		insertColorTable("goldenrod", GOLDENROD);
		insertColorTable("goldenrod1", GOLDENROD1);
		insertColorTable("goldenrod2", GOLDENROD2);
		insertColorTable("goldenrod3", GOLDENROD3);
		insertColorTable("goldenrod4", GOLDENROD4);
		insertColorTable("gray1", GRAY1);
		insertColorTable("gray10", GRAY10);
		insertColorTable("gray11", GRAY11);
		insertColorTable("gray12", GRAY12);
		insertColorTable("gray13", GRAY13);
		insertColorTable("gray14", GRAY14);
		insertColorTable("gray15", GRAY15);
		insertColorTable("gray16", GRAY16);
		insertColorTable("gray17", GRAY17);
		insertColorTable("gray18", GRAY18);
		insertColorTable("gray19", GRAY19);
		insertColorTable("gray2", GRAY2);
		insertColorTable("gray20", GRAY20);
		insertColorTable("gray21", GRAY21);
		insertColorTable("gray22", GRAY22);
		insertColorTable("gray23", GRAY23);
		insertColorTable("gray24", GRAY24);
		insertColorTable("gray25", GRAY25);
		insertColorTable("gray26", GRAY26);
		insertColorTable("gray27", GRAY27);
		insertColorTable("gray28", GRAY28);
		insertColorTable("gray29", GRAY29);
		insertColorTable("gray3", GRAY3);
		insertColorTable("gray30", GRAY30);
		insertColorTable("gray31", GRAY31);
		insertColorTable("gray32", GRAY32);
		insertColorTable("gray33", GRAY33);
		insertColorTable("gray34", GRAY34);
		insertColorTable("gray35", GRAY35);
		insertColorTable("gray36", GRAY36);
		insertColorTable("gray37", GRAY37);
		insertColorTable("gray38", GRAY38);
		insertColorTable("gray", GRAY);
		insertColorTable("gray4", GRAY4);
		insertColorTable("gray40", GRAY40);
		insertColorTable("gray41", GRAY41);
		insertColorTable("gray42", GRAY42);
		insertColorTable("gray43", GRAY43);
		insertColorTable("gray44", GRAY44);
		insertColorTable("gray45", GRAY45);
		insertColorTable("gray46", GRAY46);
		insertColorTable("gray47", GRAY47);
		insertColorTable("gray48", GRAY48);
		insertColorTable("gray49", GRAY49);
		insertColorTable("gray5", GRAY5);
		insertColorTable("gray50", GRAY50);
		insertColorTable("gray51", GRAY51);
		insertColorTable("gray52", GRAY52);
		insertColorTable("gray53", GRAY53);
		insertColorTable("gray54", GRAY54);
		insertColorTable("gray55", GRAY55);
		insertColorTable("gray56", GRAY56);
		insertColorTable("gray57", GRAY57);
		insertColorTable("gray58", GRAY58);
		insertColorTable("gray59", GRAY59);
		insertColorTable("gray6", GRAY6);
		insertColorTable("gray60", GRAY60);
		insertColorTable("gray61", GRAY61);
		insertColorTable("gray62", GRAY62);
		insertColorTable("gray63", GRAY63);
		insertColorTable("gray64", GRAY64);
		insertColorTable("gray65", GRAY65);
		insertColorTable("gray66", GRAY66);
		insertColorTable("gray67", GRAY67);
		insertColorTable("gray68", GRAY68);
		insertColorTable("gray69", GRAY69);
		insertColorTable("gray7", GRAY7);
		insertColorTable("gray70", GRAY70);
		insertColorTable("gray71", GRAY71);
		insertColorTable("gray72", GRAY72);
		insertColorTable("gray73", GRAY73);
		insertColorTable("gray74", GRAY74);
		insertColorTable("gray75", GRAY75);
		insertColorTable("gray76", GRAY76);
		insertColorTable("gray77", GRAY77);
		insertColorTable("gray78", GRAY78);
		insertColorTable("gray79", GRAY79);
		insertColorTable("gray8", GRAY8);
		insertColorTable("gray80", GRAY80);
		insertColorTable("gray81", GRAY81);
		insertColorTable("gray82", GRAY82);
		insertColorTable("gray83", GRAY83);
		insertColorTable("gray84", GRAY84);
		insertColorTable("gray85", GRAY85);
		insertColorTable("gray86", GRAY86);
		insertColorTable("gray87", GRAY87);
		insertColorTable("gray88", GRAY88);
		insertColorTable("gray89", GRAY89);
		insertColorTable("gray9", GRAY9);
		insertColorTable("gray90", GRAY90);
		insertColorTable("gray91", GRAY91);
		insertColorTable("gray92", GRAY92);
		insertColorTable("gray93", GRAY93);
		insertColorTable("gray94", GRAY94);
		insertColorTable("gray95", GRAY95);
		insertColorTable("gray96", GRAY96);
		insertColorTable("gray97", GRAY97);
		insertColorTable("green", GREEN);
		insertColorTable("green1", GREEN1);
		insertColorTable("green2", GREEN2);
		insertColorTable("green3", GREEN3);
		insertColorTable("greenyellow", GREENYELLOW);
		insertColorTable("honeydew", HONEYDEW);
		insertColorTable("honeydew1", HONEYDEW1);
		insertColorTable("honeydew2", HONEYDEW2);
		insertColorTable("honeydew3", HONEYDEW3);
		insertColorTable("hotpink", HOTPINK);
		insertColorTable("hotpink1", HOTPINK1);
		insertColorTable("hotpink2", HOTPINK2);
		insertColorTable("hotpink3", HOTPINK3);
		insertColorTable("hotpink4", HOTPINK4);
		insertColorTable("indianred", INDIANRED);
		insertColorTable("indianred1", INDIANRED1);
		insertColorTable("indianred2", INDIANRED2);
		insertColorTable("indianred3", INDIANRED3);
		insertColorTable("indianred4", INDIANRED4);
		insertColorTable("indigo", INDIGO);
		insertColorTable("ivory", IVORY);
		insertColorTable("ivory1", IVORY1);
		insertColorTable("ivory2", IVORY2);
		insertColorTable("ivory3", IVORY3);
		insertColorTable("khaki", KHAKI);
		insertColorTable("khaki1", KHAKI1);
		insertColorTable("khaki2", KHAKI2);
		insertColorTable("khaki3", KHAKI3);
		insertColorTable("khaki4", KHAKI4);
		insertColorTable("lavender", LAVENDER);
		insertColorTable("lavenderblush", LAVENDERBLUSH);
		insertColorTable("lavenderblush1", LAVENDERBLUSH1);
		insertColorTable("lavenderblush2", LAVENDERBLUSH2);
		insertColorTable("lavenderblush3", LAVENDERBLUSH3);
		insertColorTable("lawngreen", LAWNGREEN);
		insertColorTable("lemonchiffon", LEMONCHIFFON);
		insertColorTable("lemonchiffon1", LEMONCHIFFON1);
		insertColorTable("lemonchiffon2", LEMONCHIFFON2);
		insertColorTable("lemonchiffon3", LEMONCHIFFON3);
		insertColorTable("lightblue", LIGHTBLUE);
		insertColorTable("lightblue1", LIGHTBLUE1);
		insertColorTable("lightblue2", LIGHTBLUE2);
		insertColorTable("lightblue3", LIGHTBLUE3);
		insertColorTable("lightblue4", LIGHTBLUE4);
		insertColorTable("lightcoral", LIGHTCORAL);
		insertColorTable("lightcyan", LIGHTCYAN);
		insertColorTable("lightcyan1", LIGHTCYAN1);
		insertColorTable("lightcyan2", LIGHTCYAN2);
		insertColorTable("lightcyan3", LIGHTCYAN3);
		insertColorTable("lightgoldenrod", LIGHTGOLDENROD);
		insertColorTable("lightgoldenrod1", LIGHTGOLDENROD1);
		insertColorTable("lightgoldenrod2", LIGHTGOLDENROD2);
		insertColorTable("lightgoldenrod3", LIGHTGOLDENROD3);
		insertColorTable("lightgoldenrod4", LIGHTGOLDENROD4);
		insertColorTable("lightgoldenrodyellow", LIGHTGOLDENRODYELLOW);
		insertColorTable("lightgray", LIGHTGRAY);
		insertColorTable("lightgrey", LIGHTGRAY);
		insertColorTable("lightgreen", LIGHTGREEN);
		insertColorTable("lightpink", LIGHTPINK);
		insertColorTable("lightpink1", LIGHTPINK1);
		insertColorTable("lightpink2", LIGHTPINK2);
		insertColorTable("lightpink3", LIGHTPINK3);
		insertColorTable("lightpink4", LIGHTPINK4);
		insertColorTable("lightsalmon", LIGHTSALMON);
		insertColorTable("lightsalmon1", LIGHTSALMON1);
		insertColorTable("lightsalmon2", LIGHTSALMON2);
		insertColorTable("lightsalmon3", LIGHTSALMON3);
		insertColorTable("lightseagreen", LIGHTSEAGREEN);
		insertColorTable("lightskyblue", LIGHTSKYBLUE);
		insertColorTable("lightskyblue1", LIGHTSKYBLUE1);
		insertColorTable("lightskyblue2", LIGHTSKYBLUE2);
		insertColorTable("lightskyblue3", LIGHTSKYBLUE3);
		insertColorTable("lightskyblue4", LIGHTSKYBLUE4);
		insertColorTable("lightslateblue", LIGHTSLATEBLUE);
		insertColorTable("lightslategray", LIGHTSLATEGRAY);
		insertColorTable("lightsteelblue", LIGHTSTEELBLUE);
		insertColorTable("lightsteelblue1", LIGHTSTEELBLUE1);
		insertColorTable("lightsteelblue2", LIGHTSTEELBLUE2);
		insertColorTable("lightsteelblue3", LIGHTSTEELBLUE3);
		insertColorTable("lightsteelblue4", LIGHTSTEELBLUE4);
		insertColorTable("lightyellow", LIGHTYELLOW);
		insertColorTable("lightyellow1", LIGHTYELLOW1);
		insertColorTable("lightyellow2", LIGHTYELLOW2);
		insertColorTable("lightyellow3", LIGHTYELLOW3);
		insertColorTable("limegreen", LIMEGREEN);
		insertColorTable("linen", LINEN);
		insertColorTable("magenta", MAGENTA);
		insertColorTable("magenta1", MAGENTA1);
		insertColorTable("magenta2", MAGENTA2);
		insertColorTable("maroon", MAROON);
		insertColorTable("maroon1", MAROON1);
		insertColorTable("maroon2", MAROON2);
		insertColorTable("maroon3", MAROON3);
		insertColorTable("maroon4", MAROON4);
		insertColorTable("mediumaquamarine", MEDIUMAQUAMARINE);
		insertColorTable("mediumblue", MEDIUMBLUE);
		insertColorTable("mediumforestgreen", MEDIUMFORESTGREEN);
		insertColorTable("mediumgoldenrod", MEDIUMGOLDENROD);
		insertColorTable("mediumorchid", MEDIUMORCHID);
		insertColorTable("mediumorchid1", MEDIUMORCHID1);
		insertColorTable("mediumorchid2", MEDIUMORCHID2);
		insertColorTable("mediumorchid3", MEDIUMORCHID3);
		insertColorTable("mediumorchid4", MEDIUMORCHID4);
		insertColorTable("mediumpurple", MEDIUMPURPLE);
		insertColorTable("mediumpurple1", MEDIUMPURPLE1);
		insertColorTable("mediumpurple2", MEDIUMPURPLE2);
		insertColorTable("mediumpurple3", MEDIUMPURPLE3);
		insertColorTable("mediumpurple4", MEDIUMPURPLE4);
		insertColorTable("mediumseagreen", MEDIUMSEAGREEN);
		insertColorTable("mediumslateblue", MEDIUMSLATEBLUE);
		insertColorTable("mediumspringgreen", MEDIUMSPRINGGREEN);
		insertColorTable("mediumturquoisenew", MEDIUMTURQUOISE);
		insertColorTable("mediumvioletred", MEDIUMVIOLETRED);
		insertColorTable("midnightblue", MIDNIGHTBLUE);
		insertColorTable("mintcream", MINTCREAM);
		insertColorTable("mistyrose", MISTYROSE);
		insertColorTable("mistyrose1", MISTYROSE1);
		insertColorTable("mistyrose2", MISTYROSE2);
		insertColorTable("mistyrose3", MISTYROSE3);
		insertColorTable("moccasin", MOCCASIN);
		insertColorTable("navajowhite", NAVAJOWHITE);
		insertColorTable("navajowhite1", NAVAJOWHITE1);
		insertColorTable("navajowhite2", NAVAJOWHITE2);
		insertColorTable("navajowhite3", NAVAJOWHITE3);
		insertColorTable("navy", NAVYBLUE);
		insertColorTable("navyblue", NAVYBLUE);
		insertColorTable("oldlace", OLDLACE);
		insertColorTable("olive", OLIVE);
		insertColorTable("olivedrab", OLIVEDRAB);
		insertColorTable("olivedrab1", OLIVEDRAB1);
		insertColorTable("olivedrab2", OLIVEDRAB2);
		insertColorTable("olivedrab3", OLIVEDRAB3);
		insertColorTable("orange", ORANGE);
		insertColorTable("orange1", ORANGE1);
		insertColorTable("orange2", ORANGE2);
		insertColorTable("orange3", ORANGE3);
		insertColorTable("orangered", ORANGERED);
		insertColorTable("orangered1", ORANGERED1);
		insertColorTable("orangered2", ORANGERED2);
		insertColorTable("orangered3", ORANGERED3);
		insertColorTable("orchid", ORCHID);
		insertColorTable("orchid1", ORCHID1);
		insertColorTable("orchid2", ORCHID2);
		insertColorTable("orchid3", ORCHID3);
		insertColorTable("orchid4", ORCHID4);
		insertColorTable("palegoldenrod", PALEGOLDENROD);
		insertColorTable("palegreen", PALEGREEN);
		insertColorTable("palegreen1", PALEGREEN1);
		insertColorTable("palegreen2", PALEGREEN2);
		insertColorTable("palegreen3", PALEGREEN3);
		insertColorTable("paleturquoise", PALETURQUOISE);
		insertColorTable("paleturquoise1", PALETURQUOISE1);
		insertColorTable("paleturquoise2", PALETURQUOISE2);
		insertColorTable("paleturquoise3", PALETURQUOISE3);
		insertColorTable("paleturquoise4", PALETURQUOISE4);
		insertColorTable("palevioletred", PALEVIOLETRED);
		insertColorTable("palevioletred1", PALEVIOLETRED1);
		insertColorTable("palevioletred2", PALEVIOLETRED2);
		insertColorTable("palevioletred3", PALEVIOLETRED3);
		insertColorTable("palevioletred4", PALEVIOLETRED4);
		insertColorTable("papayawhip", PAPAYAWHIP);
		insertColorTable("peachpuff", PEACHPUFF);
		insertColorTable("peachpuff1", PEACHPUFF1);
		insertColorTable("peachpuff2", PEACHPUFF2);
		insertColorTable("peachpuff3", PEACHPUFF3);
		insertColorTable("pink", PINK);
		insertColorTable("pink1", PINK1);
		insertColorTable("pink2", PINK2);
		insertColorTable("pink3", PINK3);
		insertColorTable("pink4", PINK4);
		insertColorTable("plum", PLUM);
		insertColorTable("plum1", PLUM1);
		insertColorTable("plum2", PLUM2);
		insertColorTable("plum3", PLUM3);
		insertColorTable("plum4", PLUM4);
		insertColorTable("powderblue", POWDERBLUE);
		insertColorTable("purple", PURPLE);
		insertColorTable("purple1", PURPLE1);
		insertColorTable("purple2", PURPLE2);
		insertColorTable("purple3", PURPLE3);
		insertColorTable("purple4", PURPLE4);
		insertColorTable("red", RED);
		insertColorTable("red1", RED1);
		insertColorTable("red2", RED2);
		insertColorTable("rosybrown", ROSYBROWN);
		insertColorTable("rosybrown1", ROSYBROWN1);
		insertColorTable("rosybrown2", ROSYBROWN2);
		insertColorTable("rosybrown3", ROSYBROWN3);
		insertColorTable("rosybrown4", ROSYBROWN4);
		insertColorTable("royalblue", ROYALBLUE);
		insertColorTable("royalblue1", ROYALBLUE1);
		insertColorTable("royalblue2", ROYALBLUE2);
		insertColorTable("royalblue3", ROYALBLUE3);
		insertColorTable("royalblue4", ROYALBLUE4);
		insertColorTable("saddlebrown", SADDLEBROWN);
		insertColorTable("salmon", SALMON);
		insertColorTable("salmon1", SALMON1);
		insertColorTable("salmon2", SALMON2);
		insertColorTable("salmon3", SALMON3);
		insertColorTable("salmon4", SALMON4);
		insertColorTable("sandybrown", SANDYBROWN);
		insertColorTable("seagreen", SEAGREEN);
		insertColorTable("seagreen1", SEAGREEN1);
		insertColorTable("seagreen2", SEAGREEN2);
		insertColorTable("seagreen3", SEAGREEN3);
		insertColorTable("seashell", SEASHELL);
		insertColorTable("seashell1", SEASHELL1);
		insertColorTable("seashell2", SEASHELL2);
		insertColorTable("seashell3", SEASHELL3);
		insertColorTable("sienna", SIENNA);
		insertColorTable("sienna1", SIENNA1);
		insertColorTable("sienna2", SIENNA2);
		insertColorTable("sienna3", SIENNA3);
		insertColorTable("sienna4", SIENNA4);
		insertColorTable("silver", SILVER);
		insertColorTable("skyblue", SKYBLUE);
		insertColorTable("skyblue1", SKYBLUE1);
		insertColorTable("skyblue2", SKYBLUE2);
		insertColorTable("skyblue3", SKYBLUE3);
		insertColorTable("skyblue4", SKYBLUE4);
		insertColorTable("slateblue", SLATEBLUE);
		insertColorTable("slateblue1", SLATEBLUE1);
		insertColorTable("slateblue2", SLATEBLUE2);
		insertColorTable("slateblue3", SLATEBLUE3);
		insertColorTable("slateblue4", SLATEBLUE4);
		insertColorTable("slategray", SLATEGRAY);
		insertColorTable("slategray1", SLATEGRAY1);
		insertColorTable("slategray2", SLATEGRAY2);
		insertColorTable("slategray3", SLATEGRAY3);
		insertColorTable("slategray4", SLATEGRAY4);
		insertColorTable("snow", SNOW);
		insertColorTable("snow1", SNOW1);
		insertColorTable("snow2", SNOW2);
		insertColorTable("snow3", SNOW3);
		insertColorTable("springgreen", SPRINGGREEN);
		insertColorTable("springgreen1", SPRINGGREEN1);
		insertColorTable("springgreen2", SPRINGGREEN2);
		insertColorTable("springgreen3", SPRINGGREEN3);
		insertColorTable("steelblue", STEELBLUE);
		insertColorTable("steelblue1", STEELBLUE1);
		insertColorTable("steelblue2", STEELBLUE2);
		insertColorTable("steelblue3", STEELBLUE3);
		insertColorTable("steelblue4", STEELBLUE4);
		insertColorTable("tan", TAN);
		insertColorTable("tan1", TAN1);
		insertColorTable("tan2", TAN2);
		insertColorTable("tan3", TAN3);
		insertColorTable("tan4", TAN4);
		insertColorTable("teal", TEAL);
		insertColorTable("thistle", THISTLE);
		insertColorTable("thistle1", THISTLE1);
		insertColorTable("thistle2", THISTLE2);
		insertColorTable("thistle3", THISTLE3);
		insertColorTable("thistle4", THISTLE4);
		insertColorTable("tomato", TOMATO);
		insertColorTable("tomato1", TOMATO1);
		insertColorTable("tomato2", TOMATO2);
		insertColorTable("tomato3", TOMATO3);
		insertColorTable("turquoise", TURQUOISE);
		insertColorTable("turquoise1", TURQUOISE1);
		insertColorTable("turquoise2", TURQUOISE2);
		insertColorTable("turquoise3", TURQUOISE3);
		insertColorTable("turquoise4", TURQUOISE4);
		insertColorTable("violet", VIOLET);
		insertColorTable("violetred", VIOLETRED);
		insertColorTable("violetred1", VIOLETRED1);
		insertColorTable("violetred2", VIOLETRED2);
		insertColorTable("violetred3", VIOLETRED3);
		insertColorTable("violetred4", VIOLETRED4);
		insertColorTable("wheat", WHEAT);
		insertColorTable("wheat1", WHEAT1);
		insertColorTable("wheat2", WHEAT2);
		insertColorTable("wheat3", WHEAT3);
		insertColorTable("wheat4", WHEAT4);
		insertColorTable("white", WHITE);
		insertColorTable("whitesmoke", WHITESMOKE);
		insertColorTable("yellow", YELLOW);
		insertColorTable("yellow1", YELLOW1);
		insertColorTable("yellow2", YELLOW2);
		insertColorTable("yellow3", YELLOW3);
		insertColorTable("yellowgreen", YELLOWGREEN);
    }
	
	private void populateFontTable() {
		insertFont("Aharoni", "FONT");
		insertFont("Andalus", "FONT");
		insertFont("AngsanaNew", "FONT");
		insertFont("AngsanaUPC", "FONT");
		insertFont("AngsanaUPC", "FONT");
		insertFont("Aparajita", "FONT");
		insertFont("ArabicTypesetting", "FONT");
		insertFont("Arial", "FONT");
		insertFont("ArialBlack", "FONT");
		insertFont("Batang", "FONT");
		insertFont("BatangChe", "FONT");
		insertFont("BrowalliaNew", "FONT");
		insertFont("BrowalliaUPC", "FONT");
		insertFont("Caladea", "FONT");
		insertFont("Calibri", "FONT");
		insertFont("CalibriLight", "FONT");
		insertFont("Cambria", "FONT");
		insertFont("CambriaMath", "FONT");
		insertFont("Candara", "FONT");
		insertFont("Carlito", "FONT");
		insertFont("ComicSansMS", "FONT");
		insertFont("Consolas", "FONT");
		insertFont("Constantia", "FONT");
		insertFont("Corbel", "FONT");
		insertFont("CordiaNew", "FONT");
		insertFont("CordiaUPC", "FONT");
		insertFont("CourierNew", "FONT");
		insertFont("DaunPenh", "FONT");
		insertFont("David", "FONT");
		insertFont("DejaVuSans", "FONT");
		insertFont("DejaVuSansCondensed", "FONT");
		insertFont("DejaVuSansLight", "FONT");
		insertFont("DejaVuSansMono", "FONT");
		insertFont("DejaVuSerif", "FONT");
		insertFont("DejaVuSerifCondensed", "FONT");
		insertFont("DFKai-SB", "FONT");
		insertFont("Dialog", "FONT");
		insertFont("DialogInput", "FONT");
		insertFont("DilleniaUPC", "FONT");
		insertFont("DokChampa", "FONT");
		insertFont("Dotum", "FONT");
		insertFont("DotumChe", "FONT");
		insertFont("Ebrima", "FONT");
		insertFont("EstrangeloEdessa", "FONT");
		insertFont("EucrosiaUPC", "FONT");
		insertFont("Euphemia", "FONT");
		insertFont("FangSong", "FONT");
		insertFont("FranklinGothicMedium", "FONT");
		insertFont("FrankRuehl", "FONT");
		insertFont("FreesiaUPC", "FONT");
		insertFont("Gabriola", "FONT");
		insertFont("Gautami", "FONT");
		insertFont("GentiumBasic", "FONT");
		insertFont("GentiumBookBasic", "FONT");
		insertFont("Georgia", "FONT");
		insertFont("Gisha", "FONT");
		insertFont("Gulim", "FONT");
		insertFont("GulimChe", "FONT");
		insertFont("Gungsuh", "FONT");
		insertFont("GungsuhChe", "FONT");
		insertFont("Impact", "FONT");
		insertFont("IrisUPC", "FONT");
		insertFont("IskoolaPota", "FONT");
		insertFont("JasmineUPC", "FONT");
		insertFont("KaiTi", "FONT");
		insertFont("Kalinga", "FONT");
		insertFont("Kartika", "FONT");
		insertFont("KhmerUI", "FONT");
		insertFont("KodchiangUPC", "FONT");
		insertFont("Kokila", "FONT");
		insertFont("LaoUI", "FONT");
		insertFont("Latha", "FONT");
		insertFont("Leelawadee", "FONT");
		insertFont("LevenimMT", "FONT");
		insertFont("LiberationMono", "FONT");
		insertFont("LiberationSans", "FONT");
		insertFont("LiberationSansNarrow", "FONT");
		insertFont("LiberationSerif", "FONT");
		insertFont("LilyUPC", "FONT");
		insertFont("LinuxBiolinumG", "FONT");
		insertFont("LinuxLibertineDisplayG", "FONT");
		insertFont("LinuxLibertineG", "FONT");
		insertFont("LucidaBright", "FONT");
		insertFont("LucidaConsole", "FONT");
		insertFont("LucidaSans", "FONT");
		insertFont("LucidaSansTypewriter", "FONT");
		insertFont("LucidaSansUnicode", "FONT");
		insertFont("MalgunGothic", "FONT");
		insertFont("Mangal", "FONT");
		insertFont("Marlett", "FONT");
		insertFont("Meiryo", "FONT");
		insertFont("MeiryoUI", "FONT");
		insertFont("MicrosoftHimalaya", "FONT");
		insertFont("MicrosoftJhengHei", "FONT");
		insertFont("MicrosoftNewTaiLue", "FONT");
		insertFont("MicrosoftPhagsPa", "FONT");
		insertFont("MicrosoftSansSerif", "FONT");
		insertFont("MicrosoftTaiLe", "FONT");
		insertFont("MicrosoftUighur", "FONT");
		insertFont("MicrosoftYaHei", "FONT");
		insertFont("MicrosoftYiBaiti", "FONT");
		insertFont("MingLiU", "FONT");
		insertFont("MingLiU-ExtB", "FONT");
		insertFont("MingLiU_HKSCS", "FONT");
		insertFont("MingLiU_HKSCS-ExtB", "FONT");
		insertFont("Miriam", "FONT");
		insertFont("MiriamFixed", "FONT");
		insertFont("MongolianBaiti", "FONT");
		insertFont("Monospaced", "FONT");
		insertFont("MoolBoran", "FONT");
		insertFont("MSGothic", "FONT");
		insertFont("MSMincho", "FONT");
		insertFont("MSPGothic", "FONT");
		insertFont("MSPMincho", "FONT");
		insertFont("MSUIGothic", "FONT");
		insertFont("MVBoli", "FONT");
		insertFont("Narkisim", "FONT");
		insertFont("NSimSun", "FONT");
		insertFont("Nyala", "FONT");
		insertFont("OpenSans", "FONT");
		insertFont("OpenSymbol", "FONT");
		insertFont("PalatinoLinotype", "FONT");
		insertFont("PlantagenetCherokee", "FONT");
		insertFont("PMingLiU", "FONT");
		insertFont("PMingLiU-ExtB", "FONT");
		insertFont("PTSerif", "FONT");
		insertFont("Raavi", "FONT");
		insertFont("Rod", "FONT");
		insertFont("SakkalMajalla", "FONT");
		insertFont("SansSerif", "FONT");
		insertFont("SegoePrint", "FONT");
		insertFont("SegoeScript", "FONT");
		insertFont("SegoeUI", "FONT");
		insertFont("SegoeUILight", "FONT");
		insertFont("SegoeUISemibold", "FONT");
		insertFont("SegoeUISymbol", "FONT");
		insertFont("Serif", "FONT");
		insertFont("ShonarBangla", "FONT");
		insertFont("Shruti", "FONT");
		insertFont("SimHei", "FONT");
		insertFont("SimplifiedArabic", "FONT");
		insertFont("SimplifiedArabicFixed", "FONT");
		insertFont("SimSun", "FONT");
		insertFont("SimSun-ExtB", "FONT");
		insertFont("SourceCodePro", "FONT");
		insertFont("SourceSansPro", "FONT");
		insertFont("SourceSansProBlack", "FONT");
		insertFont("SourceSansProExtraLight", "FONT");
		insertFont("SourceSansProLight", "FONT");
		insertFont("SourceSansProSemibold", "FONT");
		insertFont("Sylfaen", "FONT");
		insertFont("Symbol", "FONT");
		insertFont("Tahoma", "FONT");
		insertFont("TimesNewRoman", "FONT");
		insertFont("TraditionalArabic", "FONT");
		insertFont("TrebuchetMS", "FONT");
		insertFont("Tunga", "FONT");
		insertFont("Utsaah", "FONT");
		insertFont("Vani", "FONT");
		insertFont("Verdana", "FONT");
		insertFont("Vijaya", "FONT");
		insertFont("Vrinda", "FONT");
		insertFont("Webdings", "FONT");
		insertFont("Wingdings", "FONT");
	}

	private void populateFontSizeTable() {
		insertFont("8", "FONT_SIZE");
		insertFont("9", "FONT_SIZE");
		insertFont("10", "FONT_SIZE");
		insertFont("11", "FONT_SIZE");
		insertFont("12", "FONT_SIZE");
		insertFont("14", "FONT_SIZE");
		insertFont("16", "FONT_SIZE");
		insertFont("18", "FONT_SIZE");
		insertFont("20", "FONT_SIZE");
		insertFont("22", "FONT_SIZE");
		insertFont("24", "FONT_SIZE");
		insertFont("26", "FONT_SIZE");
		insertFont("28", "FONT_SIZE");
		insertFont("36", "FONT_SIZE");
		insertFont("48", "FONT_SIZE");
		insertFont("72", "FONT_SIZE");
	}
	
	private void populateSearchEngine() {
		insertSearch("Bing Search", "Bing web search engine.", "http://www.bing.com/search?q", "q", 0, "SEARCH_ENGINE");
		insertSearch("Google Web Search", "Google's main search engine.", "http://google.com/search", "q", 1, "SEARCH_ENGINE");
		insertSearch("Yahoo! Web Search", "Yahoo's web search engine.", "http://search.yahoo.com/search", "p", 0, "SEARCH_ENGINE");
		insertSearch("Wikipedia", "English Wikipedia article search.", "http://en.wikipedia.org/wiki/Special:Search", "search", 0, "SEARCH_ENGINE");
	}
	
	private void insertColorTable(String name, int value) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_COLOR)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, value);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void insertFont(String name, String type) {
		String query = "INSERT INTO "+type+" (name) VALUES(?)";
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void insertSearch(String name, String description, String baseUrl, String queryParameter, int selected, String type) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_SEARCH)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setString(3, type);
			pstmt.setString(4, baseUrl);
			pstmt.setString(5, queryParameter);
			pstmt.setInt(6, selected);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void populateNetwork() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_NETWORK)) {
			pstmt.setInt(1, 1);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 1);
			pstmt.setInt(4, 1);
			pstmt.setInt(5, 1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	private void populateUserAgent() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_USER_AGENT)) {
			pstmt.setString(1, "11.0");
			pstmt.setString(2, "5.0");
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void populateSize() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_SIZE)) {
			pstmt.setInt(1, 1600);
			pstmt.setInt(2, 870);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	
	
	/* (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(MANAGED_STORE_UPDATE_DELAY);
				RestrictedStore[] stores;
				synchronized (this) {
					stores = this.restrictedStoreCache.values().toArray(new RestrictedStore[0]);
				}
				for (RestrictedStore store : stores) {
					Thread.yield();
					store.updateSizeFile();
				}
			} catch (Throwable err) {
				logger.error("run()", err);
				try {
					Thread.sleep(MANAGED_STORE_UPDATE_DELAY);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
