package org.loboevolution.store;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>ExternalResourcesStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ExternalResourcesStore {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ExternalResourcesStore.class.getName());

	/** The date pattern. */
	private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

	/**
	 * <p>getSourceCache.</p>
	 *
	 * @param baseUrl a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getSourceCache(String baseUrl, String type) {
		String source = "";
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					source = rs.getString(1);
				}
			}

			if (Strings.isBlank(source)) {
				deleteCache(baseUrl, type);
				source = sourceResponse(baseUrl, type);
			}

		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
		return source;
	}

	private static void saveCache(String baseUrl, String source, String type) throws Exception {
		final URL url = new URL(baseUrl);
		final URLConnection con = url.openConnection();
		String eTag = con.getHeaderField("Etag");
		long lastModified = Urls.getExpiration(con, System.currentTimeMillis());
		int contentLenght = Integer.parseInt(con.getHeaderField("Content-Length") == null ? "0" : con.getHeaderField("Content-Length"));
		String cacheControl = con.getHeaderField("Cache-Control");
		boolean isNoStore = false;
		if (cacheControl != null) {
			StringTokenizer tok = new StringTokenizer(cacheControl, ",");
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken().trim().toLowerCase();
				if ("no-store".equals(token)) {
					isNoStore = true;
				}
			}
		}

		int check = checkCache(baseUrl, contentLenght, eTag, type);
		if (!isNoStore && check == 0) {
			insertCache(baseUrl, source, contentLenght, eTag, new Date(lastModified), type);
		}
	}

	private static String sourceResponse(String scriptURI, String type) {
		String response = "";
		URL url = null;
		try {
			switch (type) {
			case "CSS":
				try {
					if (scriptURI.startsWith("//")) {
						scriptURI = "http:" + scriptURI;
					}
					url = new URL(scriptURI);
				} catch (MalformedURLException mfu) {
					int idx = scriptURI.indexOf(':');
					if (idx == -1 || idx == 1) {
						url = new URL("file:" + scriptURI);
					} else {
						throw mfu;
					}
				}
				break;
			default:
				url = new URL(scriptURI);
				URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
				url = uri.toURL();
				break;
			}

			response = HttpNetwork.getSource(url.toString());
			saveCache(scriptURI, response, type);
			return response;
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
			return "";
		}
	}

	private static int checkCache(String baseUrl, int contentLenght, String eTag, String type) {
		int check = 0;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CHECK_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setInt(2, contentLenght);
			pstmt.setString(3, eTag);
			pstmt.setString(3, type);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					check = rs.getInt(1);
				}
			}
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
		return check;
	}

	private static void insertCache(String baseUrl, String source, int contentLenght, String eTag, Date lastModified,
			String type) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_CACHE)) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, source);
			pstmt.setInt(3, contentLenght);
			pstmt.setString(4, eTag);
			pstmt.setString(5, lastModified != null ? dateFormatter.format(lastModified) : null);
			pstmt.setString(6, type);
			pstmt.executeUpdate();
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}

	private static void deleteCache(String baseUrl, String type) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			pstmt.executeUpdate();
		} catch (Exception err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
}
