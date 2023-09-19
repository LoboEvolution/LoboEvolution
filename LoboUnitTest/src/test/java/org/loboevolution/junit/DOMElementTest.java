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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;

public class DOMElementTest extends LoboUnitTest {

    private static Document document;

    private static DOMImplementationImpl domImpl;

    @BeforeClass
    public static void setUpBeforeClass() {
        UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
        context.setUserAgentEnabled(false);
        domImpl = new DOMImplementationImpl(context);
    }

    @Test
    public void testGetAttributes() {
        document = sampleHtmlFile();
        Document document = domImpl.createDocument(null, null, null);
        Element html = document.createElement("html");
        Element body = document.createElement("body");
        html.appendChild(body);
        NamedNodeMap attrs = body.getAttributes();
        assertNotNull(attrs);
        assertEquals(0, attrs.getLength());
        Attr idattr = document.createAttribute("id");
        idattr.setValue("bodyId");
        body.setAttributeNode(idattr);
        Node node = attrs.getNamedItem("id");
        assertNotNull(node);
        assertEquals(Node.ATTRIBUTE_NODE, node.getNodeType());
        assertEquals("id", node.getNodeName());
        assertEquals("bodyId", node.getNodeValue());
        assertNull(node.getNextSibling());
        assertNull(node.getParentNode());
        assertSame(idattr, node);
        assertEquals(1, attrs.getLength());
        assertSame(idattr, attrs.item(0));
        Attr attr = document.createAttribute("lang");
        attr.setValue("en");
        attrs.setNamedItem(attr);
        assertEquals(2, attrs.getLength());
        assertTrue(body.hasAttribute("lang"));
        assertEquals("en", body.getAttribute("lang"));
        assertSame(idattr.getNextSibling(), attr);
        assertSame(idattr, attr.getPreviousSibling());
        assertSame(idattr, attrs.getNamedItem("id"));
        assertSame(attr, attrs.getNamedItem("lang"));
        assertSame(attr, attrs.removeNamedItem("lang"));
        assertEquals(1, attrs.getLength());
        assertSame(idattr, attrs.getNamedItem("id"));
        assertSame(idattr, attrs.item(0));

        attrs.setNamedItem(attr);
        Attr xmlns = document.createAttributeNS(Document.XMLNS_NAMESPACE_URI, "xmlns");
        xmlns.setValue(Document.HTML_NAMESPACE_URI);
        attrs.setNamedItem(xmlns);
        assertEquals(3, attrs.getLength());
        assertTrue(body.hasAttribute("xmlns"));
        Attr version = document.createAttributeNS(Document.NAMESPACE_SVG, "version");
        version.setValue("1.1");
        attrs.setNamedItem(version);
        assertEquals(4, attrs.getLength());
        assertTrue(body.hasAttribute("version"));
        assertTrue(body.hasAttributeNS(Document.NAMESPACE_SVG, "version"));
        attrs.removeNamedItemNS(Document.NAMESPACE_SVG, "version");
        assertEquals(3, attrs.getLength());
        assertFalse(body.hasAttribute("version"));
        assertFalse(body.hasAttributeNS(Document.NAMESPACE_SVG, "version"));
    }

    @Test
    public void testGetAttributeNS() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        Attr idattr = document.createAttributeNS(Document.HTML_NAMESPACE_URI, "id");
        idattr.setValue("bodyId");
        body.setAttributeNode(idattr);
        assertNull(idattr.getParentNode());
        assertEquals("bodyId", body.getAttribute("id"));
        assertEquals("bodyId", body.getAttributeNode("id").getValue());
        assertEquals("bodyId", body.getAttributeNodeNS(Document.HTML_NAMESPACE_URI, "id").getValue());
        assertTrue(body.hasAttributeNS(Document.HTML_NAMESPACE_URI, "id"));
        assertFalse(body.hasAttributeNS(Document.HTML_NAMESPACE_URI, "foo"));
        assertNull(body.getAttribute("foo"));
        assertNull(body.getAttributeNode("foo"));
        assertNull(body.getAttributeNS(Document.NAMESPACE_SVG, "id"));
        assertNull(body.getAttributeNodeNS(Document.NAMESPACE_SVG, "id"));
        assertNotNull(idattr.getOwnerElement());
        html.appendChild(body);
        body.removeAttribute("foo");
        assertTrue(body.hasAttributes());

        boolean success = false;
        try {
            body.removeAttributeNS(Document.NAMESPACE_SVG, "id");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
        }

        assertTrue("throw_NOT_FOUND_ERR", success);
        assertTrue(body.hasAttribute("id"));
        idattr = body.removeAttributeNode(idattr);
        assertNull(idattr.getOwnerElement());
        assertFalse(body.hasAttributes());
        body.setAttributeNode(idattr);
        assertTrue(body.hasAttribute("id"));
        assertNotNull(idattr.getOwnerElement());
        body.removeAttributeNS(Document.HTML_NAMESPACE_URI, "id");
        assertFalse(body.hasAttribute("id"));
        assertFalse(body.hasAttributes());

