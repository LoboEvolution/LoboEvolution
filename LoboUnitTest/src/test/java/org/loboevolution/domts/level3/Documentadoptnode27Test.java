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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Invoke the adoptNode method on this document using a new imported Element and a new attribute created in
 * a new Document as the source.  Verify if the node has been adopted correctly by checking the
 * nodeName of the adopted Element and by checking if the attribute was adopted.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode27Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        Element docElem;
        final Element newElem;
        final Element newImpElem;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Node adoptedNode;
        final String adoptedName;
        final String adoptedNS;

        final String rootNS;
        final String rootTagname;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootTagname, null);
        newElem = newDoc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:head");
        newElem.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
        docElem = newDoc.getDocumentElement();
        docElem.appendChild(newElem);
        newImpElem = (Element) doc.importNode(newElem, true);
        adoptedNode = doc.adoptNode(newImpElem);

        if ((adoptedNode != null)) {
            adoptedName = adoptedNode.getNodeName();
            adoptedNS = adoptedNode.getNamespaceURI();
            assertEquals("xhtml:head", adoptedName, "Documentadoptnode27Assert2");
            assertEquals("http://www.w3.org/1999/xhtml", adoptedNS, "Documentadoptnode27Assert3");
        }
    }
}

