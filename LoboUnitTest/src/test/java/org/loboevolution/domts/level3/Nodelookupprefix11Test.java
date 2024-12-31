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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Invoke lookupPrefix on an imported new Element node with a namespace URI
 * and prefix in a new Document and using the parents namespace URI as an argument, verify if the prefix
 * returned is a valid prefix of the parent.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespacePrefix">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespacePrefix</a>
 */
public class Nodelookupprefix11Test extends LoboUnitTest {

    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        final Document newDoc;
        final Element elem;
        final Element importedNode;
        final String prefix;
        final Element docElem;
        final String rootNS;
        final String rootName;
        final String qname;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        qname = "dom3doc:" + rootName;
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, qname, null);
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:br");
        importedNode = (Element) newDoc.importNode(elem, true);
        prefix = importedNode.lookupPrefix("http://www.w3.org/1999/xhtml");
        assertEquals("dom3", prefix, "Nodelookupprefix11Assert2");
    }
}