        Element svg = document.createElementNS(Document.NAMESPACE_SVG, "svg");
        Attr version = document.createAttributeNS(Document.NAMESPACE_SVG, "version");
        version.setValue("1.1");
        assertEquals(Document.NAMESPACE_SVG, version.getNamespaceURI());
        svg.setAttributeNodeNS(version);
        assertNotNull(version.getOwnerElement());
        assertEquals("1.1", svg.getAttribute("version"));
        assertEquals("1.1", svg.getAttributeNode("version").getValue());
        assertEquals("1.1", svg.getAttributeNodeNS(Document.NAMESPACE_SVG, "version").getValue());
        assertNull(svg.getAttributeNS(Document.HTML_NAMESPACE_URI, "version"));
        assertNull(svg.getAttributeNodeNS(Document.HTML_NAMESPACE_URI, "version"));
        assertFalse(svg.hasAttributeNS(Document.HTML_NAMESPACE_URI, "version"));
        assertTrue(svg.hasAttributeNS(Document.NAMESPACE_SVG, "version"));

        success = false;
        try {
            svg.removeAttributeNS(Document.HTML_NAMESPACE_URI, "version");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
        }
        assertTrue("throw_NOT_FOUND_ERR", success);
        assertTrue(svg.hasAttributeNS(Document.NAMESPACE_SVG, "version"));
        svg.removeAttributeNS(Document.NAMESPACE_SVG, "version");
        assertFalse(svg.hasAttributeNS(Document.NAMESPACE_SVG, "version"));
        body.appendChild(svg);
        // xml:lang
        body.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en_UK");
        assertTrue(body.hasAttribute("xml:lang"));
        assertEquals("en_UK", body.getAttribute("xml:lang"));
        assertEquals("en_UK", body.getAttributeNS("http://www.w3.org/XML/1998/namespace", "lang"));
    }

    @Test
    public void testSetAttribute() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        assertFalse(body.hasAttributes());
        body.setAttribute("foo", "bar");
        assertTrue(body.hasAttributes());
        assertEquals("bar", body.getAttribute("foo"));
        Attr attr = body.getAttributeNode("foo");
        assertFalse(attr.isId());
        assertNull(attr.getSchemaTypeInfo().getTypeName());
        assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
        body.setAttribute("id", "bodyId");
        assertTrue(body.hasAttributes());
        assertEquals(2, body.getAttributes().getLength());
        assertEquals("bodyId", body.getAttribute("id"));
        attr = body.getAttributeNode("id");
        assertTrue(attr.isId());
        assertEquals("ID", attr.getSchemaTypeInfo().getTypeName());
        assertEquals("https://www.w3.org/TR/xml/", attr.getSchemaTypeInfo().getTypeNamespace());
        body.setAttribute("id", "newId");
        assertEquals("newId", body.getAttribute("id"));
        assertSame(attr, body.getAttributeNode("id"));
        assertEquals(2, body.getAttributes().getLength());
    }

    @Test
    public void testSetAttributeNS() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElementNS(Document.HTML_NAMESPACE_URI, "body");
        html.appendChild(body);
        assertFalse(body.hasAttributes());
        body.setAttributeNS(Document.HTML_NAMESPACE_URI, "foo", "bar");
        assertTrue(body.hasAttribute("foo"));
        assertTrue(body.hasAttributeNS(Document.HTML_NAMESPACE_URI, "foo"));
        assertEquals("bar", body.getAttributeNS(Document.HTML_NAMESPACE_URI, "foo"));
        assertEquals("bar", body.getAttribute("foo"));
        body.setAttributeNS(null, "foo", "foobar");
        assertTrue(body.hasAttribute("foo"));
        assertFalse(body.hasAttributeNS(Document.HTML_NAMESPACE_URI, "foo"));
        assertEquals("foobar", body.getAttributeNS(null, "foo"));
        assertEquals("foobar", body.getAttribute("foo"));
        body.setAttribute("foo", "bar");
        assertEquals("bar", body.getAttributeNS(null, "foo"));
        assertEquals("bar", body.getAttribute("foo"));
        Attr attr = body.getAttributeNode("foo");
        try {
            attr.setPrefix("pre");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
    }

    @Test
    public void testSetAttributeError() {
        document = sampleHtmlFile();
        Element p = document.createElement("p");
        try {
            p.setAttribute(null, "bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            p.setAttribute("foo=", "bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        //
        NamedNodeMap attrs = p.getAttributes();
        assertNull(attrs.getNamedItem(null));
    }

    @Test
    public void testSetAttributeNSError() {
        document = sampleHtmlFile();
        Element p = document.createElement("p");
        try {
            p.setAttributeNS(Document.HTML_NAMESPACE_URI, null, "bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        try {
            p.setAttributeNS(Document.HTML_NAMESPACE_URI, "foo=", "bar");
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        //
        NamedNodeMap attrs = p.getAttributes();
        assertNull(attrs.getNamedItemNS(null, null));
    }

    @Test
    public void testRemoveAttributeNotFound() {
        document = sampleHtmlFile();
        Element p = document.createElement("p");
        p.removeAttribute(null);
        p.removeAttribute("");
        p.removeAttribute("foo");
    }

    @Test
    public void testSetAttributeNode() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        Attr attr = document.createAttribute("id");
        attr.setValue("bodyId");
        assertFalse(attr.isId());
        body.setAttributeNode(attr);
        assertTrue(body.hasAttributes());
        assertTrue(attr.isId());
        assertNotNull(attr.getOwnerElement());
        assertEquals("bodyId", body.getAttribute("id"));
        assertEquals(1, body.getAttributes().getLength());
        assertEquals("bodyId", body.getId());
        assertNull(attr.lookupPrefix(Document.HTML_NAMESPACE_URI));
        assertEquals(Document.XML_NAMESPACE_URI, attr.lookupNamespaceURI("xml"));
        assertNull(attr.lookupNamespaceURI("foo"));
        // Set the attribute to itself
        body.setAttributeNode(attr);
        assertEquals(1, body.getAttributes().getLength());
        // Remove
        Attr rmattr = body.removeAttributeNode(attr);
        assertSame(rmattr, attr);
        assertFalse(body.hasAttributes());
        assertEquals(0, body.getAttributes().getLength());
        assertNull(attr.getOwnerElement());
        assertEquals("bodyId", attr.getValue());
        assertEquals("", body.getId());
        // Class attribute
        body.setAttribute("class", "fooclass");
        assertTrue(body.hasAttributes());
        assertEquals("fooclass", body.getAttribute("class"));
        assertFalse(body.getAttributeNode("class").isId());
        // Replace class attribute, first with another namespace
        attr = document.createAttributeNS("http://www.example.com/examplens", "e:class");
        attr.setValue("barclass");
        assertEquals("class", attr.getLocalName());
        assertEquals("http://www.example.com/examplens", attr.getNamespaceURI());
        body.setAttributeNodeNS(attr);
        assertEquals("e:class=barclass", attr.getName() + "=" + attr.getNodeValue());
        assertEquals("http://www.example.com/examplens", attr.lookupNamespaceURI("e"));
        assertEquals("e", attr.lookupPrefix("http://www.example.com/examplens"));
        assertNull(attr.lookupPrefix(null));
        assertEquals("fooclass", body.getAttribute("class"));
        attr = document.createAttribute("class");
        attr.setValue("barclass");
        body.setAttributeNode(attr);
        assertEquals("barclass", body.getAttribute("class"));
        Attr attr2 = body.getAttributeNode("class");
        assertSame(attr, attr2);
        assertNull(attr.getSchemaTypeInfo().getTypeName());
        // Attribute from another element
        try {
            html.setAttributeNode(attr);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INUSE_ATTRIBUTE_ERR, e.getCode());
        }
        try {
            html.setAttributeNodeNS(attr);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INUSE_ATTRIBUTE_ERR, e.getCode());
        }
        // Another document
        Document otherdoc = document.getImplementation().createDocument(null, null, null);
        Attr otherdocAttr = otherdoc.createAttribute("foo");
        try {
            body.setAttributeNode(otherdocAttr);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.WRONG_DOCUMENT_ERR, e.getCode());
        }
        try {
            body.setAttributeNodeNS(otherdocAttr);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.WRONG_DOCUMENT_ERR, e.getCode());
        }
    }

    @Test
    public void testSetAttributeNodeNS() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Attr xsi = document.createAttributeNS(Document.XMLNS_NAMESPACE_URI, "xmlns:xsi");
        xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
        assertFalse(xsi.isId());
        html.setAttributeNode(xsi);
        assertTrue(html.hasAttributes());
        assertNull(xsi.getParentNode());
        assertNotNull(xsi.getOwnerElement());
        Attr attr = document.createAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation");
        attr.setValue("http://www.w3.org/1999/xhtml https://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd");
        html.setAttributeNode(attr);
        assertTrue(html.hasAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertTrue(html.hasAttribute("xsi:schemaLocation"));
        assertNotNull(html.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertNotNull(html.getAttributeNode("xsi:schemaLocation"));
        assertEquals("http://www.w3.org/1999/xhtml https://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd",
                html.getAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertEquals("http://www.w3.org/1999/xhtml https://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd",
                html.getAttribute("xsi:schemaLocation"));
        assertEquals(2, html.getAttributes().getLength());
        assertEquals("http://www.w3.org/2001/XMLSchema-instance", attr.lookupNamespaceURI("xsi"));
        assertEquals("xsi", attr.lookupPrefix("http://www.w3.org/2001/XMLSchema-instance"));
        attr.setPrefix("sch");
        assertTrue(html.hasAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertTrue(html.hasAttribute("sch:schemaLocation"));
        assertNotNull(html.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertNotNull(html.getAttributeNode("sch:schemaLocation"));
        assertEquals("http://www.w3.org/1999/xhtml https://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd",
                html.getAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation"));
        assertEquals("http://www.w3.org/1999/xhtml https://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd",
                html.getAttribute("sch:schemaLocation"));
    }

    @Test
    public void testSetAttributeNodeNSXML() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Attr xml = document.createAttributeNS(Document.XML_NAMESPACE_URI, "xml:lang");
        xml.setValue("en");
        assertEquals(Document.XML_NAMESPACE_URI, xml.getNamespaceURI());
        assertEquals("lang", xml.getLocalName());
        assertEquals("xml:lang", xml.getName());
        assertFalse(xml.isId());
        html.setAttributeNode(xml);
        assertTrue(html.hasAttributes());
        assertNull(xml.getParentNode());
        assertNotNull(xml.getOwnerElement());
        assertTrue(html.hasAttributeNS(Document.XML_NAMESPACE_URI, "lang"));
        Attr attr = document.createAttribute("lang");
        attr.setValue("en");
        html.setAttributeNode(attr);
        assertTrue(html.hasAttributeNS(Document.XML_NAMESPACE_URI, "lang"));
        assertTrue(html.hasAttributeNS(null, "lang"));
        assertTrue(html.hasAttribute("lang"));
        assertSame(attr, html.getAttributeNode("lang"));
        assertEquals(2, html.getAttributes().getLength());
        // Recognize xml:id as an implicit 'id' attribute.
        Attr idattr = document.createAttributeNS(Document.XML_NAMESPACE_URI, "xml:id");
        idattr.setValue("foo");
        html.setAttributeNode(idattr);
        assertTrue(idattr.isId());
        assertTrue(html.hasAttributeNS(Document.XML_NAMESPACE_URI, "id"));
        assertSame(idattr, html.getAttributeNodeNS(Document.XML_NAMESPACE_URI, "id"));
        assertEquals("foo", html.getId());
        assertEquals(3, html.getAttributes().getLength());
        assertSame(idattr, html.removeAttributeNode(idattr));
        assertEquals("", html.getId());
        assertEquals(2, html.getAttributes().getLength());
        //
        try {
            document.createAttributeNS("http://www.example.com/ns", "xml:foo");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.NAMESPACE_ERR, e.getCode());
        }
    }

    @Test
    public void testSetAttributeNodeClass() {
        document = sampleHtmlFile();
        HTMLCollection fooelms = document.getElementsByClassName("foo");
        assertEquals(0, fooelms.getLength());
        Element body = document.createElement("body");
        document.getDocumentElement().appendChild(body);
        Attr attr = document.createAttribute("class");
        attr.setValue("foo bar");
        assertEquals("class", attr.getName());
        assertEquals("foo bar", attr.getValue());
        body.setAttributeNode(attr);
        assertTrue(body.hasAttribute("class"));
        assertNull(attr.getParentNode());
        assertSame(body, attr.getOwnerElement());
        assertEquals("foo bar", attr.getValue());
        assertEquals(1, fooelms.getLength());
        assertEquals(fooelms.toString(), document.getElementsByClassName("foo").toString());
        assertSame(body, fooelms.item(0));
        HTMLCollection barelms = document.getElementsByClassName("bar");
        assertEquals(1, barelms.getLength());
        HTMLCollection foobarelms = document.getElementsByClassName("bar foo");
        assertEquals(1, foobarelms.getLength());
        assertSame(body, foobarelms.item(0));
        body.getClassList().remove("bar");
        assertEquals(0, barelms.getLength());
        assertEquals(0, foobarelms.getLength());
        body.getClassList().toggle("bar");
        assertEquals(1, barelms.getLength());
        body.removeAttribute("class");
        assertNull(attr.getOwnerElement());
        assertEquals("foo bar", attr.getValue());
        assertEquals(0, fooelms.getLength());
        assertEquals(0, barelms.getLength());
    }

    @Test
    public void testGetClassList() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        DOMTokenList list = body.getClassList();
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertFalse(list.contains("foo"));
        assertFalse(body.hasAttribute("class"));
        Attr attr = document.createAttribute("class");
        attr.setValue("foo");
        body.setAttributeNode(attr);
        assertEquals(1, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("foo", list.getValue());
        assertTrue(list.contains("foo"));
        attr.setValue("foo bar");
        assertEquals(2, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("foo bar", list.getValue());
        assertTrue(list.contains("foo"));
        list.add("000");
        assertEquals(3, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("000", list.item(2));
        assertEquals("foo bar 000", list.getValue());
        assertEquals("foo bar 000", attr.getValue());
        assertTrue(list.contains("foo"));
        list.toggle("111");
        assertEquals(4, list.getLength());
        list.toggle("111");
        assertEquals(3, list.getLength());
        list.replace("000", "111");
        assertEquals("foo bar 111", list.getValue());
        list.remove("111");
        assertEquals(2, list.getLength());
        assertEquals("foo bar", list.getValue());
        body.removeAttribute("class");
        assertEquals(0, list.getLength());
        assertEquals("foo bar", attr.getValue());
    }

    @Test
    public void testGetClassListQuirks() {
        document = sampleHtmlFile();
        document.removeChild(document.getDoctype());
        Element body = document.createElement("body");
        DOMTokenList list = body.getClassList();
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertFalse(list.contains("foo"));
        assertFalse(body.hasAttribute("class"));
        Attr attr = document.createAttribute("class");
        attr.setValue("foo");
        body.setAttributeNode(attr);
        assertEquals(1, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("foo", list.getValue());
        assertTrue(list.contains("foo"));
        attr.setValue("Foo bar");
        assertEquals(2, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("foo bar", list.getValue());
        assertTrue(list.contains("foo"));
        assertTrue(list.contains("Foo"));
        list.add("000");
        assertEquals(3, list.getLength());
        assertEquals("foo", list.item(0));
        assertEquals("000", list.item(2));
        assertEquals("foo bar 000", list.getValue());
        assertEquals("foo bar 000", attr.getValue());
        list.toggle("111");
        assertEquals(4, list.getLength());
        list.toggle("111");
        assertEquals(3, list.getLength());
        list.replace("000", "111");
        assertEquals("foo bar 111", list.getValue());
        list.remove("111");
        assertEquals(2, list.getLength());
        assertEquals("foo bar", list.getValue());
        body.removeAttribute("class");
        assertEquals(0, list.getLength());
        assertEquals("foo bar", attr.getValue());
    }

    @Test
    public void testGetClassList2() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        DOMTokenList list = body.getClassList();
        assertNotNull(list);
        assertEquals(0, list.getLength());
        list.add("foo");
        assertTrue(body.hasAttribute("class"));
        assertEquals("foo", body.getAttribute("class"));
        list.add("foo");
        assertEquals("foo", body.getAttribute("class"));
        list.add("bar");
        assertEquals("foo bar", body.getAttribute("class"));
        assertEquals("[object HTMLBodyElement]", body.toString());
        try {
            list.add(null);
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.SYNTAX_ERR, e.getCode());
        }
        try {
            list.add("");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.SYNTAX_ERR, e.getCode());
        }
        try {
            list.add("foo bar");
            fail("Must throw exception");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        list.replace("bar", "faa");
        assertEquals("foo faa", body.getAttribute("class"));
        list.replace("faa", "foo");
        assertEquals("foo", body.getAttribute("class"));
        body.setClassName("bar");
        assertEquals("bar", body.getAttribute("class"));
        assertEquals("bar", list.getValue());
        assertEquals("[object HTMLBodyElement]", body.toString());
    }

    @Test
    public void matchesStringString() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        document.getDocumentElement().appendChild(body);
        body.setAttribute("id", "bodyId");
        Element div1 = document.createElement("div");
        Element div2 = document.createElement("div");
        body.appendChild(div1);
        body.appendChild(div2);
        assertFalse(body.matches(".foo"));
        DOMTokenList list = body.getClassList();
        assertNotNull(list);
        assertEquals(0, list.getLength());
        Attr attr = document.createAttribute("class");
        attr.setValue("foo");
        body.setAttributeNode(attr);
        assertTrue(body.matches(".foo"));
        assertTrue(body.matches("#bodyId"));
        attr.setValue("foo bar");
        assertTrue(body.matches(".bar"));
        assertTrue(div1.matches(".bar div"));
        assertTrue(div1.matches("body > div"));
        assertTrue(div1.matches("div:first-child"));
        assertFalse(div2.matches("div:first-child"));
        assertTrue(div2.matches("div:last-child"));
        assertTrue(div1.matches("div:first-line"));
        body.removeAttribute("class");
        assertFalse(body.matches(".bar"));
    }

    @Test
    public void matchesStringStringQuirks() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        document.getDocumentElement().appendChild(body);
        body.setAttribute("id", "bodyId");
        Element div1 = document.createElement("div");
        Element div2 = document.createElement("div");
        body.appendChild(div1);
        body.appendChild(div2);
        assertFalse(body.matches(".foo"));
        DOMTokenList list = body.getClassList();
        assertNotNull(list);
        assertEquals(0, list.getLength());
        Attr attr = document.createAttribute("class");
        attr.setValue("foo");
        body.setAttributeNode(attr);
        assertTrue(body.matches(".foo"));
        assertTrue(body.matches(".foo"));
        assertTrue(body.matches("#bodyId"));
        attr.setValue("foo bar");
        assertTrue(body.matches(".bar"));
        assertTrue(div1.matches(".bar div"));
        assertTrue(div1.matches(".Bar div"));
        assertTrue(div1.matches("body > div"));
        assertTrue(div1.matches("div:first-child"));
        assertFalse(div2.matches("div:first-child"));
        assertTrue(div2.matches("div:last-child"));
        assertTrue(div1.matches("div:first-line"));
        body.removeAttribute("class");
        assertFalse(body.matches(".bar"));
    }

    @Test
    public void matchesStringString2() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        document.getDocumentElement().appendChild(body);
        body.setAttribute("id", "bodyId");
        Element div1 = document.createElement("div");
        Element div2 = document.createElement("div");
        body.appendChild(div1);
        body.appendChild(div2);
        assertTrue(div2.matches("div:last-child"));
        assertFalse(div1.matches("div:last-child"));
        assertFalse(body.matches("div:last-child"));
        NodeList elements = document.querySelectorAll("div:last-child");
        assertNotNull(elements);
        assertEquals(1, elements.getLength());
        assertSame(div2, elements.item(0));
        elements = document.querySelectorAll("div:first-child");
        assertNotNull(elements);
        assertEquals(1, elements.getLength());
        assertSame(div1, elements.item(0));
        elements = document.querySelectorAll("#nosuchID");
        assertNotNull(elements);
        assertEquals(0, elements.getLength());
    }

    @Test
    public void testGetStyle() {
        document = sampleHtmlFile();
        HTMLElementImpl body = (HTMLElementImpl) document.createElement("body");
        assertNotNull(body.getStyle());
        assertFalse(body.hasAttributes());
        document.getDocumentElement().appendChild(body);
        body.setAttribute("style", "font-family: Arial");
        assertTrue(body.hasAttributes());
        assertTrue(body.hasAttribute("style"));
        assertEquals("font-family: Arial", body.getAttribute("style"));
        CSSStyleDeclaration style = body.getStyle();
        assertNotNull(style);
        assertEquals("font-family: arial", style.getCssText());
        style.setCssText("font-family: Helvetica");
        assertEquals("font-family: Helvetica", style.getCssText());
        assertEquals("font-family: Helvetica", body.getAttribute("style"));
        body.removeAttribute("style");
        body.setAttribute("style", "font-family");
    }

    @Test
    public void testCreateElement() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        assertNull(body.getNamespaceURI());
        Element svg = document.createElementNS(Document.NAMESPACE_SVG, "svg");
        body.appendChild(svg);
        assertEquals(Document.NAMESPACE_SVG, svg.getNamespaceURI());
        Element p = document.createElementNS(null, "p");
        body.appendChild(p);
        assertNull(p.getNamespaceURI());
    }

    @Test
    public void testGetChildren() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        body.appendChild(document.createTextNode("\n   \n"));
        Element div1 = document.createElement("div");
        body.appendChild(div1);
        body.appendChild(document.createTextNode("\n   \n"));
        Element div2 = document.createElement("div");
        body.appendChild(div2);
        body.appendChild(document.createTextNode("\n   \n"));
        Element div3 = document.createElement("div");
        body.appendChild(div3);
        body.appendChild(document.createTextNode("\n   \n"));
        body.appendChild(document.createComment("This is a comment"));
        Element div4 = document.createElement("div");
        body.appendChild(div4);
        body.appendChild(document.createTextNode("\n   \n"));
        body.appendChild(document.createTextNode("\n   \n"));
        HTMLCollectionImpl list = (HTMLCollectionImpl) body.getChildren();
        assertNotNull(list);
        assertEquals(4, list.getLength());
        assertSame(div1, list.item(0));
        assertSame(div2, list.item(1));
        assertSame(div3, list.item(2));
        assertSame(div4, list.item(3));
        assertNull(list.item(4));
        assertSame(div1, body.getFirstElementChild());
        assertSame(div4, body.getLastElementChild());
        assertEquals(list.getLength(), body.getChildElementCount());
        assertSame(list, body.getChildren());
        assertFalse(list.isEmpty());
        //
        list = (HTMLCollectionImpl) document.getChildren();
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertSame(html, list.item(0));
        assertEquals(1, document.getChildElementCount());
        assertFalse(list.isEmpty());
        //
        list = (HTMLCollectionImpl) html.getChildren();
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertSame(body, list.item(0));
        assertNull(list.item(1));
        assertNull(list.item(-1));
        assertEquals(1, html.getChildElementCount());
        assertSame(body, html.getFirstElementChild());
        assertSame(body, html.getLastElementChild());
        assertSame(list, html.getChildren());
        assertFalse(list.isEmpty());
        //
        list = (HTMLCollectionImpl) div4.getChildren();
        assertTrue(list.isEmpty());
        div4.appendChild(document.createTextNode(" "));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetChildNodes() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        Text first = document.createTextNode("\n   \n");
        body.appendChild(first);
        Element div1 = document.createElement("div");
        body.appendChild(div1);
        body.appendChild(document.createTextNode("\n   \n"));
        Element div2 = document.createElement("div");
        body.appendChild(div2);
        body.appendChild(document.createTextNode("\n   \n"));
        Element div3 = document.createElement("div");
        body.appendChild(div3);
        body.appendChild(document.createTextNode("\n   \n"));
        body.appendChild(document.createComment("This is a comment"));
        Element div4 = document.createElement("div");
        body.appendChild(div4);
        body.appendChild(document.createTextNode("\n   \n"));
        Text last = document.createTextNode("\n   \n");
        body.appendChild(last);
        NodeListImpl list = (NodeListImpl) body.getChildNodes();
        assertNotNull(list);
        assertEquals(11, list.getLength());
        assertSame(first, list.item(0));
        assertSame(div1, list.item(1));
        assertSame(div2, list.item(3));
        assertSame(div3, list.item(5));
        assertSame(div4, list.item(8));
        assertSame(last, list.item(10));
        assertNull(list.item(11));
        assertNull(list.item(-1));
        assertSame(first, body.getFirstChild());
        assertSame(last, body.getLastChild());
        assertSame(list, body.getChildNodes());
        assertTrue(list.contains(div4));
        assertFalse(list.contains(html));
        assertFalse(list.isEmpty());

        list = (NodeListImpl) document.getChildNodes();
        assertNotNull(list);

        assertEquals(2, list.getLength());
        assertSame(html, list.item(1));
        assertSame(document.getDoctype(), document.getFirstChild());
        assertFalse(list.contains(div4));
        assertTrue(list.contains(html));
        assertFalse(list.isEmpty());
        list = (NodeListImpl) html.getChildNodes();
        assertNotNull(list);
        assertEquals(3, list.getLength());
        assertSame(body, list.item(2));
        assertNotNull(list.item(1));
        assertNull(list.item(-1));
        assertSame(document.getHead(), html.getFirstChild());
        assertSame(body, html.getLastChild());
        assertSame(list, html.getChildNodes());
        assertTrue(list.contains(body));
        assertFalse(list.contains(div4));
        assertFalse(list.isEmpty());

        list = (NodeListImpl) div4.getChildNodes();
        assertTrue(list.isEmpty());
        div4.appendChild(document.createTextNode(" "));
        assertFalse(list.isEmpty());
    }

    @Test
    public void testGetTextContent() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        body.appendChild(document.createTextNode("    "));
        Element div = document.createElement("div");
        body.appendChild(div);
        div.appendChild(document.createTextNode("   "));
        Element span1 = document.createElement("span");
        span1.appendChild(document.createTextNode("span 1"));
        div.appendChild(span1);
        div.appendChild(document.createTextNode("   "));
        Element span2 = document.createElement("span");
        span2.appendChild(document.createTextNode("span 2"));
        div.appendChild(span2);
        body.appendChild(document.createTextNode("   "));
        Element span3 = document.createElement("span");
        span3.appendChild(document.createTextNode("span 3"));
        body.appendChild(span3);
        body.appendChild(document.createTextNode("     "));
        body.appendChild(document.createComment("This is a comment"));
        Element span4 = document.createElement("span");
        span4.appendChild(document.createTextNode("span 4"));
        body.appendChild(span4);
        body.appendChild(document.createTextNode("   "));
        //
        assertEquals("       span 1   span 2   span 3     span 4   ", body.getTextContent());
    }

    @Test
    public void testGetInnerText() {
        document = sampleHtmlFile();
        ElementImpl html = (ElementImpl) document.getDocumentElement();
        ElementImpl body = (ElementImpl) document.createElement("body");
        html.appendChild(body);
        body.appendChild(document.createTextNode("    "));
        Element div = document.createElement("div");
        body.appendChild(div);
        div.appendChild(document.createTextNode("   "));
        Element span1 = document.createElement("span");
        span1.appendChild(document.createTextNode(" span   1 "));
        div.appendChild(span1);
        div.appendChild(document.createTextNode("   "));
        Element span2 = document.createElement("span");
        span2.appendChild(document.createTextNode(" span     2 "));
        span2.setAttribute("style", "text-transform: capitalize");
        div.appendChild(span2);
        // pre
        Element pre = document.createElement("pre");
        pre.appendChild(document.createTextNode("  white  space   must   be\n   preserved   "));
        div.appendChild(pre);
        //
        body.appendChild(document.createTextNode("   "));
        Element span3 = document.createElement("span");
        span3.appendChild(document.createTextNode(" span 3"));
        body.appendChild(span3);
        body.appendChild(document.createTextNode("     "));
        body.appendChild(document.createComment("This is a comment"));
        Element span4 = document.createElement("span");
        span4.appendChild(document.createTextNode(" span \n 4 "));
        span4.setAttribute("style", "white-space: pre-line; text-transform: uppercase");
        body.appendChild(span4);
        body.appendChild(document.createTextNode("   "));
        //
        assertEquals(" span 1 Span 2 \n  white  space   must   be\n   preserved   \n\nspan 3 SPAN\n4\n",
                body.getInnerText());
    }

    @Test
    public void testCloneNode() {
        document = sampleHtmlFile();
        Element html = document.getDocumentElement();
        Element body = document.createElement("body");
        html.appendChild(body);
        Attr attr = document.createAttribute("id");
        attr.setValue("bodyId");
        body.setAttributeNode(attr);
        body.setAttribute("class", "fooclass");
        Element div = document.createElement("div");
        body.appendChild(div);
        div.appendChild(document.createTextNode("foo"));
        Element elm = (Element) body.cloneNode(false);
        assertEquals(body.getNodeName(), elm.getNodeName());
        assertEquals(body.getAttributes(), elm.getAttributes());
        Attr cloneId = elm.getAttributeNode("id");
        assertTrue(cloneId.isId());
        assertFalse(body.isEqualNode(elm));
        assertTrue(cloneId.isId());
        elm = (Element) body.cloneNode(true);
        assertTrue(body.isEqualNode(elm));
        assertEquals("div", elm.getChildNodes().item(0).getNodeName());
        assertEquals("foo", elm.getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
    }

    @Test
    public void testGetElementById() {
        document = sampleHtmlFile();
        Element body = document.createElement("body");
        document.getDocumentElement().appendChild(body);
        Attr attr = document.createAttribute("id");
        attr.setValue("myId");
        Attr fooAttr = document.createAttribute("foo");
        fooAttr.setValue("bar");
        body.setAttributeNode(attr);
        body.setAttributeNode(fooAttr);
        assertSame(body, document.getElementById("myId"));
        body.setIdAttributeNode(fooAttr, true); // Does not work, is ignored
        assertNotSame(body, document.getElementById("bar"));
        // test for xml:id
        body.removeAttribute("id");
        attr = document.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:id");
        attr.setValue("xmlId");
        body.setAttributeNode(attr);
        assertTrue(attr.isId());
        assertEquals("xmlId", body.getAttributeNS("http://www.w3.org/XML/1998/namespace", "id"));
        assertSame(body, document.getElementById("xmlId"));
    }

    @Test
    public void getElementsByTagName() {
        document = sampleHtmlFile();
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
        HTMLCollectionImpl list = (HTMLCollectionImpl) docElm.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(5, list.getLength());
        assertNull(list.item(-1));
        assertTrue(elem1.isEqualNode(list.item(0)));
        assertTrue(elem2.isEqualNode(list.item(1)));
        assertTrue(elem3.isEqualNode(list.item(2)));
        assertTrue(elem4.isEqualNode(list.item(3)));
        assertNotNull(list.item(4));

        list = (HTMLCollectionImpl) elem1.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(2, list.getLength());
        assertNull(list.item(-1));
        assertTrue(elem2.isEqualNode(list.item(0)));
        assertTrue(elem3.isEqualNode(list.item(1)));
        assertNull(list.item(2));
        //
        list = (HTMLCollectionImpl) elem2.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(1, list.getLength());
        assertNull(list.item(-1));
        assertSame(elem3, list.item(0));
        assertNull(list.item(1));
        //
        list = (HTMLCollectionImpl) elem4.getElementsByTagName("div");
        assertNotNull(list);
        assertEquals(0, list.getLength());
        assertNull(list.item(-1));
        assertNull(list.item(0));
        Element foo = document.createElementNS(Document.NAMESPACE_SVG, "s:svg");
        elem4.appendChild(foo);
        list = (HTMLCollectionImpl) elem4.getElementsByTagName("svg");
        assertNotNull(list);
        assertTrue(list.isEmpty());
        //
        list = (HTMLCollectionImpl) elem4.getElementsByTagName("s:svg");
        assertNotNull(list);
        assertEquals(1, list.getLength());
    }

    @Test
    public void testGetTagName() {
        document = sampleHtmlFile();
        Element elm = document.createElement("p");
        assertEquals("p", elm.getTagName());
        elm = document.createElementNS("http://www.example.com/examplens", "e:p");
        assertEquals("p", elm.getLocalName());
        assertEquals("e:p", elm.getTagName());
    }
}
