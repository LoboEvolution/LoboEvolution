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

package org.loboevolution.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.info.TabInfo;
import org.loboevolution.net.Cookie;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.*;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * The class HtmlRendererConfigImpl.
 */
@Data
@Slf4j
public class HtmlRendererConfigImpl implements HtmlRendererConfig {

    private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

    private boolean acryl;
    private boolean aero;
    private boolean aluminium;
    private boolean bernstein;
    private boolean bold;
    private boolean fast;
    private boolean graphite;
    private boolean hiFi;
    private boolean italic;
    private boolean modern;
    private boolean blackWhite;
    private boolean whiteBlack;
    private Color color = Color.BLACK;
    private boolean luna;
    private boolean mcWin;
    private boolean mint;
    private boolean noire;
    private boolean smart;
    private boolean strikethrough;
    private boolean subscript;
    private boolean superscript;
    private boolean texture;
    private boolean underline;
    private String font;

    private float fontSize = 16.0f;

    public HtmlRendererConfigImpl() {

        try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(
                     " SELECT DISTINCT acryl, aero, aluminium, bernstein, fast, graphite," +
                             " 	    		 hiFi,luna, mcWin, mint, noire, smart, texture," +
                             "	 			 subscript, superscript, underline, italic, strikethrough," +
                             "				 fontSize, font, color, bold, modern, black, white" +
                             " FROM LOOK_AND_FEEL")) {
            while (rs != null && rs.next()) {
                setAcryl(rs.getInt(1) == 1);
                setAero(rs.getInt(2) == 1);
                setAluminium(rs.getInt(3) == 1);
                setBernstein(rs.getInt(4) == 1);
                setFast(rs.getInt(5) == 1);
                setGraphite(rs.getInt(6) == 1);
                setHiFi(rs.getInt(7) == 1);
                setLuna(rs.getInt(8) == 1);
                setMcWin(rs.getInt(9) == 1);
                setMint(rs.getInt(10) == 1);
                setNoire(rs.getInt(11) == 1);
                setSmart(rs.getInt(12) == 1);
                setTexture(rs.getInt(13) == 1);
                setSubscript(rs.getInt(14) == 1);
                setSuperscript(rs.getInt(15) == 1);
                setUnderline(rs.getInt(16) == 1);
                setItalic(rs.getInt(17) == 1);
                setStrikethrough(rs.getInt(18) == 1);
                setFontSize(Float.parseFloat(rs.getString(19)));
                setFont(rs.getString(20));
                setColor(Color.BLACK);
                setBold(rs.getInt(22) == 1);
                setModern(rs.getInt(23) == 1);
                setBlackWhite(rs.getInt(24) == 1);
                setWhiteBlack(rs.getInt(25) == 1);
            }
        } catch (final Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void deleteInput(final String text, final String baseUrl) {
        InputStore.deleteInput(text, baseUrl);
    }

    @Override
    public void insertLogin(final String type, final String value, final String baseUrl, final boolean navigationEnabled) {
        InputStore.insertLogin(type, value, baseUrl, navigationEnabled);
    }

    @Override
    public String getSourceCache(final URI baseUri, final String type, final String integrity, final boolean test) {
        try {
            final ExternalResourcesStore resourcesStore = new ExternalResourcesStore();
            String baseUrl = baseUri.toString();
            String source = resourcesStore.getSourceCache(baseUrl, type, test);
            if (Strings.isBlank(source)) {
                source = HttpNetwork.sourceResponse(baseUri, integrity);
                if (!test) {
                    resourcesStore.saveCache(baseUrl, source, type);
                }
                return source;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<String> getStyles(final String href, final String baseUrl) {
        final StyleStore styleStore = new StyleStore();
        return styleStore.getStyles(href, baseUrl);
    }

    public void insertStyle(final String title, final String href, final String baseUrl, final int enable) {
        final StyleStore styleStore = new StyleStore();
        styleStore.insertStyle(title, href, baseUrl, enable);
    }

    @Override
    public Rectangle getInitialWindowBounds() {
        return GeneralStore.getInitialWindowBounds();
    }

    @Override
    public int countStorage(final int index) {
        return WebStore.countStorage(index);
    }

    @Override
    public Map<String, String> getMapStorage(final int index, final int i) {
        return WebStore.getMapStorage(index, i);
    }

    @Override
    public Object getValue(final String key, final int i, final int index) {
        return WebStore.getValue(key, i, index);
    }

    @Override
    public void deleteStorage(final String keyName, final int i, final int index) {
        WebStore.deleteStorage(keyName, i, index);
    }

    @Override
    public void deleteStorage(final int session, final int index) {
        WebStore.deleteStorage(session, index);
    }

    @Override
    public void insertStorage(final String keyName, final String keyValue, final int i, final int index) {
        WebStore.insertStorage(keyName, keyValue, i, index);
    }

    @Override
    public List<TabInfo> getTabs() {
        return TabStore.getTabs();
    }

    @Override
    public List<String> autocomplete(final String type, final String text, final String baseUrl) {
        return InputStore.autocomplete(type, text, baseUrl);
    }

    @Override
    public URL getResourceFile(final String fileName) {
        return DesktopConfig.getResourceFile(fileName,DesktopConfig.PATH_IMAGE);
    }

    @Override
    public boolean isVisited(final String href) {
        return LinkStore.isVisited(href);
    }

    @Override
    public void saveCookie(final String toString, final String cookieSpec) {
        CookieStore.saveCookie(toString, cookieSpec);
    }

    @Override
    public List<Cookie> getCookies(final String host, final String path) {
        return CookieStore.getCookies(host,path);
    }

    @Override
    public GeneralInfo getGeneralInfo() {
        return GeneralStore.getGeneralInfo();
    }
}