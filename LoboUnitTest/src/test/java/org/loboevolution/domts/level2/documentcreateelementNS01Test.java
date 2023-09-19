
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


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
public class documentcreateelementNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        String namespaceURI = "http://www.w3.org/DOM/Test/level2";
        String qualifiedName = "XML:XML";
        String nodeName;
        String nsURI;
        String localName;
        String prefix;
        String tagName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS(namespaceURI, qualifiedName);
        nodeName = element.getNodeName();
        nsURI = element.getNamespaceURI();
        localName = element.getLocalName();
        prefix = element.getPrefix();
        tagName = element.getTagName();
        assertEquals("documentcreateelementNS01_nodeName", "XML:XML", nodeName);
        assertEquals("documentcreateelementNS01_namespaceURI", "http://www.w3.org/DOM/Test/level2", nsURI);
        assertEquals("documentcreateelementNS01_localName", "XML", localName);
        assertEquals("documentcreateelementNS01_prefix", "XML", prefix);
        assertEquals("documentcreateelementNS01_tagName", "XML:XML", tagName);
    }
}

