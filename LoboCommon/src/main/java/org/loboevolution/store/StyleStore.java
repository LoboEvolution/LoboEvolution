package org.loboevolution.store;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StyleStore implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(StyleStore.class.getName());

    private final String DELETE_STYLE = "DELETE FROM STYLE";

    private final String DELETE_STYLE_HREF = "DELETE FROM STYLE WHERE href = ? AND baseUrl = ?";

    private final String INSERT_STYLE = "INSERT OR REPLACE INTO STYLE(title, href, baseUrl, enable) values (?, ?, ?, ?)";

    private final String STYLE = "SELECT title FROM STYLE WHERE href = ? AND baseUrl = ? AND enable = 1";

    private final String STYLE_ALL = "SELECT title FROM STYLE WHERE baseUrl = ?";

    private final String UPDATE_STYLE_ALL = "UPDATE STYLE SET enable = 0";

    private final String UPDATE_STYLE = "UPDATE STYLE SET enable = 1 WHERE title = ?";

    /**
     * Gets the styles
     */
    public List<String> getStyles(String href, String baseUrl) {
        final List<String> values = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
             PreparedStatement pstmt = conn.prepareStatement(this.STYLE)) {
            pstmt.setString(1, href);
            pstmt.setString(1, baseUrl);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs != null && rs.next()) {
                    values.add(rs.getString(1));
                }
            }
        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return values;
    }

    /**
     * <p>selectStyle.</p>
     */
    public void selectStyle(String title) {
        try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_STYLE_ALL)) {
            pstmt.executeUpdate();

            try (PreparedStatement pstmt2 = conn.prepareStatement(UPDATE_STYLE)) {
                pstmt2.setString(1, title);
                pstmt2.executeUpdate();

            } catch (final Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Gets the styles all
     */
    public List<String> getStylesAll(String baseUrl) {
        final List<String> values = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
             PreparedStatement pstmt = conn.prepareStatement(this.STYLE_ALL)) {
            pstmt.setString(1, baseUrl);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs != null && rs.next()) {
                    values.add(rs.getString(1));
                }
            }
        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return values;
    }

    /**
     * <p>insertStyle.</p>
     *
     * @param title
     */
    public void insertStyle(String title, String href, String baseUrl, int enable) {

        try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
             PreparedStatement pstmt = conn.prepareStatement(DELETE_STYLE_HREF)) {
            pstmt.setString(1, href);
            pstmt.setString(2, baseUrl);
            pstmt.executeUpdate();

            try (PreparedStatement pstmt2 = conn.prepareStatement(INSERT_STYLE)) {
                pstmt2.setString(1, title);
                pstmt2.setString(2, href);
                pstmt2.setString(3, baseUrl);
                pstmt2.setInt(4, enable);
                pstmt2.executeUpdate();

            } catch (final Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }

        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * <p>deleteStyle.</p>
     */
    public void deleteStyle() {
        try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
             PreparedStatement pstmt = conn.prepareStatement(DELETE_STYLE)) {
            pstmt.executeUpdate();
        } catch (final Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}