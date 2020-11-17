package org.loboevolution.store;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>DownloadStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DownloadStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DownloadStore.class.getName());

	private final String DELETE_DOWNLOAD = "DELETE FROM DOWNLOAD";

	private final String DOWNLOAD_ORDERED = "SELECT baseUrl FROM DOWNLOAD ORDER BY dt ASC";

	private final String INSERT_DOWNLOAD = "INSERT INTO DOWNLOAD (baseUrl, dt) VALUES(?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

	/**
	 * <p>addAsRecent.</p>
	 *
	 * @param uri a {@link String} object.
	 */
	public void addAsRecent(String uri) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_DOWNLOAD)) {
			pstmt.setString(1, new URL(uri).toExternalForm());
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>deleteDownload.</p>
	 */
	public void deleteDownload() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_DOWNLOAD)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * <p>getDownload.</p>
	 * @return a {@link List} object.
	 */
	public List<String> getDownload() {
		final List<String> recent = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DOWNLOAD_ORDERED)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					recent.add(rs.getString(1));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return recent;
	}
}
