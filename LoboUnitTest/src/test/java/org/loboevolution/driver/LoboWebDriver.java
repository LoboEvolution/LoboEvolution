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

package org.loboevolution.driver;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.html.node.Document;
import org.xml.sax.InputSource;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>LoboWebDriver class.</p>
 *
 *
 *
 */
public class LoboWebDriver {
	
	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger(LoboWebDriver.class.getName());

	/**
	 * <p>loadHtml.</p>
	 *
	 * @param html a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	protected HTMLDocumentImpl loadHtml(String html) {
		BrowserFrame frame = new BrowserFrame("Unit Test");
		BrowserPanel panel = new BrowserPanel(frame);
		return createHtmlPanel(panel, fileToElab(html));
	}
	
	/**
	 * <p>fileToElab.</p>
	 *
	 * @param html a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String fileToElab(String html) {
		try {
			Path tempFile = Files.createTempFile(null, null);
			Files.write(tempFile, html.getBytes(StandardCharsets.UTF_8));
			return tempFile.toUri().toString();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		 return null; 
	}

	private HTMLDocumentImpl createHtmlPanel(IBrowserPanel browserPanel, String uri) {
		final HtmlPanel panel = new HtmlPanel();
		panel.setBrowserPanel(browserPanel);
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());

			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
					Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext();
				final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);
				rendererContext.setTest(true);
				panel.setPreferredSize(new Dimension(800, 400));
				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(),rendererContext);
				final Document document = builder.parse(is);
				panel.setDocument(document, rendererContext);
				return (HTMLDocumentImpl)document;
			} catch (SocketTimeoutException e) {
				logger.log(Level.SEVERE, "More than " + connection.getConnectTimeout() + " elapsed.");
		    }

		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
