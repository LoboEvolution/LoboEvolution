package org.loboevolution.net;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.loboevolution.store.SQLiteCommon;

public class HttpNetwork {

	public static String GZIP_ENCODING = "gzip";

	private static String USER_AGENT = "SELECT DISTINCT description FROM USER_AGENT";

	private static InputStream getGzipStream(URLConnection con) throws IOException {
		final InputStream cis = con.getInputStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getInputStream());
			} else {
				return con.getInputStream();
			}
		} else {
			return null;
		}
	}

	private static InputStream getGzipStreamError(HttpURLConnection con) throws IOException {
		final InputStream cis = con.getErrorStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getErrorStream());
			} else {
				return con.getErrorStream();
			}
		} else {
			return null;
		}
	}

	public static InputStream getInputStream(URLConnection connection) throws IOException {
		InputStream in;
		if (connection instanceof HttpURLConnection) {
			in = getGzipStreamError((HttpURLConnection) connection);
			if (in == null) {
				in = getGzipStream(connection);
			}
		} else {
			in = connection.getInputStream();
		}
		return in;
	}

	public static Image getImage(String href) {
		try {
			final URL u = new URL(href);
			final URLConnection connection = u.openConnection();
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
					Reader reader = new InputStreamReader(in, "utf-8");) {

				if (href.startsWith("https")) {
					return Toolkit.getDefaultToolkit().createImage(ImageIO.read(in).getSource());
				} else if (href.endsWith(".gif")) {
					try {
						return new ImageIcon(u).getImage();
					} catch (final Exception e) {
						return ImageIO.read(in);
					}
				} else if (href.endsWith(".bmp")) {
					try {
						return ImageIO.read(in);
					} catch (final IOException e) {
						e.printStackTrace();
					}
				} else {
					return ImageIO.read(in);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getSource(String uri) {
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", getUserAgentValue());
			try (InputStream in = openConnectionCheckRedirects(connection)) {
				return toString(in);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getUserAgentValue() {
		String userAgent = "";
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(USER_AGENT)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					userAgent = rs.getString(1);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return userAgent;
	}

	public static InputStream openConnectionCheckRedirects(URLConnection c) throws Exception {
		boolean redir;
		int redirects = 0;
		InputStream in = null;
		do {
			if (c instanceof HttpURLConnection) {
				((HttpURLConnection) c).setInstanceFollowRedirects(false);
			}
			in = getInputStream(c);
			redir = false;
			if (c instanceof HttpURLConnection) {
				final HttpURLConnection http = (HttpURLConnection) c;
				final int stat = http.getResponseCode();
				if (stat >= 300 && stat <= 307 && stat != 306 && stat != HttpURLConnection.HTTP_NOT_MODIFIED) {
					final URL base = http.getURL();
					final String loc = http.getHeaderField("Location");
					URL target = null;
					if (loc != null) {
						target = new URL(base, loc);
					}
					http.disconnect();
					if (target == null || !(target.getProtocol().equals("http") || target.getProtocol().equals("https"))
							|| redirects >= 5) {
						throw new SecurityException("illegal URL redirect");
					}
					redir = true;
					c = target.openConnection();
					redirects++;
				}
			}
		} while (redir);
		return in;
	}

	public static String toString(InputStream inputStream) throws IOException {
		final String newLine = System.getProperty("line.separator");
		final StringBuilder result = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			boolean flag = false;
			for (String line; (line = reader.readLine()) != null;) {
				result.append(flag ? newLine : "").append(line);
				flag = true;
			}
		}
		return result.toString();
	}

}
