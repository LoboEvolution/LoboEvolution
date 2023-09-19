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

package org.loboevolution.driver;

import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.gui.LocalHtmlRendererContext;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * <p>LoboWebDriver class.</p>
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
		final String url = LoboWebDriver.class.getResource("/org/lobo/html/htmlsample.html").toString();
		HTMLDocumentImpl doc = null;
		try {
			WritableLineReader wis = new WritableLineReader(new StringReader(html));
			final HtmlRendererConfig config = new LocalHtmlRendererConfig();
			final UserAgentContext ucontext = new UserAgentContext(config, true);
			HtmlPanel panel = new HtmlPanel();
			panel.setPreferredSize(new Dimension(800, 400));
			final HtmlRendererContext rendererContext = new LocalHtmlRendererContext(panel, ucontext);
			ucontext.setUserAgentEnabled(true);
			doc = new HTMLDocumentImpl(ucontext, rendererContext, config, wis, url);
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
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	protected static HTMLDocumentImpl loadHtml(InputStream in, String url) {
		HTMLDocumentImpl doc = null;
		try {
			WritableLineReader wis = new WritableLineReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			final HtmlRendererConfig config = new LocalHtmlRendererConfig();
			final UserAgentContext ucontext = new UserAgentContext(config, true);
			HtmlPanel panel = new HtmlPanel();
			panel.setPreferredSize(new Dimension(800, 400));
			final HtmlRendererContext rendererContext = new LocalHtmlRendererContext(panel, ucontext);
			ucontext.setUserAgentEnabled(true);
			doc = new HTMLDocumentImpl(ucontext, rendererContext, config, wis, url);
			doc.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

}
