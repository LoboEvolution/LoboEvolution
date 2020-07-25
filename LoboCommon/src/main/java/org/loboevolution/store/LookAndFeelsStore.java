package org.loboevolution.store;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.laf.LAFSettings;

/**
 * <p>LookAndFeelsStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class LookAndFeelsStore {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LookAndFeelsStore.class.getName());

	private final String DELETE_LAF = "DELETE FROM LOOK_AND_FEEL";

	private final String INSERT_LAF = " INSERT INTO LOOK_AND_FEEL (acryl, aero, aluminium, bernstein, fast, graphite,"
			+ "hiFi,luna, mcWin, mint, noire, smart, texture, subscript, superscript, underline, italic, "
			+ "strikethrough, fontSize, font, color, bold, modern, black, white) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final String INSERT_SEARCH2 = "INSERT INTO SEARCH (name, description, type, selected) VALUES(?,?,?,?)";

	/**
	 * Connect to the test.db database
	 *
	 * @return the Connection object
	 */
	private Connection connect(String dbPath) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbPath);
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return conn;
	}

	/**
	 * <p>deleteLAF.</p>
	 */
	public void deleteLAF() {
		try (Connection conn = connect(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.DELETE_LAF)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Insert a new row into the search selected table
	 *
	 * @param search a {@link org.loboevolution.store.SearchEngineStore} object.
	 */
	public void insertFileSelected(SearchEngineStore search) {
		try (Connection conn = connect(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_SEARCH2)) {
			pstmt.setString(1, search.getName());
			pstmt.setString(2, search.getDescription());
			pstmt.setString(3, search.getType());
			pstmt.setInt(4, search.isSelected() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Insert a new row into the look_and_feel table
	 *
	 * @param laf a {@link org.loboevolution.laf.LAFSettings} object.
	 */
	public void insertLAF(LAFSettings laf) {
		try (Connection conn = connect(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(this.INSERT_LAF)) {

			pstmt.setInt(1, laf.isAcryl() ? 1 : 0);
			pstmt.setInt(2, laf.isAero() ? 1 : 0);
			pstmt.setInt(3, laf.isAluminium() ? 1 : 0);
			pstmt.setInt(4, laf.isBernstein() ? 1 : 0);
			pstmt.setInt(5, laf.isFast() ? 1 : 0);
			pstmt.setInt(6, laf.isGraphite() ? 1 : 0);
			pstmt.setInt(7, laf.isHiFi() ? 1 : 0);
			pstmt.setInt(8, laf.isLuna() ? 1 : 0);
			pstmt.setInt(9, laf.isMcWin() ? 1 : 0);
			pstmt.setInt(10, laf.isMint() ? 1 : 0);
			pstmt.setInt(11, laf.isNoire() ? 1 : 0);
			pstmt.setInt(12, laf.isSmart() ? 1 : 0);
			pstmt.setInt(13, laf.isTexture() ? 1 : 0);
			pstmt.setInt(14, laf.isSubscript() ? 1 : 0);
			pstmt.setInt(15, laf.isSuperscript() ? 1 : 0);
			pstmt.setInt(16, laf.isUnderline() ? 1 : 0);
			pstmt.setInt(17, laf.isItalic() ? 1 : 0);
			pstmt.setInt(18, laf.isStrikethrough() ? 1 : 0);
			pstmt.setFloat(19, laf.getFontSize());
			pstmt.setString(20, laf.getFont());

			final Color c = laf.getColor();
			pstmt.setString(21, "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")");
			pstmt.setInt(22, laf.isBold() ? 1 : 0);
			pstmt.setInt(23, laf.isModern() ? 1 : 0);
			pstmt.setInt(24, laf.isBlackWhite() ? 1 : 0);
			pstmt.setInt(25, laf.isWhiteBlack() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
