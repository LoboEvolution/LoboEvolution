/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
package org.loboevolution.config;

import org.loboevolution.info.GeneralInfo;
import org.loboevolution.info.TabInfo;
import org.loboevolution.net.Cookie;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The interface HtmlRendererConfig.
 */
public interface HtmlRendererConfig {

    default float getFontSize() {
        return 16.0f;
    }

    /**
     * The Acryl .
     */

    default boolean isAcryl() {
        return false;
    }

    /**
     * The Aero .
     */

    default boolean isAero() {
        return true;
    }

    /**
     * The Aluminium .
     */

    default boolean isAluminium() {
        return false;
    }

    /**
     * The Bernstein .
     */

    default boolean isBernstein() {
        return false;
    }

    /**
     * The Bold .
     */

    default boolean isBold() {
        return false;
    }

    /**
     * The Fast .
     */

    default boolean isFast() {
        return false;
    }

    /**
     * The Graphite .
     */

    default boolean isGraphite() {
        return false;
    }

    /**
     * The HiFi .
     */

    default boolean isHiFi() {
        return false;
    }

    /**
     * The Italic .
     */

    default boolean isItalic() {
        return false;
    }

    /**
     * The modern .
     */

    default boolean isModern() {
        return false;
    }

    /**
     * The blackWhite .
     */

    default boolean isBlackWhite() {
        return false;
    }

    /**
     * The Italic .
     */

    default boolean isWhiteBlack() {
        return true;
    }

    /**
     * The Color .
     */
    default Color getColor() {
        return Color.BLACK;
    }

    /**
     * The Luna .
     */

    default boolean isLuna() {
        return false;
    }

    /**
     * The McWin .
     */

    default boolean isMcWin() {
        return false;
    }

    /**
     * The Mint .
     */

    default boolean isMint() {
        return false;
    }

    /**
     * The Noire .
     */

    default boolean isNoire() {
        return false;
    }

    /**
     * The Smart .
     */

    default boolean isSmart() {
        return false;
    }

    /**
     * The Strikethrough .
     */

    default boolean isStrikethrough() {
        return false;
    }

    /**
     * The Subscript .
     */

    default boolean isSubscript() {
        return false;
    }

    /**
     * The Superscript .
     */

    default boolean isSuperscript() {
        return false;
    }

    /**
     * The Texture .
     */

    default boolean isTexture() {
        return false;
    }

    /**
     * The Underline .
     */

    default boolean isUnderline() {
        return false;
    }

    /**
     * The Font .
     */
    default String getFont() {
        return "TimesNewRoman";
    }

    void deleteInput(String text, String baseUrl);
    void insertLogin(String type, String value, String baseUrl, boolean navigationEnabled);

    String getSourceCache(String baseUrl, String type, boolean test);

    List<String> getStyles(String href, String baseUrl);

    void insertStyle(String title, String href, String baseUrl, int enable);

    Rectangle getInitialWindowBounds();

    int countStorage(int index);

    Map<String, String> getMapStorage(int index, int i);

    Object getValue(String key, int i, int index);

    void deleteStorage(String keyName, int i, int index);

    void deleteStorage(int session, int index);

    void insertStorage(String keyName, String keyValue, int i, int index);

    List<TabInfo> getTabs();

    List<String> autocomplete(String type, String text, String baseUrl);

    URL getResourceFile(String fileName);

    boolean isVisited(String href);

    void saveCookie(String toString, String cookieSpec);

    List<Cookie> getCookies(String host, String path);

    GeneralInfo getGeneralInfo();
}
