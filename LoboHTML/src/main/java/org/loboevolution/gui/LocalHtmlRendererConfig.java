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
import org.loboevolution.common.Urls;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IToolBar;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.GeneralInfo;
import org.loboevolution.info.TabInfo;
import org.loboevolution.net.Cookie;
import org.loboevolution.net.HttpNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
public class LocalHtmlRendererConfig implements HtmlRendererConfig {


    @Override
    public void deleteInput(String text, String baseUrl) {

    }

    @Override
    public void insertLogin(String type, String value, String baseUrl, boolean navigationEnabled) {

    }

    @Override
    public String getSourceCache(String baseUrl, String type, boolean test) {
        return HttpNetwork.sourceResponse(baseUrl, type);
    }

    @Override
    public List<String> getStyles(String href, String baseUrl) {
        return new ArrayList<>();
    }

    @Override
    public void insertStyle(String title, String href, String baseUrl, int enable) {

    }

    @Override
    public Rectangle getInitialWindowBounds() {
        return new Rectangle(500, 800);
    }

    @Override
    public int countStorage(int index) {
        return 0;
    }

    @Override
    public Map<String, String> getMapStorage(int index, int i) {
        return new HashMap<>();
    }

    @Override
    public Object getValue(String key, int i, int index) {
        return null;
    }

    @Override
    public void deleteStorage(String keyName, int i, int index) {
    }

    @Override
    public void deleteStorage(int session, int index) {

    }

    @Override
    public void insertStorage(String keyName, String keyValue, int i, int index) {

    }

    @Override
    public List<TabInfo> getTabs() {
        return new ArrayList<>();
    }

    @Override
    public List<String> autocomplete(String type, String text, String baseUrl) {
        return new ArrayList<>();
    }

    @Override
    public URL getResourceFile(String fileName) {
        return  DesktopConfig.getResourceFile(fileName);
    }

    @Override
    public boolean isVisited(String href) {
        return false;
    }

    @Override
    public void saveCookie(String toString, String cookieSpec) {

    }

    @Override
    public List<Cookie> getCookies(String host, String path) {
        return new ArrayList<>();
    }

    @Override
    public GeneralInfo getGeneralInfo() {
        return new GeneralInfo();
    }
}


