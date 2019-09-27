package org.lobo.store;

import java.io.File;

public class SQLiteCommon {

	public static final String COOKIES = " SELECT DISTINCT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly FROM COOKIE WHERE domain = ? AND path = ?";

	public static final String INSERT_COOKIES = "INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)";

	public static final String SOURCE_CACHE = "SELECT source FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) > strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
        
    public static final String CHECK_CACHE = "SELECT count(*) FROM cache WHERE baseUrl = ? AND contenLenght = ? AND etag = ? AND type = ?";
    
    public static final String INSERT_CACHE = "INSERT INTO CACHE (baseUrl, source, contenLenght, etag, lastModified, type) VALUES(?,?,?,?,?,?)";
    
    public static final String INPUT = "SELECT DISTINCT value from INPUT where (name like ?) ";
	
	public static final String INSERT_INPUT= "INSERT INTO INPUT (name, value) VALUES(?,?)";
	
	public static final String DELETE_INPUT = "DELETE FROM INPUT";
	
    public static final String DELETE_SOURCE_CACHE = "DELETE FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) < strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
	
	/** The Constant SETTINGS_DIR. */
	public static final String JDBC_SQLITE = "jdbc:sqlite:";

	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE.sqlite";

	public static void createDatabaseDirectory() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		store.mkdirs();
	}

	public static String getCacheStore() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, ".lobo");
		final File store = new File(storeDir, "cache");
		return store.getPath();
	}

	public static String getDatabaseDirectory() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		new File(JDBC_SQLITE + store + "\\" + LOBO_DB);
		return JDBC_SQLITE + store + "\\" + LOBO_DB;
	}

	public static String getDatabaseStore() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		return store + "\\" + LOBO_DB;
	}
}
