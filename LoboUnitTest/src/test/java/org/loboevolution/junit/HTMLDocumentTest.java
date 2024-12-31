/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.loboevolution.css.CSSRuleList;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.css.CSSStyleSheet;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.js.css.CSSStyleSheetImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLDocumentTest extends LoboUnitTest {

    private static Document document;

    @BeforeAll
    public static void setUpBeforeClass() {
        document = sampleHtmlFile();
    }

    @Test
    public void getDoctype() {
        final DocumentType docType = document.getDoctype();
        assertNotNull(docType);
        assertEquals("html", docType.getName());
        assertEquals("<!DOCTYPE html>", docType.toString());
    }

    @Test
    public void getDocumentElement() {
        final Element elm = document.getDocumentElement();
        assertNotNull(elm);
        assertEquals("HTML", elm.getTagName());
    }

    @Test
    public void getNamespaceURI() {
        assertNull(document.getNamespaceURI());
        final Text text = document.createTextNode("foo");
        assertNotNull(text);
        assertNull(text.getNamespaceURI());
        final CDATASection cdata = document.createCDATASection("foo");
        assertNotNull(cdata);
        assertNull(cdata.getNamespaceURI());
        final Comment comment = document.createComment("foo");
        assertNotNull(comment);
        assertNull(comment.getNamespaceURI());
        final ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
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
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        text = document.createTextNode("text");
        elm = document.createElement("p");
        try {
            text.appendChild(elm);
            fail("Must throw exception.");
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        final Attr foo = document.createAttribute("foo");
        try {
            text.appendChild(foo);
            fail("Must throw exception.");
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        final ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
                "type=\"text/css\" href=\"sheet.css\"");
        try {
            text.appendChild(pi);
            fail("Must throw exception.");
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testAppendChildTwoDoctypesError() throws DOMException {
        final UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
        context.setUserAgentEnabled(false);
        final Document document = new DOMImplementationImpl(context).createDocument(null, null, null);
        document.appendChild(document.getImplementation().createDocumentType("foo", null, null));
        try {
            document.appendChild(document.getImplementation().createDocumentType("bar", null, null));
            fail("Must throw exception.");
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testCloneNode() {
        final DOMImplementation domImpl = document.getImplementation();
        Document document = domImpl.createDocument(null, null, null);
        Document cloned = (Document) document.cloneNode(false);
        assertTrue(document.isEqualNode(cloned));
        assertSame(document.getClass(), cloned.getClass());
        final DocumentType docType = domImpl.createDocumentType("html", null, null);
        document = domImpl.createDocument(null, null, docType);
        assertTrue(document.isEqualNode(document.cloneNode(true)));
        cloned = (Document) document.cloneNode(false);
        assertNull(cloned.getDoctype());
        assertNull(cloned.getDocumentElement());
        assertSame(document.getClass(), cloned.getClass());
        final Element docElm = document.createElement("html");
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
        final HTMLElement docelm = (HTMLElement) document.getDocumentElement();
        assertTrue(document.contains(document));
        assertTrue(document.contains(docelm));
        assertTrue(docelm.contains(docelm));
        assertFalse(docelm.contains(document));
        final Element h1 = document.getElementById("h1");
        final Element span1 = document.getElementById("span1");
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
        assertInstanceOf(HTMLLinkElement.class, elm);
        elm = document.createElement("LINK");
        assertInstanceOf(HTMLLinkElement.class, elm);
        assertEquals("LINK", elm.getLocalName());
        assertEquals("LINK", elm.getTagName());
        elm = document.createElement("style");
        assertInstanceOf(HTMLStyleElement.class, elm);
        elm = document.createElement("STYLE");
        assertInstanceOf(HTMLStyleElement.class, elm);
        assertEquals("STYLE", elm.getLocalName());

        final HTMLElement html = (HTMLElement) document.createElement("html");
        try {
            elm.appendChild(html);
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        try {
            document.createElement(null);
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement("");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createElement("<");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement(">");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNS() {
        Element elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "link");
        assertInstanceOf(HTMLLinkElement.class, elm);

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "LINK");
        assertInstanceOf(HTMLLinkElement.class, elm);
        assertEquals("LINK", elm.getLocalName());
        assertEquals("LINK", elm.getTagName());
        assertEquals(HTMLDocument.HTML_NAMESPACE_URI, elm.getNamespaceURI());

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "style");
        assertInstanceOf(HTMLStyleElement.class, elm);

        elm = document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "STYLE");
        assertInstanceOf(HTMLStyleElement.class, elm);
        assertEquals("STYLE", elm.getLocalName());

        elm = document.createElementNS("http://www.w3.org/2000/svg", "g:rect");
        assertEquals("g", elm.getPrefix());
        assertEquals("rect", elm.getLocalName());
        assertEquals("g:rect", elm.getTagName());

        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "s:div");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, null);
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(HTMLDocument.HTML_NAMESPACE_URI, "");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "x:");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ":x");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ":");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "<");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ">");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testAttributes() {
        final Element p = document.createElement("p");
        final Attr attr = document.createAttribute("id");
        attr.setValue("theId");
        p.setAttributeNode(attr);
        final Attr cloned = (Attr) attr.cloneNode(false);
        assertNotNull(cloned);
        assertEquals(attr.getName(), cloned.getName());
        assertEquals(attr.getNamespaceURI(), cloned.getNamespaceURI());
        assertEquals(attr.getValue(), cloned.getValue());

        try {
            document.createAttribute(null);
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute("");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createAttribute("<");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute(">");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute("\"");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, null);
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ":");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "x:");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ":x");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, ">");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "<");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(Document.XML_NAMESPACE_URI, "\"");
            fail("Must throw exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testEntities1() {
        final Element elm = document.getElementById("entity");
        assertNotNull(elm);
        assertEquals("SPAN", elm.getTagName());
        assertEquals("<>", elm.getTextContent());
        final NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        final Node node0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, node0.getNodeType());
        assertEquals("<>", node0.getNodeValue());
        final Attr classattr = elm.getAttributeNode("class");
        assertNotNull(classattr);
        assertEquals("ent\"ity", classattr.getValue());
        assertEquals("[object Attr]", classattr.toString());
    }

    @Test
    public void testEntities2() {
        final Element elm = document.getElementById("entiacute");
        assertNotNull(elm);
        assertEquals("SPAN", elm.getTagName());
        assertEquals("ítem", elm.getTextContent());
        final NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        final Node ent0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, ent0.getNodeType());
        assertEquals("ítem", ent0.getNodeValue());
    }

    @Test
    public void testEntities3() {
        final Element elm = document.getElementById("inflink");
        assertNotNull(elm);
        assertEquals("A", elm.getTagName());
        assertEquals("List item \u221e", elm.getTextContent());
        final NodeList nl = elm.getChildNodes();
        assertNotNull(nl);
        assertEquals(1, nl.getLength());
        final Node ent0 = nl.item(0);
        assertEquals(Node.TEXT_NODE, ent0.getNodeType());
        assertEquals("List item \u221e", ent0.getNodeValue());
    }

    @Test
    public void testAttributeEntities() {
        final Element p = document.createElement("p");
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
        final Element p = document.createElement("p");
        try {
            p.setAttribute("foo=", "bar");
            fail("Must throw an exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementError() {
        try {
            document.createElement("p'");
            fail("Must throw an exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testComment() {
        final Comment c = document.createComment(" A comment ");
        assertEquals(" A comment ", c.getData());
        final Node clone = c.cloneNode(false);
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
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testProcessingInstruction() {
        final ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
                "type=\"text/xsl\" href=\"style.xsl\"");
        assertEquals("[object HTMLProcessingElement]", pi.toString());
        final Node clone = pi.cloneNode(false);
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
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("foo:xml-stylesheet", "type=\"text/xsl\" href=\"style.xsl\"");
            fail("Must throw an exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("foo:xml-stylesheet", "type=\"text/xsl\" href=\"style.xsl\"?>");
            fail("Must throw an exception");
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCloneDocumentNode() {
        final HTMLDocument doc = (HTMLDocument) document.cloneNode(false);
        assertNull(doc.getDoctype());
        assertNull(doc.getDocumentElement());
        assertSame(document.getImplementation(), doc.getImplementation());
    }

    @Test
    public void testCloneNodeDeep() {
        testCloneNode(document.getFirstChild());
    }

    private void testCloneNode(final Node nd) {
        Node prev = nd;
        Node node = nd;
        while (node != null) {
            prev = node;
            final Node cloned = node.cloneNode(true);
            assertTrue(node.isEqualNode(cloned));
            node = node.getNextSibling();
        }
        if (prev != null) {
            testCloneNode(prev.getFirstChild());
        }
    }

    @Test
    public void getChildNodes() {
        final NodeListImpl list = (NodeListImpl) document.getChildNodes();
        assertNotNull(list);
        assertEquals(2, list.getLength());
    }

    @Test
    public void getElementById() {
        final HTMLElementImpl elm = (HTMLElementImpl) document.getElementById("ul1");
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
        final Element html = document.getDocumentElement();
        list = document.getElementsByTagName("div");
        assertEquals(1, list.getLength());
        html.appendChild(document.createElement("div"));
        assertEquals(2, list.getLength());
        final HTMLCollection stylelist2 = document.getElementsByTagName("style");
        assertEquals(stylelist.toString(), stylelist2.toString());
        stylelist = (HTMLCollectionImpl) document.getElementsByTagName("STYLE");
        assertEquals(1, stylelist.getLength());
        list = document.getElementsByTagName("html");
        assertEquals(1, list.getLength());
        assertSame(document.getDocumentElement(), list.item(0));
    }

    @Test
    public void getElementsByTagNameCI() {
        final Element para = document.getElementById("para1");
        final Element spanUC = document.createElementNS("http://www.example.com/foonamespace", "SPAN");
        para.appendChild(spanUC);
        final HTMLCollectionImpl list = (HTMLCollectionImpl) document.getElementsByTagName("SPAN");
        assertFalse(list.isEmpty());
        assertEquals(5, list.getLength());
        assertSame(document.getElementById("entity"), list.item(0));
        assertSame(spanUC, list.item(1));
        assertSame(document.getElementById("span1"), list.item(2));
    }

    @Test
    public void getElementsByClassName() {
        final HTMLCollection tablelist = document.getElementsByClassName("tableclass");
        assertNotNull(tablelist);
        assertEquals(1, tablelist.getLength());
        final Element elem = (Element) tablelist.item(0);
        assertEquals("TABLE", elem.getNodeName());
        HTMLCollection list = ((HTMLElement) elem.getElementsByTagName("tr").item(0)).getElementsByClassName("tableclass");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        list = document.getElementsByClassName("liclass");
        assertNotNull(list);
        assertEquals(6, list.getLength());
        assertEquals("LI", list.item(0).getNodeName());
        final Element li = document.createElement("li");
        li.setAttribute("class", "liclass");
        list.item(0).getParentNode().appendChild(li);
        assertEquals(6, list.getLength());
        list = document.getElementsByClassName("xxxxxx");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        list = document.getElementsByClassName("smallitalic");
        assertEquals(1, list.getLength());
        final Element div = document.createElement("div");
        list.item(0).appendChild(div);
        assertEquals(1, list.getLength());
        div.setAttribute("class", "smallitalic");
        assertEquals("smallitalic", div.getAttribute("class"));
        assertEquals(2, list.getLength());
        div.setAttribute("class", "nothing");
        assertEquals(1, list.getLength());
        final HTMLCollection tablelist2 = document.getElementsByClassName("tableclass");
        assertEquals(tablelist.toString(), tablelist2.toString());
    }

    @Test
    public void getElementsByTagNameNS() {
        HTMLCollection list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "*");
        assertNotNull(list);
        assertEquals(3, list.getLength());
        final Element svg = (Element) list.item(0);
        assertEquals("svg", svg.getNodeName());
        final Attr version = svg.getAttributeNode("version");
        assertNull(version.getNamespaceURI());
        assertNull(svg.getPrefix());
        assertEquals("rect", list.item(1).getNodeName());
        list.item(0).appendChild(document.createElementNS("http://www.w3.org/2000/svg", "circle"));
        assertEquals(4, list.getLength());
        final HTMLCollection svglist = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "svg");
        assertNotNull(svglist);
        assertEquals(1, svglist.getLength());
        assertEquals("svg", svglist.item(0).getNodeName());
        list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "rect");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        final Node oldrect = list.item(0);
        assertEquals("rect", oldrect.getNodeName());
        final Element newrect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
        oldrect.getParentNode().appendChild(newrect);
        assertEquals(Node.DOCUMENT_POSITION_PRECEDING, oldrect.compareDocumentPosition(newrect));
        assertEquals(2, list.getLength());
        final Node node = svglist.item(0);
        assertEquals("svg", node.getNodeName());
        node.getParentNode().removeChild(node);
        assertEquals(0, svglist.getLength());
        list = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "xxxxxx");
        assertNotNull(list);
        assertEquals(0, list.getLength());
    }

    @Test
    public void testQuerySelectorAll() {
        final Element elm = document.getElementById("ul1");
        NodeList qlist = document.querySelectorAll("#ul1");
        assertEquals(1, qlist.getLength());
        assertSame(elm, qlist.item(0));
        qlist = document.querySelectorAll("#xxxxxx");
        assertEquals(0, qlist.getLength());
    }

    @Test
    public void testQuerySelectorAll2() {
        final HTMLCollection list = document.getElementsByTagName("p");
        final NodeListImpl qlist = (NodeListImpl) document.querySelectorAll("p");
        final int sz = list.getLength();
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
        } catch (final DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void getTextContent() {
        final Element elm = (Element) document.getElementsByTagName("style").item(0);
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
        final Element elm = document.getElementById("para1");
        assertNotNull(elm);
        elm.appendChild(document.createComment(" comment "));
        final String text = elm.getTextContent();
        assertNotNull(text);
        assertEquals("Paragraph", text);

        final Attr classNode = elm.getAttributeNode("class");
        assertNotNull(classNode);
        assertEquals("boldmargin", classNode.getTextContent());

        final Element div = document.getElementById("div1");
        assertNotNull(div);
    }

    @Test
    public void textIsElementContentWhitespace() {
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
    public void textGetWholeText() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("One"));
        final Text text = document.createTextNode("Two");
        p.appendChild(text);
        p.appendChild(document.createTextNode("Three"));
        p.appendChild(document.createTextNode(" Four"));
        assertEquals("OneTwoThree Four", text.getWholeText());
    }

    @Test
    public void textGetWholeTextWithER1() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p1 "));
        final Text text = document.createTextNode(" p3");
        p.appendChild(text);
        p.appendChild(document.createTextNode(" p4"));
        final NodeListImpl list = (NodeListImpl) p.getChildNodes();
        assertNotNull(list);
        assertEquals(3, p.getChildNodes().getLength());
        assertEquals("p1  p3 p4", text.getWholeText());
    }

    @Test
    public void textGetWholeTextWithER2() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p1 "));
        p.appendChild(document.createElement("span"));
        final Text text = document.createTextNode(" p3");
        p.appendChild(text);
        p.appendChild(document.createTextNode(" p4"));
        assertEquals(" p3 p4", text.getWholeText());
    }

    @Test
    public void textReplaceWholeText() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("One"));
        final Text text = document.createTextNode("Two");
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
    public void textReplaceWholeTextWithER1() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        final Text text = document.createTextNode("p three");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p four"));
        assertEquals(3, p.getChildNodes().getLength());
        assertEquals("foo", text.replaceWholeText("foo").getData());
        assertEquals(1, p.getChildNodes().getLength());
    }

    @Test
    public void textReplaceWholeTextWithER2() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        final Text text = document.createTextNode("p three");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p four"));
        assertEquals(3, p.getChildNodes().getLength());
        assertNull(text.replaceWholeText(""));
        assertFalse(p.hasChildNodes());
    }

    @Test
    public void textReplaceWholeTextWithER3() {
        final Element p = document.createElement("p");
        p.appendChild(document.createTextNode("p one"));
        p.appendChild(document.createElement("span"));
        final Text text = document.createTextNode("p four");
        p.appendChild(text);
        p.appendChild(document.createTextNode("p five"));
        assertEquals(4, p.getChildNodes().getLength());
        text.replaceWholeText("foo");
        assertEquals(4, p.getChildNodes().getLength());
    }

    @Test
    public void getStyleSheet() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        final CSSStyleSheet sheet = doc.getStyleSheets().item(0);
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
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        final HTMLElementImpl elm = (HTMLElementImpl) doc.getElementById("firstH3");
        assertNotNull(elm);
        assertEquals("font-family: 'Does Not Exist', Neither; color: navy", elm.getAttribute("style"));
        final CSSStyleDeclaration style = elm.getStyle();
        assertEquals("font-family: \"Does not exist\", Neither; color: navy", style.getCssText());
        assertEquals(2, style.getLength());
        assertEquals("\"does not exist\", neither", style.getPropertyValue("font-family"));
        final Attr attr = elm.getAttributeNode("style");
        assertNotNull(attr);
        attr.setValue("");
        assertEquals(0, style.getLength());
    }

    @Test
    public void getElementgetComputedStylePresentationalAttribute() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
        final HTMLElementImpl elm = (HTMLElementImpl) doc.getElementById("fooimg");
        assertNotNull(elm);
        assertEquals("200", elm.getAttribute("width"));
        assertEquals("180", elm.getAttribute("height"));
    }


    @Test
    public void testCompatComputedStyle() {
        final HTMLElementImpl elm = (HTMLElementImpl) document.getElementById("cell12");
        assertNotNull(elm);
        assertNotNull(elm.getCurrentStyle());
        assertNotNull(elm.getCurrentStyle());
        final CSSStyleDeclaration styledecl = elm.getCurrentStyle();
        assertEquals("padding: 4pt 6pt; margin-left: 5pt", styledecl.getCssText());
        assertEquals(2, styledecl.getLength());
        assertEquals("5pt", styledecl.getPropertyValue("margin-left"));
        assertNull(styledecl.getPropertyValue("does-not-exist"));
        assertEquals("", styledecl.getPropertyValue("does-not-exist"));
    }

    @Test
    public void testStyleElement() {
        final Element style = (Element) document.getElementsByTagName("style").item(0);
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLStyleElement) style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertTrue(sheet.getCssRules().getLength() > 0);
        assertSame(sheet.getOwnerNode(), style);

        style.setAttribute("media", "screen");
        final CSSStyleSheetImpl sheet2 = (CSSStyleSheetImpl) ((HTMLStyleElement) style).getStyleSheet();
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

        final Attr type = style.getAttributeNode("type");
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

        final Attr media = style.getAttributeNode("media");
        media.setNodeValue("&%/(*");
        assertNull(((HTMLLinkElement) style).getSheet());

        media.setNodeValue("screen");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        assertNotNull(sheet);

        final long sz = sheet.getCssRules().getLength();
        assertEquals(3, sz);
        final Text text = document.createTextNode("@namespace svg url('http://www.w3.org/2000/svg');\n");
        style.insertBefore(text, style.getFirstChild());
        final long szp1 = sz + 1;
        assertEquals(szp1, sheet.getCssRules().getLength());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();

        CSSRuleList rules = sheet.getCssRules();
        assertEquals(szp1, rules.getLength());
        final Text text2 = document.createTextNode(
                "@font-feature-values Some Font, Other Font {@swash{swishy:1;flowing:2;}@styleset{double-W:14;sharp-terminals:16 1;}}\n");
        style.replaceChild(text2, text);
        assertEquals(szp1, sheet.getCssRules().getLength());
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
        rules = sheet.getCssRules();
        assertEquals(szp1, rules.getLength());

        try {
            style.removeChild(text);
            fail("Must throw exception");
        } catch (final DOMException e) {
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
        final Element style = document.createElement("style");
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
        final Element style = (Element) document.getElementsByTagName("style").item(0);
        final Text text = document.createTextNode("data");
        assertEquals("[object Text]", text.toString());
        text.setData("hello</style>");
        assertEquals("[object Text]", text.toString());
        style.appendChild(text);
        assertEquals("[object Text]", text.toString());
        text.setData("hello</foo>");
        assertEquals("[object Text]", text.toString());
        Element cloned = (Element) style.cloneNode(true);
        assertTrue(style.isEqualNode(cloned));
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) style).getSheet();
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
        final Element link = (Element) document.getElementsByTagName("link").item(0);
        CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertTrue(sheet.getCssRules().getLength() > 0);

        link.setAttribute("media", "screen");
        final CSSStyleSheetImpl sheet2 = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet2);
        assertSame(sheet2, sheet);
        assertEquals(1, sheet2.getMedia().getLength());
        assertEquals("screen", sheet2.getMedia().item(0));

        link.setAttribute("href", "css/alter1.css");
        sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertSame(sheet2, sheet);
        assertSame(sheet.getOwnerNode(), link);

        final Attr href = link.getAttributeNode("href");
        assertNotNull(href);
        href.setValue("http://www.example.com/css/example.css");
        assertNotNull(((HTMLLinkElement) link).getSheet());
        assertEquals(0, sheet.getCssRules().getLength());

        link.setAttribute("media", "screen only and");
        assertNull(((HTMLLinkElement) link).getSheet());
    }

    @Test
    public void testLinkElement2() {
        final Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/foo");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementBadMIMEType() {
        final Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/css/background.png");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
    }

    @Test
    public void testLinkElementBadExtension() {
        final Element link = document.createElement("link");
        link.setAttribute("href", "http://www.example.com/etc/fakepasswd");
        assertNull(((HTMLLinkElement) link).getSheet());

        link.setAttribute("rel", "stylesheet");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
    }

    @Test
    public void testLinkElementEvil() {
        final Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "file:/dev/zero");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementEvilJar() {
        final Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "jar:http://www.example.com/evil.jar!/file");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getMedia().getLength());
        assertEquals(0, sheet.getCssRules().getLength());
    }

    @Test
    public void testLinkElementEvilBase() {
        final Element base = (Element) document.getElementsByTagName("base").item(0);
        base.setAttribute("href", "jar:http://www.example.com/evil.jar!/dir/file1");

        final Element link = document.createElement("link");
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
        final Element base = (Element) document.getElementsByTagName("base").item(0);
        base.setAttribute("href", "jar:http://www.example.com/evil.jar!/dir/file1");
        final Element link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "jar:http://www.example.com/evil.jar!/file2");
        final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) ((HTMLLinkElement) link).getSheet();
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
        final Element base = (Element) document.getElementsByTagName("base").item(0);
        assertEquals("http://www.example.com/", base.getBaseURI());
        base.setAttribute("href", "http://www.example.com/newbase/");
        assertEquals("http://www.example.com/newbase/", document.getBaseURI());
        assertEquals("http://www.example.com/newbase/", base.getBaseURI());
        final Element anchor = (Element) document.getElementsByTagName("a").item(0);
        anchor.setAttribute("href", "http://www.example.com/foo/");
        assertEquals("http://www.example.com/foo/", anchor.getAttribute("href"));
        assertEquals("http://www.example.com/newbase/", document.getBaseURI());
        final Attr attr = document.createAttribute("href");
        attr.setValue("http://www.example.com/other/base/");
        base.setAttributeNode(attr);
        assertEquals("http://www.example.com/other/base/", document.getBaseURI());
        final Node parent = base.getParentNode();
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
        } catch (final DOMException e) {
        }

        base.setAttributeNode(attr);
        assertEquals("http://www.example.com/yet/another/base/", document.getBaseURI());

        attr.setValue("foo:");
        assertEquals("http://www.example.com/xhtml/htmlsample.html", document.getBaseURI());
    }

    @Test
    public void testFontIOError() {
        final Element head = (Element) document.getElementsByTagName("head").item(0);
        final Element style = document.createElement("style");
        style.setAttribute("type", "text/css");
        style.setTextContent("@font-face{font-family:'Mechanical Bold';src:url('font/MechanicalBd.otf');}");
        head.appendChild(style);
        final Element elm = document.getElementById("firstH3");
        assertNotNull(elm);
    }
}
