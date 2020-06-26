package org.loboevolution.store;

import java.io.File;

/**
 * <p>SQLiteCommon class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SQLiteCommon {

	/** Constant COOKIES=" SELECT DISTINCT cookieName, cookieValu"{trunked} */
	public static final String COOKIES = " SELECT DISTINCT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly FROM COOKIE WHERE domain = ? AND path = ?";

	/** Constant INSERT_COOKIES="INSERT INTO COOKIE (cookieName, cookieV"{trunked} */
	public static final String INSERT_COOKIES = "INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)";

	/** Constant SOURCE_CACHE="SELECT source FROM cache WHERE baseUrl "{trunked} */
	public static final String SOURCE_CACHE = "SELECT source FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) > strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
        
    /** Constant CHECK_CACHE="SELECT count(*) FROM cache WHERE baseUr"{trunked} */
    public static final String CHECK_CACHE = "SELECT count(*) FROM cache WHERE baseUrl = ? AND contenLenght = ? AND etag = ? AND type = ?";
    
    /** Constant INSERT_CACHE="INSERT INTO CACHE (baseUrl, source, con"{trunked} */
    public static final String INSERT_CACHE = "INSERT INTO CACHE (baseUrl, source, contenLenght, etag, lastModified, type) VALUES(?,?,?,?,?,?)";
    
    /** Constant INPUT="SELECT DISTINCT value from INPUT where "{trunked} */
    public static final String INPUT = "SELECT DISTINCT value from INPUT where (name like ?) ";
	
	/** Constant INSERT_INPUT="INSERT INTO INPUT (name, value) VALUES("{trunked} */
	public static final String INSERT_INPUT= "INSERT INTO INPUT (name, value) VALUES(?,?)";
	
	/** Constant DELETE_INPUT="DELETE FROM INPUT" */
	public static final String DELETE_INPUT = "DELETE FROM INPUT";
	
    /** Constant DELETE_SOURCE_CACHE="DELETE FROM cache WHERE baseUrl = ? AND"{trunked} */
    public static final String DELETE_SOURCE_CACHE = "DELETE FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) < strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
	
    /** Constant DELETE_LINK="DELETE FROM LINK_VISITED" */
    public static final String DELETE_LINK = "DELETE FROM LINK_VISITED";
    
    /** Constant INSERT_LINK="INSERT INTO LINK_VISITED VALUES(?)" */
    public static final String INSERT_LINK = "INSERT INTO LINK_VISITED VALUES(?)";
    
    /** Constant LINK="SELECT COUNT(*) FROM LINK_VISITED WHERE"{trunked} */
    public static final String LINK = "SELECT COUNT(*) FROM LINK_VISITED WHERE HREF = ?";
    
    /** Constant LINK="SELECT NAME FROM WEB_STORAGE WHERE"{trunked} */
    public static final String WEBSTORAGE_VALUE = "SELECT VALUE FROM WEB_STORAGE WHERE KEY = ? AND TABINDEX = ?";
    
    /** Constant LINK="SELECT NAME, VALUE FROM WEB_STORAGE"{trunked} */
    public static final String WEBSTORAGE_MAP = "SELECT KEY, VALUE FROM WEB_STORAGE AND TABINDEX = ?";
        
    /** Constant WEBSTORAGE_DELETE_KEY="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String WEBSTORAGE_DELETE_KEY = "DELETE FROM WEB_STORAGE WHERE KEY = ? AND SESSION = ? AND TABINDEX = ?";
    
    /** Constant DELETE_WEBSTORAGE="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String DELETE_WEBSTORAGE = "DELETE FROM WEB_STORAGE WHERE SESSION = ? AND TABINDEX = ?";
    
    /** Constant DELETE_WEBSTORAGE="DELETE FROM WEB_STORAGE WHER"{trunked} */
    public static final String DELETE_ALL_WEBSTORAGE = "DELETE FROM WEB_STORAGE WHERE SESSION = ? AND TABINDEX > -1";
    
    /** Constant WEBSTORAGE="INSERT INTO WEB_STORAGE (key, value, session) "{trunked} */
	public static final String WEBSTORAGE= "INSERT INTO WEB_STORAGE (key, value, session, tabIndex) VALUES(?,?,?,?)";
	
	 /** Constant WEBSTORAGE_SIZE="SELECT COUNT(*) FROM LINK_VISITED WHERE"{trunked} */
    public static final String WEBSTORAGE_SIZE = "SELECT COUNT(*) FROM WEB_STORAGE AND TABINDEX = ?";
    
	/** The Constant SETTINGS_DIR. */
	public static final String JDBC_SQLITE = "jdbc:sqlite:";

	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE.sqlite";

	/**
	 * <p>createDatabaseDirectory.</p>
	 */
	public static void createDatabaseDirectory() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		store.mkdirs();
	}

	/**
	 * <p>getCacheStore.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getCacheStore() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, ".lobo");
		final File store = new File(storeDir, "cache");
		return store.getPath();
	}

	/**
	 * <p>getDatabaseDirectory.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDatabaseDirectory() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		new File(JDBC_SQLITE + store + "\\" + LOBO_DB);
		return JDBC_SQLITE + store + "\\" + LOBO_DB;
	}

	/**
	 * <p>getDatabaseStore.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDatabaseStore() {
		final File homeDir = new File(System.getProperty("user.home"));
		final File storeDir = new File(homeDir, "lobo");
		final File store = new File(storeDir, "store");
		return store + "\\" + LOBO_DB;
	}
}
