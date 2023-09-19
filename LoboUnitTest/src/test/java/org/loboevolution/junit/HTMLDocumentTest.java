/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.junit;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.js.css.CSSStyleSheetImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.css.CSSRuleList;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;

public class HTMLDocumentTest extends LoboUnitTest {

    private static Document document;

    @BeforeClass
    public static void setUpBeforeClass() {
        document = sampleHtmlFile();
    }

    @Test
    public void getDoctype() {
        DocumentType docType = document.getDoctype();
        assertNotNull(docType);
        assertEquals("html", docType.getName());
        assertEquals("<!DOCTYPE html>", docType.toString());
    }

    @Test
    public void getDocumentElement() {
        Element elm = document.getDocumentElement();
        assertNotNull(elm);
        assertEquals("HTML", elm.getTagName());
    }

    @Test
    public void getNamespaceURI() {
        assertNull(document.getNamespaceURI());
        Text text = document.createTextNode("foo");
        assertNotNull(text);
        assertNull(text.getNamespaceURI());
        CDATASection cdata = document.createCDATASection("foo");
        assertNotNull(cdata);
        assertNull(cdata.getNamespaceURI());
        Comment comment = document.createComment("foo");
        assertNotNull(comment);
        assertNull(comment.getNamespaceURI());
        ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
                "type=\"text/css\" href=\"sheet.css\"");
        assertNotNull(pi);
        assertNull(pi.getNamespaceURI());
    }

    @Test
    public void testAppendChildToTextError() throws DOMException {
        Element elm = document.createElement("br");
        Text text = document.createTextNode("text");
        try {
            elm.appendChild(text);
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        text = document.createTextNode("text");
        elm = document.createElement("p");
        try {
            text.appendChild(elm);
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        Attr foo = document.createAttribute("foo");
        try {
            text.appendChild(foo);
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
                "type=\"text/css\" href=\"sheet.css\"");
        try {
            text.appendChild(pi);
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testAppendChildTwoDoctypesError() throws DOMException {
        UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
        context.setUserAgentEnabled(false);
        Document document = new DOMImplementationImpl(context).createDocument(null, null, null);
        document.appendChild(document.getImplementation().createDocumentType("foo", null, null));
        try {
            document.appendChild(document.getImplementation().createDocumentType("bar", null, null));
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testCloneNode() {
        DOMImplementation domImpl = document.getImplementation();
        Document document = domImpl.createDocument(null, null, null);
        Document cloned = (Document) document.cloneNode(false);
        assertTrue(document.isEqualNode(cloned));
        assertSame(document.getClass(), cloned.getClass());
        DocumentType docType = domImpl.createDocumentType("html", null, null);
        document = domImpl.createDocument(null, null, docType);
        assertTrue(document.isEqualNode(document.cloneNode(true)));
        cloned = (Document) document.cloneNode(false);
        assertNull(cloned.getDoctype());
        assertNull(cloned.getDocumentElement());
        assertSame(document.getClass(), cloned.getClass());
        Element docElm = document.createElement("html");
        docElm.setAttribute("id", "myId");
        document.appendChild(docElm);
        assertTrue(document.isEqualNode(document.cloneNode(true)));
    }

    @Test
    public void testCloneNode2() {
        assertTrue(document.isEqualNode(document.cloneNode(true)));
    }

    @Test
    public void testContains() {
        HTMLElement docelm = (HTMLElement) document.getDocumentElement();
        assertTrue(document.contains(document));
        assertTrue(document.contains(docelm));
        assertTrue(docelm.contains(docelm));
        assertFalse(docelm.contains(document));
        Element h1 = document.getElementById("h1");
        Element span1 = document.getElementById("span1");
        assertTrue(document.contains(h1));
        assertTrue(document.contains(span1));
        assertTrue(docelm.contains(h1));
        assertTrue(docelm.contains(span1));
        assertFalse(h1.contains(docelm));
        assertFalse(span1.contains(docelm));
        assertFalse(h1.contains(document));
        assertFalse(span1.contains(document));
        assertFalse(h1.contains(span1));
        assertFalse(span1.contains(h1));
    }

    @Test
    public void testCreateElement() {
        Element elm = document.createElement("link");
        assertTrue(elm instanceof HTMLLinkElement);
        elm = document.createElement("LINK");
        assertTrue(elm instanceof HTMLLinkElement);
        assertEquals("LINK", elm.getLocalName());
        assertEquals("LINK", elm.getTagName());
        elm = document.createElement("style");
        assertTrue(elm instanceof HTMLStyleElement);
        elm = document.createElement("STYLE");
        assertTrue(elm instanceof HTMLStyleElement);
        assertEquals("STYLE", elm.getLocalName());

        HTMLElement html = (HTMLElement) document.createElement("html");
        try {
            elm.appendChild(html);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        try {
            document.createElement(null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement("");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createElement("<");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement(">");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNS() {
        Element elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "link");
        assertTrue(elm instanceof HTMLLinkElement);

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "LINK");
        assertTrue(elm instanceof HTMLLinkElement);
        assertEquals("LINK", elm.getLocalName());
        assertEquals("LINK", elm.getTagName());
        assertEquals(HTMLDocument.HTML_NAMESPACE_URI, elm.getNamespaceURI());

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "style");
        assertTrue(elm instanceof HTMLStyleElement);

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "STYLE");
        assertTrue(elm instanceof HTMLStyleElement);
        assertEquals("STYLE", elm.getLocalName());

        elm = document.createElementNS("http://www.w3.org/2000/svg", "g:rect");
        assertEquals("g", elm.getPrefix());
        assertEquals("rect", elm.getLocalName());
        assertEquals("g:rect", elm.getTagName());

        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "s:div");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "x:");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ":x");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ":");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "<");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ">");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testAttributes() {
        Element p = document.createElement("p");
        Attr attr = document.createAttribute("id");
        attr.setValue("theId");
        p.setAttributeNode(attr);
        Attr cloned = (Attr) attr.cloneNode(false);
        assertNotNull(cloned);
        assertEquals(attr.getName(), cloned.getName());
        assertEquals(attr.getNamespaceURI(), cloned.getNamespaceURI());
        assertEquals(attr.getValue(), cloned.getValue());

        try {
            document.createAttribute(null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute("");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createAttribute("<");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute(">");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute("\"");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ":");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "x:");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ":x");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ">");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "<");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "\"");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testEntities1() {
        Element elm = document.getElementById("entity");
        assertNotNull(elm);
        assertEquals("SPAN", elm.getTagName());
        assertEquals("<>", elm.getTextContent());
        NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        Node node0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, node0.getNodeType());
        assertEquals("<>", node0.getNodeValue());
        Attr classattr = elm.getAttributeNode("class");
        assertNotNull(classattr);
        assertEquals("ent\"ity", classattr.getValue());
        assertEquals("[object Attr]", classattr.toString());
    }

    @Test
    public void testEntities2() {
        Element elm = document.getElementById("entiacute");
        assertNotNull(elm);
        assertEquals("SPAN", elm.getTagName());
        assertEquals("ítem", elm.getTextContent());
        NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        Node ent0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, ent0.getNodeType());
        assertEquals("ítem", ent0.getNodeValue());
    }

    @Test
    public void testEntities3() {
        Element elm = document.getElementById("inflink");
        assertNotNull(elm);
        assertEquals("A", elm.getTagName());
        assertEquals("List item \u221e", elm.getTextContent());
        NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        Node ent0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, ent0.getNodeType());
        assertEquals("List item \u221e", ent0.getNodeValue());
    }

    @Test
    public void testAttributeEntities() {
        Element p = document.createElement("p");
        Attr attr = document.createAttribute("id");
        attr.setValue("para>Id");
        p.setAttributeNode(attr);
        assertEquals("para>Id", p.getAttribute("id"));
        assertEquals("para>Id", attr.getValue());
        assertEquals("[object Attr]", attr.toString());
        attr.setValue("para<Id");
        assertEquals("para<Id", attr.getValue());
        assertEquals("[object Attr]", attr.toString());

        p.setAttribute("class", "\"fooclass&");
        assertEquals("\"fooclass&", p.getAttribute("class"));
        attr = p.getAttributeNode("class");
        assertEquals("[object Attr]", attr.toString());

        p.setAttribute("foo", "bar\"");
        assertEquals("bar\"", p.getAttribute("foo"));
        attr = p.getAttributeNode("foo");
        assertEquals("[object Attr]", attr.toString());
    }

    @Test
    public void testSetAttributeError() {
        Element p = document.createElement("p");
        try {
            p.setAttribute("foo=", "bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementError() {
        try {
            document.createElement("p'");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testComment() {
        Comment c = document.createComment(" A comment ");
        assertEquals(" A comment ", c.getData());
        Node clone = c.cloneNode(false);
        assertNotNull(clone);
        assertEquals(c.getNodeType(), clone.getNodeType());
        assertEquals(c.getNodeName(), clone.getNodeName());
        assertEquals(c.getNodeValue(), clone.getNodeValue());
    }

    @Test
    public void testBadComment() {
        try {
            document.createComment("Bad-->comment");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testProcessingInstruction() {
        ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
                "type=\"text/xsl\" href=\"style.xsl\"");
        assertEquals("[object HTMLProcessingElement]", pi.toString());
        Node clone = pi.cloneNode(false);
        assertNotNull(clone);
        assertEquals(pi.getNodeType(), clone.getNodeType());
        assertEquals(pi.getNodeName(), clone.getNodeName());
        assertEquals(pi.getNodeValue(), clone.getNodeValue());
    }

    @Test
    public void testBadProcessingInstruction() {
        try {
            document.createProcessingInstruction("xml", "encoding=UTF-8");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("foo:xml-stylesheet", "type=\"text/xsl\" href=\"style.xsl\"");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("foo:xml-stylesheet", "type=\"text/xsl\" href=\"style.xsl\"?>");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCloneDocumentNode() {
        HTMLDocument doc = (HTMLDocument) document.cloneNode(false);
        assertNull(doc.getDoctype());
        assertNull(doc.getDocumentElement());
        assertSame(document.getImplementation(), doc.getImplementation());
    }

    @Test
    public void testCloneNodeDeep() {
        testCloneNode(document.getFirstChild());
    }

    private void testCloneNode(Node node) {
        Node prev = node;
        while (node != null) {
            prev = node;
            Node cloned = node.cloneNode(true);
            assertTrue(node.isEqualNode(cloned));
            node = node.getNextSibling();
        }
        if (prev != null) {
            testCloneNode(prev.getFirstChild());
        }
    }

    @Test
    public void getChildNodes() {
        NodeListImpl list = (NodeListImpl)document.getChildNodes();
        assertNotNull(list);
        assertEquals(2, list.getLength());
    }

    @Test
    public void getElementById() {
        HTMLElementImpl elm = (HTMLElementImpl) document.getElementById("ul1");
        assertNotNull(elm);
        assertEquals("UL", elm.getTagName());
        assertNull(document.getElementById("xxxxxx"));
        assertEquals("ul1", elm.getAttribute("id"));
        assertEquals("ul1", elm.getId());
    }

    @Test
    public void getElementsByTagName() {
        HTMLCollectionImpl stylelist = (HTMLCollectionImpl) document.getElementsByTagName("style");
        assertNotNull(stylelist);
        assertEquals(2, stylelist.getLength());
        assertEquals("STYLE", stylelist.item(0).getNodeName());
        assertEquals("STYLE", stylelist.item(1).getNodeName());
        assertNull(stylelist.item(-1));
        assertNull(stylelist.item(2));
        assertFalse(stylelist.isEmpty());
        HTMLCollection list = document.getElementsByTagName("li");
        assertNotNull(list);
        assertEquals(6, list.getLength());
        assertEquals("LI", list.item(0).getNodeName());
        list.item(0).getParentNode().appendChild(document.createElement("li"));
        assertEquals(7, list.getLength());
        list = document.getElementsByTagName("xxxxxx");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        Element html = document.getDocumentElement();
        list = document.getElementsByTagName("div");
        assertEquals(1, list.getLength());
        html.appendChild(document.createElement("div"));
        assertEquals(2, list.getLength());
        HTMLCollection stylelist2 = document.getElementsByTagName("style");
        assertEquals(stylelist.toString(), stylelist2.toString());
        stylelist = (HTMLCollectionImpl) document.getElementsByTagName("STYLE");
        assertEquals(1, stylelist.getLength());
        list = document.getElementsByTagName("html");
        assertEquals(1, list.getLength());
        assertSame(document.getDocumentElement(), list.item(0));
    }

    @Test
    public void getElementsByTagNameCI() {
        Element para = document.getElementById("para1");
        Element spanUC = document.createElementNS("http://www.example.com/foonamespace", "SPAN");
        para.appendChild(spanUC);
        HTMLCollectionImpl list = (HTMLCollectionImpl) document.getElementsByTagName("SPAN");
        assertFalse(list.isEmpty());
        assertEquals(5, list.getLength());
        assertSame(document.getElementById("entity"), list.item(0));
        assertSame(spanUC, list.item(1));
        assertSame(document.getElementById("span1"), list.item(2));
    }

    @Test
    public void getElementsByClassName() {
        HTMLCollection tablelist = document.getElementsByClassName("tableclass");
        assertNotNull(tablelist);
        assertEquals(1, tablelist.getLength());
        Element elem = (Element) tablelist.item(0);
        assertEquals("TABLE", elem.getNodeName());
        HTMLCollection list = ((HTMLElement) elem.getElementsByTagName("tr").item(0)).getElementsByClassName("tableclass");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        list = document.getElementsByClassName("liclass");
        assertNotNull(list);
        assertEquals(6, list.getLength());
        assertEquals("LI", list.item(0).getNodeName());
        Element li = document.createElement("li");
        li.setAttribute("class", "liclass");
        list.item(0).getParentNode().appendChild(li);
        assertEquals(6, list.getLength());
        list = document.getElementsByClassName("xxxxxx");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        list = document.getElementsByClassName("smallitalic");
        assertEquals(1, list.getLength());
        Element div = document.createElement("div");
        list.item(0).appendChild(div);
        assertEquals(1, list.getLength());
        div.setAttribute("class", "smallitalic");
        assertEquals("smallitalic", div.getAttribute("class"));
        assertEquals(2, list.getLength());
        div.setAttribute("class", "nothing");
        assertEquals(1, list.getLength());
        HTMLCollection tablelist2 = document.getElementsByClassName("tableclass");
        assertEquals(tablelist.toString(), tablelist2.toString());
    }

    @Test
    public void getElementsByTagNameNS() {
        HTMLCollection list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "*");
        assertNotNull(list);
        assertEquals(3, list.getLength());
        Element svg = (Element) list.item(0);
        assertEquals("svg", svg.getNodeName());
        Attr version = svg.getAttributeNode("version");
        assertNull(version.getNamespaceURI());
        assertNull(svg.getPrefix());
        assertEquals("rect", list.item(1).getNodeName());
        list.item(0).appendChild(document.createElementNS("http://www.w3.org/2000/svg", "circle"));
        assertEquals(4, list.getLength());
        HTMLCollection svglist = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "svg");
        assertNotNull(svglist);
        assertEquals(1, svglist.getLength());
        assertEquals("svg", svglist.item(0).getNodeName());
        list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "rect");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        Node oldrect = list.item(0);
        assertEquals("rect", oldrect.getNodeName());
        Element newrect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
        oldrect.getParentNode().appendChild(newrect);
        assertEquals(Node.DOCUMENT_POSITION_PRECEDING, oldrect.compareDocumentPosition(newrect));
        assertEquals(2, list.getLength());
        Node node = svglist.item(0);
        assertEquals("svg", node.getNodeName());
        node.getParentNode().removeChild(node);
        assertEquals(0, svglist.getLength());
        list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "xxxxxx");
        assertNotNull(list);
        assertEquals(0, list.getLength());
    }

    @Test
    public void testQuerySelectorAll() {
        Element elm = document.getElementById("ul1");
        NodeList qlist = document.querySelectorAll("#ul1");
        assertEquals(1, qlist.getLength());
        assertSame(elm, qlist.item(0));
        qlist = document.querySelectorAll("#xxxxxx");
        assertEquals(0, qlist.getLength());
    }

    @Test
    public void testQuerySelectorAll2() {
        HTMLCollection list = document.getElementsByTagName("p");
        NodeListImpl qlist = (NodeListImpl) document.querySelectorAll("p");
        int sz = list.getLength();
        assertEquals(sz, qlist.getLength());
        assertFalse(qlist.isEmpty());
        for (int i = 0; i < sz; i++) {
            assertTrue(qlist.contains(list.item(i)));
        }
    }

    @Test
    public void testQuerySelectorAllNS() {
        try {
            document.querySelectorAll("svg|*");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void getTextContent() {
        Element elm = (Element) document.getElementsByTagName("style").item(0);
        assertNotNull(elm);
        String text = elm.getTextContent();
        assertNotNull(text);
        assertEquals(1106, text.trim().length());
        document.normalizeDocument();
        text = elm.getTextContent();
        assertNotNull(text);
        assertEquals(1106, text.trim().length());

        document.getDomConfig().setParameter("use-computed-styles", true);
        document.normalizeDocument();
        text = elm.getTextContent();
        assertNotNull(text);
        assertEquals(1052, text.trim().length());
    }

    @Test
    public void getTextContent2() {
        Element elm = document.getElementById("para1");
        assertNotNull(elm);
        elm.appendChild(document.createComment(" comment "));
        String text = elm.getTextContent();
        assertNotNull(text);
        assertEquals("Paragraph <>", text);

        Attr classNode = elm.getAttributeNode("class");
        assertNotNull(classNode);
        assertEquals("boldmargin", classNode.getTextContent());

        Element div = document.getElementById("div1");
        assertNotNull(div);
    }

    @Test
    public void TextIsElementContentWhitespace() {
        Text text = document.createTextNode("foo ");
        assertNotNull(text);
        assertNotNull(text.getData());
        assertFalse(text.isElementContentWhitespace());
        text = document.createTextNode("\n \t\r");
        assertNotNull(text);
        assertNotNull(text.getData());
        assertTrue(text.isElementContentWhitespace());
    }

    @Test
    public void TextGetWholeText() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("One"));
        Text text = document.createTextNode("Two");
        p.appendChild(text);
        p.appendChild(document.createTextNode("Three"));
        p.appendChild(document.createTextNode(" Four"));
        assertEquals("OneTwoThree Four", text.getWholeText());
    }

    @Test
    public void TextGetWholeTextWithER1() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p1 "));
        Text text = document.createTextNode(" p3");
        p.appendChild(text);
        p.appendChild(document.createTextNode(" p4"));
        NodeListImpl list = (NodeListImpl)p.getChildNodes();
        assertNotNull(list);
        assertEquals(3, p.getChildNodes().getLength());
        assertEquals("p1  p3 p4", text.getWholeText());
    }

    @Test
    public void TextGetWholeTextWithER2() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p1 "));
        p.appendChild(document.createElement("span"));
        Text text = document.createTextNode(" p3");
        p.appendChild(text);
        p.appendChild(document.createTextNode(" p4"));
        assertEquals(" p3 p4", text.getWholeText());
    }

    @Test
    public void TextReplaceWholeText() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("One"));
        Text text = document.createTextNode("Two");
        p.appendChild(text);
        p.appendChild(document.createTextNode("Three"));
        p.appendChild(document.createTextNode(" Four"));
        assertEquals(4, p.getChildNodes().getLength());
        assertEquals("foo", text.replaceWholeText("foo").getData());
        assertEquals(1, p.getChildNodes().getLength());
        assertNull(text.replaceWholeText(""));
        assertFalse(p.hasChildNodes());
    }

    @Test
    public void TextReplaceWholeTextWithER1() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        Text text = document.createTextNode("p three");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p four"));
        assertEquals(3, p.getChildNodes().getLength());
        assertEquals("foo", text.replaceWholeText("foo").getData());
        assertEquals(1, p.getChildNodes().getLength());
    }

    @Test
    public void TextReplaceWholeTextWithER2() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        Text text = document.createTextNode("p three");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p four"));
        assertEquals(3, p.getChildNodes().getLength());
        assertNull(text.replaceWholeText(""));
        assertFalse(p.hasChildNodes());
    }

    @Test
    public void TextReplaceWholeTextWithER3() {
        Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        p.appendChild(document.createElement("span"));
        Text text = document.createTextNode("p four");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p five"));
        assertEquals(4, p.getChildNodes().getLength());
        text.replaceWholeText("foo");
        assertEquals(4, p.getChildNodes().getLength());
    }

    @Test
    public void getStyleSheet() {
        HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        CSSStyleSheet sheet = doc.getStyleSheets().item(0);
        assertNotNull(sheet);
        assertNotNull(sheet.getCssRules());
        assertEquals(6, doc.getStyleSheets().getLength());
        assertEquals("file:/C:/Users/utente/workspace/LoboEvolution/target/LoboUnitTest/test-classes/org/lobo/css/common.css", sheet.getHref());
        assertNotNull(sheet);
        assertEquals("background-color: red", sheet.getCssRules().item(0).getStyle().getCssText());
        assertEquals(3, sheet.getCssRules().getLength());
    }

    @Test
    public void getElementgetStyle() {
        HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        HTMLElementImpl elm = (HTMLElementImpl) doc.getElementById("firstH3");
        assertNotNull(elm);
        assertEquals("font-family: 'Does Not Exist', Neither; color: navy", elm.getAttribute("style"));
        CSSStyleDeclaration style = elm.getStyle();
        assertEquals("font-family: \"does not exist\", neither; color: navy", style.getCssText());
        assertEquals(2, style.getLength());
        assertEquals("\"does not exist\", neither", style.getPropertyValue("font-family"));
        Attr attr = elm.getAttributeNode("style");
        assertNotNull(attr);
        attr.setValue("");
        assertEquals(0, style.getLength());
    }

    @Test
    public void getElementgetComputedStylePresentationalAttribute() {
        HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        HTMLElementImpl elm = (HTMLElementImpl) doc.getElementById("fooimg");
        assertNotNull(elm);
        assertEquals("200", elm.getAttribute("width"));
        assertEquals("180", elm.getAttribute("height"));
    }


    @Test
    public void testCompatComputedStyle() {
        HTMLElementImpl elm = (HTMLElementImpl) document.getElementById("cell12");
        assertNotNull(elm);
        assertNotNull(elm.getCurrentStyle());
        assertNotNull(elm.getCurrentStyle());
        CSSStyleDeclaration styledecl = elm.getCurrentStyle();
        assertEquals("padding: 4pt 6pt; margin-left: 5pt", styledecl.getCssText());
        assertEquals(2, styledecl.getLength());
        assertEquals("5pt", styledecl.getPropertyValue("margin-left"));
        assertNull(styledecl.getPropertyValue("does-not-exist"));
        assertEquals("", styledecl.getPropertyValue("does-not-exist"));
    }

    @Test
    public void testStyleElement() {
        Element style = (Element) document.getElementsByTagName("style").item(0);
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLStyleElement) style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertTrue(sheet.getCssRules().getLength() > 0);
        assertSame(sheet.getOwnerNode(), style);

        style.setAttribute("media", "screen");
        CSSStyleSheetImpl sheet2 = (CSSStyleSheetImpl) ((HTMLStyleElement) style).getStyleSheet();
        assertNotNull(sheet2);
        assertSame(sheet2, sheet);
        assertEquals(1, sheet2.getMedia().getLength());
        assertEquals("screen", sheet2.getMedia().item(0));
        assertTrue(sheet2.getCssRules().getLength() > 0);
        style.setTextContent("body {font-size: 14pt; margin-left: 7%;} h1 {font-size: 2.4em;}");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertSame(sheet2, sheet);
        assertEquals(2, sheet.getCssRules().getLength());
        assertSame(sheet.getOwnerNode(), style);

        assertEquals(2, sheet.insertRule("h3 {font-family: Arial}", 2));
        style.normalize();
        assertEquals("body {font-size: 14pt; margin-left: 7%; }h1 {font-size: 2.4em; }h3 {font-family: Arial; }",
                style.getTextContent());

        Attr type = style.getAttributeNode("type");
        type.setNodeValue("foo");
        assertNull(((HTMLLinkElement) style).getSheet());
        assertEquals("body {font-size: 14pt; margin-left: 7%; }h1 {font-size: 2.4em; }h3 {font-family: Arial; }",
                style.getTextContent());

        type.setNodeValue("");
        assertNotNull(((HTMLLinkElement) style).getSheet());
        assertEquals("body {font-size: 14pt; margin-left: 7%; }h1 {font-size: 2.4em; }h3 {font-family: Arial; }",
                style.getTextContent());

        type.setNodeValue("text/CSS");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);

        Attr media = style.getAttributeNode("media");
        media.setNodeValue("&%/(*");
        assertNull(((HTMLLinkElement) style).getSheet());

        media.setNodeValue("screen");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);

        long sz = sheet.getCssRules().getLength();
        assertEquals(3, sz);
        Text text = document.createTextNode("@namespace svg url('http://www.w3.org/2000/svg');\n");
        style.insertBefore(text, style.getFirstChild());
        long szp1 = sz + 1;
        assertEquals(szp1, sheet.getCssRules().getLength());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();

        CSSRuleList rules = sheet.getCssRules();
        assertEquals(szp1, rules.getLength());
        Text text2 = document.createTextNode(
                "@font-feature-values Some Font, Other Font {@swash{swishy:1;flowing:2;}@styleset{double-W:14;sharp-terminals:16 1;}}\n");
        style.replaceChild(text2, text);
        assertEquals(szp1, sheet.getCssRules().getLength());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        rules = sheet.getCssRules();
        assertEquals(szp1, rules.getLength());

        try {
            style.removeChild(text);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.getCode());
        }
        style.removeChild(text2);
        assertEquals(sz, sheet.getCssRules().getLength());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        rules = sheet.getCssRules();
        assertEquals(sz, rules.getLength());

        style.setTextContent("$@foo{bar}");

        type.setNodeValue("text/xsl");
        style.setTextContent(
                "<?xml version=\"1.0\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
                        + "<xsl:output method=\"text\"/><xsl:template match=\"foo\">bar<xsl:value-of select=\".\"/>"
                        + "</xsl:template></xsl:stylesheet>");
        assertNull(((HTMLLinkElement) style).getSheet());
        assertEquals(
                "<?xml version=\"1.0\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
                        + "<xsl:output method=\"text\"/><xsl:template match=\"foo\">bar<xsl:value-of select=\".\"/>"
                        + "</xsl:template></xsl:stylesheet>",
                style.getTextContent());

        style.normalize();
        assertEquals(
                "<?xml version=\"1.0\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
                        + "<xsl:output method=\"text\"/><xsl:template match=\"foo\">bar<xsl:value-of select=\".\"/>"
                        + "</xsl:template></xsl:stylesheet>",
                style.getTextContent());
    }

    @Test
    public void testStyleElement2() {
        Element style = document.createElement("style");
        style.setAttribute("type", "text/css");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLStyleElement) style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());

        style.setAttribute("type", "");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());

        style.removeAttributeNode(style.getAttributeNode("type"));
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());

        style.setAttribute("type", "text/xsl");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNull(sheet);

        style.removeAttribute("type");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());
        style.setTextContent("body {color: blue;}");
        assertEquals(1, sheet.getCssRules().getLength());
        assertEquals("<style>body {color: blue;}</style>", style.toString());

        style.setTextContent("foo:");
        assertEquals("<style>foo:</style>", style.toString());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertEquals(0, sheet.getCssRules().getLength());
        assertEquals("<style>foo:</style>", style.toString());
        style.normalize();
        assertEquals("<style>foo:</style>", style.toString());
    }

    @Test
    public void testRawText() {
        Element style = (Element) document.getElementsByTagName("style").item(0);
        Text text = document.createTextNode("data");
        assertEquals("[object Text]", text.toString());
        text.setData("hello</style>");
        assertEquals("[object Text]", text.toString());
        style.appendChild(text);
        assertEquals("[object Text]>", text.toString());
        text.setData("hello</foo>");
        assertEquals("[object Text]", text.toString());
        Element cloned = (Element) style.cloneNode(true);
        assertTrue(style.isEqualNode(cloned));
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        CSSStyleSheetImpl clonesheet = (CSSStyleSheetImpl) ((HTMLLinkElement) cloned).getSheet();
        assertNotNull(clonesheet);
        assertEquals(sheet.getCssRules().getLength(), clonesheet.getCssRules().getLength());
        cloned = (Element) style.cloneNode(false);
        clonesheet = (CSSStyleSheetImpl) ((HTMLLinkElement) cloned).getSheet();
        assertNotNull(clonesheet);
        assertEquals(0, clonesheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElement() {
        Element link = (Element) document.getElementsByTagName("link").item(0);
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertTrue(sheet.getCssRules().getLength() > 0);

        link.setAttribute("media", "screen");
        CSSStyleSheetImpl sheet2 = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet2);
        assertSame(sheet2, sheet);
        assertEquals(1, sheet2.getMedia().getLength());
        assertEquals("screen", sheet2.getMedia().item(0));

        link.setAttribute("href", "css/alter1.css");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertSame(sheet2, sheet);
        assertSame(sheet.getOwnerNode(), link);

        Attr href = link.getAttributeNode("href");
        assertNotNull(href);
        href.setValue("http://www.example.com/css/example.css");
        assertNotNull(((HTMLLinkElement) link).getSheet());
        assertEquals(0, sheet.getCssRules().getLength());

        link.setAttribute("media", "screen only and");
        assertNull(((HTMLLinkElement) link).getSheet());
    }

    @Test
    public void testLinkElement2() {
        Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/foo");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementBadMIMEType() {
        Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/css/background.png");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
    }

    @Test
    public void testLinkElementBadExtension() {
        Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/etc/fakepasswd");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
    }

    @Test(timeout = 8000)
    public void testLinkElementEvil() {
        Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "file:/dev/zero");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test(timeout = 8000)
    public void testLinkElementEvilJar() {
        Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "jar:http://www.example.com/evil.jar!/file");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementEvilBase() {
        Element base = (Element) document.getElementsByTagName("base").item(0);
        base.setAttribute("href", "jar:http://www.example.com/evil.jar!/dir/file1");

        Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "jar:http://www.example.com/evil.jar!/file2");
        document.getElementsByTagName("head").item(0).appendChild(link);
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());

        document.setDocumentURI("jar:http://www.example.com/foo.jar!/dir/file1");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementEvilBaseNoDocumentURI() {
        document.setDocumentURI(null);
        Element base = (Element) document.getElementsByTagName("base").item(0);
        base.setAttribute("href", "jar:http://www.example.com/evil.jar!/dir/file1");
        Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "jar:http://www.example.com/evil.jar!/file2");
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
        assertSame(sheet.getOwnerNode(), link);
        assertEquals("jar:http://www.example.com/evil.jar!/file2", link.getAttribute("href"));
    }

    @Test
    public void testBaseElement() {
        assertEquals("http://www.example.com/xhtml/htmlsample.html", document.getDocumentURI());
        assertEquals("http://www.example.com/", document.getBaseURI());
        assertEquals("http://www.example.com/", document.getBaseURI());
        Element base = (Element) document.getElementsByTagName("base").item(0);
        assertEquals("http://www.example.com/", base.getBaseURI());
        base.setAttribute("href", "http://www.example.com/newbase/");
        assertEquals("http://www.example.com/newbase/", document.getBaseURI());
        assertEquals("http://www.example.com/newbase/", base.getBaseURI());
        Element anchor = (Element) document.getElementsByTagName("a").item(0);
        anchor.setAttribute("href", "http://www.example.com/foo/");
        assertEquals("http://www.example.com/foo/", anchor.getAttribute("href"));
        assertEquals("http://www.example.com/newbase/", document.getBaseURI());
        Attr attr = document.createAttribute("href");
        attr.setValue("http://www.example.com/other/base/");
        base.setAttributeNode(attr);
        assertEquals("http://www.example.com/other/base/", document.getBaseURI());
        Node parent = base.getParentNode();
        parent.removeChild(base);
        attr.setValue("http://www.example.com/yet/another/base/");
        assertEquals("http://www.example.com/xhtml/htmlsample.html", document.getBaseURI());

        parent.appendChild(base);
        assertEquals("http://www.example.com/yet/another/base/", document.getBaseURI());

        base.removeAttributeNode(attr);
        assertEquals("http://www.example.com/xhtml/htmlsample.html", document.getBaseURI());
        try {
            base.removeAttributeNode(attr);
            fail("Must throw exception.");
        } catch (DOMException e) {
        }

        base.setAttributeNode(attr);
        assertEquals("http://www.example.com/yet/another/base/", document.getBaseURI());

        attr.setValue("foo:");
        assertEquals("http://www.example.com/xhtml/htmlsample.html", document.getBaseURI());
    }

    @Test
    public void testFontIOError() {
        Element head = (Element) document.getElementsByTagName("head").item(0);
        Element style = document.createElement("style");
        style.setAttribute("type", "text/css");
        style.setTextContent("@font-face{font-family:'Mechanical Bold';src:url('font/MechanicalBd.otf');}");
        head.appendChild(style);
        Element elm = document.getElementById("firstH3");
        assertNotNull(elm);
    }
}
