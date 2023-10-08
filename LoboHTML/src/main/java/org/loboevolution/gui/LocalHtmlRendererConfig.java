/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.info.TabInfo;
import org.loboevolution.net.Cookie;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

@Data
@AllArgsConstructor
public class LocalHtmlRendererConfig implements HtmlRendererConfig {


    @Override
    public void deleteInput(final String text, final String baseUrl) {

    }

    @Override
    public void insertLogin(final String type, final String value, final String baseUrl, final boolean navigationEnabled) {

    }

    @Override
    public String getSourceCache(final String baseUrl, final String type, final String integrity, final boolean test) {
        return HttpNetwork.sourceResponse(baseUrl, type, integrity);
    }

    @Override
    public List<String> getStyles(final String href, final String baseUrl) {
        return new ArrayList<>();
    }

    @Override
    public void insertStyle(final String title, final String href, final String baseUrl, final int enable) {

    }

    @Override
    public Rectangle getInitialWindowBounds() {
        return new Rectangle(500, 800);
    }

    @Override
    public int countStorage(final int index) {
        return 0;
    }

    @Override
    public Map<String, String> getMapStorage(final int index, final int i) {
        return new HashMap<>();
    }

    @Override
    public Object getValue(final String key, final int i, final int index) {
        return null;
    }

    @Override
    public void deleteStorage(final String keyName, final int i, final int index) {
    }

    @Override
    public void deleteStorage(final int session, final int index) {

    }

    @Override
    public void insertStorage(final String keyName, final String keyValue, final int i, final int index) {

    }

    @Override
    public List<TabInfo> getTabs() {
        return new ArrayList<>();
    }

    @Override
    public List<String> autocomplete(final String type, final String text, final String baseUrl) {
        return new ArrayList<>();
    }

    @Override
    public URL getResourceFile(final String fileName) {
        return  DesktopConfig.getResourceFile(fileName,DesktopConfig.PATH_IMAGE);
    }

    @Override
    public boolean isVisited(final String href) {
        return false;
    }

    @Override
    public void saveCookie(final String toString, final String cookieSpec) {

    }

    @Override
    public List<Cookie> getCookies(final String host, final String path) {
        return new ArrayList<>();
    }

    @Override
    public GeneralInfo getGeneralInfo() {
        return new GeneralInfo();
    }
}


