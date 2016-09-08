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
import java.net.URL;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Class IdTest.
 */
public class IdTest {

	/** The Constant TEST_URI. */
	private static final String TEST_URI = "file:///C:/Users/Administrator/Desktop/1.html";

	public static void main(String[] args) throws Exception {
		LogManager.getLogger("org.lobobrowser");
		UserAgentContext uacontext = new SimpleUserAgentContext();
		URL url = new URL(TEST_URI);
		InputStream in = url.openConnection().getInputStream();
		try {
			DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
			InputSourceImpl is = new InputSourceImpl(in, TEST_URI, "UTF-8");
			Document document = builder.parse(is);
			HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
			Element element = doc.getElementById("1div");
			System.out.println("##Element=" + element);
			NodeList nodeList = doc.getElementsByTagName("input");
			int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {
				System.out.println("##Input=" + nodeList.item(i));
			}
		} finally {
			in.close();
		}
	}

}
