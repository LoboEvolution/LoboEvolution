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
import java.io.Serializable;
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

	public void saveSettings(String name, Serializable classLoader) throws IOException {
		// TODO
	}

	public Serializable retrieveSettings(String name, ClassLoader classLoader) throws IOException {
		return null;
		// TODO
	}

	public void createDatabase() {
		String urlDatabase = SQLiteCommon.getSettingsDirectory();
		File f = new File(SQLiteCommon.getDirectory());
		if (!f.exists()) {
			try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement()) {
				createTable(urlDatabase, LOOK_AND_FEEL);
				createTable(urlDatabase, SEARCH);
				createTable(urlDatabase, COLOR);
				createTable(urlDatabase, FONT);
				createTable(urlDatabase, FONT_SIZE);
				createTable(urlDatabase, BOOKMARKS);
				populateColorTable(urlDatabase);
				populateFontTable(urlDatabase);
				populateFontSizeTable(urlDatabase);
				populateSearchEngine(urlDatabase);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	private void createTable(String urlDatabase, String table) {
		try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement()) {
        	stmt.execute(table);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
    }
	
	private void populateColorTable(String urlDatabase) {
		insertColorTable(urlDatabase, "aliceblue", ALICEBLUE);
		insertColorTable(urlDatabase, "antiquewhite", ANTIQUEWHITE);
		insertColorTable(urlDatabase, "antiquewhite1", ANTIQUEWHITE1);
		insertColorTable(urlDatabase, "antiquewhite2", ANTIQUEWHITE2);
		insertColorTable(urlDatabase, "antiquewhite3", ANTIQUEWHITE3);
		insertColorTable(urlDatabase, "antiquewhite4", ANTIQUEWHITE4);
		insertColorTable(urlDatabase, "aqua", AQUA);
		insertColorTable(urlDatabase, "aquamarine", AQUAMARINE);
		insertColorTable(urlDatabase, "aquamarine1", AQUAMARINE1);
		insertColorTable(urlDatabase, "aquamarine2", AQUAMARINE2);
		insertColorTable(urlDatabase, "azure", AZURE);
		insertColorTable(urlDatabase, "azure1", AZURE1);
		insertColorTable(urlDatabase, "azure2", AZURE2);
		insertColorTable(urlDatabase, "azure3", AZURE3);
		insertColorTable(urlDatabase, "beige", BEIGE);
		insertColorTable(urlDatabase, "bisque", BISQUE);
		insertColorTable(urlDatabase, "bisque1", BISQUE1);
		insertColorTable(urlDatabase, "bisque2", BISQUE2);
		insertColorTable(urlDatabase, "bisque3", BISQUE3);
		insertColorTable(urlDatabase, "black", BLACK);
		insertColorTable(urlDatabase, "blanchedalmond", BLANCHEDALMOND);
		insertColorTable(urlDatabase, "blue", BLUE);
		insertColorTable(urlDatabase, "blue1", BLUE1);
		insertColorTable(urlDatabase, "blueviolet", BLUEVIOLET);
		insertColorTable(urlDatabase, "brown", BROWN);
		insertColorTable(urlDatabase, "brown1", BROWN1);
		insertColorTable(urlDatabase, "brown2", BROWN2);
		insertColorTable(urlDatabase, "brown3", BROWN3);
		insertColorTable(urlDatabase, "brown4", BROWN4);
		insertColorTable(urlDatabase, "burlywood", BURLYWOOD);
		insertColorTable(urlDatabase, "burlywood1", BURLYWOOD1);
		insertColorTable(urlDatabase, "burlywood2", BURLYWOOD2);
		insertColorTable(urlDatabase, "burlywood3", BURLYWOOD3);
		insertColorTable(urlDatabase, "burlywood4", BURLYWOOD4);
		insertColorTable(urlDatabase, "cadetblue", CADETBLUE);
		insertColorTable(urlDatabase, "cadetblue1", CADETBLUE1);
		insertColorTable(urlDatabase, "cadetblue2", CADETBLUE2);
		insertColorTable(urlDatabase, "cadetblue3", CADETBLUE3);
		insertColorTable(urlDatabase, "cadetblue4", CADETBLUE4);
		insertColorTable(urlDatabase, "chartreuse", CHARTREUSE);
		insertColorTable(urlDatabase, "chartreuse1", CHARTREUSE1);
		insertColorTable(urlDatabase, "chartreuse2", CHARTREUSE2);
		insertColorTable(urlDatabase, "chartreuse3", CHARTREUSE3);
		insertColorTable(urlDatabase, "chocolate", CHOCOLATE);
		insertColorTable(urlDatabase, "chocolate1", CHOCOLATE1);
		insertColorTable(urlDatabase, "chocolate2", CHOCOLATE2);
		insertColorTable(urlDatabase, "chocolate3", CHOCOLATE3);
		insertColorTable(urlDatabase, "coral", CORAL);
		insertColorTable(urlDatabase, "coral1", CORAL1);
		insertColorTable(urlDatabase, "coral2", CORAL2);
		insertColorTable(urlDatabase, "coral3", CORAL3);
		insertColorTable(urlDatabase, "coral4", CORAL4);
		insertColorTable(urlDatabase, "cornflowerblue", CORNFLOWERBLUE);
		insertColorTable(urlDatabase, "cornsilk", CORNSILK);
		insertColorTable(urlDatabase, "cornsilk1", CORNSILK1);
		insertColorTable(urlDatabase, "cornsilk2", CORNSILK2);
		insertColorTable(urlDatabase, "cornsilk3", CORNSILK3);
		insertColorTable(urlDatabase, "crimson", CRIMSON);
		insertColorTable(urlDatabase, "cyan", CYAN);
		insertColorTable(urlDatabase, "cyan1", CYAN1);
		insertColorTable(urlDatabase, "cyan2", CYAN2);
		insertColorTable(urlDatabase, "darkblue", DARKBLUE);
		insertColorTable(urlDatabase, "darkcyan", DARKCYAN);
		insertColorTable(urlDatabase, "darkgoldenrod", DARKGOLDENROD);
		insertColorTable(urlDatabase, "darkgoldenrod1", DARKGOLDENROD1);
		insertColorTable(urlDatabase, "darkgoldenrod2", DARKGOLDENROD2);
		insertColorTable(urlDatabase, "darkgoldenrod3", DARKGOLDENROD3);
		insertColorTable(urlDatabase, "darkgoldenrod4", DARKGOLDENROD4);
		insertColorTable(urlDatabase, "darkgray", DARKGRAY);
		insertColorTable(urlDatabase, "darkgreen", DARKGREEN);
		insertColorTable(urlDatabase, "darkkhaki", DARKKHAKI);
		insertColorTable(urlDatabase, "darkmagenta", DARKMAGENTA);
		insertColorTable(urlDatabase, "darkolivegreen", DARKOLIVEGREEN);
		insertColorTable(urlDatabase, "darkolivegreen1", DARKOLIVEGREEN1);
		insertColorTable(urlDatabase, "darkolivegreen2", DARKOLIVEGREEN2);
		insertColorTable(urlDatabase, "darkolivegreen3", DARKOLIVEGREEN3);
		insertColorTable(urlDatabase, "darkolivegreen4", DARKOLIVEGREEN4);
		insertColorTable(urlDatabase, "darkorange", DARKORANGE);
		insertColorTable(urlDatabase, "darkorange1", DARKORANGE1);
		insertColorTable(urlDatabase, "darkorange2", DARKORANGE2);
		insertColorTable(urlDatabase, "darkorange3", DARKORANGE3);
		insertColorTable(urlDatabase, "darkorange4", DARKORANGE4);
		insertColorTable(urlDatabase, "darkorchid", DARKORCHID);
		insertColorTable(urlDatabase, "darkorchid1", DARKORCHID1);
		insertColorTable(urlDatabase, "darkorchid2", DARKORCHID2);
		insertColorTable(urlDatabase, "darkorchid3", DARKORCHID3);
		insertColorTable(urlDatabase, "darkorchid4", DARKORCHID4);
		insertColorTable(urlDatabase, "darkred", DARKRED);
		insertColorTable(urlDatabase, "darksalmon", DARKSALMON);
		insertColorTable(urlDatabase, "darkseagreen", DARKSEAGREEN);
		insertColorTable(urlDatabase, "darkseagreen1", DARKSEAGREEN1);
		insertColorTable(urlDatabase, "darkseagreen2", DARKSEAGREEN2);
		insertColorTable(urlDatabase, "darkseagreen3", DARKSEAGREEN3);
		insertColorTable(urlDatabase, "darkseagreen4", DARKSEAGREEN4);
		insertColorTable(urlDatabase, "darkslateblue", DARKSLATEBLUE);
		insertColorTable(urlDatabase, "darkslategray", DARKSLATEGRAY);
		insertColorTable(urlDatabase, "darkslategray1", DARKSLATEGRAY1);
		insertColorTable(urlDatabase, "darkslategray2", DARKSLATEGRAY2);
		insertColorTable(urlDatabase, "darkslategray3", DARKSLATEGRAY3);
		insertColorTable(urlDatabase, "darkslategray4", DARKSLATEGRAY4);
		insertColorTable(urlDatabase, "darkturquoise", DARKTURQUOISE);
		insertColorTable(urlDatabase, "darkviolet", DARKVIOLET);
		insertColorTable(urlDatabase, "deeppink", DEEPPINK);
		insertColorTable(urlDatabase, "deeppink1", DEEPPINK1);
		insertColorTable(urlDatabase, "deeppink2", DEEPPINK2);
		insertColorTable(urlDatabase, "deeppink3", DEEPPINK3);
		insertColorTable(urlDatabase, "deepskyblue", DEEPSKYBLUE);
		insertColorTable(urlDatabase, "deepskyblue1", DEEPSKYBLUE1);
		insertColorTable(urlDatabase, "deepskyblue2", DEEPSKYBLUE2);
		insertColorTable(urlDatabase, "deepskyblue3", DEEPSKYBLUE3);
		insertColorTable(urlDatabase, "dimgray", DIMGRAY);
		insertColorTable(urlDatabase, "dodgerblue", DODGERBLUE);
		insertColorTable(urlDatabase, "dodgerblue1", DODGERBLUE1);
		insertColorTable(urlDatabase, "dodgerblue2", DODGERBLUE2);
		insertColorTable(urlDatabase, "dodgerblue3", DODGERBLUE3);
		insertColorTable(urlDatabase, "firebrick", FIREBRICK);
		insertColorTable(urlDatabase, "firebrick1", FIREBRICK1);
		insertColorTable(urlDatabase, "firebrick2", FIREBRICK2);
		insertColorTable(urlDatabase, "firebrick3", FIREBRICK3);
		insertColorTable(urlDatabase, "firebrick4", FIREBRICK4);
		insertColorTable(urlDatabase, "floralwhite", FLORALWHITE);
		insertColorTable(urlDatabase, "forestgreen", FORESTGREEN);
		insertColorTable(urlDatabase, "fractal", FRACTAL);
		insertColorTable(urlDatabase, "gainsboro", GAINSBORO);
		insertColorTable(urlDatabase, "ghostwhite", GHOSTWHITE);
		insertColorTable(urlDatabase, "gold", GOLD);
		insertColorTable(urlDatabase, "gold1", GOLD1);
		insertColorTable(urlDatabase, "gold2", GOLD2);
		insertColorTable(urlDatabase, "gold3", GOLD3);
		insertColorTable(urlDatabase, "goldenrod", GOLDENROD);
		insertColorTable(urlDatabase, "goldenrod1", GOLDENROD1);
		insertColorTable(urlDatabase, "goldenrod2", GOLDENROD2);
		insertColorTable(urlDatabase, "goldenrod3", GOLDENROD3);
		insertColorTable(urlDatabase, "goldenrod4", GOLDENROD4);
		insertColorTable(urlDatabase, "gray1", GRAY1);
		insertColorTable(urlDatabase, "gray10", GRAY10);
		insertColorTable(urlDatabase, "gray11", GRAY11);
		insertColorTable(urlDatabase, "gray12", GRAY12);
		insertColorTable(urlDatabase, "gray13", GRAY13);
		insertColorTable(urlDatabase, "gray14", GRAY14);
		insertColorTable(urlDatabase, "gray15", GRAY15);
		insertColorTable(urlDatabase, "gray16", GRAY16);
		insertColorTable(urlDatabase, "gray17", GRAY17);
		insertColorTable(urlDatabase, "gray18", GRAY18);
		insertColorTable(urlDatabase, "gray19", GRAY19);
		insertColorTable(urlDatabase, "gray2", GRAY2);
		insertColorTable(urlDatabase, "gray20", GRAY20);
		insertColorTable(urlDatabase, "gray21", GRAY21);
		insertColorTable(urlDatabase, "gray22", GRAY22);
		insertColorTable(urlDatabase, "gray23", GRAY23);
		insertColorTable(urlDatabase, "gray24", GRAY24);
		insertColorTable(urlDatabase, "gray25", GRAY25);
		insertColorTable(urlDatabase, "gray26", GRAY26);
		insertColorTable(urlDatabase, "gray27", GRAY27);
		insertColorTable(urlDatabase, "gray28", GRAY28);
		insertColorTable(urlDatabase, "gray29", GRAY29);
		insertColorTable(urlDatabase, "gray3", GRAY3);
		insertColorTable(urlDatabase, "gray30", GRAY30);
		insertColorTable(urlDatabase, "gray31", GRAY31);
		insertColorTable(urlDatabase, "gray32", GRAY32);
		insertColorTable(urlDatabase, "gray33", GRAY33);
		insertColorTable(urlDatabase, "gray34", GRAY34);
		insertColorTable(urlDatabase, "gray35", GRAY35);
		insertColorTable(urlDatabase, "gray36", GRAY36);
		insertColorTable(urlDatabase, "gray37", GRAY37);
		insertColorTable(urlDatabase, "gray38", GRAY38);
		insertColorTable(urlDatabase, "gray", GRAY);
		insertColorTable(urlDatabase, "gray4", GRAY4);
		insertColorTable(urlDatabase, "gray40", GRAY40);
		insertColorTable(urlDatabase, "gray41", GRAY41);
		insertColorTable(urlDatabase, "gray42", GRAY42);
		insertColorTable(urlDatabase, "gray43", GRAY43);
		insertColorTable(urlDatabase, "gray44", GRAY44);
		insertColorTable(urlDatabase, "gray45", GRAY45);
		insertColorTable(urlDatabase, "gray46", GRAY46);
		insertColorTable(urlDatabase, "gray47", GRAY47);
		insertColorTable(urlDatabase, "gray48", GRAY48);
		insertColorTable(urlDatabase, "gray49", GRAY49);
		insertColorTable(urlDatabase, "gray5", GRAY5);
		insertColorTable(urlDatabase, "gray50", GRAY50);
		insertColorTable(urlDatabase, "gray51", GRAY51);
		insertColorTable(urlDatabase, "gray52", GRAY52);
		insertColorTable(urlDatabase, "gray53", GRAY53);
		insertColorTable(urlDatabase, "gray54", GRAY54);
		insertColorTable(urlDatabase, "gray55", GRAY55);
		insertColorTable(urlDatabase, "gray56", GRAY56);
		insertColorTable(urlDatabase, "gray57", GRAY57);
		insertColorTable(urlDatabase, "gray58", GRAY58);
		insertColorTable(urlDatabase, "gray59", GRAY59);
		insertColorTable(urlDatabase, "gray6", GRAY6);
		insertColorTable(urlDatabase, "gray60", GRAY60);
		insertColorTable(urlDatabase, "gray61", GRAY61);
		insertColorTable(urlDatabase, "gray62", GRAY62);
		insertColorTable(urlDatabase, "gray63", GRAY63);
		insertColorTable(urlDatabase, "gray64", GRAY64);
		insertColorTable(urlDatabase, "gray65", GRAY65);
		insertColorTable(urlDatabase, "gray66", GRAY66);
		insertColorTable(urlDatabase, "gray67", GRAY67);
		insertColorTable(urlDatabase, "gray68", GRAY68);
		insertColorTable(urlDatabase, "gray69", GRAY69);
		insertColorTable(urlDatabase, "gray7", GRAY7);
		insertColorTable(urlDatabase, "gray70", GRAY70);
		insertColorTable(urlDatabase, "gray71", GRAY71);
		insertColorTable(urlDatabase, "gray72", GRAY72);
		insertColorTable(urlDatabase, "gray73", GRAY73);
		insertColorTable(urlDatabase, "gray74", GRAY74);
		insertColorTable(urlDatabase, "gray75", GRAY75);
		insertColorTable(urlDatabase, "gray76", GRAY76);
		insertColorTable(urlDatabase, "gray77", GRAY77);
		insertColorTable(urlDatabase, "gray78", GRAY78);
		insertColorTable(urlDatabase, "gray79", GRAY79);
		insertColorTable(urlDatabase, "gray8", GRAY8);
		insertColorTable(urlDatabase, "gray80", GRAY80);
		insertColorTable(urlDatabase, "gray81", GRAY81);
		insertColorTable(urlDatabase, "gray82", GRAY82);
		insertColorTable(urlDatabase, "gray83", GRAY83);
		insertColorTable(urlDatabase, "gray84", GRAY84);
		insertColorTable(urlDatabase, "gray85", GRAY85);
		insertColorTable(urlDatabase, "gray86", GRAY86);
		insertColorTable(urlDatabase, "gray87", GRAY87);
		insertColorTable(urlDatabase, "gray88", GRAY88);
		insertColorTable(urlDatabase, "gray89", GRAY89);
		insertColorTable(urlDatabase, "gray9", GRAY9);
		insertColorTable(urlDatabase, "gray90", GRAY90);
		insertColorTable(urlDatabase, "gray91", GRAY91);
		insertColorTable(urlDatabase, "gray92", GRAY92);
		insertColorTable(urlDatabase, "gray93", GRAY93);
		insertColorTable(urlDatabase, "gray94", GRAY94);
		insertColorTable(urlDatabase, "gray95", GRAY95);
		insertColorTable(urlDatabase, "gray96", GRAY96);
		insertColorTable(urlDatabase, "gray97", GRAY97);
		insertColorTable(urlDatabase, "green", GREEN);
		insertColorTable(urlDatabase, "green1", GREEN1);
		insertColorTable(urlDatabase, "green2", GREEN2);
		insertColorTable(urlDatabase, "green3", GREEN3);
		insertColorTable(urlDatabase, "greenyellow", GREENYELLOW);
		insertColorTable(urlDatabase, "honeydew", HONEYDEW);
		insertColorTable(urlDatabase, "honeydew1", HONEYDEW1);
		insertColorTable(urlDatabase, "honeydew2", HONEYDEW2);
		insertColorTable(urlDatabase, "honeydew3", HONEYDEW3);
		insertColorTable(urlDatabase, "hotpink", HOTPINK);
		insertColorTable(urlDatabase, "hotpink1", HOTPINK1);
		insertColorTable(urlDatabase, "hotpink2", HOTPINK2);
		insertColorTable(urlDatabase, "hotpink3", HOTPINK3);
		insertColorTable(urlDatabase, "hotpink4", HOTPINK4);
		insertColorTable(urlDatabase, "indianred", INDIANRED);
		insertColorTable(urlDatabase, "indianred1", INDIANRED1);
		insertColorTable(urlDatabase, "indianred2", INDIANRED2);
		insertColorTable(urlDatabase, "indianred3", INDIANRED3);
		insertColorTable(urlDatabase, "indianred4", INDIANRED4);
		insertColorTable(urlDatabase, "indigo", INDIGO);
		insertColorTable(urlDatabase, "ivory", IVORY);
		insertColorTable(urlDatabase, "ivory1", IVORY1);
		insertColorTable(urlDatabase, "ivory2", IVORY2);
		insertColorTable(urlDatabase, "ivory3", IVORY3);
		insertColorTable(urlDatabase, "khaki", KHAKI);
		insertColorTable(urlDatabase, "khaki1", KHAKI1);
		insertColorTable(urlDatabase, "khaki2", KHAKI2);
		insertColorTable(urlDatabase, "khaki3", KHAKI3);
		insertColorTable(urlDatabase, "khaki4", KHAKI4);
		insertColorTable(urlDatabase, "lavender", LAVENDER);
		insertColorTable(urlDatabase, "lavenderblush", LAVENDERBLUSH);
		insertColorTable(urlDatabase, "lavenderblush1", LAVENDERBLUSH1);
		insertColorTable(urlDatabase, "lavenderblush2", LAVENDERBLUSH2);
		insertColorTable(urlDatabase, "lavenderblush3", LAVENDERBLUSH3);
		insertColorTable(urlDatabase, "lawngreen", LAWNGREEN);
		insertColorTable(urlDatabase, "lemonchiffon", LEMONCHIFFON);
		insertColorTable(urlDatabase, "lemonchiffon1", LEMONCHIFFON1);
		insertColorTable(urlDatabase, "lemonchiffon2", LEMONCHIFFON2);
		insertColorTable(urlDatabase, "lemonchiffon3", LEMONCHIFFON3);
		insertColorTable(urlDatabase, "lightblue", LIGHTBLUE);
		insertColorTable(urlDatabase, "lightblue1", LIGHTBLUE1);
		insertColorTable(urlDatabase, "lightblue2", LIGHTBLUE2);
		insertColorTable(urlDatabase, "lightblue3", LIGHTBLUE3);
		insertColorTable(urlDatabase, "lightblue4", LIGHTBLUE4);
		insertColorTable(urlDatabase, "lightcoral", LIGHTCORAL);
		insertColorTable(urlDatabase, "lightcyan", LIGHTCYAN);
		insertColorTable(urlDatabase, "lightcyan1", LIGHTCYAN1);
		insertColorTable(urlDatabase, "lightcyan2", LIGHTCYAN2);
		insertColorTable(urlDatabase, "lightcyan3", LIGHTCYAN3);
		insertColorTable(urlDatabase, "lightgoldenrod", LIGHTGOLDENROD);
		insertColorTable(urlDatabase, "lightgoldenrod1", LIGHTGOLDENROD1);
		insertColorTable(urlDatabase, "lightgoldenrod2", LIGHTGOLDENROD2);
		insertColorTable(urlDatabase, "lightgoldenrod3", LIGHTGOLDENROD3);
		insertColorTable(urlDatabase, "lightgoldenrod4", LIGHTGOLDENROD4);
		insertColorTable(urlDatabase, "lightgoldenrodyellow", LIGHTGOLDENRODYELLOW);
		insertColorTable(urlDatabase, "lightgray", LIGHTGRAY);
		insertColorTable(urlDatabase, "lightgrey", LIGHTGRAY);
		insertColorTable(urlDatabase, "lightgreen", LIGHTGREEN);
		insertColorTable(urlDatabase, "lightpink", LIGHTPINK);
		insertColorTable(urlDatabase, "lightpink1", LIGHTPINK1);
		insertColorTable(urlDatabase, "lightpink2", LIGHTPINK2);
		insertColorTable(urlDatabase, "lightpink3", LIGHTPINK3);
		insertColorTable(urlDatabase, "lightpink4", LIGHTPINK4);
		insertColorTable(urlDatabase, "lightsalmon", LIGHTSALMON);
		insertColorTable(urlDatabase, "lightsalmon1", LIGHTSALMON1);
		insertColorTable(urlDatabase, "lightsalmon2", LIGHTSALMON2);
		insertColorTable(urlDatabase, "lightsalmon3", LIGHTSALMON3);
		insertColorTable(urlDatabase, "lightseagreen", LIGHTSEAGREEN);
		insertColorTable(urlDatabase, "lightskyblue", LIGHTSKYBLUE);
		insertColorTable(urlDatabase, "lightskyblue1", LIGHTSKYBLUE1);
		insertColorTable(urlDatabase, "lightskyblue2", LIGHTSKYBLUE2);
		insertColorTable(urlDatabase, "lightskyblue3", LIGHTSKYBLUE3);
		insertColorTable(urlDatabase, "lightskyblue4", LIGHTSKYBLUE4);
		insertColorTable(urlDatabase, "lightslateblue", LIGHTSLATEBLUE);
		insertColorTable(urlDatabase, "lightslategray", LIGHTSLATEGRAY);
		insertColorTable(urlDatabase, "lightsteelblue", LIGHTSTEELBLUE);
		insertColorTable(urlDatabase, "lightsteelblue1", LIGHTSTEELBLUE1);
		insertColorTable(urlDatabase, "lightsteelblue2", LIGHTSTEELBLUE2);
		insertColorTable(urlDatabase, "lightsteelblue3", LIGHTSTEELBLUE3);
		insertColorTable(urlDatabase, "lightsteelblue4", LIGHTSTEELBLUE4);
		insertColorTable(urlDatabase, "lightyellow", LIGHTYELLOW);
		insertColorTable(urlDatabase, "lightyellow1", LIGHTYELLOW1);
		insertColorTable(urlDatabase, "lightyellow2", LIGHTYELLOW2);
		insertColorTable(urlDatabase, "lightyellow3", LIGHTYELLOW3);
		insertColorTable(urlDatabase, "limegreen", LIMEGREEN);
		insertColorTable(urlDatabase, "linen", LINEN);
		insertColorTable(urlDatabase, "magenta", MAGENTA);
		insertColorTable(urlDatabase, "magenta1", MAGENTA1);
		insertColorTable(urlDatabase, "magenta2", MAGENTA2);
		insertColorTable(urlDatabase, "maroon", MAROON);
		insertColorTable(urlDatabase, "maroon1", MAROON1);
		insertColorTable(urlDatabase, "maroon2", MAROON2);
		insertColorTable(urlDatabase, "maroon3", MAROON3);
		insertColorTable(urlDatabase, "maroon4", MAROON4);
		insertColorTable(urlDatabase, "mediumaquamarine", MEDIUMAQUAMARINE);
		insertColorTable(urlDatabase, "mediumblue", MEDIUMBLUE);
		insertColorTable(urlDatabase, "mediumforestgreen", MEDIUMFORESTGREEN);
		insertColorTable(urlDatabase, "mediumgoldenrod", MEDIUMGOLDENROD);
		insertColorTable(urlDatabase, "mediumorchid", MEDIUMORCHID);
		insertColorTable(urlDatabase, "mediumorchid1", MEDIUMORCHID1);
		insertColorTable(urlDatabase, "mediumorchid2", MEDIUMORCHID2);
		insertColorTable(urlDatabase, "mediumorchid3", MEDIUMORCHID3);
		insertColorTable(urlDatabase, "mediumorchid4", MEDIUMORCHID4);
		insertColorTable(urlDatabase, "mediumpurple", MEDIUMPURPLE);
		insertColorTable(urlDatabase, "mediumpurple1", MEDIUMPURPLE1);
		insertColorTable(urlDatabase, "mediumpurple2", MEDIUMPURPLE2);
		insertColorTable(urlDatabase, "mediumpurple3", MEDIUMPURPLE3);
		insertColorTable(urlDatabase, "mediumpurple4", MEDIUMPURPLE4);
		insertColorTable(urlDatabase, "mediumseagreen", MEDIUMSEAGREEN);
		insertColorTable(urlDatabase, "mediumslateblue", MEDIUMSLATEBLUE);
		insertColorTable(urlDatabase, "mediumspringgreen", MEDIUMSPRINGGREEN);
		insertColorTable(urlDatabase, "mediumturquoisenew", MEDIUMTURQUOISE);
		insertColorTable(urlDatabase, "mediumvioletred", MEDIUMVIOLETRED);
		insertColorTable(urlDatabase, "midnightblue", MIDNIGHTBLUE);
		insertColorTable(urlDatabase, "mintcream", MINTCREAM);
		insertColorTable(urlDatabase, "mistyrose", MISTYROSE);
		insertColorTable(urlDatabase, "mistyrose1", MISTYROSE1);
		insertColorTable(urlDatabase, "mistyrose2", MISTYROSE2);
		insertColorTable(urlDatabase, "mistyrose3", MISTYROSE3);
		insertColorTable(urlDatabase, "moccasin", MOCCASIN);
		insertColorTable(urlDatabase, "navajowhite", NAVAJOWHITE);
		insertColorTable(urlDatabase, "navajowhite1", NAVAJOWHITE1);
		insertColorTable(urlDatabase, "navajowhite2", NAVAJOWHITE2);
		insertColorTable(urlDatabase, "navajowhite3", NAVAJOWHITE3);
		insertColorTable(urlDatabase, "navy", NAVYBLUE);
		insertColorTable(urlDatabase, "navyblue", NAVYBLUE);
		insertColorTable(urlDatabase, "oldlace", OLDLACE);
		insertColorTable(urlDatabase, "olive", OLIVE);
		insertColorTable(urlDatabase, "olivedrab", OLIVEDRAB);
		insertColorTable(urlDatabase, "olivedrab1", OLIVEDRAB1);
		insertColorTable(urlDatabase, "olivedrab2", OLIVEDRAB2);
		insertColorTable(urlDatabase, "olivedrab3", OLIVEDRAB3);
		insertColorTable(urlDatabase, "orange", ORANGE);
		insertColorTable(urlDatabase, "orange1", ORANGE1);
		insertColorTable(urlDatabase, "orange2", ORANGE2);
		insertColorTable(urlDatabase, "orange3", ORANGE3);
		insertColorTable(urlDatabase, "orangered", ORANGERED);
		insertColorTable(urlDatabase, "orangered1", ORANGERED1);
		insertColorTable(urlDatabase, "orangered2", ORANGERED2);
		insertColorTable(urlDatabase, "orangered3", ORANGERED3);
		insertColorTable(urlDatabase, "orchid", ORCHID);
		insertColorTable(urlDatabase, "orchid1", ORCHID1);
		insertColorTable(urlDatabase, "orchid2", ORCHID2);
		insertColorTable(urlDatabase, "orchid3", ORCHID3);
		insertColorTable(urlDatabase, "orchid4", ORCHID4);
		insertColorTable(urlDatabase, "palegoldenrod", PALEGOLDENROD);
		insertColorTable(urlDatabase, "palegreen", PALEGREEN);
		insertColorTable(urlDatabase, "palegreen1", PALEGREEN1);
		insertColorTable(urlDatabase, "palegreen2", PALEGREEN2);
		insertColorTable(urlDatabase, "palegreen3", PALEGREEN3);
		insertColorTable(urlDatabase, "paleturquoise", PALETURQUOISE);
		insertColorTable(urlDatabase, "paleturquoise1", PALETURQUOISE1);
		insertColorTable(urlDatabase, "paleturquoise2", PALETURQUOISE2);
		insertColorTable(urlDatabase, "paleturquoise3", PALETURQUOISE3);
		insertColorTable(urlDatabase, "paleturquoise4", PALETURQUOISE4);
		insertColorTable(urlDatabase, "palevioletred", PALEVIOLETRED);
		insertColorTable(urlDatabase, "palevioletred1", PALEVIOLETRED1);
		insertColorTable(urlDatabase, "palevioletred2", PALEVIOLETRED2);
		insertColorTable(urlDatabase, "palevioletred3", PALEVIOLETRED3);
		insertColorTable(urlDatabase, "palevioletred4", PALEVIOLETRED4);
		insertColorTable(urlDatabase, "papayawhip", PAPAYAWHIP);
		insertColorTable(urlDatabase, "peachpuff", PEACHPUFF);
		insertColorTable(urlDatabase, "peachpuff1", PEACHPUFF1);
		insertColorTable(urlDatabase, "peachpuff2", PEACHPUFF2);
		insertColorTable(urlDatabase, "peachpuff3", PEACHPUFF3);
		insertColorTable(urlDatabase, "pink", PINK);
		insertColorTable(urlDatabase, "pink1", PINK1);
		insertColorTable(urlDatabase, "pink2", PINK2);
		insertColorTable(urlDatabase, "pink3", PINK3);
		insertColorTable(urlDatabase, "pink4", PINK4);
		insertColorTable(urlDatabase, "plum", PLUM);
		insertColorTable(urlDatabase, "plum1", PLUM1);
		insertColorTable(urlDatabase, "plum2", PLUM2);
		insertColorTable(urlDatabase, "plum3", PLUM3);
		insertColorTable(urlDatabase, "plum4", PLUM4);
		insertColorTable(urlDatabase, "powderblue", POWDERBLUE);
		insertColorTable(urlDatabase, "purple", PURPLE);
		insertColorTable(urlDatabase, "purple1", PURPLE1);
		insertColorTable(urlDatabase, "purple2", PURPLE2);
		insertColorTable(urlDatabase, "purple3", PURPLE3);
		insertColorTable(urlDatabase, "purple4", PURPLE4);
		insertColorTable(urlDatabase, "red", RED);
		insertColorTable(urlDatabase, "red1", RED1);
		insertColorTable(urlDatabase, "red2", RED2);
		insertColorTable(urlDatabase, "rosybrown", ROSYBROWN);
		insertColorTable(urlDatabase, "rosybrown1", ROSYBROWN1);
		insertColorTable(urlDatabase, "rosybrown2", ROSYBROWN2);
		insertColorTable(urlDatabase, "rosybrown3", ROSYBROWN3);
		insertColorTable(urlDatabase, "rosybrown4", ROSYBROWN4);
		insertColorTable(urlDatabase, "royalblue", ROYALBLUE);
		insertColorTable(urlDatabase, "royalblue1", ROYALBLUE1);
		insertColorTable(urlDatabase, "royalblue2", ROYALBLUE2);
		insertColorTable(urlDatabase, "royalblue3", ROYALBLUE3);
		insertColorTable(urlDatabase, "royalblue4", ROYALBLUE4);
		insertColorTable(urlDatabase, "saddlebrown", SADDLEBROWN);
		insertColorTable(urlDatabase, "salmon", SALMON);
		insertColorTable(urlDatabase, "salmon1", SALMON1);
		insertColorTable(urlDatabase, "salmon2", SALMON2);
		insertColorTable(urlDatabase, "salmon3", SALMON3);
		insertColorTable(urlDatabase, "salmon4", SALMON4);
		insertColorTable(urlDatabase, "sandybrown", SANDYBROWN);
		insertColorTable(urlDatabase, "seagreen", SEAGREEN);
		insertColorTable(urlDatabase, "seagreen1", SEAGREEN1);
		insertColorTable(urlDatabase, "seagreen2", SEAGREEN2);
		insertColorTable(urlDatabase, "seagreen3", SEAGREEN3);
		insertColorTable(urlDatabase, "seashell", SEASHELL);
		insertColorTable(urlDatabase, "seashell1", SEASHELL1);
		insertColorTable(urlDatabase, "seashell2", SEASHELL2);
		insertColorTable(urlDatabase, "seashell3", SEASHELL3);
		insertColorTable(urlDatabase, "sienna", SIENNA);
		insertColorTable(urlDatabase, "sienna1", SIENNA1);
		insertColorTable(urlDatabase, "sienna2", SIENNA2);
		insertColorTable(urlDatabase, "sienna3", SIENNA3);
		insertColorTable(urlDatabase, "sienna4", SIENNA4);
		insertColorTable(urlDatabase, "silver", SILVER);
		insertColorTable(urlDatabase, "skyblue", SKYBLUE);
		insertColorTable(urlDatabase, "skyblue1", SKYBLUE1);
		insertColorTable(urlDatabase, "skyblue2", SKYBLUE2);
		insertColorTable(urlDatabase, "skyblue3", SKYBLUE3);
		insertColorTable(urlDatabase, "skyblue4", SKYBLUE4);
		insertColorTable(urlDatabase, "slateblue", SLATEBLUE);
		insertColorTable(urlDatabase, "slateblue1", SLATEBLUE1);
		insertColorTable(urlDatabase, "slateblue2", SLATEBLUE2);
		insertColorTable(urlDatabase, "slateblue3", SLATEBLUE3);
		insertColorTable(urlDatabase, "slateblue4", SLATEBLUE4);
		insertColorTable(urlDatabase, "slategray", SLATEGRAY);
		insertColorTable(urlDatabase, "slategray1", SLATEGRAY1);
		insertColorTable(urlDatabase, "slategray2", SLATEGRAY2);
		insertColorTable(urlDatabase, "slategray3", SLATEGRAY3);
		insertColorTable(urlDatabase, "slategray4", SLATEGRAY4);
		insertColorTable(urlDatabase, "snow", SNOW);
		insertColorTable(urlDatabase, "snow1", SNOW1);
		insertColorTable(urlDatabase, "snow2", SNOW2);
		insertColorTable(urlDatabase, "snow3", SNOW3);
		insertColorTable(urlDatabase, "springgreen", SPRINGGREEN);
		insertColorTable(urlDatabase, "springgreen1", SPRINGGREEN1);
		insertColorTable(urlDatabase, "springgreen2", SPRINGGREEN2);
		insertColorTable(urlDatabase, "springgreen3", SPRINGGREEN3);
		insertColorTable(urlDatabase, "steelblue", STEELBLUE);
		insertColorTable(urlDatabase, "steelblue1", STEELBLUE1);
		insertColorTable(urlDatabase, "steelblue2", STEELBLUE2);
		insertColorTable(urlDatabase, "steelblue3", STEELBLUE3);
		insertColorTable(urlDatabase, "steelblue4", STEELBLUE4);
		insertColorTable(urlDatabase, "tan", TAN);
		insertColorTable(urlDatabase, "tan1", TAN1);
		insertColorTable(urlDatabase, "tan2", TAN2);
		insertColorTable(urlDatabase, "tan3", TAN3);
		insertColorTable(urlDatabase, "tan4", TAN4);
		insertColorTable(urlDatabase, "teal", TEAL);
		insertColorTable(urlDatabase, "thistle", THISTLE);
		insertColorTable(urlDatabase, "thistle1", THISTLE1);
		insertColorTable(urlDatabase, "thistle2", THISTLE2);
		insertColorTable(urlDatabase, "thistle3", THISTLE3);
		insertColorTable(urlDatabase, "thistle4", THISTLE4);
		insertColorTable(urlDatabase, "tomato", TOMATO);
		insertColorTable(urlDatabase, "tomato1", TOMATO1);
		insertColorTable(urlDatabase, "tomato2", TOMATO2);
		insertColorTable(urlDatabase, "tomato3", TOMATO3);
		insertColorTable(urlDatabase, "turquoise", TURQUOISE);
		insertColorTable(urlDatabase, "turquoise1", TURQUOISE1);
		insertColorTable(urlDatabase, "turquoise2", TURQUOISE2);
		insertColorTable(urlDatabase, "turquoise3", TURQUOISE3);
		insertColorTable(urlDatabase, "turquoise4", TURQUOISE4);
		insertColorTable(urlDatabase, "violet", VIOLET);
		insertColorTable(urlDatabase, "violetred", VIOLETRED);
		insertColorTable(urlDatabase, "violetred1", VIOLETRED1);
		insertColorTable(urlDatabase, "violetred2", VIOLETRED2);
		insertColorTable(urlDatabase, "violetred3", VIOLETRED3);
		insertColorTable(urlDatabase, "violetred4", VIOLETRED4);
		insertColorTable(urlDatabase, "wheat", WHEAT);
		insertColorTable(urlDatabase, "wheat1", WHEAT1);
		insertColorTable(urlDatabase, "wheat2", WHEAT2);
		insertColorTable(urlDatabase, "wheat3", WHEAT3);
		insertColorTable(urlDatabase, "wheat4", WHEAT4);
		insertColorTable(urlDatabase, "white", WHITE);
		insertColorTable(urlDatabase, "whitesmoke", WHITESMOKE);
		insertColorTable(urlDatabase, "yellow", YELLOW);
		insertColorTable(urlDatabase, "yellow1", YELLOW1);
		insertColorTable(urlDatabase, "yellow2", YELLOW2);
		insertColorTable(urlDatabase, "yellow3", YELLOW3);
		insertColorTable(urlDatabase, "yellowgreen", YELLOWGREEN);
    }
	
	private void populateFontTable(String urlDatabase) {
		insertFont(urlDatabase, "Aharoni", "FONT");
		insertFont(urlDatabase, "Andalus", "FONT");
		insertFont(urlDatabase, "AngsanaNew", "FONT");
		insertFont(urlDatabase, "AngsanaUPC", "FONT");
		insertFont(urlDatabase, "AngsanaUPC", "FONT");
		insertFont(urlDatabase, "Aparajita", "FONT");
		insertFont(urlDatabase, "ArabicTypesetting", "FONT");
		insertFont(urlDatabase, "Arial", "FONT");
		insertFont(urlDatabase, "ArialBlack", "FONT");
		insertFont(urlDatabase, "Batang", "FONT");
		insertFont(urlDatabase, "BatangChe", "FONT");
		insertFont(urlDatabase, "BrowalliaNew", "FONT");
		insertFont(urlDatabase, "BrowalliaUPC", "FONT");
		insertFont(urlDatabase, "Caladea", "FONT");
		insertFont(urlDatabase, "Calibri", "FONT");
		insertFont(urlDatabase, "CalibriLight", "FONT");
		insertFont(urlDatabase, "Cambria", "FONT");
		insertFont(urlDatabase, "CambriaMath", "FONT");
		insertFont(urlDatabase, "Candara", "FONT");
		insertFont(urlDatabase, "Carlito", "FONT");
		insertFont(urlDatabase, "ComicSansMS", "FONT");
		insertFont(urlDatabase, "Consolas", "FONT");
		insertFont(urlDatabase, "Constantia", "FONT");
		insertFont(urlDatabase, "Corbel", "FONT");
		insertFont(urlDatabase, "CordiaNew", "FONT");
		insertFont(urlDatabase, "CordiaUPC", "FONT");
		insertFont(urlDatabase, "CourierNew", "FONT");
		insertFont(urlDatabase, "DaunPenh", "FONT");
		insertFont(urlDatabase, "David", "FONT");
		insertFont(urlDatabase, "DejaVuSans", "FONT");
		insertFont(urlDatabase, "DejaVuSansCondensed", "FONT");
		insertFont(urlDatabase, "DejaVuSansLight", "FONT");
		insertFont(urlDatabase, "DejaVuSansMono", "FONT");
		insertFont(urlDatabase, "DejaVuSerif", "FONT");
		insertFont(urlDatabase, "DejaVuSerifCondensed", "FONT");
		insertFont(urlDatabase, "DFKai-SB", "FONT");
		insertFont(urlDatabase, "Dialog", "FONT");
		insertFont(urlDatabase, "DialogInput", "FONT");
		insertFont(urlDatabase, "DilleniaUPC", "FONT");
		insertFont(urlDatabase, "DokChampa", "FONT");
		insertFont(urlDatabase, "Dotum", "FONT");
		insertFont(urlDatabase, "DotumChe", "FONT");
		insertFont(urlDatabase, "Ebrima", "FONT");
		insertFont(urlDatabase, "EstrangeloEdessa", "FONT");
		insertFont(urlDatabase, "EucrosiaUPC", "FONT");
		insertFont(urlDatabase, "Euphemia", "FONT");
		insertFont(urlDatabase, "FangSong", "FONT");
		insertFont(urlDatabase, "FranklinGothicMedium", "FONT");
		insertFont(urlDatabase, "FrankRuehl", "FONT");
		insertFont(urlDatabase, "FreesiaUPC", "FONT");
		insertFont(urlDatabase, "Gabriola", "FONT");
		insertFont(urlDatabase, "Gautami", "FONT");
		insertFont(urlDatabase, "GentiumBasic", "FONT");
		insertFont(urlDatabase, "GentiumBookBasic", "FONT");
		insertFont(urlDatabase, "Georgia", "FONT");
		insertFont(urlDatabase, "Gisha", "FONT");
		insertFont(urlDatabase, "Gulim", "FONT");
		insertFont(urlDatabase, "GulimChe", "FONT");
		insertFont(urlDatabase, "Gungsuh", "FONT");
		insertFont(urlDatabase, "GungsuhChe", "FONT");
		insertFont(urlDatabase, "Impact", "FONT");
		insertFont(urlDatabase, "IrisUPC", "FONT");
		insertFont(urlDatabase, "IskoolaPota", "FONT");
		insertFont(urlDatabase, "JasmineUPC", "FONT");
		insertFont(urlDatabase, "KaiTi", "FONT");
		insertFont(urlDatabase, "Kalinga", "FONT");
		insertFont(urlDatabase, "Kartika", "FONT");
		insertFont(urlDatabase, "KhmerUI", "FONT");
		insertFont(urlDatabase, "KodchiangUPC", "FONT");
		insertFont(urlDatabase, "Kokila", "FONT");
		insertFont(urlDatabase, "LaoUI", "FONT");
		insertFont(urlDatabase, "Latha", "FONT");
		insertFont(urlDatabase, "Leelawadee", "FONT");
		insertFont(urlDatabase, "LevenimMT", "FONT");
		insertFont(urlDatabase, "LiberationMono", "FONT");
		insertFont(urlDatabase, "LiberationSans", "FONT");
		insertFont(urlDatabase, "LiberationSansNarrow", "FONT");
		insertFont(urlDatabase, "LiberationSerif", "FONT");
		insertFont(urlDatabase, "LilyUPC", "FONT");
		insertFont(urlDatabase, "LinuxBiolinumG", "FONT");
		insertFont(urlDatabase, "LinuxLibertineDisplayG", "FONT");
		insertFont(urlDatabase, "LinuxLibertineG", "FONT");
		insertFont(urlDatabase, "LucidaBright", "FONT");
		insertFont(urlDatabase, "LucidaConsole", "FONT");
		insertFont(urlDatabase, "LucidaSans", "FONT");
		insertFont(urlDatabase, "LucidaSansTypewriter", "FONT");
		insertFont(urlDatabase, "LucidaSansUnicode", "FONT");
		insertFont(urlDatabase, "MalgunGothic", "FONT");
		insertFont(urlDatabase, "Mangal", "FONT");
		insertFont(urlDatabase, "Marlett", "FONT");
		insertFont(urlDatabase, "Meiryo", "FONT");
		insertFont(urlDatabase, "MeiryoUI", "FONT");
		insertFont(urlDatabase, "MicrosoftHimalaya", "FONT");
		insertFont(urlDatabase, "MicrosoftJhengHei", "FONT");
		insertFont(urlDatabase, "MicrosoftNewTaiLue", "FONT");
		insertFont(urlDatabase, "MicrosoftPhagsPa", "FONT");
		insertFont(urlDatabase, "MicrosoftSansSerif", "FONT");
		insertFont(urlDatabase, "MicrosoftTaiLe", "FONT");
		insertFont(urlDatabase, "MicrosoftUighur", "FONT");
		insertFont(urlDatabase, "MicrosoftYaHei", "FONT");
		insertFont(urlDatabase, "MicrosoftYiBaiti", "FONT");
		insertFont(urlDatabase, "MingLiU", "FONT");
		insertFont(urlDatabase, "MingLiU-ExtB", "FONT");
		insertFont(urlDatabase, "MingLiU_HKSCS", "FONT");
		insertFont(urlDatabase, "MingLiU_HKSCS-ExtB", "FONT");
		insertFont(urlDatabase, "Miriam", "FONT");
		insertFont(urlDatabase, "MiriamFixed", "FONT");
		insertFont(urlDatabase, "MongolianBaiti", "FONT");
		insertFont(urlDatabase, "Monospaced", "FONT");
		insertFont(urlDatabase, "MoolBoran", "FONT");
		insertFont(urlDatabase, "MSGothic", "FONT");
		insertFont(urlDatabase, "MSMincho", "FONT");
		insertFont(urlDatabase, "MSPGothic", "FONT");
		insertFont(urlDatabase, "MSPMincho", "FONT");
		insertFont(urlDatabase, "MSUIGothic", "FONT");
		insertFont(urlDatabase, "MVBoli", "FONT");
		insertFont(urlDatabase, "Narkisim", "FONT");
		insertFont(urlDatabase, "NSimSun", "FONT");
		insertFont(urlDatabase, "Nyala", "FONT");
		insertFont(urlDatabase, "OpenSans", "FONT");
		insertFont(urlDatabase, "OpenSymbol", "FONT");
		insertFont(urlDatabase, "PalatinoLinotype", "FONT");
		insertFont(urlDatabase, "PlantagenetCherokee", "FONT");
		insertFont(urlDatabase, "PMingLiU", "FONT");
		insertFont(urlDatabase, "PMingLiU-ExtB", "FONT");
		insertFont(urlDatabase, "PTSerif", "FONT");
		insertFont(urlDatabase, "Raavi", "FONT");
		insertFont(urlDatabase, "Rod", "FONT");
		insertFont(urlDatabase, "SakkalMajalla", "FONT");
		insertFont(urlDatabase, "SansSerif", "FONT");
		insertFont(urlDatabase, "SegoePrint", "FONT");
		insertFont(urlDatabase, "SegoeScript", "FONT");
		insertFont(urlDatabase, "SegoeUI", "FONT");
		insertFont(urlDatabase, "SegoeUILight", "FONT");
		insertFont(urlDatabase, "SegoeUISemibold", "FONT");
		insertFont(urlDatabase, "SegoeUISymbol", "FONT");
		insertFont(urlDatabase, "Serif", "FONT");
		insertFont(urlDatabase, "ShonarBangla", "FONT");
		insertFont(urlDatabase, "Shruti", "FONT");
		insertFont(urlDatabase, "SimHei", "FONT");
		insertFont(urlDatabase, "SimplifiedArabic", "FONT");
		insertFont(urlDatabase, "SimplifiedArabicFixed", "FONT");
		insertFont(urlDatabase, "SimSun", "FONT");
		insertFont(urlDatabase, "SimSun-ExtB", "FONT");
		insertFont(urlDatabase, "SourceCodePro", "FONT");
		insertFont(urlDatabase, "SourceSansPro", "FONT");
		insertFont(urlDatabase, "SourceSansProBlack", "FONT");
		insertFont(urlDatabase, "SourceSansProExtraLight", "FONT");
		insertFont(urlDatabase, "SourceSansProLight", "FONT");
		insertFont(urlDatabase, "SourceSansProSemibold", "FONT");
		insertFont(urlDatabase, "Sylfaen", "FONT");
		insertFont(urlDatabase, "Symbol", "FONT");
		insertFont(urlDatabase, "Tahoma", "FONT");
		insertFont(urlDatabase, "TimesNewRoman", "FONT");
		insertFont(urlDatabase, "TraditionalArabic", "FONT");
		insertFont(urlDatabase, "TrebuchetMS", "FONT");
		insertFont(urlDatabase, "Tunga", "FONT");
		insertFont(urlDatabase, "Utsaah", "FONT");
		insertFont(urlDatabase, "Vani", "FONT");
		insertFont(urlDatabase, "Verdana", "FONT");
		insertFont(urlDatabase, "Vijaya", "FONT");
		insertFont(urlDatabase, "Vrinda", "FONT");
		insertFont(urlDatabase, "Webdings", "FONT");
		insertFont(urlDatabase, "Wingdings", "FONT");
	}

	private void populateFontSizeTable(String urlDatabase) {
		insertFont(urlDatabase, "8", "FONT_SIZE");
		insertFont(urlDatabase, "9", "FONT_SIZE");
		insertFont(urlDatabase, "10", "FONT_SIZE");
		insertFont(urlDatabase, "11", "FONT_SIZE");
		insertFont(urlDatabase, "12", "FONT_SIZE");
		insertFont(urlDatabase, "14", "FONT_SIZE");
		insertFont(urlDatabase, "16", "FONT_SIZE");
		insertFont(urlDatabase, "18", "FONT_SIZE");
		insertFont(urlDatabase, "20", "FONT_SIZE");
		insertFont(urlDatabase, "22", "FONT_SIZE");
		insertFont(urlDatabase, "24", "FONT_SIZE");
		insertFont(urlDatabase, "26", "FONT_SIZE");
		insertFont(urlDatabase, "28", "FONT_SIZE");
		insertFont(urlDatabase, "36", "FONT_SIZE");
		insertFont(urlDatabase, "48", "FONT_SIZE");
		insertFont(urlDatabase, "72", "FONT_SIZE");
	}
	
	private void populateSearchEngine(String urlDatabase) {
		insertSearch(urlDatabase, "Bing Search", "Bing web search engine.", "http://www.bing.com/search?q", "q", 0, "SEARCH_ENGINE");
		insertSearch(urlDatabase, "Google Web Search", "Google's main search engine.", "http://google.com/search", "q", 1, "SEARCH_ENGINE");
		insertSearch(urlDatabase, "Yahoo! Web Search", "Yahoo's web search engine.", "http://search.yahoo.com/search", "p", 0, "SEARCH_ENGINE");
		insertSearch(urlDatabase, "Wikipedia", "English Wikipedia article search.", "http://en.wikipedia.org/wiki/Special:Search", "search", 0, "SEARCH_ENGINE");
	}
	
	private void insertColorTable(String urlDatabase, String name, int value) {
		try (Connection conn = DriverManager.getConnection(urlDatabase);
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO COLOR (name, value) VALUES(?,?)")) {
			pstmt.setString(1, name);
			pstmt.setInt(2, value);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void insertFont(String urlDatabase, String name, String type) {
		try (Connection conn = DriverManager.getConnection(urlDatabase);
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO "+type+" (name) VALUES(?)")) {
			pstmt.setString(1, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void insertSearch(String urlDatabase, String name, String description, String baseUrl, String queryParameter, int selected, String type) {
		try (Connection conn = DriverManager.getConnection(urlDatabase);
				 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)")) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setString(3, type);
			pstmt.setString(4, baseUrl);
			pstmt.setString(5, queryParameter);
			pstmt.setInt(6, selected);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
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
