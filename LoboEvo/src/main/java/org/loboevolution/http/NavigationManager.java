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

package org.loboevolution.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.HtmlRendererContextImpl;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.NavigatorFrame;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.config.HtmlRendererConfigImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.UserAgent;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.ToolsStore;
import org.loboevolution.html.node.Document;
import org.xml.sax.InputSource;

/**
 * <p>NavigationManager class.</p>
 */
@Slf4j
public class NavigationManager {

	/**
	 * <p>getDocument.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	public static Document getDocument(final String uri) {
		final HtmlPanel panel = new HtmlPanel();
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			connection.getHeaderField("Set-Cookie");
			try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
                 final Reader reader = new InputStreamReader(in, "utf-8")) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext(new HtmlRendererConfigImpl());
				final HtmlRendererConfig config = new HtmlRendererConfigImpl();
				final HtmlRendererContext rendererContext = new HtmlRendererContextImpl(panel, ucontext, config);
				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext, config);
				return builder.parse(is);
			} catch (final SocketTimeoutException e) {
				log.error("More time elapsed {}", connection.getConnectTimeout());
		    }
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>insertHistory.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param title a {@link java.lang.String} object.
	 * @param index a {@link java.lang.Integer} object.
	 */
	public static void insertHistory(final String uri, final String title, final int index) {
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
	 * @return a {@link HtmlPanel} object.
	 */
	public static HtmlPanel getHtmlPanelSearch(final IBrowserPanel browserPanel, final String search) {
		final ToolsStore tools = new ToolsStore();
		final List<SearchEngineStore> searchEngineStores = tools.getSearchEngines();
		for (final SearchEngineStore searchEngineStore : searchEngineStores) {
			if (searchEngineStore.isSelected()) {
				final String uri = searchEngineStore.getBaseUrl() + search.replace(" ", "%20");
				return NavigatorFrame.createHtmlPanel(browserPanel, uri);
			}
		}

		final SearchEngineStore searchEngine = new ToolsStore().getSelectedSearchEngine();
		final String uri = searchEngine + search.replace(" ", "%20");
		return NavigatorFrame.createHtmlPanel(browserPanel, uri);
	}
}
