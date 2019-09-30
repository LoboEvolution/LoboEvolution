package org.lobo.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.lobo.net.HttpNetwork;
import org.lobo.store.NavigationStore;
import org.lobo.store.SearchEngineStore;
import org.lobo.store.ToolsStore;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class NavigationManager {

	public static Document getDocument(String uri) {
		final HtmlPanel panel = new HtmlPanel();
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
					Reader reader = new InputStreamReader(in, "utf-8");) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext();
				final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);

				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext);
				return builder.parse(is);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HtmlPanel getHtmlPanel(String uri, int index) {
		final NavigationStore history = new NavigationStore();
		CookieManager.putCookies(uri);
		history.addAsRecent(uri,index);
		return HtmlPanel.createHtmlPanel(uri);
	}

	public static HtmlPanel getHtmlPanelSearch(String search, int indexPanel) {
		final ToolsStore tools = new ToolsStore();
		final List<SearchEngineStore> searchEngineStores = tools.getSearchEngines();
		for (final SearchEngineStore searchEngineStore : searchEngineStores) {
			if (searchEngineStore.isSelected()) {
				final String uri = searchEngineStore.getBaseUrl() + search.replace(" ", "%20");
				return getHtmlPanel(uri, indexPanel);
			}
		}

		final String uri = "https://www.google.com/search?q=" + search.replace(" ", "%20");
		return getHtmlPanel(uri, indexPanel);
	}
}
