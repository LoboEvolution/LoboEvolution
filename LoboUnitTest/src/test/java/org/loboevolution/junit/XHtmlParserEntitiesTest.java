/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for entity decoding in XHTML documents parsed in XML mode
 * (predefined XML entities and entities declared by the XHTML DTD).
 */
public class XHtmlParserEntitiesTest extends LoboUnitTest {

	@Test
	public void decodePredefinedXmlEntities() {
		final Document xmlDoc = sampleXmlFile("XHtmlEntities.xhtml");
		final Element p1 = xmlDoc.getElementById("p1");
		assertEquals("<abbr> & \u00A0 ' \"", p1.getTextContent());
	}

	@Test
	public void decodeNumericAndDtdEntities() {
		final Document xmlDoc = sampleXmlFile("XHtmlEntities.xhtml");
		final Element p2 = xmlDoc.getElementById("p2");
		assertEquals("\u00A9 \u221E", p2.getTextContent());
}

	@Test
	public void parseXmlLangAttribute() {
		final Document xmlDoc = sampleXmlFile("XHtmlLangAttr.xhtml");
		final Element body = xmlDoc.getElementById("DocBody");
		final Attr lang = body.getAttributeNode("xml:lang");
		assertEquals("en", lang.getValue());
		assertEquals(Node.XML_NAMESPACE_URI, lang.getNamespaceURI());
		assertTrue(body.hasAttributeNS(Node.XML_NAMESPACE_URI, "lang"));
	}
}