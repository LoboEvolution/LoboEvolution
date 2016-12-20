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
import org.w3c.dom.Node;

public class DomCommentTest extends SimpleLoboTest {

    @Test
    public void asText() throws Exception {
        final String content = "<html><body><!-- a comment --></body></html>";
        HTMLDocumentImpl page = loadPage(content);
        assertEquals("", page.getTextContent());
    }

    @Test
    public void textSibling() throws Exception {
        final String content = "<html><body id='body'><!-- c1 -->text<!-- c2 --></body></html>";
        HTMLDocumentImpl page = loadPage(content);
        Node node = page.getElementById("body").getFirstChild();
        assertEquals("#text", node.getNextSibling().getNodeName());
        assertEquals(Node.TEXT_NODE, node.getNextSibling().getNodeType());
    }
}
