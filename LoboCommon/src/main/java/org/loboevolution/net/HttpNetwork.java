package org.loboevolution.net;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.store.SQLiteCommon;

/**
 * <p>HttpNetwork class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HttpNetwork {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(HttpNetwork.class.getName());

	/** Constant GZIP_ENCODING="gzip" */
	public static final String GZIP_ENCODING = "gzip";

	private static final String USER_AGENT = "SELECT DISTINCT description FROM USER_AGENT";

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

	/**
	 * <p>getInputStream.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.io.IOException if any.
	 */
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

	/**
	 * <p>getImage.</p>
	 *
	 * @param href a {@link java.lang.String} object.
	 * @param baseUri a {@link java.lang.String} object.
	 * @return a {@link java.awt.Image} object.
	 */
	public static Image getImage(String href, String baseUri) {
		try {
			if (Strings.isBlank(href))
				return null;

			if (href.contains(";base64,")) {
				final String base64 = href.split(";base64,")[1];
				byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
				try (InputStream stream = new ByteArrayInputStream(decodedBytes)) {
					return ImageIO.read(stream);
				}
			} else {

				String scriptURI = href;
				if (Strings.isNotBlank(baseUri)) {
					final URL baseURL = new URL(baseUri);
					final URL scriptURL = Urls.createURL(baseURL, href);
					scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
				}

				final URL u = new URL(scriptURI);
				final URLConnection connection = u.openConnection();
				connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
				try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
						Reader reader = new InputStreamReader(in, "utf-8")) {

					if (href.contains(";base64,")) {
						final String base64 = href.split(";base64,")[1];
						byte[] decodedBytes = Base64.getDecoder().decode(base64);
						InputStream stream = new ByteArrayInputStream(decodedBytes);
						return ImageIO.read(stream);
					} else if (href.endsWith(".svg")) {
						return null; //TODO SVG From URL
					} else if (href.startsWith("https")) {
						if (in != null) {
							BufferedImage bi = ImageIO.read(in);
							if (bi != null) {
								return Toolkit.getDefaultToolkit().createImage(bi.getSource());
							}
						}
						return null;
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
							logger.log(Level.SEVERE, e.getMessage(), e);
						}
					} else {
						return ImageIO.read(in);
					}
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>getSource.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public static String getSource(String uri) throws Exception {

		final URL url = new URL(uri);
		final URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", getUserAgentValue());
		try (InputStream in = openConnectionCheckRedirects(connection)) {
			return toString(in);
		}

	}

	/**
	 * <p>getUserAgentValue.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
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
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return userAgent;
	}

	/**
	 * <p>openConnectionCheckRedirects.</p>
	 *
	 * @param c a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.lang.Exception if any.
	 */
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

	/**
	 * <p>toString.</p>
	 *
	 * @param inputStream a {@link java.io.InputStream} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
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
