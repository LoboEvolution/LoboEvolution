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
import java.net.URL;
import java.util.logging.*;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.*;
import org.lobobrowser.html.domimpl.*;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class IdTest.
 */
public class IdTest {

    /** The Constant TEST_URI. */
    private static final String TEST_URI = "file:///C:/Users/Administrator/Desktop/1.html";

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);
        UserAgentContext uacontext = new SimpleUserAgentContext();
        URL url = new URL(TEST_URI);
        InputStream in = url.openConnection().getInputStream();
        try {
            DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
            InputSourceImpl is = new InputSourceImpl(in, TEST_URI, "ISO-8859-1");
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
