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
import org.lobobrowser.html.domimpl.HTMLFormElementImpl;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class HtmlElementTest extends SimpleLoboTest {

	@Test
	public void hasAttributeWith() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		assertTrue("Element should have attribute", node.hasAttribute("id"));
	}

	@Test
	public void hasAttributeWithMissingValue() throws Exception {
		String htmlSource = "<html><head></head><body id='tag' attrib>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		assertTrue("Element should have attribute", node.hasAttribute("attrib"));
	}

	@Test
	public void hasAttributeNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		assertFalse("Element should not have attribute", node.hasAttribute("foo"));
	}

	@Test
	public void hasAttributeNSWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//assertTrue("Element should have attribute", node.hasAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void hasAttributeNSNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//assertFalse("Element should not have attribute", node.hasAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void getAttributeWith() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		Element node = page.getElementById("tag");
		assertEquals("Element should have attribute", "tag", node.getAttribute("id"));
	}

	@Test
	public void getAttributeWithMissingValue() throws Exception {
		String htmlSource = "<html><head></head><body id='tag' attrib>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		Element node = page.getElementById("tag");
		assertEquals("", node.getAttribute("attrib"));
	}

	@Test
	public void getAttributeWithEmptyValue() throws Exception {
		String htmlSource = "<html><head></head><body id='tag' attrib=''>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		Element node = page.getElementById("tag");
		assertEquals("", node.getAttribute("attrib"));
	}

	@Test
	public void getAttributeNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		Element node = page.getElementById("tag");
		assertEquals(null, node.getAttribute("foo"));
	}

	@Test
	public void getAttributeNSWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//assertEquals("Element should have attribute", "bar", node.getAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void getAttributeNSNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//assertEquals("Element should not have attribute", "", node.getAttributeNS("http://foobar", "foo"));//TODO
	     
	}

	@Test
	public void getNamespaceURIWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("ns:foo".equals(attr.getName())) {
				assertEquals("Element should have a namespace URI", "http://foobar", attr.getNamespaceURI());
				return;
			}
		}
	}

	@Test
	public void getNamespaceURINone() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("id".equals(attr.getName())) {
				assertEquals("Element should not have a namespace URI", null, attr.getNamespaceURI());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void getLocalNameWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("ns:foo".equals(attr.getName())) {
				assertEquals("Element should have a local name", "foo", attr.getLocalName());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void getLocalNameNone() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("id".equals(attr.getName())) {
				// This is not standard, but to change it now would break
				// backwards compatibility.
				assertEquals("Element should not have a local name", "id", attr.getLocalName());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void getPrefixWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("ns:foo".equals(attr.getName())) {
				assertEquals("Element should have a prefix", "ns", attr.getPrefix());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void getPrefixNone() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("id".equals(attr.getName())) {
				assertEquals("Element should not have a prefix", null, attr.getPrefix());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void setPrefix() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr = (Attr) attributes.item(i);
			if ("ns:foo".equals(attr.getName())) {
				attr.setPrefix("other");
				assertEquals("Element should have a changed prefix", "other", attr.getPrefix());
				assertEquals("setPrefix should change qualified name", "other:foo", attr.getName());
				return;
			}
		}
		fail("Attribute ns:foo not found.");
	}

	@Test
	public void setAttributeWith() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		node.setAttribute("id", "other");
		assertEquals("Element should have attribute", "other", node.getAttribute("id"));
	}

	@Test
	public void setAttributeNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		node.setAttribute("foo", "other");
		assertEquals("Element should have attribute", "other", node.getAttribute("foo"));
	}

	@Test
	public void setAttributeNSWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//node.setAttributeNS("http://foobar", "ns:foo", "other");
		//assertEquals("Element should have attribute", "other", node.getAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void setAttributeNSNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		Element node = page.getElementById("tag");
		//node.setAttributeNS("http://foobar", "ns:foo", "other");TODO
		//assertEquals("Element should not have attribute", "other", node.getAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void removeAttributeWith() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		node.removeAttribute("id");
		assertEquals("Element should not have removed attribute", "", node.getAttribute("id"));
	}

	@Test
	public void removeAttributeNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		node.removeAttribute("foo");
		assertEquals("Element should not have attribute", "", node.getAttribute("foo"));
	}

	@Test
	public void removeAttributeNSWith() throws Exception {
		String htmlSource = "<html><head></head><body xmlns:ns='http://foobar' id='tag' ns:foo='bar'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//node.removeAttributeNS("http://foobar", "foo");
		//TODOassertEquals("Element should not have removed attribute", "", node.getAttributeNS("http://foobar", "foo"));/TODO
	}

	@Test
	public void removeAttributeNSNone() throws Exception {
		String htmlSource = "<html><head></head><body id='tag'>text</body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);

		Element node = page.getElementById("tag");
		//node.removeAttributeNS("http://foobar", "foo");
		//assertEquals("Element should not have attribute", "", node.getAttributeNS("http://foobar", "foo"));TODO
	}

	@Test
	public void getForm() throws Exception {
		String htmlSource = "<html><head><title>foo</title></head><body>" + "<form id='form1'>"
				+ "<table><tr><td><input type='text' id='foo'/></td></tr></table>" + "</form></body></html>";
		HTMLDocumentImpl page = loadPage(htmlSource);
		final HTMLFormElementImpl form = (HTMLFormElementImpl) page.getElementById("form1");

		final HTMLInputElementImpl input = (HTMLInputElementImpl) page.getElementById("foo");
		assertSame(form, input.getForm());
	}

	@Test
	public void getElementsByTagName() throws Exception {
		String htmlSource = "<html>" + "<head>" + "<script>" + "  function test() {"
				+ "    var form = document.getElementById('myForm');"
				+ "    alert(form.getElementsByTagName('input').length);"
				+ "    alert(document.body.getElementsByTagName('input').length);" + "  }" + "</script>"
				+ "</head>" + "<body >" + "<form id='myForm'>"
				+ "  <input type='button' name='button1' value='pushme'>" + "</form>"
				+ "<input type='button' name='button2'>" + "</body></html>";

		HTMLDocumentImpl page = loadPage(htmlSource);
		assertEquals(1, page.getElementById("myForm").getElementsByTagName("input").getLength());
		assertEquals(2, page.getBody().getElementsByTagName("input").getLength());
	}

	@Test
	public void getElementsByTagName2() throws Exception {
		String htmlSource = "<html><head><title>First</title></head>" + "<body>"
				+ "<form><input type='button' name='button1' value='pushme'></form>"
				+ "<div>a</div> <div>b</div> <div>c</div>" + "</body></html>";

		HTMLDocumentImpl page = loadPage(htmlSource);
		Element body = page.getBody();

		NodeList inputs = body.getElementsByTagName("input");
		assertEquals(1, inputs.getLength());
		assertEquals("button", inputs.item(0).getAttributes().getNamedItem("type").getNodeValue());

		final NodeList divs = body.getElementsByTagName("div");
		assertEquals(3, divs.getLength());

		// case sensitive
		inputs = page.getElementsByTagName("inPUT");
		assertEquals(1, inputs.getLength());

		// empty
		inputs = page.getElementsByTagName("");
		assertEquals(0, inputs.getLength());
	}
}
