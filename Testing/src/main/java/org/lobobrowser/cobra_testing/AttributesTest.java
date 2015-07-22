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

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;

/**
 * The Class AttributesTest.
 */
public class AttributesTest {

    /** The Constant TEST_URI. */
    private static final String TEST_URI = "http://www.google.com";

    /**
     * @param args
     */
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
            org.w3c.dom.Element element = document.getDocumentElement();
            if (element != null) {
                org.w3c.dom.NodeList list = element
                        .getElementsByTagName("input");
                int size = list.getLength();
                for (int i = 0; i < size; i++) {
                    org.w3c.dom.Node currentNode = list.item(i);
                    org.w3c.dom.NamedNodeMap map = currentNode.getAttributes();
                    System.out.println("node#" + i + ": " + currentNode);
                    int length = map.getLength();
                    for (int j = 0; j < length; j++) {
                        System.out.println("  item#" + i + "." + j + ": "
                                + map.item(j));
                    }
                    org.w3c.dom.Node typeNode = map.getNamedItem("type");
                    System.out.println("  type=" + typeNode);
                }
            }
        } finally {
            in.close();
        }
    }

}
