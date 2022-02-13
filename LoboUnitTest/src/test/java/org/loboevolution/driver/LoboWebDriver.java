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
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

import java.io.StringReader;
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
		HTMLDocumentImpl doc = null;
		try {
			WritableLineReader wis = new WritableLineReader(new StringReader(html));
			UserAgentContext context = new UserAgentContext();
			final UserAgentContext ucontext = new UserAgentContext();
			final HtmlRendererContext rendererContext = new HtmlRendererContext(null, ucontext);
			rendererContext.setTest(true);
			ucontext.setUserAgentEnabled(true);
			doc = new HTMLDocumentImpl(context, rendererContext, wis, "http://www.example.com/");
			doc.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
