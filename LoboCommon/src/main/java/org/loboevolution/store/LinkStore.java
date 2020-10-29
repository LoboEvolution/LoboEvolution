package org.loboevolution.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>LinkStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class LinkStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LinkStore.class.getName());
	
	/**
	 * <p>isVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isVisited(String link) {
        boolean vis = false;
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
			PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.LINK)) {
    		pstmt.setString(1, link);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					vis = rs.getInt(1) > 0;
				}
			}
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return vis;
    }	

	/**
	 * <p>insertLinkVisited.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 */
	public static void insertLinkVisited(String link) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_LINK)) {
			pstmt.setString(1, link);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <p>deleteLinks.</p>
	 */
	public static void deleteLinks() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_LINK)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
