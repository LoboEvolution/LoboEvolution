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
package org.lobobrowser.cobra_testing;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class LinkAndMetaTest.
 */
public class LinkAndMetaTest {

	/** The Constant TEST_URI. */
	private static final String TEST_URI = "http://sourceforge.net/projects/loboevolution/";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LogManager.getLogger("org.lobobrowser");
		SimpleUserAgentContext uacontext = new SimpleUserAgentContext();
		uacontext.setScriptingEnabled(false);
		URL url = new URL(TEST_URI);
		InputStream in = url.openConnection().getInputStream();
		try {
			Reader reader = new InputStreamReader(in, "UTF-8");
			DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
			InputSourceImpl is = new InputSourceImpl(reader, url.toExternalForm());
			Document document = builder.parse(is);
			// Here is where we use Cobra's HTML parser.
			NodeList metaList = document.getElementsByTagName("META");
			int metaLength = metaList.getLength();
			System.out.println("metaList.length=" + metaLength);
			for (int i = 0; i < metaLength; i++) {
				Node metaNode = metaList.item(i);
				if (metaNode instanceof Element) {
					Element metaElement = (Element) metaNode;
					String name = metaElement.getAttribute("name");
					if ("description".equalsIgnoreCase(name)) {
						System.out.println("Description: " + metaElement.getAttribute("content"));
						break;
					}
				}
			}
			NodeList linkList = document.getElementsByTagName("LINK");
			int linkLength = linkList.getLength();
			System.out.println("linkList.length=" + linkLength);
			for (int i = 0; i < linkLength; i++) {
				Node linkNode = linkList.item(i);
				if (linkNode instanceof Element) {
					Element linkElement = (Element) linkNode;
					String rel = linkElement.getAttribute("rel");
					if ("alternate".equalsIgnoreCase(rel)) {
						String type = linkElement.getAttribute("type");
						System.out.println("Feed: " + type + ": " + linkElement.getAttribute("href"));
					}
				}
			}
		} finally {
			in.close();
		}
	}

}
