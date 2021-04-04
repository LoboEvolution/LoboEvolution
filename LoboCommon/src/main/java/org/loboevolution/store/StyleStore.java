/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

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

/**
 * <p>StyleStore class.</p>
 *
 *
 *
 */
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
     *
     * @param href a {@link java.lang.String} object.
     * @param baseUrl a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
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
     *
     * @param title a {@link java.lang.String} object.
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
     *
     * @param baseUrl a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
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
     * @param title a {@link java.lang.String} object.
     * @param href a {@link java.lang.String} object.
     * @param baseUrl a {@link java.lang.String} object.
     * @param enable a int.
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
