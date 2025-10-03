/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.store.laf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.store.DatabseSQLite;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class LAFSettings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class LAFSettings implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The Constant DB_PATH.
     */
    private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

    /**
     * The Acryl .
     */
    @Builder.Default
    private boolean acryl = false;

    /**
     * The Aero .
     */
    @Builder.Default
    private boolean aero = true;

    /**
     * The Aluminium .
     */
    @Builder.Default
    private boolean aluminium = false;

    /**
     * The Bernstein .
     */
    @Builder.Default
    private boolean bernstein = false;

    /**
     * The Bold .
     */
    @Builder.Default
    private boolean bold = false;

    /**
     * The Fast .
     */
    @Builder.Default
    private boolean fast = false;

    /**
     * The Graphite .
     */
    @Builder.Default
    private boolean graphite = false;

    /**
     * The HiFi .
     */
    @Builder.Default
    private boolean hiFi = false;

    /**
     * The Italic .
     */
    @Builder.Default
    private boolean italic = false;

    /**
     * The modern .
     */
    @Builder.Default
    private boolean modern = false;

    /**
     * The blackWhite .
     */
    @Builder.Default
    private boolean blackWhite = false;

    /**
     * The Italic .
     */
    @Builder.Default
    private boolean whiteBlack = true;

    /**
     * The Color .
     */
    @Builder.Default
    private Color color = Color.BLACK;

    /**
     * The Luna .
     */
    @Builder.Default
    private boolean luna = false;

    /**
     * The McWin .
     */
    @Builder.Default
    private boolean mcWin = false;

    /**
     * The Mint .
     */
    @Builder.Default
    private boolean mint = false;

    /**
     * The Noire .
     */
    @Builder.Default
    private boolean noire = false;

    /**
     * The Smart .
     */
    @Builder.Default
    private boolean smart = false;

    /**
     * The Strikethrough .
     */
    @Builder.Default
    private boolean strikethrough = false;

    /**
     * The Subscript .
     */
    @Builder.Default
    private boolean subscript = false;

    /**
     * The Superscript .
     */
    @Builder.Default
    private boolean superscript = false;

    /**
     * The Texture .
     */
    @Builder.Default
    private boolean texture = false;

    /**
     * The Underline .
     */
    @Builder.Default
    private boolean underline = false;

    /**
     * The Font .
     */
    @Builder.Default
    private String font = "TimesNewRoman";

    /**
     * The Font Size .
     */
    @Builder.Default
    private float fontSize = 16.0f;

    private static final String LOOK_AND_FEEL =
            " SELECT DISTINCT acryl, aero, aluminium, bernstein, fast, graphite," +
                    " 	    		 hiFi,luna, mcWin, mint, noire, smart, texture," +
                    "	 			 subscript, superscript, underline, italic, strikethrough," +
                    "				 fontSize, font, color, bold, modern, black, white" +
                    " FROM LOOK_AND_FEEL";

    /**
     * <p>getFonts.</p>
     *
     * @param type a {@link java.lang.String} object.
     * @return an array of {@link java.lang.String} objects.
     */
    public static String[] getFonts(final String type) {
        final List<String> fonts = new ArrayList<>();
        final String query = "SELECT name FROM " + type;
        try (final Connection conn = DriverManager.getConnection(DatabseSQLite.getDatabaseDirectory());
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(query)) {
            while (rs != null && rs.next()) {
                fonts.add(rs.getString(1));
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return fonts.toArray(new String[0]);
    }

    /**
     * <p>getInstance.</p>
     *
     * @return the instance
     */
    public LAFSettings getInstance() {
        LAFSettings laf = null;
        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(this.LOOK_AND_FEEL)) {
            while (rs != null && rs.next()) {
                laf = LAFSettings.builder().
                        acryl(rs.getInt(1) == 1).
                        aero(rs.getInt(2) == 1).
                        aluminium(rs.getInt(3) == 1).
                        bernstein(rs.getInt(4) == 1).
                        fast(rs.getInt(5) == 1).
                        graphite(rs.getInt(6) == 1).
                        hiFi(rs.getInt(7) == 1).
                        luna(rs.getInt(8) == 1).
                        mcWin(rs.getInt(9) == 1).
                        mint(rs.getInt(10) == 1).
                        noire(rs.getInt(11) == 1).
                        smart(rs.getInt(12) == 1).
                        texture(rs.getInt(13) == 1).
                        subscript(rs.getInt(14) == 1).
                        superscript(rs.getInt(15) == 1).
                        underline(rs.getInt(16) == 1).
                        italic(rs.getInt(17) == 1).
                        strikethrough(rs.getInt(18) == 1).
                        fontSize(Float.parseFloat(rs.getString(19))).
                        font(rs.getString(20)).
                        color(Color.BLACK).
                        bold(rs.getInt(22) == 1).
                        modern(rs.getInt(23) == 1).
                        blackWhite(rs.getInt(24) == 1).
                        whiteBlack(rs.getInt(25) == 1).build();
            }
        } catch (final SQLException e) {
            return this;
        }
        return laf;
    }
}
