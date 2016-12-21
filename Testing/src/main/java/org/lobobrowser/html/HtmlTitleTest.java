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


public class HtmlTitleTest extends SimpleLoboTest {

	@Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head><title id='myId'>foo</title>\n"
            + "</head><body >\n"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
      	assertTrue(TITLE.equals(doc.getElementById("myId").getNodeName()));
    }
	 
    @Test
    public void pageAsText() throws Exception {
        String htmlSource = "<html>\n"
            + "<head>\n"
            + "<title>Dummy</title>\n"
            + "</head>\n"
            + "\n"
            + "<body>\n"
            + "Dummy page\n"
            + "</body>\n"
            + "</html>\n";

        HTMLDocumentImpl page = loadPage(htmlSource);
        final String expected = "Dummy" + SEPARATOR_LINE + "Dummy page";
        assertEquals(expected, page.getTextContent());
    }

    @Test
    public void asText() throws Exception {
        String htmlSource = "<html>\n"
            + "<head>\n"
            + "<title>Title\nText     Test</title>\n"
            + "</head>\n"
            + "\n"
            + "<body>\n"
            + "Dummy page\n"
            + "</body>\n"
            + "</html>\n";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("Title Text Test", page.getTextContent());
    }

    @Test
    public void asTextEmptyTitle() throws Exception {
        String htmlSource = "<html>\n"
            + "<head>\n"
            + "<title></title>\n"
            + "</head>\n"
            + "\n"
            + "<body>\n"
            + "Dummy page\n"
            + "</body>\n"
            + "</html>\n";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("", page.getTextContent());
    }
}
