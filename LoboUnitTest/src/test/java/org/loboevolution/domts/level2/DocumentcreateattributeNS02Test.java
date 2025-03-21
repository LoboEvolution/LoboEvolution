
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The method createAttributeNS creates an attribute of the given qualified name and namespace URI
 * <p>
 * Invoke the createAttributeNS method on this Document object with a valid values for
 * namespaceURI, and a qualifiedName as below.  This should return a valid Attr node.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class DocumentcreateattributeNS02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Attr attribute1;
        final Attr attribute2;
        String name;
        String nodeName;
        String nodeValue;
        final String prefix;
        String namespaceURI;
        doc = sampleXmlFile("staffNS.xml");
        attribute1 = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:xml");
        name = attribute1.getName();
        nodeName = attribute1.getNodeName();
        nodeValue = attribute1.getNodeValue();
        prefix = attribute1.getPrefix();
        namespaceURI = attribute1.getNamespaceURI();
        assertEquals("xml:xml", name);
        assertEquals("xml:xml", nodeName);
        assertNull(nodeValue);
        assertEquals("xml", prefix);
        assertEquals("http://www.w3.org/XML/1998/namespace", namespaceURI);
        attribute2 = doc.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
        name = attribute2.getName();
        nodeName = attribute2.getNodeName();
        nodeValue = attribute2.getNodeValue();
        namespaceURI = attribute2.getNamespaceURI();
        assertEquals("xmlns", name);
        assertEquals("xmlns", nodeName);
        assertNull(nodeValue);
        assertEquals("http://www.w3.org/2000/xmlns/", namespaceURI);
    }

}

