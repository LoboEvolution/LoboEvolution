package org.lobo.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.lobo.menu.tools.pref.ToolsSettings;
import org.lobo.menu.tools.pref.search.SearchEngine;
import org.lobo.net.HttpNetwork;
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

	public static HtmlPanel getHtmlPanel(String uri) {
		final NavigationHistory history = new NavigationHistory();
		final HtmlPanel panel = new HtmlPanel();
		HtmlPanel newpanel = panel.createHtmlPanel(uri);
		CookieManager.putCookies(uri);
		history.addAsRecent(uri);
		return newpanel;
	}

	public static HtmlPanel getHtmlPanelSearch(String search) {
		final ToolsSettings tools = new ToolsSettings();
		final List<SearchEngine> searchEngines = tools.getSearchEngines();
		for (final SearchEngine searchEngine : searchEngines) {
			if (searchEngine.isSelected()) {
				final String uri = searchEngine.getBaseUrl() + search.replace(" ", "%20");
				return getHtmlPanel(uri);
			}
		}

		final String uri = "https://www.google.com/search?q=" + search.replace(" ", "%20");
		return getHtmlPanel(uri);
	}
}
