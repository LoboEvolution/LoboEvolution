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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.w3c.dom.Element;


public class HtmlSpanTest extends SimpleLoboTest {

    @Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head>\n"
            + "</head><body>\n"
            + "<span id='myId'></span>\n"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
		assertTrue(SPAN.equals(doc.getElementById("myId").getNodeName()));

    }

    @Test
    public void asText() throws Exception {
        String htmlSource = "<html><head></head><body>\n"
            + "<span id='outside'>\n"
            + "<span>\n"
            + "before\n"
            + "</span>\n"
            + "<span>\n"
            + "inside\n"
            + "</span>\n"
            + "<span>\n"
            + "after\n"
            + "</span>\n"
            + "</span>\n"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        Element elt = page.getElementById("outside");
        assertEquals("before inside after", elt.getTextContent());
        assertEquals("before inside after", page.getTextContent());
    }
}
