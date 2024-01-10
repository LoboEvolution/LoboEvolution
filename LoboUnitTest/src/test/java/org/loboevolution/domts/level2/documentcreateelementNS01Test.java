
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The method createElementNS creates an element of the given valid qualifiedName and NamespaceURI.
 * <p>
 * Invoke the createElementNS method on this Document object with a valid namespaceURI
 * and qualifiedName.  Check if a valid Element object is returned with the same node attributes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS</a>
 */
public class DocumentcreateelementNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element element;
        final String namespaceURI = "http://www.w3.org/DOM/Test/level2";
        final String qualifiedName = "XML:XML";
        final String nodeName;
        final String nsURI;
        final String localName;
        final String prefix;
        final String tagName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS(namespaceURI, qualifiedName);
        nodeName = element.getNodeName();
        nsURI = element.getNamespaceURI();
        localName = element.getLocalName();
        prefix = element.getPrefix();
        tagName = element.getTagName();
        assertEquals("XML:XML", nodeName);
        assertEquals("http://www.w3.org/DOM/Test/level2", nsURI);
        assertEquals("XML", localName);
        assertEquals("XML", prefix);
        assertEquals("XML:XML", tagName);
    }
}

