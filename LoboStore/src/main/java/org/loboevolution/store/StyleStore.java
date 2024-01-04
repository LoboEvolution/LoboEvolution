/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.store;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>StyleStore class.</p>
 */
@Slf4j
public class StyleStore implements QueryStore, Serializable {

    private static final long serialVersionUID = 1L;

    /** The Constant DB_PATH. */
    private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

    /**
     * Gets the styles
     *
     * @param href a {@link java.lang.String} object.
     * @param baseUrl a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<String> getStyles(final String href, final String baseUrl) {
        final List<String> values = new ArrayList<>();
        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.STYLE)) {
            pstmt.setString(1, href);
            pstmt.setString(1, baseUrl);
            try (final ResultSet rs = pstmt.executeQuery()) {
                while (rs != null && rs.next()) {
                    values.add(rs.getString(1));
                }
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return values;
    }

    /**
     * <p>selectStyle.</p>
     *
     * @param title a {@link java.lang.String} object.
     */
    public void selectStyle(final String title) {
        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(UPDATE_STYLE_ALL)) {
            pstmt.executeUpdate();

            try (final PreparedStatement pstmt2 = conn.prepareStatement(UPDATE_STYLE)) {
                pstmt2.setString(1, title);
                pstmt2.executeUpdate();

            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Gets the styles all
     *
     * @param baseUrl a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<String> getStylesAll(final String baseUrl) {
        final List<String> values = new ArrayList<>();
        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(this.STYLE_ALL)) {
            pstmt.setString(1, baseUrl);
            try (final ResultSet rs = pstmt.executeQuery()) {
                while (rs != null && rs.next()) {
                    values.add(rs.getString(1));
                }
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return values;
    }

    /**
     * <p>insertStyle.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @param href a {@link java.lang.String} object.
     * @param baseUrl a {@link java.lang.String} object.
     * @param enable a {@link java.lang.Integer} object.
     */
    public void insertStyle(final String title, final String href, final String baseUrl, final int enable) {

        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_STYLE_HREF)) {
            pstmt.setString(1, href);
            pstmt.setString(2, baseUrl);
            pstmt.executeUpdate();

            try (final PreparedStatement pstmt2 = conn.prepareStatement(INSERT_STYLE)) {
                pstmt2.setString(1, title);
                pstmt2.setString(2, href);
                pstmt2.setString(3, baseUrl);
                pstmt2.setInt(4, enable);
                pstmt2.executeUpdate();

            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }

        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * <p>deleteStyle.</p>
     */
    public void deleteStyle() {
        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_STYLE)) {
            pstmt.executeUpdate();
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
