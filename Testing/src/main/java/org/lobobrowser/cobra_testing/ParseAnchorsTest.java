/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.cobra_testing;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Class ParseAnchorsTest.
 */
public class ParseAnchorsTest {

	/** The Constant TEST_URI. */
	private static final String TEST_URI = "http://sourceforge.net/projects/loboevolution/";

	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);
		UserAgentContext uacontext = new SimpleUserAgentContext();
		// In this case we will use a standard XML document
		// as opposed to Cobra's HTML DOM implementation.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		URL url = new URL(TEST_URI);
		InputStream in = url.openConnection().getInputStream();
		try {
			Reader reader = new InputStreamReader(in, "UTF-8");
			Document document = builder.newDocument();
			// Here is where we use Cobra's HTML parser.
			HtmlParser parser = new HtmlParser(uacontext, document);
			parser.parse(reader);
			// Now we use XPath to locate "a" elements that are
			// descendents of any "html" element.
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xpath.evaluate("html//a", document, XPathConstants.NODESET);
			int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {
				Element element = (Element) nodeList.item(i);
				System.out.println("## Anchor # " + (i + 1) + ": " + element.getAttribute("href"));
			}
		} finally {
			in.close();
		}
	}

}
