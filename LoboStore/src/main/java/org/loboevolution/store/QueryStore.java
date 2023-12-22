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

package org.loboevolution.store;

public interface QueryStore {

    String DELETE_BOOKMARKS = "DELETE FROM BOOKMARKS";

    String DELETE_BOOKMARKS_BY_URL = "DELETE FROM BOOKMARKS WHERE baseUrl = ?";

    String INSERT_BOOKMARKS = "INSERT INTO BOOKMARKS (name, description, baseUrl, tags) VALUES(?,?,?,?)";

    String DELETE_DOWNLOAD = "DELETE FROM DOWNLOAD";

    String DOWNLOAD_ORDERED = "SELECT baseUrl FROM DOWNLOAD ORDER BY dt ASC";

    String INSERT_DOWNLOAD = "INSERT INTO DOWNLOAD (baseUrl, dt) VALUES(?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

    String DELETE_LAF = "DELETE FROM LOOK_AND_FEEL";

    String INSERT_LAF = " INSERT INTO LOOK_AND_FEEL (acryl, aero, aluminium, bernstein, fast, graphite,"
            + "hiFi,luna, mcWin, mint, noire, smart, texture, subscript, superscript, underline, italic, "
            + "strikethrough, fontSize, font, color, bold, modern, black, white) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String DELETE_HOST = "DELETE FROM HOST";

    String DELETE_HOST_BY_URL = "DELETE FROM HOST where baseUrl = ?";

    String HOST = "SELECT DISTINCT baseUrl, name FROM HOST ORDER BY dt DESC LIMIT ?";

    String HOST_TAB = "SELECT baseUrl, name FROM HOST where tab = ? ORDER BY dt DESC";

    String INSERT_HOST = "INSERT INTO HOST (baseUrl, name, tab, dt) VALUES(?,?,?, strftime('%Y-%m-%d %H:%M:%S', 'now'))";

    String DELETE_STYLE = "DELETE FROM STYLE";

    String DELETE_STYLE_HREF = "DELETE FROM STYLE WHERE href = ? AND baseUrl = ?";

    String INSERT_STYLE = "INSERT OR REPLACE INTO STYLE(title, href, baseUrl, enable) values (?, ?, ?, ?)";

    String STYLE = "SELECT title FROM STYLE WHERE href = ? AND baseUrl = ? AND enable = 1";

    String STYLE_ALL = "SELECT title FROM STYLE WHERE baseUrl = ?";

    String UPDATE_STYLE_ALL = "UPDATE STYLE SET enable = 0";

    String UPDATE_STYLE = "UPDATE STYLE SET enable = 1 WHERE title = ?";

    String DELETE_SEARCH = "DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'";

    String INSERT_SEARCH = "INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)";

    String SEARCH2 = "SELECT DISTINCT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC";

    String UPDATE_SEARCH = "UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'";

    String UPDATE_SEARCH2 = "UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'";

    String CONNECTIONS = "SELECT DISTINCT proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses FROM CONNECTION";

    String DELETE_CONNECTIONS = "DELETE FROM CONNECTION";

    String INSERT_CONNECTIONS = "INSERT INTO CONNECTION (proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses) VALUES(?,?,?,?,?,?,?)";



}
