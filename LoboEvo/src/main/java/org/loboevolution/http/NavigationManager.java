/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.ToolsStore;
import org.loboevolution.html.node.Document;
import org.xml.sax.InputSource;

/**
 * <p>NavigationManager class.</p>
 *
 *
 *
 */
public class NavigationManager {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NavigationManager.class.getName());

	/**
	 * <p>getDocument.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	public static Document getDocument(String uri) {
		final HtmlPanel panel = new HtmlPanel();
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
					Reader reader = new InputStreamReader(in, "utf-8")) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext();
				final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);

				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext);
				return builder.parse(is);
			} catch (SocketTimeoutException e) {
				logger.log(Level.SEVERE, "More than " + connection.getConnectTimeout() + " elapsed.");
		    }
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>insertHistory.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param title a {@link java.lang.String} object.
	 * @param index a int.
	 */
	public static void insertHistory(String uri, String title, int index) {
		final NavigationStore history = new NavigationStore();
		CookieManager.putCookies(uri);
		history.deleteHost(uri);
		history.addAsRecent(uri,title, index);
	}

	/**
	 * <p>getHtmlPanelSearch.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param search a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public static HtmlPanel getHtmlPanelSearch(IBrowserPanel browserPanel, String search) {
		final ToolsStore tools = new ToolsStore();
		final List<SearchEngineStore> searchEngineStores = tools.getSearchEngines();
		for (final SearchEngineStore searchEngineStore : searchEngineStores) {
			if (searchEngineStore.isSelected()) {
				final String uri = searchEngineStore.getBaseUrl() + search.replace(" ", "%20");
				return HtmlPanel.createHtmlPanel(browserPanel, uri);
			}
		}

		final String uri = "https://www.google.com/search?q=" + search.replace(" ", "%20");
		return HtmlPanel.createHtmlPanel(browserPanel, uri);
	}
}
