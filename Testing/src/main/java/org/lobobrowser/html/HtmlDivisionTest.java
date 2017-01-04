/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
import org.lobobrowser.w3c.html.HTMLDivElement;

public class HtmlDivisionTest extends SimpleLoboTest {

	@Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head>"
            + "</head><body >"
            + "  <div id='myId'/>"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
		assertTrue(DIV.equals(doc.getElementById("myId").getNodeName()));
    }
	
    @Test
    public void asText() throws Exception {
        String expected = "hello" + SEPARATOR_LINE + "world";
        testAsText(expected, "<div>hello</div>world");
        testAsText(expected, "<div>hello<br/></div>world");

        expected = "hello" + SEPARATOR_LINE + SEPARATOR_LINE + "world";
        testAsText(expected, "<div>hello<br/><br/></div>world");
    }

    @Test
    public void asText_contiguousBlocks() throws Exception {
        final String expected = "hello" + SEPARATOR_LINE + "world";
        testAsText(expected, "<div><table><tr><td>hello</td></tr><tr><td>world<br/></td></tr></table></div>");
        testAsText(expected, "<div>hello</div><div>world</div>");
        testAsText(expected, "<div>hello</div><div><div>world</div></div>");
        testAsText(expected, "<div><table><tr><td>hello</td></tr><tr><td>world<br/></td></tr></table></div>");
    }

    private void testAsText(final String expected, final String htmlSnippet) throws Exception {
        String htmlSource = "<html><head></head><body>"
            + htmlSnippet
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals(expected, page.getTextContent());
    }

    @Test
    public void asTextDiv() throws Exception {
        String htmlSource = "<html><head></head><body>"
            + "<div id='foo'>  hello </div>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("hello", page.getTextContent());
        HTMLDivElement div = (HTMLDivElement)page.getElementById("foo");
        assertEquals("hello", div.getTextContent());
    }

    @Test
    public void css() throws Exception {
        String htmlSource = "<html><head></head><body>"
            + "<div style='display:inline'>1</div><div style='display:inline'>2</div>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("12", page.getBody().getTextContent());
    }
}
