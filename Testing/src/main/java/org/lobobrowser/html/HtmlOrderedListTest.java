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

import static org.junit.Assert.*;

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.w3c.dom.Element;


public class HtmlOrderedListTest extends SimpleLoboTest {

	@Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert(document.getElementById('myId'));\n"
            + "  }\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "  <ol id='myId'/>\n"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
        assertTrue(OL.equals(doc.getElementById("myId").getNodeName()));
    }
	
    @Test
    public void asText() throws Exception {
        String htmlSource = "<html><head>\n"
            + "</head><body>\n"
            + "  <ol id='foo'>\n"
            + "  <li>first item</li>\n"
            + "  <li>second item</li>\n"
            + "  </ol>\n"
            + "  </table>\n"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        Element node = page.getElementById("foo");
        final String expectedText = "1. first item" + SEPARATOR_LINE + "2. second item";

        assertEquals(expectedText, node.getTextContent());
        assertEquals(expectedText, page.getTextContent());
    }
}
