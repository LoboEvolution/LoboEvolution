/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * <p>LoboWebDriver class.</p>
 */
public class LoboWebDriver {
	
	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger(LoboWebDriver.class.getName());

	private static String url = LoboUnitTest.class.getResource("/org/lobo/html/htmlsample.html").toString();

	/**
	 * <p>loadHtml.</p>
	 *
	 * @param html a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	protected HTMLDocumentImpl loadHtml(String html) {
		HTMLDocumentImpl doc = null;
		try {
			WritableLineReader wis = new WritableLineReader(new StringReader(html));
			final UserAgentContext ucontext = new UserAgentContext(true);
			HtmlPanel panel = new HtmlPanel();
			panel.setPreferredSize(new Dimension(800, 400));
			final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);
			rendererContext.setTest(true);
			ucontext.setUserAgentEnabled(true);


			doc = new HTMLDocumentImpl(ucontext, rendererContext, wis, url);
			doc.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * <p>loadHtml.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	protected static HTMLDocumentImpl loadHtml(InputStream in) {
		HTMLDocumentImpl doc = null;
		try {
			WritableLineReader wis = new WritableLineReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			final UserAgentContext ucontext = new UserAgentContext(true);
			HtmlPanel panel = new HtmlPanel();
			panel.setPreferredSize(new Dimension(800, 400));
			final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);
			rendererContext.setTest(true);
			ucontext.setUserAgentEnabled(true);
			doc = new HTMLDocumentImpl(ucontext, rendererContext, wis, url);
			doc.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

}
