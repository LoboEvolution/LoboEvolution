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


