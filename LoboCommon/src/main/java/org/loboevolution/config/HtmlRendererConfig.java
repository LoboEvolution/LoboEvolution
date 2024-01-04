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

    void deleteInput(final String text, final String baseUrl);
    void insertLogin(final String type, final String value, final String baseUrl, boolean navigationEnabled);

    String getSourceCache(final String baseUrl, final String type, String integrity, boolean test);

    List<String> getStyles(final String href, final String baseUrl);

    void insertStyle(final String title, final String href, final String baseUrl, final int enable);

    Rectangle getInitialWindowBounds();

    int countStorage(int index);

    Map<String, String> getMapStorage(int index, final int i);

    Object getValue(final String key, final int i, final int index);

    void deleteStorage(final String keyName, final int i, final int index);

    void deleteStorage(int session, final int index);

    void insertStorage(final String keyName, final String keyValue, final int i, final int index);

    List<TabInfo> getTabs();

    List<String> autocomplete(final String type, final String text, final String baseUrl);

    URL getResourceFile(final String fileName);

    boolean isVisited(final String href);

    void saveCookie(final String toString, final String cookieSpec);

    List<Cookie> getCookies(final String host, final String path);

    GeneralInfo getGeneralInfo();
}
