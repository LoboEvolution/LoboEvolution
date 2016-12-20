/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SimpleLoboTest implements HtmlProperties{
	
	public final String SEPARATOR_LINE = "\n";

	public static HTMLDocumentImpl loadPage(String htmlSource) {
		try {
			UserAgentContext uacontext = new SimpleUserAgentContext();
			HtmlPanel panel = new HtmlPanel();
			HtmlRendererContext rcontext = new SimpleHtmlRendererContext(panel, (UserAgentContext) null);
			DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext, rcontext);
			Reader reader = new StringReader(htmlSource.trim());
			Document document = builder.newDocument();
			
			Window window = Window.getWindow(rcontext);
			HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
			window.setDocument(doc);
			document.setUserData(Executor.SCOPE_KEY, window.getWindowScope(), null);
			HtmlParser parser = new HtmlParser(new SimpleUserAgentContext(), document);
			parser.parse(reader);
			
			return doc;
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
