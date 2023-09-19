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
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLStyleElementImpl;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.http.UserAgentContext;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DOMDocumentTest extends LoboUnitTest {

    private static DOMImplementationImpl domImpl;

    @BeforeClass
    public static void setUpBeforeClass() {
        UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
        context.setUserAgentEnabled(false);
        domImpl = new DOMImplementationImpl(context);
    }

    @Test
    public void testCreateDocument() {
        Document document = domImpl.createDocument(null, null, null);
        assertEquals("BackCompat", document.getCompatMode());
        DocumentType doctype = domImpl.createDocumentType("HTML", null, null);
        document = domImpl.createDocument(null, null, doctype);
        assertEquals("CSS1Compat", document.getCompatMode());
    }

    @Test
    public void testGetOwnerDocument() {
        Document document = domImpl.createDocument(null, null, null);
        assertNotNull(document.getOwnerDocument());
    }

    @Test
    public void testText() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Text c = document.createTextNode("A text node");
        assertEquals("A text node", c.getData());
        assertEquals("A text node", c.getNodeValue());
        Text d = c.splitText(7);
        assertEquals("A text ", c.getData());
        assertEquals("node", d.getData());
        try {
            c.splitText(100);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }
        try {
            c.splitText(-100);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        Element elm = document.createElement("p");
        c = document.createTextNode("A text node");
        elm.appendChild(c);
        assertNull(c.getNextSibling());
        d = c.splitText(7);
        assertEquals("A text ", c.getData());
        assertEquals("node", d.getData());
        assertEquals(2, elm.getChildNodes().getLength());
        assertSame(d, c.getNextSibling());

        c = document.createTextNode("A text node<");
        assertEquals("A text node<", c.getData());
        assertEquals("A text node<", c.getNodeValue());
        c.appendData("foo>");
        assertEquals("A text node<foo>", c.getData());
        assertEquals("A text node<foo>", c.getNodeValue());

        c.deleteData(11, 20);
        assertEquals("A text node", c.getData());
        assertEquals("A text node", c.getNodeValue());

        c.replaceData(0, 1, "My");
        assertEquals("My text node", c.getData());
        assertEquals("My text node", c.getNodeValue());

        c.deleteData(0, 3);
        assertEquals("text node", c.getData());
        assertEquals("text node", c.getNodeValue());

        try {
            document.createTextNode(null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            c.replaceWholeText(null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            c.deleteData(-1, 3);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        try {
            c.deleteData(1, -3);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        try {
            c.deleteData(20, 3);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        c.insertData(0, "The ");
        assertEquals("The text node", c.getData());
        assertEquals("The text node", c.getNodeValue());

        c.insertData(13, " is now larger");
        assertEquals("The text node is now larger", c.getData());

        c.deleteData(9, 5);
        assertEquals("The text is now larger", c.getData());
        c.insertData(9, "node ");
        assertEquals("The text node is now larger", c.getData());
        c.deleteData(26, 200);
        assertEquals("The text node is now large", c.getData());

        try {
            c.insertData(-1, "foo");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        try {
            c.insertData(30, "foo");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }

        Node clone = c.cloneNode(false);
        assertNotNull(clone);
        assertEquals(c.getNodeType(), clone.getNodeType());
        assertEquals(c.getNodeName(), clone.getNodeName());
        assertEquals(c.getNodeValue(), clone.getNodeValue());
    }

    @Test
    public void testCharacterData() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        CDATASection c = document.createCDATASection("A CDATA section");
        assertEquals("A CDATA section", c.getData());
        assertEquals("A CDATA section", c.getNodeValue());

        assertEquals(15, c.getLength());
        c = document.createCDATASection("A CDATA section<");
        assertEquals("A CDATA section<", c.getData());
        assertEquals("A CDATA section<", c.getNodeValue());

        Node clone = c.cloneNode(false);
        assertNotNull(clone);
        assertEquals(c.getNodeType(), clone.getNodeType());
        assertEquals(c.getNodeName(), clone.getNodeName());
        assertEquals(c.getNodeValue(), clone.getNodeValue());

        try {
            document.createCDATASection(null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            c.setData("]]>");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createCDATASection("]]>");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        try {
            c.substringData(-1, 4);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }
        try {
            c.substringData(1, -1);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }
        try {
            c.substringData(67, 1);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }
        assertEquals("A ", c.substringData(0, 2));
        assertEquals("A CDATA section<", c.substringData(0, 200));
        try {
            c.replaceData(67, 1, "foo");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INDEX_SIZE_ERR, e.getCode());
        }
        c.replaceData(15, 1, "");
        c.replaceData(0, 1, "My");
        assertEquals("My CDATA section", c.getData());
        assertEquals("My CDATA section", c.getWholeText());
        try {
            c.appendChild(document.createComment(" hi "));
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testCloneNode() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Document cloned = (Document) document.cloneNode(false);
        assertTrue(document.isEqualNode(cloned));
        assertSame(document.getClass(), cloned.getClass());
        DocumentType docType = domImpl.createDocumentType("foo", null, "http://www.example.com/foo.dtd");
        document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "foo", docType);
        Element docElm = document.getDocumentElement();
        docElm.setAttribute("id", "myId");
        assertTrue(document.isEqualNode(document.cloneNode(true)));
        cloned = (Document) document.cloneNode(false);
        assertNull(cloned.getDoctype());
        assertNull(cloned.getDocumentElement());
        assertSame(document.getClass(), cloned.getClass());
        ProcessingInstruction pi = document.createProcessingInstruction("foo", "bar");
        document.appendChild(pi);
        Comment comment = document.createComment(" A comment ");
        document.appendChild(comment);
        document.appendChild(document.createComment(" End comment "));
        cloned = (Document) document.cloneNode(true);
        assertTrue(document.isEqualNode(cloned));
        assertSame(document.getClass(), cloned.getClass());
    }

    @Test
    public void testCreateElementNS() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Element element = document.createElementNS(null, "element");
        element.setAttribute("Id", "myId");
        assertEquals("myId", element.getAttribute("Id"));
        assertEquals("myId", element.getId());
        assertTrue(element.getAttributeNode("Id").isId());
        element.setAttribute("foo", "bar");
        assertEquals("bar", element.getAttribute("foo"));
        assertFalse(element.getAttributeNode("foo").isId());
        document.appendChild(element);
        assertSame(element, document.getDocumentElement());
        assertEquals("[object HTMLElement]", element.toString());
    }

    @Test
    public void testCreateElementError() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        try {
            document.createElement("p'");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
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
            document.createElement("-p");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement(".p");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElement(":");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNSError() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "e:p'");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNSError2() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        try {
            document.createElementNS(null, "foo:bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNSError3() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);

        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, "foo:");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(Document.XML_NAMESPACE_URI, ":foo");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNSHighCharError() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        try {
            document.createElementNS(null, "\u26a1");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateElementNSInjectionError() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        try {
            document.createElementNS(null, "\"");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(null, "div><iframe");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createElementNS(null, "foo disableProtection");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateComment() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Comment comment = document.createComment("My comment");
        assertNotNull(comment);
        assertEquals("My comment", comment.getData());
        assertEquals("[object Comment]", comment.toString());

        comment = document.createComment("<--");
        assertNotNull(comment);
        assertEquals("<--", comment.getData());
        assertEquals("[object Comment]", comment.toString());

        comment = document.createComment("-->");
        assertNotNull(comment);
        assertEquals("-->", comment.getData());
        assertEquals("[object Comment]", comment.toString());

        try {
            document.createComment(null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            comment.appendChild(document.createComment(" hi "));
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateDocumentFragment() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "foo", null);
        DocumentFragment df = document.createDocumentFragment();
        assertNotNull(df);
        Comment comment = document.createComment("My comment");
        df.appendChild(comment);
        Element div = document.createElement("div");
        df.appendChild(div);
        df.appendChild(document.createComment(" another comment "));
        Element p = document.createElement("p");
        p.setAttribute("lang", "en");
        p.setAttribute("class", "para");
        df.appendChild(p);
        Element span = document.createElement("span");
        span.appendChild(document.createTextNode("foo"));
        p.appendChild(span);
        df.isEqualNode(df.cloneNode(true));
        assertEquals("[object DocumentFragment]", df.toString());
    }

    @Test
    public void testCreateAttribute() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Element docElm = document.createElementNS(Document.XML_NAMESPACE_URI, "doc");
        document.appendChild(docElm);
        Attr attr = document.createAttribute("lang");
        assertNotNull(attr);
        attr.setValue("en");
        assertNull(attr.getNextSibling());
        assertNull(attr.getPreviousSibling());
        assertNull(attr.getFirstChild());
        assertNull(attr.getLastChild());
        assertEquals(0, attr.getChildNodes().getLength());
        assertNull(attr.getNamespaceURI());
        assertEquals("lang", attr.getName());
        assertEquals("en", attr.getValue());
        assertEquals("lang", attr.getNodeName());
        assertEquals("en", attr.getNodeValue());
        assertEquals("[object Attr]", attr.toString());
        docElm.setAttributeNodeNS(attr);

        try {
            document.createAttribute(null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttribute("");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }

        Attr attr2 = document.createAttribute("foo");
        attr2.setValue("bar");
        try {
            attr.insertBefore(attr2, null);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
        }
        try {
            attr.removeChild(attr2);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
        }
        try {
            attr.replaceChild(attr2, attr);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
        }
        docElm.setAttributeNode(attr2);
        Attr attr3 = document.createAttribute("id");
        attr3.setValue("myId");
        docElm.setAttributeNodeNS(attr3);
        assertNull(attr.getPreviousSibling());
        assertSame(attr.getNextSibling(), attr2);
        assertSame(attr, attr2.getPreviousSibling());
        assertSame(attr2.getNextSibling(), attr3);
        assertSame(attr2, attr3.getPreviousSibling());
        assertNull(attr3.getNextSibling());

        attr = document.createAttribute("foo");
        attr.setValue("foo\u00a0bar&\"");
        assertEquals("[object Attr]", attr.toString());
    }

    @Test
    public void testCreateAttributeNS2() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Attr attr = document.createAttributeNS("http://www.w3.org/2000/svg", "version");
        assertNotNull(attr);
        attr.setValue("1.1");
        assertEquals(0, attr.getChildNodes().getLength());
        assertNull(attr.getFirstChild());
        assertNull(attr.getLastChild());
        assertEquals("http://www.w3.org/2000/svg", attr.getNamespaceURI());
        assertEquals("version", attr.getName());
        assertEquals("version", attr.getNodeName());
        assertEquals("1.1", attr.getValue());
        assertEquals("1.1", attr.getNodeValue());
        assertEquals("[object Attr]", attr.toString());
    }

    @Test
    public void testCreateAttributeNSError() {
        Document document = domImpl.createDocument("http://www.example.com/examplens", null, null);
        try {
            document.createAttributeNS("http://www.example.com/examplens", "xmlns");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        Attr attr = document.createAttributeNS("http://www.example.com/examplens", "doc");
        try {
            attr.setPrefix("xmlns");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        try {
            attr.setPrefix("foo:");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            attr.setPrefix("xml");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }

        attr = document.createAttributeNS(Document.XML_NAMESPACE_URI, "doc");
        attr.setPrefix("xml");
        try {
            document.createAttributeNS(null, "foo:bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("", "foo:bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("http://www.example.com/examplens", null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("http://www.example.com/examplens", "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("http://www.example.com/examplens", ":bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("http://www.example.com/examplens", "foo:");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS("", "");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, "'");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, "\"");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, "><iframe><a ");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createAttributeNS(null, "foo disableProtection");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
    }

    @Test
    public void testProcessingInstruction() {
        Document document = domImpl.createDocument(null, null, null);
        ProcessingInstruction pi = document.createProcessingInstruction("xml-foo", "pseudoattr=\"value\"");
        assertEquals("[object HTMLProcessingElement]", pi.toString());
        assertNull(pi.getNextSibling());
        assertNull(pi.getPreviousSibling());
        assertNull(pi.getFirstChild());
        assertNull(pi.getLastChild());

        try {
            pi.setData("?>");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("xml-foo", "?>");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("xml-foo ?>",
                    "<DOCTYPE SYSTEM='http://www.example.com/malicious.dtd'>");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction(null, "bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            document.createProcessingInstruction("", "bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        Node clone = pi.cloneNode(true);
        assertTrue(pi.isEqualNode(clone));
    }

    @Test
    public void testStyleProcessingInstruction() {
        ProcessingInstruction pi = domImpl.createDocument(null, null, null)
                .createProcessingInstruction("xml-stylesheet", "type=\"text/css\" href=\"style.css\"");
        assertEquals("[object HTMLProcessingElement]", pi.toString());
    }

    @Test
    public void testStyleXSLProcessingInstruction() {
        ProcessingInstruction pi = domImpl.createDocument(null, null, null)
                .createProcessingInstruction("xml-stylesheet", "type=\"application/xsl\" href=\"sheet.xsl\"");
        assertEquals("[object HTMLProcessingElement]", pi.toString());
    }

    @Test
    public void testStyleElement() {
        Document document = domImpl.createDocument("", "foo", null);
        Element style = document.createElement("style");
        document.getDocumentElement().appendChild(style);
        style.setAttribute("type", "text/css");
        CSSStyleSheet sheet = ((HTMLStyleElementImpl)style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());

        style.removeAttributeNode(style.getAttributeNode("type"));
        sheet = ((HTMLStyleElementImpl)style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());

        style.setAttribute("type", "text/xsl");
        sheet = ((HTMLStyleElementImpl)style).getStyleSheet();
        assertNull(sheet);

        style.removeAttribute("type");
        sheet = ((HTMLStyleElementImpl)style).getStyleSheet();
        assertNotNull(sheet);
        assertEquals(0, sheet.getCssRules().getLength());
        style.setTextContent("body {color: blue;}");
        assertEquals(1, sheet.getCssRules().getLength());
        assertEquals("<style>body {color: blue;}</style>", style.toString());

        style.setTextContent("foo:");
        assertEquals("<style>foo:</style>", style.toString());
        sheet = ((HTMLStyleElementImpl)style).getStyleSheet();
        assertEquals(0, sheet.getCssRules().getLength());
        assertEquals("<style>foo:</style>", style.toString());
        style.normalize();
        assertEquals("<style>foo:</style>", style.toString());
    }

    @Test
    public void getElementsByTagName() {
        Document document = domImpl.createDocument("", "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElement("div");
        elem1.setAttribute("id", "div1");
        docElm.appendChild(elem1);
        Element elem2 = document.createElement("div");
        elem2.setAttribute("id", "div2");
        elem1.appendChild(elem2);
        Element elem3 = document.createElement("div");
        elem3.setAttribute("id", "div3");
        elem2.appendChild(elem3);
        Element elem4 = document.createElement("div");
        elem4.setAttribute("id", "div4");
        docElm.appendChild(elem4);
        HTMLCollectionImpl list = (HTMLCollectionImpl)document.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem3, list.item(2));
        assertSame(elem4, list.item(3));
        assertNull(list.item(4));

        assertNotEquals(0, list.getLength());
        assertTrue(list.contains(elem1));
        assertTrue(list.contains(elem2));
        assertTrue(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertFalse(list.isEmpty());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem4.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        assertTrue(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem4));

        it = list.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void getElementsByTagNameNS() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem1.setAttribute("id", "div1");
        docElm.appendChild(elem1);
        Element elem2 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem2.setAttribute("id", "div2");
        elem1.appendChild(elem2);
        Element elem3 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem3.setAttribute("id", "div3");
        elem2.appendChild(elem3);
        Element elem4 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem4.setAttribute("id", "div4");
        docElm.appendChild(elem4);
        HTMLCollectionImpl list = (HTMLCollectionImpl)document.getElementsByTagNameNS(Document.XML_NAMESPACE_URI, "div");
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem3, list.item(2));
        assertSame(elem4, list.item(3));
        assertNull(list.item(4));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void getElementsByTagNameNSAsterisk() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem1.setAttribute("id", "div1");
        docElm.appendChild(elem1);
        Element elem2 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem2.setAttribute("id", "div2");
        elem1.appendChild(elem2);
        Element elem3 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem3.setAttribute("id", "div3");
        elem2.appendChild(elem3);
        Element elem4 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem4.setAttribute("id", "div4");
        docElm.appendChild(elem4);
        HTMLCollectionImpl list = (HTMLCollectionImpl)document.getElementsByTagNameNS("*", "div");
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem3, list.item(2));
        assertSame(elem4, list.item(3));
        assertNull(list.item(4));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void getElementsByTagNameNSMixed() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElementNS("http://www.example.com/differentns", "div");
        elem1.setAttribute("id", "div1");
        docElm.appendChild(elem1);
        Element elem2 = document.createElementNS("http://www.example.com/differentns", "div");
        elem2.setAttribute("id", "div2");
        elem1.appendChild(elem2);
        Element elem3 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem3.setAttribute("id", "div3");
        elem2.appendChild(elem3);
        Element elem4 = document.createElementNS("http://www.example.com/differentns", "div");
        elem4.setAttribute("id", "div4");
        docElm.appendChild(elem4);

        HTMLCollectionImpl list = (HTMLCollectionImpl) document.getElementsByTagNameNS(Document.XML_NAMESPACE_URI, "div");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem3, list.item(0));
        assertNull(list.item(1));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem3, it.next());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByTagNameNS("http://www.example.com/differentns", "div");
        assertNotNull(list);
        assertEquals(3, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem4, list.item(2));
        assertNull(list.item(3));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem1.getElementsByTagNameNS("http://www.example.com/differentns", "div");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem2, list.item(0));
        assertNull(list.item(1));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem2, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem1.getElementsByTagNameNS(Document.XML_NAMESPACE_URI, "div");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem3, list.item(0));
        assertNull(list.item(1));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem3, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem3.getElementsByTagNameNS(Document.XML_NAMESPACE_URI, "div");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        it = list.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void getElementsByTagNameNSMixedAsterisk() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElementNS("http://www.example.com/differentns", "div");
        elem1.setAttribute("id", "div1");
        docElm.appendChild(elem1);
        Element elem2 = document.createElementNS("http://www.example.com/differentns", "div");
        elem2.setAttribute("id", "div2");
        elem1.appendChild(elem2);
        Element elem3 = document.createElementNS(Document.XML_NAMESPACE_URI, "div");
        elem3.setAttribute("id", "div3");
        elem2.appendChild(elem3);
        Element elem4 = document.createElementNS("http://www.example.com/differentns", "div");
        elem4.setAttribute("id", "div4");
        docElm.appendChild(elem4);

        HTMLCollectionImpl list = (HTMLCollectionImpl)document.getElementsByTagNameNS("*", "div");
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem3, list.item(2));
        assertSame(elem4, list.item(3));
        assertNull(list.item(4));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem1.getElementsByTagNameNS("*", "div");
        assertNotNull(list);
        assertEquals(2, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem2, list.item(0));
        assertSame(elem3, list.item(1));
        assertNull(list.item(2));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem2.getElementsByTagNameNS("*", "div");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem3, list.item(0));
        assertNull(list.item(1));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem3, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem3.getElementsByTagNameNS("*", "div");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));
        assertEquals("[object HTMLCollection]", list.toString());
        it = list.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void getElementsByClassName() {
        Document document = domImpl.createDocument("", "doc", null);
        Element docElm = document.getDocumentElement();
        Element elem1 = document.createElement("div");
        elem1.setAttribute("class", "foo bar");
        docElm.appendChild(elem1);
        Element elem2 = document.createElement("p");
        elem2.setAttribute("class", "foo");
        elem1.appendChild(elem2);
        Element elem3 = document.createElement("span");
        elem3.setAttribute("class", "foo");
        elem2.appendChild(elem3);
        Element elem4 = document.createElement("section");
        elem4.setAttribute("class", "bar foo otherclass");
        docElm.appendChild(elem4);
        HTMLCollectionImpl list = (HTMLCollectionImpl)document.getElementsByClassName("foo");
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem2, list.item(1));
        assertSame(elem3, list.item(2));
        assertSame(elem4, list.item(3));
        assertNull(list.item(4));

        assertFalse(list.isEmpty());
        assertTrue(list.contains(elem1));
        assertTrue(list.contains(elem2));
        assertTrue(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        Iterator<Node> it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem2, it.next());
        assertSame(elem3, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByClassName("bar");
        assertNotNull(list);
        assertEquals(2, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem4, list.item(1));
        assertNull(list.item(2));

        assertTrue(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByClassName("foo bar");
        assertNotNull(list);
        assertEquals(2, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem4, list.item(1));
        assertNull(list.item(2));

        assertTrue(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByClassName("bar foo");
        assertNotNull(list);
        assertEquals(2, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem1, list.item(0));
        assertSame(elem4, list.item(1));
        assertNull(list.item(2));

        assertTrue(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem1, it.next());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByClassName("otherclass");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem4, list.item(0));
        assertNull(list.item(1));

        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertTrue(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem4, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)document.getElementsByClassName("noclass");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        assertTrue(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertFalse(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertFalse(it.hasNext());

        list = (HTMLCollectionImpl)document.getElementsByClassName("");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        assertTrue(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertFalse(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem2.getElementsByClassName("bar");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        assertTrue(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertFalse(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem3.getElementsByClassName("foo");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));

        assertTrue(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem3));
        assertFalse(list.contains(elem4));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }

        list = (HTMLCollectionImpl)elem2.getElementsByClassName("foo");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem3, list.item(0));
        assertNull(list.item(1));

        assertFalse(list.isEmpty());
        assertFalse(list.contains(elem1));
        assertFalse(list.contains(elem2));
        assertFalse(list.contains(elem4));
        assertTrue(list.contains(elem3));
        assertFalse(list.contains(docElm));

        it = list.iterator();
        assertTrue(it.hasNext());
        assertSame(elem3, it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Must throw exception.");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void testImportNode() {
        Document document = domImpl.createDocument(null, "doc", null);
        Element docElm = document.getDocumentElement();
        Element head = document.createElement("body");
        head.appendChild(document.createComment(" head comment "));
        docElm.appendChild(head);
        Element body = document.createElement("body");
        Attr idAttr = document.createAttribute("id");
        idAttr.setValue("bodyId");
        body.setAttributeNode(idAttr);
        assertTrue(idAttr.isId());
        body.setAttribute("lang", "en");
        Text text = document.createTextNode("text");
        body.appendChild(text);
        docElm.appendChild(body);

        Document doc2 = domImpl.createDocument(null, null, null);
        Node docElm2 = doc2.importNode(docElm, true);
        assertNotSame(docElm, docElm2);
        assertEquals(2, docElm2.getChildNodes().getLength());
        Node head2 = docElm2.getFirstChild();
        assertNotSame(head, head2);
        Element body2 = (Element) docElm2.getLastChild();
        assertNotSame(body, body2);
        assertSame(doc2, head2.getOwnerDocument());
        assertSame(doc2, body2.getOwnerDocument());
        Node text2 = body2.getFirstChild();
        assertSame(doc2, text2.getOwnerDocument());
        Attr idAttr2 = (Attr) body2.getAttributes().getNamedItem("id");
        assertSame(doc2, idAttr2.getOwnerDocument());
        assertTrue(idAttr2.isId());
        assertEquals(2, body2.getAttributes().getLength());
        assertEquals(1, body2.getChildNodes().getLength());
        DocumentFragment df = document.createDocumentFragment();
        Element div = document.createElement("div");
        idAttr = document.createAttribute("id");
        idAttr.setValue("divId");
        div.setAttributeNode(idAttr);
        div.setAttribute("lang", "en");
        Text textbd = document.createTextNode("text below div");
        div.appendChild(textbd);
        div.appendChild(document.createComment(" Comment below div "));
        div.appendChild(document.createElement("span"));
        df.appendChild(div);
        text = document.createTextNode("text");
        df.appendChild(text);
        Element p = document.createElement("p");
        df.appendChild(p);
        df.appendChild(document.createComment(" Comment "));
        Node df2 = doc2.importNode(df, true);
        assertNotSame(df, df2);
        assertEquals(4, df2.getChildNodes().getLength());
        Node textbd2 = df2.getFirstChild();
        assertNotSame(textbd, textbd2);
    }

    @Test
    public void testImportNodeForeign()  {
        Document document = domImpl.createDocument(null, "doc", null);
        Element docElm = document.getDocumentElement();
        Element head = document.createElement("body");
        head.appendChild(document.createComment(" head comment "));
        docElm.appendChild(head);
        Element body = document.createElement("body");
        Attr idAttr = document.createAttribute("id");
        idAttr.setValue("bodyId");
        body.setAttributeNode(idAttr);
        body.setIdAttributeNode(idAttr, true);
        assertTrue(idAttr.isId());
        body.setAttribute("lang", "en");
        Text text = document.createTextNode("text");
        body.appendChild(text);
        docElm.appendChild(body);

        Document doc2 = domImpl.createDocument(null, null, null);
        Node docElm2 = doc2.importNode(docElm, true);
        assertNotSame(docElm, docElm2);
        assertEquals(2, docElm2.getChildNodes().getLength());
        Node head2 = docElm2.getFirstChild();
        assertNotSame(head, head2);
        Element body2 = (Element) docElm2.getLastChild();
        assertNotSame(body, body2);
        assertSame(doc2, head2.getOwnerDocument());
        assertSame(doc2, body2.getOwnerDocument());
        Node text2 = body2.getFirstChild();
        assertSame(doc2, text2.getOwnerDocument());
        Attr idAttr2 = (Attr) body2.getAttributes().getNamedItem("id");
        assertSame(doc2, idAttr2.getOwnerDocument());
        assertTrue(idAttr2.isId());
        assertEquals(2, body2.getAttributes().getLength());
        assertEquals(1, body2.getChildNodes().getLength());
        DocumentFragment df = document.createDocumentFragment();
        Element div = document.createElement("div");
        idAttr = document.createAttribute("id");
        idAttr.setValue("divId");
        div.setAttributeNode(idAttr);
        div.setIdAttributeNode(idAttr, true);
        div.setAttribute("lang", "en");
        Text textbd = document.createTextNode("text below div");
        div.appendChild(textbd);
        div.appendChild(document.createComment(" Comment below div "));
        div.appendChild(document.createElement("span"));
        df.appendChild(div);
        text = document.createTextNode("text");
        df.appendChild(text);
        Element p = document.createElement("p");
        df.appendChild(p);
        df.appendChild(document.createComment(" Comment "));
        Node df2 = doc2.importNode(df, true);
        assertNotSame(df, df2);
        assertEquals(4, df2.getChildNodes().getLength());
        Node textbd2 = df2.getFirstChild();
        assertNotSame(textbd, textbd2);
    }

    @Test
    public void testAppendappendChild() {
        Document document = domImpl.createDocument("http://www.example.com/examplens", null, null);
        Element docelm = document.createElementNS(null, "doc");
        Element element = document.createElementNS(null, "element");
        document.appendChild(docelm);
        assertSame(document, docelm.getParentNode());
        try {
            document.appendChild(element);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        assertSame(docelm, document.getDocumentElement());
        try {
            document.appendChild(element);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        assertSame(docelm, document.getDocumentElement());
        DocumentType dtd = domImpl.createDocumentType("doc", null, null);
        document.appendChild(dtd);
        DocumentType dtd2 = domImpl.createDocumentType("element", null, null);
        try {
            document.appendChild(dtd2);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        assertSame(dtd, document.getDoctype());
        try {
            document.appendChild(dtd2);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        assertSame(dtd, document.getDoctype());
    }

    @Test
    public void testInsertBefore() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);
        Comment comment = document.createComment(" Comment ");
        document.appendChild(comment);

        Element docelm = document.createElementNS(null, "doc");
        Element element = document.createElementNS(null, "element");
        try {
            document.insertBefore(element, docelm);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.getCode());
        }
        document.insertBefore(docelm, comment);
        assertSame(docelm, document.getDocumentElement());
        assertSame(comment, docelm.getNextSibling());
        assertNull(docelm.getPreviousSibling());
        try {
            document.insertBefore(element, docelm);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        assertNull(element.getNextSibling());

        DocumentType dtd = domImpl.createDocumentType("doc", null, null);
        DocumentType dtd2 = domImpl.createDocumentType("element", null, null);
        try {
            document.insertBefore(dtd2, dtd);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.getCode());
        }
        document.insertBefore(dtd, docelm);
        assertSame(dtd, document.getDoctype());
        assertSame(docelm, dtd.getNextSibling());
        assertNull(dtd.getPreviousSibling());
        assertSame(dtd, docelm.getPreviousSibling());
        try {
            document.insertBefore(dtd2, dtd);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        try {
            document.insertBefore(element, dtd);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        try {
            document.insertBefore(dtd2, docelm);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }

        Comment comment1 = document.createComment(" First comment ");
        document.insertBefore(comment1, dtd);
        assertSame(comment1, dtd.getPreviousSibling());
        assertSame(docelm, dtd.getNextSibling());
        assertNull(comment1.getPreviousSibling());
        assertSame(dtd, comment1.getNextSibling());

        Element bar = document.createElementNS(null, "bar");
        docelm.appendChild(bar);
        Element foo = document.createElementNS(null, "foo");
        assertSame(foo, docelm.insertBefore(foo, bar));
        assertSame(bar, foo.getNextElementSibling());
        assertSame(foo, bar.getPreviousElementSibling());
    }

    @Test
    public void testReplaceChild() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, null, null);

        Element docelm = document.createElementNS(null, "doc");
        Element element = document.createElementNS(null, "element");
        try {
            document.replaceChild(element, docelm);
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.getCode());
        }
        document.appendChild(docelm);
        assertSame(docelm, document.getDocumentElement());
        document.replaceChild(element, docelm);
        assertSame(element, document.getDocumentElement());
        document.replaceChild(docelm, element);
        assertSame(docelm, document.getDocumentElement());

        DocumentType dtd = domImpl.createDocumentType("doc", null, null);
        DocumentType dtd2 = domImpl.createDocumentType("element", null, null);
        try {
            document.replaceChild(dtd2, dtd);
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.getCode());
        }
        document.appendChild(dtd);
        assertSame(dtd, document.getDoctype());
        document.replaceChild(dtd2, dtd);
        assertSame(dtd2, document.getDoctype());
        document.replaceChild(dtd, dtd2);
        assertSame(dtd, document.getDoctype());

        try {
            document.replaceChild(element, dtd);
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
        try {
            document.replaceChild(dtd2, docelm);
        } catch (DOMException e) {
            assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
        }
    }

    @Test
    public void testBaseAttribute() {
        Document document = domImpl.createDocument("", "foo", null);
        Element element = document.getDocumentElement();
        element.setAttributeNS(Document.XML_NAMESPACE_URI, "xml:base", "http://www.example.com/");
        assertEquals("http://www.example.com/", element.getAttribute("xml:base"));
        Attr attr = element.getAttributeNode("xml:base");
        assertNotNull(attr);
        attr.setValue("jar:http://www.example.com/evil.jar!/file");
        assertNull(document.getBaseURI());
        document.setDocumentURI("http://www.example.com/foo.html");
        assertEquals("http://www.example.com/foo.html", document.getBaseURI());
        assertEquals("jar:http://www.example.com/evil.jar!/file", attr.getValue());
        attr.setValue("file:/dev/zero");
        assertEquals("http://www.example.com/foo.html", document.getBaseURI());
    }

    @Test
    public void testLookupNamespaceURI() {
        Document document = domImpl.createDocument(Document.XML_NAMESPACE_URI, "x:doc", null);
        Element docelm = document.getDocumentElement();
        assertEquals("[object HTMLElement]", docelm.toString());
        assertEquals(Document.XML_NAMESPACE_URI, docelm.lookupNamespaceURI("x"));
        assertNull(docelm.lookupNamespaceURI("z"));
        assertEquals(Document.XML_NAMESPACE_URI, document.lookupNamespaceURI("x"));
        assertNull(document.lookupNamespaceURI("z"));
        document = domImpl.createDocument("", null, null);
        assertNull(document.lookupNamespaceURI("x"));
    }
}
