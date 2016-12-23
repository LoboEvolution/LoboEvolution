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
package  org.lobobrowser.html;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lobobrowser.html.domimpl.DOMTextImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomTextTest extends SimpleLoboTest {

    @Test
    public void asText_nbsp() throws Exception {
        testPlainText("a b&nbsp;c d &nbsp;e",  "a b c d  e");
        testPlainText("a b&nbsp;c  d &nbsp; e", "a b c d   e");
        testPlainText("&nbsp;a&nbsp;", " a ");
        testPlainText("&nbsp; a&nbsp;", "  a ");
        testPlainText("&nbsp;a &nbsp;", " a  ");
    }

    @Test
    public void asText_fontFormat() throws Exception {
        testAsText("a <b>b</b> c",  "a b c");
        testAsText("a <b>b</b>c",   "a bc");
        testAsText("a<b>b</b> c",   "ab c");
        testAsText("a<b>b</b>c",    "abc");

        testAsText("a <i>b</i> c",  "a b c");
        testAsText("a <i>b</i>c",   "a bc");
        testAsText("a<i>b</i> c",   "ab c");
        testAsText("a<i>b</i>c",    "abc");

        testAsText("a <tt>b</tt> c",  "a b c");
        testAsText("a <tt>b</tt>c",   "a bc");
        testAsText("a<tt>b</tt> c",   "ab c");
        testAsText("a<tt>b</tt>c",    "abc");

        testAsText("a <font>b</font> c",  "a b c");
        testAsText("a<font>b</font> c",   "ab c");
        testAsText("a <font>b</font>c",   "a bc");
        testAsText("a<font>b</font>c",    "abc");

        testAsText("a <span>b</span> c",  "a b c");
        testAsText("a<span>b</span> c",   "ab c");
        testAsText("a <span>b</span>c",   "a bc");
        testAsText("a<span>b</span>c",    "abc");

        testAsText("a<b><font><i>b</i></font></b>c",  "abc");
        testAsText("a<b><font> <i>b</i></font></b>c", "a bc");
    }

    @Test
    public void asText_regression() throws Exception {
        String expected = "a" + SEPARATOR_LINE + "b" + SEPARATOR_LINE + "c";
        testAsText("a<ul><li>b</ul>c", expected);
        testAsText("a<p>b<br>c", expected);
        testAsText("a<table><tr><td>b</td></tr></table>c", expected);
        testAsText("a<div>b</div>c", expected);

        expected = "a" + SEPARATOR_LINE + "b" + SEPARATOR_LINE + "b" + SEPARATOR_LINE + "c";
        testAsText("a<table><tr><td> b </td></tr><tr><td> b </td></tr></table>c", expected);
    }

    @Test
    public void asText_table_elements() throws Exception {
        String htmlSource = "<table id='table'><tr id='row'><td id='cell'> b </td></tr></table>";
        final String content = "<html><body><span id='foo'>" + htmlSource + "</span></body></html>";

        HTMLDocumentImpl page = loadPage(content);

        assertEquals("b", page.getElementById("cell").getTextContent());
        assertEquals("b", page.getElementById("row").getTextContent());
        assertEquals("b", page.getElementById("table").getTextContent());
    }

    private void testPlainText(final String htmlSource, final String expectedText) throws Exception {
        final String content = "<html><body><span id='foo'>" + htmlSource + "</span></body></html>";

        HTMLDocumentImpl page = loadPage(content);
        assertEquals(expectedText, page.getTextContent());

        Element elt = page.getElementById("foo");
        assertEquals(expectedText, elt.getTextContent());

        Node node = elt.getFirstChild();
        assertEquals(expectedText, node.getTextContent());
    }

    private void testAsText(final String htmlSource, final String expectedText) throws Exception {
        final String content = "<html><body><span id='foo'>" + htmlSource + "</span></body></html>";

        HTMLDocumentImpl page = loadPage(content);
        Element elt = page.getElementById("foo");
        assertEquals(expectedText, page.getTextContent());
    }
    
    @Test
    public void splitLastDOMTextImpl() throws Exception {
        final String content
            = "<html><head></head><body>"
            + "<br><div id='tag'></div><br></body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node divNode = page.getElementById("tag");

        DOMTextImpl firstNode = new DOMTextImpl("test split");
        divNode.appendChild(firstNode);

        assertNull(firstNode.getPreviousSibling());

        final DOMTextImpl secondNode = (DOMTextImpl) firstNode.splitText(5);

        final DOMTextImpl thirdNode = new DOMTextImpl("test split");
        divNode.appendChild(thirdNode);

        assertSame(secondNode, firstNode.getNextSibling());
        assertNull(firstNode.getPreviousSibling());
        assertSame(firstNode, secondNode.getPreviousSibling());
        assertSame(thirdNode, secondNode.getNextSibling());
        assertSame(secondNode, thirdNode.getPreviousSibling());
        assertNull(thirdNode.getNextSibling());
        assertSame(divNode, secondNode.getParentNode());
        assertSame(divNode, thirdNode.getParentNode());
    }

    @Test
    public void setTextContent() throws Exception {
        String htmlSource = "<html><body><span id='s'>abc</span></body></html>";
        HTMLDocumentImpl page = loadPage(htmlSource);
        final DOMTextImpl text = (DOMTextImpl) page.getElementById("s").getFirstChild();
        assertEquals("abc", text.getTextContent());
        text.setTextContent("xyz");
        assertEquals("xyz", text.getTextContent());
        assertEquals("xyz", page.getTextContent());
    }

    @Test
    public void getTextContentWhitespace() throws Exception {
        String htmlSource = "<html><body><div id='s'><b>Hello</b> <b>World</b>!</div></body></html>";
        HTMLDocumentImpl page = loadPage(htmlSource);
        Element text = page.getElementById("s");
        assertEquals("Hello World!", text.getTextContent());
    }
}
