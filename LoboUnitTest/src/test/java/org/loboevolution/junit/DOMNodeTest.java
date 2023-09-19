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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;

public class DOMNodeTest extends LoboUnitTest {

	private static Document document;
	private static DOMImplementationImpl impl;

	@BeforeClass
	public static void setUpBeforeClass() {
		UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
		context.setUserAgentEnabled(false);
		impl = new DOMImplementationImpl(context);
	}

	@Test
	public void compareDocumentPosition1() {
		document = sampleHtmlFile();
		Node html = document.getDocumentElement();
		Element div1 = document.createElement("div");
		Element div2 = document.createElement("div");
		html.appendChild(div1);
		html.appendChild(div2);
		assertEquals(Node.DOCUMENT_POSITION_FOLLOWING, div1.compareDocumentPosition(div2));
		assertEquals(Node.DOCUMENT_POSITION_PRECEDING, div2.compareDocumentPosition(div1));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINED_BY + Node.DOCUMENT_POSITION_FOLLOWING, html.compareDocumentPosition(div1));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINED_BY + Node.DOCUMENT_POSITION_FOLLOWING, html.compareDocumentPosition(div2));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINS + Node.DOCUMENT_POSITION_PRECEDING, div1.compareDocumentPosition(html));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINS + Node.DOCUMENT_POSITION_PRECEDING, div2.compareDocumentPosition(html));
		assertEquals(0, html.compareDocumentPosition(html));
		assertEquals(0, div1.compareDocumentPosition(div1));
		Element p = document.createElement("p");
		div1.appendChild(p);
		assertEquals(Node.DOCUMENT_POSITION_CONTAINED_BY + Node.DOCUMENT_POSITION_FOLLOWING, div1.compareDocumentPosition(p));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINS + Node.DOCUMENT_POSITION_PRECEDING, p.compareDocumentPosition(div1));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINED_BY + Node.DOCUMENT_POSITION_FOLLOWING, html.compareDocumentPosition(p));
		assertEquals(Node.DOCUMENT_POSITION_CONTAINS + Node.DOCUMENT_POSITION_PRECEDING, p.compareDocumentPosition(html));
		assertEquals(Node.DOCUMENT_POSITION_PRECEDING, p.compareDocumentPosition(div2));
		assertEquals(Node.DOCUMENT_POSITION_FOLLOWING, div2.compareDocumentPosition(p));
		Document otherdoc = impl.createDocument(null, "html", null);
		Element otherDocElm = otherdoc.getDocumentElement();
		assertEquals(Node.DOCUMENT_POSITION_DISCONNECTED, div2.compareDocumentPosition(otherDocElm));
		assertEquals(Node.DOCUMENT_POSITION_DISCONNECTED, otherDocElm.compareDocumentPosition(div2));
	}

	@Test
	public void normalize() {
		document = sampleHtmlFile();
		assertEquals(2, document.getChildNodes().getLength());
		Element elm = document.createElement("body");
		Element html = document.getDocumentElement();
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(document.createTextNode("\t     "));
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(elm);
		Text foo = document.createTextNode("foo");
		Text bar = document.createTextNode("bar");
		elm.appendChild(document.createTextNode(" \t  "));
		elm.appendChild(foo);
		elm.appendChild(document.createTextNode("     "));
		elm.appendChild(bar);
		elm.appendChild(document.createTextNode("\n   "));
		elm.appendChild(document.createTextNode("   "));
		assertEquals(6, elm.getChildNodes().getLength());
		elm.normalize();
		System.out.println(elm.getChildNodes().item(0).getNodeValue());
		assertEquals(1, elm.getChildNodes().getLength());

		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(document.createTextNode("\t     "));
		html.appendChild(document.createTextNode("\n     "));

		assertEquals(1, html.getChildNodes().getLength());
		assertEquals(1, elm.getChildNodes().getLength());
	}

	@Test
	public void normalize2() {
		document = sampleHtmlFile();
		assertEquals(2, document.getChildNodes().getLength());
		Element elm = document.createElement("body");
		Element html = document.getDocumentElement();
		html.appendChild(document.createComment(" Comment "));
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(document.createTextNode("\t     "));
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(elm);
		Comment comment = document.createComment("Hi");
		Text foo = document.createTextNode("foo");
		Text bar = document.createTextNode("bar \u212b");
		elm.appendChild(comment);
		elm.appendChild(document.createTextNode(" \t  "));
		elm.appendChild(foo);
		elm.appendChild(document.createTextNode("     "));
		elm.appendChild(document.createTextNode("     "));
		elm.appendChild(bar);
		elm.appendChild(document.createTextNode("\n   "));
		Comment comment2 = document.createComment("Hi");
		elm.appendChild(comment2);
		assertEquals(8, elm.getChildNodes().getLength());
		elm.normalize();
		assertEquals(4, elm.getChildNodes().getLength());
		//
		DOMConfiguration config = document.getDomConfig();
		assertTrue(config.canSetParameter("normalize-characters", false));
		assertTrue(config.canSetParameter("normalize-characters", true));
		assertFalse((Boolean) config.getParameter("normalize-characters"));
		config.setParameter("normalize-characters", true);
		assertTrue((Boolean) config.getParameter("normalize-characters"));
		elm.normalize();
		assertEquals(4, elm.getChildNodes().getLength());
		assertEquals(" foo bar \u00c5 ", elm.getChildNodes().item(1).getNodeValue());
		//
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(document.createTextNode("\t     "));
		html.appendChild(document.createTextNode("\n     "));
		html.appendChild(document.createComment(" Middle comment "));
		html.appendChild(document.createTextNode(""));
		html.appendChild(document.createComment(" End comment "));
		assertEquals(11, html.getChildNodes().getLength());
		document.normalizeDocument();
		assertEquals(6, html.getChildNodes().getLength());
		assertEquals(4, elm.getChildNodes().getLength());
		assertEquals(" ", html.getChildNodes().item(3).getNodeValue());
		assertEquals(Node.COMMENT_NODE, html.getChildNodes().item(4).getNodeType());
		assertEquals(Node.COMMENT_NODE, html.getChildNodes().item(5).getNodeType());
		// Use computed styles (same result, just slower)
		assertTrue(config.canSetParameter("use-computed-styles", false));
		assertTrue(config.canSetParameter("use-computed-styles", true));
		assertFalse((boolean) config.getParameter("use-computed-styles"));
		config.setParameter("use-computed-styles", true);
		assertTrue((boolean) config.getParameter("use-computed-styles"));
		document.normalizeDocument();
		assertEquals(6, html.getChildNodes().getLength());
		assertEquals(4, elm.getChildNodes().getLength());
		// Remove comment nodes
		assertTrue(config.canSetParameter("comments", false));
		assertTrue(config.canSetParameter("comments", true));
		assertTrue((boolean) config.getParameter("comments"));
		config.setParameter("comments", false);
		document.normalizeDocument();
		assertEquals(4, html.getChildNodes().getLength());
		assertEquals(1, elm.getChildNodes().getLength());
		// Other canSetParameter cases.
		assertTrue(config.canSetParameter("css-whitespace-processing", false));
		assertTrue(config.canSetParameter("css-whitespace-processing", true));
		assertTrue((boolean) config.getParameter("css-whitespace-processing"));
		//
		assertTrue(config.canSetParameter("canonical-form", false));
		assertTrue(config.canSetParameter("check-character-normalization", false));
		assertTrue(config.canSetParameter("datatype-normalization", false));
		assertTrue(config.canSetParameter("validate", false));
		assertTrue(config.canSetParameter("validate-if-schema", false));
		assertTrue(config.canSetParameter("element-content-whitespace", true));
		assertTrue(config.canSetParameter("cdata-sections", true));
		assertTrue(config.canSetParameter("namespaces", true));
		assertFalse(config.canSetParameter("namespaces", false));
		assertTrue(config.canSetParameter("namespace-declarations", true));
		assertFalse(config.canSetParameter("namespace-declarations", false));
		assertTrue(config.canSetParameter("well-formed", true));
		assertTrue(config.canSetParameter("well-formed", false));
		assertFalse(config.canSetParameter("split-cdata-sections", true));
		assertFalse(config.canSetParameter("split-cdata-sections", false));
		//
		DOMStringList names = config.getParameterNames();
		assertEquals(4, names.getLength());
		assertTrue(names.contains("css-whitespace-processing"));
		assertTrue(names.contains("normalize-characters"));
	}

	@Test
	public void isSameNode() {
		document = sampleHtmlFile();
		Element elm1 = document.createElement("p");
		elm1.setAttribute("foo", "bar");
		Text foo1 = document.createTextNode("foo");
		elm1.appendChild(foo1);
		assertTrue(elm1.isSameNode(elm1));
		assertFalse(elm1.isSameNode(foo1));
	}

	@Test
	public void isEqualNode() {
		document = sampleHtmlFile();
		Element elm1 = document.createElement("p");
		Element elm2 = document.createElement("p");
		assertTrue(elm1.isEqualNode(elm2));
		elm1.setAttribute("foo", "bar");
		assertFalse(elm1.isEqualNode(elm2));
		elm2.setAttribute("foo", "foo");
		assertFalse(elm1.isEqualNode(elm2));
		elm2.setAttribute("foo", "bar");
		assertTrue(elm1.isEqualNode(elm2));
		elm2.removeAttribute("foo");
		Text foo1 = document.createTextNode("foo");
		elm1.appendChild(foo1);
		assertFalse(elm1.isEqualNode(elm2));
		elm2.setAttribute("foo", "bar");
		assertFalse(elm1.isEqualNode(elm2));
		Text foo2 = document.createTextNode("foo");
		elm2.appendChild(foo2);
		assertTrue(elm1.isEqualNode(elm2));
		Text bar = document.createTextNode("bar");
		elm2.replaceChild(bar, foo2);
		assertFalse(elm1.isEqualNode(elm2));
	}

	@Test
	public void getNodeValue() {
		document = sampleHtmlFile();
		Element elm = document.createElement("p");
		assertEquals("p", elm.getNodeName());
		assertNull(elm.getNodeValue());
		Text text = document.createTextNode("foo");
		assertEquals("#text", text.getNodeName());
		assertEquals("foo", text.getNodeValue());
		text.setNodeValue("bar");
		assertEquals("bar", text.getNodeValue());
		Attr attr = document.createAttribute("id");
		attr.setValue("fooid");
		assertEquals("id", attr.getNodeName());
		assertEquals("fooid", attr.getNodeValue());
		attr.setNodeValue("barid");
		assertEquals("barid", attr.getNodeValue());
		CDATASection cdata = document.createCDATASection("var j = 1");
		assertEquals("#cdata-section", cdata.getNodeName());
		assertEquals("var j = 1", cdata.getNodeValue());
		cdata.setNodeValue("foo");
		assertEquals("foo", cdata.getNodeValue());
		Comment comment = document.createComment("***");
		assertEquals("#comment", comment.getNodeName());
		assertEquals("***", comment.getNodeValue());
		comment.setNodeValue("comment");
		assertEquals("comment", comment.getNodeValue());
		DocumentFragment fragment = document.createDocumentFragment();
		assertEquals("[object DocumentFragment]", fragment.getNodeName());
		assertNull(fragment.getNodeValue());
		assertEquals("[object HTMLDocument]", document.getNodeName());
		assertNull(document.getNodeValue());
		ProcessingInstruction pi = document.createProcessingInstruction("xml-stylesheet",
				"type=\"text/css\" href=\"sheet.css\"");
		assertEquals("xml-stylesheet", pi.getNodeName());
		assertEquals("type=\"text/css\" href=\"sheet.css\"", pi.getNodeValue());
		DocumentType dt = impl.createDocumentType("xhtml", "-//W3C//DTD XHTML 1.1//EN", "w3c/xhtml11.dtd");
		assertEquals("xhtml", dt.getNodeName());
		assertNull(dt.getNodeValue());
	}

	@Test
	public void getPreviousSibling() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm1 = document.createElement("div");
		html.appendChild(elm1);
		assertSame(html, elm1.getParentNode());
		Element elm2 = document.createElement("p");
		html.appendChild(elm2);
		assertSame(html, elm2.getParentNode());
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertNotNull(elm1.getPreviousSibling());
		assertNull(elm2.getNextSibling());
	}

	@Test
	public void getFirstChild() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm1 = document.createElement("div");
		html.appendChild(elm1);
		Element elm2 = document.createElement("p");
		html.appendChild(elm2);
		assertSame(document.getHead(), html.getFirstChild());
		assertSame(elm2, html.getLastChild());
		assertNull(elm1.getFirstChild());
		assertNull(elm1.getLastChild());
	}

	@Test
	public void testDocumentFragment() throws DOMException {
		document = sampleHtmlFile();
		DocumentFragment fragment = document.createDocumentFragment();
		Element div = document.createElement("div");
		div.appendChild(document.createElement("span"));
		div.appendChild(document.createTextNode("text under div"));
		div.appendChild(document.createElement("i"));
		Element p = document.createElement("p");
		p.appendChild(document.createElement("img"));
		p.appendChild(document.createTextNode("text under p"));
		p.appendChild(document.createElement("b"));
		fragment.appendChild(div);
		fragment.appendChild(p);
		assertSame(fragment.getFirstChild(), div);
		assertSame(fragment.getLastChild(), p);
		assertSame(div.getNextSibling(), p);
		assertSame(p.getPreviousSibling(), div);
		assertSame(div.getNextElementSibling(), p);
		assertSame(p.getPreviousElementSibling(), div);
	}

	private DocumentFragment createDocumentFragment() {
		DocumentFragment fragment = document.createDocumentFragment();
		Element div = document.createElement("div");
		div.appendChild(document.createElement("span"));
		div.appendChild(document.createTextNode("text under div"));
		div.appendChild(document.createElement("i"));
		Element p = document.createElement("p");
		p.appendChild(document.createElement("img"));
		p.appendChild(document.createTextNode("text under p"));
		p.appendChild(document.createElement("b"));
		fragment.appendChild(div);
		fragment.appendChild(p);
		return fragment;
	}

	@Test
	public void prependChild() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm = document.createElement("body");
		ProcessingInstruction pi = document.createProcessingInstruction("xml-foo", "bar");
		DocumentType docType = impl.createDocumentType("html", null, null);
		assertTrue(html.hasChildNodes());
		Node appended = html.prependChild(elm);
		assertTrue(html.hasChildNodes());
		assertSame(appended, elm);
		assertSame(html, elm.getParentNode());
		assertSame(appended, html.getChildNodes().item(0));
		assertSame(appended, html.getChildren().item(0));
		assertNotNull(html.getChildNodes().item(1));
		assertNotNull(html.getChildren().item(1));
		assertNull(html.getChildNodes().item(-1));
		assertNull(html.getChildren().item(-1));
		assertSame(document, elm.getOwnerDocument());
		assertSame(document, html.getOwnerDocument());
		Attr attr = document.createAttribute("id");
		//
		try {
			elm.prependChild(attr);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.prependChild(document);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.prependChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertNull(docType.getOwnerDocument());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.prependChild(elm);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.prependChild(html);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}

		DocumentFragment fragment = createDocumentFragment();
		try {
			fragment.prependChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		elm.prependChild(pi);
		assertTrue(elm.hasChildNodes());
		assertSame(pi, elm.getChildNodes().item(0));
		Text text = document.createTextNode("foo");
		appended = elm.prependChild(text);
		assertSame(appended, text);
		assertSame(pi, text.getNextSibling());
		assertSame(text, pi.getPreviousSibling());
		assertSame(appended, elm.getChildNodes().item(0));
		assertNotNull(text.getNextElementSibling());
		assertNull(text.getPreviousElementSibling());
		// Test appending to void elements
		Element head = document.createElement("head");
		html.prependChild(head);
		assertSame(head, html.getChildNodes().item(0));
		assertSame(head, html.getChildren().item(0));
		Element base = document.createElement("base");
		head.prependChild(base);
		assertSame(base, head.getChildNodes().item(0));
		assertSame(base, head.getChildren().item(0));
		Element base2 = document.createElement("base");
		try {
			head.prependChild(base2);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		assertFalse(base.hasChildNodes());
		try {
			base.prependChild(document.createTextNode("foo"));
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		assertFalse(base.hasChildNodes());
		Element link = document.createElement("link");
		try {
			link.prependChild(document.createTextNode("foo"));
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		assertFalse(link.hasChildNodes());
		Element meta = document.createElement("meta");
		try {
			meta.prependChild(document.createTextNode("foo"));
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		assertFalse(meta.hasChildNodes());
		assertNull(meta.getChildNodes().item(0));
		assertNull(meta.getChildren().item(0));
		// Document fragment
		Node first = fragment.getFirstChild();
		assertEquals(2, elm.getChildNodes().getLength());
		elm.prependChild(fragment);
		assertNotSame(fragment, elm.getFirstChild());
		assertEquals(4, elm.getChildNodes().getLength());
		assertSame(first, elm.getFirstChild());
		assertNotNull(fragment.getFirstChild());
		assertNotNull(fragment.getLastChild());
		assertNull(fragment.getParentNode());
	}

	@Test
	public void testAppendChild() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm = document.createElement("body");
		ProcessingInstruction pi = document.createProcessingInstruction("xml-foo", "bar");
		DocumentType docType = impl.createDocumentType("html", null, null);
		assertTrue(html.hasChildNodes());
		Node appended = html.appendChild(elm);
		assertTrue(html.hasChildNodes());
		assertSame(appended, elm);
		assertSame(html, elm.getParentNode());
		assertSame(appended, html.getChildNodes().item(2));
		assertSame(appended, html.getChildren().item(2));
		assertNull(html.getChildNodes().item(-1));
		assertNull(html.getChildren().item(-1));
		assertSame(document, elm.getOwnerDocument());
		assertSame(document, html.getOwnerDocument());
		Attr attr = document.createAttribute("id");
		DocumentFragment fragment = createDocumentFragment();
		//
		try {
			docType.appendChild(fragment);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(fragment.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		//
		try {
			elm.appendChild(attr);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.appendChild(document);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.appendChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertNull(docType.getOwnerDocument());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.appendChild(elm);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			elm.appendChild(html);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}

		Text text = document.createTextNode("text inside elm");
		try {
			text.appendChild(document);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			text.appendChild(text);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(text.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}

		try {
			text.appendChild(elm);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNotNull(elm.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			text.appendChild(attr);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			text.appendChild(pi);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(pi.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			text.appendChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			text.appendChild(fragment);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(fragment.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		//
		try {
			attr.appendChild(text);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(text.getParentNode());
			assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
		}
		try {
			attr.appendChild(pi);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(pi.getParentNode());
			assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
		}
		try {
			attr.appendChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
		}
		try {
			attr.appendChild(fragment);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(fragment.getParentNode());
			assertEquals(DOMException.NOT_SUPPORTED_ERR, e.getCode());
		}
		//
		try {
			pi.appendChild(fragment);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(fragment.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		//
		try {
			pi.appendChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		try {
			fragment.appendChild(docType);
			fail("Must throw exception.");
		} catch (DOMException e) {
			assertNull(docType.getParentNode());
			assertEquals(DOMException.HIERARCHY_REQUEST_ERR, e.getCode());
		}
		elm.appendChild(pi);
		assertTrue(elm.hasChildNodes());
		assertSame(pi, elm.getChildNodes().item(0));
		appended = elm.appendChild(text);
		assertSame(appended, text);
		assertSame(pi, text.getPreviousSibling());
		assertSame(text, pi.getNextSibling());
		assertSame(appended, elm.getChildNodes().item(1));
		assertNull(text.getNextElementSibling());
		assertNotNull(text.getPreviousElementSibling());
		// Test appending to void elements
		Element head = document.createElement("head");
		html.appendChild(head);
		Element base = document.createElement("base");
		head.appendChild(base);
		assertSame(base, head.getChildNodes().item(0));
		assertSame(base, head.getChildren().item(0));
		assertFalse(base.hasChildNodes());
		assertFalse(base.hasChildNodes());
		Element link = document.createElement("link");
		assertFalse(link.hasChildNodes());
		Element meta = document.createElement("meta");
		assertFalse(meta.hasChildNodes());
		assertNull(meta.getChildNodes().item(0));
		assertNull(meta.getChildren().item(0));
		// Document fragment
		Node last = fragment.getLastChild();
		assertEquals(2, elm.getChildNodes().getLength());
		elm.appendChild(fragment);
		assertEquals(3, elm.getChildNodes().getLength());
		assertSame(fragment, elm.getLastChild());
		assertNotNull(fragment.getFirstChild());
		assertNotNull(fragment.getLastChild());
		assertNotNull(fragment.getParentNode());
	}

	@Test
	public void insertBefore() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm1 = document.createElement("p");
		html.appendChild(elm1);
		assertSame(html, elm1.getParentNode());
		assertEquals(3, html.getChildNodes().getLength());
		Element elm2 = document.createElement("div");
		Element elm = (Element) html.insertBefore(elm2, elm1);
		assertSame(elm2, elm);
		assertSame(elm2, elm1.getPreviousSibling());
		assertSame(elm2, elm1.getPreviousElementSibling());
		assertSame(elm1, elm2.getNextSibling());
		assertSame(elm1, elm2.getNextElementSibling());
		assertEquals(4, html.getChildNodes().getLength());
		assertSame(elm2, html.getChildNodes().item(2));
		assertSame(html, elm2.getParentNode());
		Element elm3 = document.createElement("div");
		elm = (Element) html.insertBefore(elm3, null);
		assertSame(elm, elm3);
		assertEquals(5, html.getChildNodes().getLength());
		assertSame(elm3, html.getChildNodes().item(4));
	}

	@Test
	public void insertBefore2() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Element span = document.createElement("span");
		body.appendChild(span);
		assertSame(body, span.getParentNode());
		assertFalse(span.hasChildNodes());
		Text text = document.createTextNode("foo");
		span.appendChild(text);
		assertTrue(span.hasChildNodes());
		Text text2 = document.createTextNode("bar");
		body.appendChild(text2);
		assertSame(body, text2.getParentNode());
		Element div = document.createElement("div");
		div.appendChild(document.createTextNode("inside div"));
		body.appendChild(div);
		assertSame(body, div.getParentNode());
		Element p = document.createElement("p");
		p.appendChild(document.createTextNode("inside p"));
		body.appendChild(p);
		assertSame(body, p.getParentNode());
		assertSame(span.getNextSibling(), text2);
		assertSame(text2.getNextSibling(), div);
		assertSame(div.getNextSibling(), p);
		assertNull(p.getNextSibling());
		assertSame(span.getNextElementSibling(), div);
		assertSame(text2.getNextElementSibling(), div);
		assertSame(div.getNextElementSibling(), p);
		assertNull(p.getNextElementSibling());
		assertSame(span, body.getFirstChild());
		assertSame(span, body.getFirstElementChild());
		assertSame(p, body.getLastChild());
		assertSame(p, body.getLastElementChild());

		HTMLCollection listspan = body.getElementsByTagName("span");
		HTMLCollection listdiv = body.getElementsByTagName("div");
		HTMLCollection listp = body.getElementsByTagName("p");
		assertEquals(1, listspan.getLength());
		assertEquals(1, listdiv.getLength());
		assertEquals(1, listp.getLength());

		assertSame(p.getPreviousSibling(), div);
		assertSame(p.getPreviousElementSibling(), div);
		assertSame(div.getPreviousSibling(), text2);
		assertSame(div.getPreviousElementSibling(), span);
		assertSame(text2.getPreviousSibling(), span);
		assertSame(text2.getPreviousElementSibling(), span);
		assertNull(span.getPreviousSibling());
		assertNull(span.getPreviousElementSibling());

		Element p2 = document.createElement("p");
		Element elm = (Element) body.insertBefore(p2, div);
		assertSame(p2, elm);
		assertSame(p2, div.getPreviousSibling());
		assertSame(p2, div.getPreviousElementSibling());
		assertSame(div, p2.getNextSibling());
		assertSame(div, p2.getNextElementSibling());
		assertEquals(5, body.getChildNodes().getLength());
		assertSame(p2, body.getChildNodes().item(2));
		assertSame(body, p2.getParentNode());

		listspan = body.getElementsByTagName("span");
		listdiv = body.getElementsByTagName("div");
		listp = body.getElementsByTagName("p");

		assertEquals(1, listspan.getLength());
		assertEquals(1, listdiv.getLength());
		assertEquals(2, listp.getLength());

		Element elm3 = document.createElement("div");
		elm = (Element) body.insertBefore(elm3, null);
		assertSame(elm, elm3);
		assertEquals(6, body.getChildNodes().getLength());
		assertEquals(5, body.getChildren().getLength());
		assertSame(span, body.getChildNodes().item(0));
		assertSame(text2, body.getChildNodes().item(1));
		assertSame(p2, body.getChildNodes().item(2));
		assertSame(div, body.getChildNodes().item(3));
		assertSame(p, body.getChildNodes().item(4));
		assertSame(elm, body.getChildNodes().item(5));
		assertSame(span, body.getChildren().item(0));
		assertSame(p2, body.getChildren().item(1));
		assertSame(div, body.getChildren().item(2));
		assertSame(p, body.getChildren().item(3));
		assertSame(elm, body.getChildren().item(4));
		assertSame(body, elm.getParentNode());
		assertSame(p, elm.getPreviousSibling());
		assertSame(p, elm.getPreviousElementSibling());
		assertSame(elm, p.getNextSibling());
		assertSame(elm, p.getNextElementSibling());
		assertNull(elm.getNextSibling());
		assertNull(elm.getNextElementSibling());

		listspan = body.getElementsByTagName("span");
		listdiv = body.getElementsByTagName("div");
		listp = body.getElementsByTagName("p");

		assertEquals(1, listspan.getLength());
		assertEquals(2, listdiv.getLength());
		assertEquals(2, listp.getLength());
	}

	@Test
	public void insertBeforeMyself() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm1 = document.createElement("div");
		html.appendChild(elm1);
		Element elm2 = document.createElement("p");
		html.appendChild(elm2);
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm1, elm2.getPreviousElementSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertSame(elm2, elm1.getNextElementSibling());
		assertSame(html, elm1.getParentNode());
		assertEquals(4, html.getChildNodes().getLength());
		assertSame(elm1, html.insertBefore(elm1, elm1));
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm1, elm2.getPreviousElementSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertSame(elm2, elm1.getNextElementSibling());
		assertEquals(4, html.getChildNodes().getLength());
		assertSame(elm2, html.insertBefore(elm2, elm2));
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm1, elm2.getPreviousElementSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertSame(elm2, elm1.getNextElementSibling());
	}

	@Test
	public void insertBeforeDF() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Element elm1 = document.createElement("p");
		body.appendChild(elm1);
		Element elm2 = document.createElement("div");
		body.insertBefore(elm2, elm1);
		Element elm3 = document.createElement("span");
		body.insertBefore(elm3, elm1);
		assertEquals(3, body.getChildNodes().getLength());
		DocumentFragment fragment = createDocumentFragment();
		body.insertBefore(fragment, elm3);
		assertEquals(4, body.getChildNodes().getLength());
		assertEquals("span", body.getChildNodes().item(2).getNodeName());
		assertEquals("p", body.getChildNodes().item(3).getNodeName());
		assertNotNull(fragment.getFirstChild());
		assertNotNull(fragment.getLastChild());
		assertNotNull(fragment.getParentNode());
	}

	@Test
	public void removeChild() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm = document.createElement("body");
		html.appendChild(elm);
		assertSame(html, elm.getParentNode());
		assertSame(document.getHead(), html.getFirstChild());
		assertSame(elm, html.getLastChild());
		assertSame(document.getHead(), html.getFirstElementChild());
		assertSame(elm, html.getLastElementChild());
		Attr attr = document.createAttribute("id");
		attr.setValue("bodyId");
		assertFalse(elm.hasAttributes());
		elm.setAttributeNode(attr);
		assertTrue(elm.hasAttributes());
		assertFalse(elm.hasChildNodes());
		assertNull(attr.getParentNode());
		Text text = document.createTextNode("foo");
		elm.appendChild(text);
		assertTrue(elm.hasChildNodes());
		assertSame(elm, text.getParentNode());
		text = (Text) elm.removeChild(text);
		assertFalse(elm.hasChildNodes());
		assertNotNull(text.getParentNode());
		assertNull(text.getNextSibling());
		assertNull(text.getPreviousSibling());
		assertNull(text.getNextElementSibling());
		assertNull(text.getPreviousSibling());
		assertNull(text.getPreviousElementSibling());
		elm.removeAttribute(attr.getName());
		assertFalse(elm.hasAttributes());
		elm = (Element) html.removeChild(elm);
		assertTrue(html.hasChildNodes());
		assertNotNull(html.getFirstChild());
		assertNotNull(html.getLastChild());
		assertNotNull(elm.getParentNode());
		assertNull(elm.getNextSibling());
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getNextElementSibling());
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getPreviousElementSibling());
	}

	@Test
	public void removeChild2() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Element elm = document.createElement("span");
		body.appendChild(elm);
		assertSame(body, elm.getParentNode());
		assertFalse(elm.hasChildNodes());
		Text text = document.createTextNode("foo");
		elm.appendChild(text);
		assertTrue(elm.hasChildNodes());
		Text text2 = document.createTextNode("bar");
		body.appendChild(text2);
		assertSame(body, text2.getParentNode());
		Element div = document.createElement("div");
		div.appendChild(document.createTextNode("inside div"));
		body.appendChild(div);
		assertSame(body, div.getParentNode());
		Element p = document.createElement("p");
		p.appendChild(document.createTextNode("inside p"));
		body.appendChild(p);
		assertSame(body, p.getParentNode());
		assertSame(elm.getNextSibling(), text2);
		assertSame(text2.getNextSibling(), div);
		assertSame(div.getNextSibling(), p);
		assertNull(p.getNextSibling());
		assertSame(elm.getNextElementSibling(), div);
		assertSame(text2.getNextElementSibling(), div);
		assertSame(div.getNextElementSibling(), p);
		assertNull(p.getNextElementSibling());
		assertSame(elm, body.getFirstChild());
		assertSame(elm, body.getFirstElementChild());
		assertSame(p, body.getLastChild());
		assertSame(p, body.getLastElementChild());
		//
		HTMLCollection listspan = body.getElementsByTagName("span");
		HTMLCollection listdiv = body.getElementsByTagName("div");
		HTMLCollection listp = body.getElementsByTagName("p");
		assertEquals(1, listspan.getLength());
		assertEquals(1, listdiv.getLength());
		assertEquals(1, listp.getLength());
		//
		assertSame(p.getPreviousSibling(), div);
		assertSame(p.getPreviousElementSibling(), div);
		assertSame(div.getPreviousSibling(), text2);
		assertSame(div.getPreviousElementSibling(), elm);
		assertSame(text2.getPreviousSibling(), elm);
		assertSame(text2.getPreviousElementSibling(), elm);
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getPreviousElementSibling());
		assertEquals(4, body.getChildNodes().getLength());
		assertSame(elm, body.getChildNodes().item(0));
		assertSame(text2, body.getChildNodes().item(1));
		assertSame(div, body.getChildNodes().item(2));
		//
		body.removeChild(div);
		assertTrue(body.hasChildNodes());
		assertNotNull(div.getParentNode());
		assertNull(div.getNextSibling());
		assertNull(div.getPreviousSibling());
		assertNull(div.getNextElementSibling());
		assertNull(div.getPreviousSibling());
		assertNull(div.getPreviousElementSibling());
		assertSame(elm.getNextSibling(), text2);
		assertSame(text2.getNextSibling(), p);
		assertNull(p.getNextSibling());
		assertSame(elm.getNextElementSibling(), p);
		assertSame(text2.getNextElementSibling(), p);
		assertNull(p.getNextElementSibling());

		listspan = body.getElementsByTagName("span");
		listdiv = body.getElementsByTagName("div");
		listp = body.getElementsByTagName("p");

		assertEquals(1, listspan.getLength());
		assertEquals(0, listdiv.getLength());
		assertEquals(1, listp.getLength());
		assertSame(elm, body.getFirstChild());
		assertSame(p, body.getLastChild());
		assertEquals(3, body.getChildNodes().getLength());
		assertSame(elm, body.getChildNodes().item(0));
		assertSame(p, body.getChildNodes().item(2));
		//
		elm = (Element) body.removeChild(elm);
		assertTrue(body.hasChildNodes());
		assertNotNull(elm.getParentNode());
		assertNull(elm.getNextSibling());
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getNextElementSibling());
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getPreviousElementSibling());
		assertSame(text2.getNextSibling(), p);
		assertNull(p.getNextSibling());
		assertSame(text2, p.getPreviousSibling());
		assertSame(text2.getNextElementSibling(), p);
		assertNull(p.getNextElementSibling());
		assertNull(p.getPreviousElementSibling());

		listspan = body.getElementsByTagName("span");
		listdiv = body.getElementsByTagName("div");
		listp = body.getElementsByTagName("p");

		assertEquals(0, listspan.getLength());
		assertEquals(0, listdiv.getLength());
		assertEquals(1, listp.getLength());
		assertSame(text2, body.getFirstChild());
		assertSame(p, body.getLastChild());
		assertSame(p, body.getFirstElementChild());
		assertSame(p, body.getLastElementChild());
		assertEquals(2, body.getChildNodes().getLength());
		assertSame(text2, body.getChildNodes().item(0));
		assertSame(p, body.getChildNodes().item(1));
		//
		body.removeChild(text2);
		assertNotNull(text2.getParentNode());
		assertNull(text2.getNextSibling());
		assertNull(text2.getPreviousSibling());
		assertNull(text2.getNextElementSibling());
		assertNull(text2.getPreviousSibling());
		assertNull(text2.getPreviousElementSibling());
		assertNull(p.getNextElementSibling());
		assertNull(p.getPreviousElementSibling());
		assertNull(p.getNextSibling());
		assertNull(p.getPreviousSibling());

		listspan = body.getElementsByTagName("span");
		listdiv = body.getElementsByTagName("div");
		listp = body.getElementsByTagName("p");

		assertEquals(0, listspan.getLength());
		assertEquals(0, listdiv.getLength());
		assertEquals(1, listp.getLength());
		assertSame(p, body.getFirstChild());
		assertSame(p, body.getLastChild());
		assertSame(p, body.getFirstElementChild());
		assertSame(p, body.getLastElementChild());
		assertEquals(1, body.getChildNodes().getLength());
		assertSame(p, body.getChildNodes().item(0));
		//
		body.removeChild(p);
		assertFalse(body.hasChildNodes());
		assertEquals(0, body.getChildNodes().getLength());
		assertNull(body.getFirstChild());
		assertNull(body.getLastChild());
		assertNull(body.getFirstElementChild());
		assertNull(body.getLastElementChild());
		assertNotNull(p.getParentNode());
		assertNull(p.getNextSibling());
		assertNull(p.getPreviousSibling());
		assertNull(p.getNextElementSibling());
		assertNull(p.getPreviousSibling());
		assertNull(p.getPreviousElementSibling());
	}

	@Test
	public void replaceChild() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		body.setAttribute("id", "body1");
		html.appendChild(body);
		Element body2 = document.createElement("body");
		body2.setAttribute("id", "body2");
		Element elm = (Element) html.replaceChild(body2, body);
		assertEquals(body, elm);
		assertSame(html, body2.getParentNode());
		assertNull(elm.getParentNode());
		assertNull(body.getParentNode());
		assertNull(body.getNextSibling());
		assertNull(body.getPreviousSibling());
		assertNull(body.getNextElementSibling());
		assertNull(body.getPreviousElementSibling());
		Text foo1 = document.createTextNode("foo1");
		body2.appendChild(foo1);
		Text foo2 = document.createTextNode("foo2");
		assertNull(foo2.getParentNode());
		Text text = (Text) body2.replaceChild(foo2, foo1);
		assertSame(foo1, text);
		assertEquals("foo1", text.getTextContent());
		assertEquals(body2, foo2.getParentNode());
		assertNull(text.getParentNode());
	}

	@Test
	public void replaceChild2() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Element span = document.createElement("span");
		body.appendChild(span);
		assertSame(body, span.getParentNode());
		assertFalse(span.hasChildNodes());
		Text text = document.createTextNode("foo");
		span.appendChild(text);
		assertTrue(span.hasChildNodes());
		Text text2 = document.createTextNode("bar");
		body.appendChild(text2);
		assertSame(body, text2.getParentNode());
		Element div = document.createElement("div");
		div.appendChild(document.createTextNode("inside div"));
		body.appendChild(div);
		assertSame(body, div.getParentNode());
		Element p = document.createElement("p");
		p.appendChild(document.createTextNode("inside p"));
		body.appendChild(p);
		//
		HTMLCollection listspan = body.getElementsByTagName("span");
		HTMLCollection listdiv = body.getElementsByTagName("div");
		HTMLCollection listp = body.getElementsByTagName("p");
		assertEquals(1, listspan.getLength());
		assertEquals(1, listdiv.getLength());
		assertEquals(1, listp.getLength());
		//
		Element div2 = document.createElement("div");
		div2.setAttribute("id", "div2");
		Element elm = (Element) body.replaceChild(div2, div);
		assertSame(div, elm);
		assertSame(body, div2.getParentNode());
		assertNull(elm.getParentNode());
		assertNull(elm.getNextSibling());
		assertNull(elm.getPreviousSibling());
		assertNull(elm.getNextElementSibling());
		assertNull(elm.getPreviousElementSibling());
		//
		assertNull(span.getPreviousSibling());
		assertNull(span.getPreviousElementSibling());
		assertSame(span.getNextSibling(), text2);
		assertSame(text2.getNextSibling(), div2);
		assertSame(div2.getNextSibling(), p);
		assertNull(p.getNextSibling());
		assertSame(span.getNextElementSibling(), div2);
		assertSame(text2.getNextElementSibling(), div2);
		assertSame(div2.getNextElementSibling(), p);
		assertNull(p.getNextElementSibling());
		assertSame(span, body.getFirstChild());
		assertSame(span, body.getFirstElementChild());
		assertSame(p, body.getLastChild());
		assertSame(p, body.getLastElementChild());
		assertEquals("div2", div2.getAttribute("id"));
		assertEquals(1, listspan.getLength());
		assertEquals(1, listdiv.getLength());
		assertEquals(1, listp.getLength());
		//
		assertEquals(4, body.getChildNodes().getLength());
		assertSame(span, body.getChildNodes().item(0));
		assertSame(text2, body.getChildNodes().item(1));
		assertSame(div2, body.getChildNodes().item(2));
		assertSame(p, body.getChildNodes().item(3));
		assertEquals(3, body.getChildren().getLength());
		assertSame(span, body.getChildren().item(0));
		assertSame(div2, body.getChildren().item(1));
		assertSame(p, body.getChildren().item(2));
	}

	@Test
	public void replaceByMyself() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element elm1 = document.createElement("div");
		html.appendChild(elm1);
		Element elm2 = document.createElement("p");
		html.appendChild(elm2);
		assertSame(elm1, html.replaceChild(elm1, elm1));
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm1, elm2.getPreviousElementSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertSame(elm2, elm1.getNextElementSibling());
		assertEquals(4, html.getChildNodes().getLength());
		assertSame(elm2, html.replaceChild(elm2, elm2));
		assertSame(elm1, elm2.getPreviousSibling());
		assertSame(elm1, elm2.getPreviousElementSibling());
		assertSame(elm2, elm1.getNextSibling());
		assertSame(elm2, elm1.getNextElementSibling());
	}

	@Test
	public void replaceChildDF() throws DOMException {
		document = sampleHtmlFile();
		Element html = document.getDocumentElement();
		Element body = document.createElement("body");
		html.appendChild(body);
		Element elm1 = document.createElement("p");
		body.appendChild(elm1);
		Element elm2 = document.createElement("div");
		body.insertBefore(elm2, elm1);
		Element elm3 = document.createElement("span");
		body.insertBefore(elm3, elm1);
		assertEquals(3, body.getChildNodes().getLength());
		DocumentFragment fragment = createDocumentFragment();
		body.replaceChild(fragment, elm3);
		assertEquals(3, body.getChildNodes().getLength());
		assertEquals("p", body.getChildNodes().item(2).getNodeName());
		assertNotNull(fragment.getFirstChild());
		assertNotNull(fragment.getLastChild());
		assertNull(fragment.getParentNode());
	}

	@Test
	public void cloneNode() {
		document = sampleHtmlFile();
		Element elm = document.createElement("p");
		elm.setAttribute("foo", "bar");
		Text foo = document.createTextNode("foo");
		elm.appendChild(foo);
		Element elmc = (Element)elm.cloneNode(true);
		assertEquals(elm.getTagName(), elmc.getTagName());
		assertEquals(elm.getAttribute("foo"), elmc.getAttribute("foo"));
		assertEquals(elm.getChildNodes().getLength(), elmc.getChildNodes().getLength());
		assertEquals(elm.getChildNodes().item(0).getNodeValue(), elmc.getChildNodes().item(0).getNodeValue());
	}

}
