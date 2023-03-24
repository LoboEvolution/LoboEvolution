package org.loboevolution.config;

import lombok.Data;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.info.TabInfo;
import org.loboevolution.net.Cookie;
import org.loboevolution.store.*;

import java.awt.*;
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

        try (Connection conn = DriverManager.getConnection(DB_PATH);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
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
        } catch (final Exception e) {}
    }

    @Override
    public void deleteInput(String text, String baseUrl) {
        InputStore.deleteInput(text, baseUrl);
    }

    @Override
    public void insertLogin(String type, String value, String baseUrl, boolean navigationEnabled) {
        InputStore.insertLogin(type, value, baseUrl, navigationEnabled);
    }

    @Override
    public String getSourceCache(String baseUrl, String type, boolean test) {
        return ExternalResourcesStore.getSourceCache(baseUrl, type, test);
    }

    @Override
    public List<String> getStyles(String href, String baseUrl) {
        StyleStore styleStore = new StyleStore();
        return styleStore.getStyles(href, baseUrl);
    }

    public void insertStyle(String title, String href, String baseUrl, int enable) {
        StyleStore styleStore = new StyleStore();
        styleStore.insertStyle(title, href, baseUrl, enable);
    }

    @Override
    public Rectangle getInitialWindowBounds() {
        return GeneralStore.getInitialWindowBounds();
    }

    @Override
    public int countStorage(int index) {
        return WebStore.countStorage(index);
    }

    @Override
    public Map<String, String> getMapStorage(int index, int i) {
        return WebStore.getMapStorage(index, i);
    }

    @Override
    public Object getValue(String key, int i, int index) {
        return WebStore.getValue(key, i, index);
    }

    @Override
    public void deleteStorage(String keyName, int i, int index) {
        WebStore.deleteStorage(keyName, i, index);
    }

    @Override
    public void deleteStorage(int session, int index) {
        WebStore.deleteStorage(session, index);
    }

    @Override
    public void insertStorage(String keyName, String keyValue, int i, int index) {
        WebStore.insertStorage(keyName, keyValue, i, index);
    }

    @Override
    public List<TabInfo> getTabs() {
        return TabStore.getTabs();
    }

    @Override
    public List<String> autocomplete(String type, String text, String baseUrl) {
        return InputStore.autocomplete(type, text, baseUrl);
    }

    @Override
    public URL getResourceFile(String fileName) {
        return DesktopConfig.getResourceFile(fileName);
    }

    @Override
    public boolean isVisited(String href) {
        return LinkStore.isVisited(href);
    }

    @Override
    public void saveCookie(String toString, String cookieSpec) {
        CookieStore.saveCookie(toString, cookieSpec);
    }

    @Override
    public List<Cookie> getCookies(String host, String path) {
        return CookieStore.getCookies(host,path);
    }

    @Override
    public GeneralInfo getGeneralInfo() {
        return GeneralStore.getGeneralInfo();
    }
}