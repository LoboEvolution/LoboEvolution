package org.loboevolution.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.ToolsStore;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * <p>NavigationManager class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
					Reader reader = new InputStreamReader(in, "utf-8");) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext();
				final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);

				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext);
				return builder.parse(is);
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>getHtmlPanel.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public static HtmlPanel getHtmlPanel(String uri, int index) {
		final NavigationStore history = new NavigationStore();
		CookieManager.putCookies(uri);
		history.deleteHost(uri);
		history.addAsRecent(uri,index);
		return HtmlPanel.createHtmlPanel(uri);
	}

	/**
	 * <p>getHtmlPanelSearch.</p>
	 *
	 * @param search a {@link java.lang.String} object.
	 * @param indexPanel a int.
	 * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
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
