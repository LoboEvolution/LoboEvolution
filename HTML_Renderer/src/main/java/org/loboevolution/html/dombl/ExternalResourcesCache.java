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
package org.loboevolution.html.dombl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.http.SSLCertificate;
import org.loboevolution.http.Urls;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Strings;
import org.loboevolution.util.io.IORoutines;

import com.loboevolution.store.SQLiteCommon;

public class ExternalResourcesCache {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ExternalResourcesCache.class);
	
	/** The date pattern. */
	private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
	
	public static String getSourceCache(String baseUrl, String type) {
		String source = "";
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				source = rs.getString(1);
			}
			
			if (Strings.isBlank(source)) {
				deleteCache(baseUrl, type);
				source = httpURLConnection(baseUrl, type);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return source;
	}
	
	private static void saveCache(String baseUrl, String source, String type, HttpURLConnection con) {
		String eTag = con.getHeaderField("Etag");
		long lastModified = Urls.getExpiration(con, System.currentTimeMillis());
		int contentLenght = Integer.parseInt(con.getHeaderField("Content-Length"));
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
	
	private static String httpURLConnection(String scriptURI, String type) {

		StringBuilder response = new StringBuilder();
		int responseCode = -1;
		try {
			
			URL url = null;
					
			if("CSS".equals(type)) {
				try {
					if (scriptURI.startsWith("//")) {
						scriptURI = "http:" + scriptURI;
					}
					url = new URL(scriptURI);
				} catch (MalformedURLException mfu) {
					int idx = scriptURI.indexOf(':');
					if (idx == -1 || idx == 1) {
						// try file
						url = new URL("file:" + scriptURI);
					} else {
						throw mfu;
					}
				}
			} else {
				url = new URL(scriptURI);
				URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
				url = uri.toURL();
			}
			
			
			SSLCertificate.setCertificate();
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			con.setRequestProperty("Accept-Encoding", UserAgentContext.GZIP_ENCODING);
			con.setRequestMethod("GET");
			responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(IORoutines.getInputStream(con)));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			saveCache(scriptURI, response.toString(), type, con);

		} catch (Exception e) {
			logger.warn("Unable to parse script. URI=[" + scriptURI + "]. Response status was " + responseCode + ".");
			return "";
		}
		return response.toString();
	}

	private static int checkCache(String baseUrl, int contentLenght, String eTag, String type) {
		int check = 0;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CHECK_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setInt(2, contentLenght);
			pstmt.setString(3, eTag);
			pstmt.setString(3, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs != null && rs.next()) {
				check = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return check;
	}
	
	private static void insertCache(String baseUrl, String source, int contentLenght, String eTag, Date lastModified, String type) {
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
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private static void deleteCache(String baseUrl, String type) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_SOURCE_CACHE)) {
			pstmt.setString(1, baseUrl);
			pstmt.setString(2, type);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
