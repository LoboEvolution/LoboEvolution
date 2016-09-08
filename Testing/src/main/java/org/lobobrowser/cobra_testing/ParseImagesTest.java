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

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.w3c.dom.Document;

/**
 * The Class ParseImagesTest.
 */
public class ParseImagesTest {

	/** The Constant TEST_URI. */
	private static final String TEST_URI = "http://sourceforge.net/projects/loboevolution";

	public static void main(String[] args) throws Exception {
		UserAgentContext uacontext = new SimpleUserAgentContext();
		DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
		URL url = new URL(TEST_URI);
		InputStream in = url.openConnection().getInputStream();
		try {
			Reader reader = new InputStreamReader(in, "UTF-8");
			InputSourceImpl inputSource = new InputSourceImpl(reader, TEST_URI);
			Document d = builder.parse(inputSource);
			HTMLDocumentImpl document = (HTMLDocumentImpl) d;
			HTMLCollection images = document.getImages();
			int length = images.getLength();
			for (int i = 0; i < length; i++) {
				System.out.println("- Image#" + i + ": " + images.item(i));
			}
		} finally {
			in.close();
		}
	}
}
