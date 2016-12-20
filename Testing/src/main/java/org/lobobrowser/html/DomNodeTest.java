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
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.w3c.dom.Node;

public class DomNodeTest extends SimpleLoboTest {

     @Test
    public void elementHasAttributesWith() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        assertTrue("Element should have attribute", node.hasAttributes());
    }

    @Test
    public void elementHasAttributesNone() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node parent = node.getParentNode();
        assertFalse("Element should not have attribute", parent.hasAttributes());
    }

    @Test
    public void nonElementHasAttributes() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node child = node.getFirstChild();
        assertFalse("Text should not have attribute", child.hasAttributes());
    }

    @Test
    public void nonElementGetPrefix() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node child = node.getFirstChild();
        assertEquals("Text should not have a prefix", null, child.getPrefix());
    }

    @Test
    public void nonElementGetNamespaceURI() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node child = node.getFirstChild();
        assertEquals("Text should not have a prefix", null, child.getNamespaceURI());
    }

    @Test
    public void nonElementGetLocalName() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node child = node.getFirstChild();
        assertEquals("Text should not have a prefix", null, child.getLocalName());
    }

    @Test
    public void nonElementSetPrefix() throws Exception {
        final String content = "<html><head></head><body id='tag'>text</body></html>";
        HTMLDocumentImpl page = loadPage(content);

        Node node = page.getElementById("tag");
        Node child = node.getFirstChild();
        child.setPrefix("bar"); // This does nothing.
        assertEquals("Text should not have a prefix", null, child.getPrefix());
    }

    @Test
    public void removeAllChildren() throws Exception {
        final String content
            = "<html><head></head><body>\n"
            + "<p id='tag'><table>\n"
            + "<tr><td>row 1</td></tr>\n"
            + "<tr><td>row 2</td></tr>\n"
            + "</table></p></body></html>";
        HTMLDocumentImpl page = loadPage(content);

        DOMNodeImpl node = (DOMNodeImpl)page.getElementById("tag");
        node.removeAllChildren();
        assertEquals("Did not remove all nodes", null, node.getFirstChild());
    }
}
