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

import org.lobobrowser.html.*;
import org.lobobrowser.html.test.*;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.parser.*;
import org.lobobrowser.html.domimpl.*;
import org.w3c.dom.*;

import java.net.*;
import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ParseImagesTest.
 */
public class ParseImagesTest {

    /** The Constant TEST_URI. */
    private static final String TEST_URI = "http://lobobrowser.org";

    public static void main(String[] args) throws Exception {
        UserAgentContext uacontext = new SimpleUserAgentContext();
        DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
        URL url = new URL(TEST_URI);
        InputStream in = url.openConnection().getInputStream();
        try {
            Reader reader = new InputStreamReader(in, "ISO-8859-1");
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
