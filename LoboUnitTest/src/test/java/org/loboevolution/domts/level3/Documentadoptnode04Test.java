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

import static org.junit.jupiter.api.Assertions.*;


/**
 * Invoke adoptNode on a new document to adopt a new namespace aware attribute node created by
 * this document.  Check if this attribute has been adopted successfully by verifying the nodeName,
 * namespaceURI, prefix, specified and ownerElement attributes of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode04Test extends LoboUnitTest {

    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Attr newAttr;
        final Attr adoptedAttr;
        final String nodeName;
        final String nodeNamespaceURI;
        final String nodePrefix;
        final Node attrOwnerElem;
        final boolean isSpecified;
        final DocumentType nullDocType = null;

        final Element docElem;
        final String rootNS;
        final String rootName;
        final String xmlNS = "http://www.w3.org/XML/1998/namespace";
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        newAttr = doc.createAttributeNS(xmlNS, "xml:lang");
        adoptedAttr = (Attr) newDoc.adoptNode(newAttr);

        if ((adoptedAttr != null)) {
            nodeName = adoptedAttr.getNodeName();
            nodeNamespaceURI = adoptedAttr.getNamespaceURI();
            nodePrefix = adoptedAttr.getPrefix();
            attrOwnerElem = adoptedAttr.getOwnerElement();
            isSpecified = adoptedAttr.isSpecified();
            assertEquals( "xml:lang", nodeName, "Documentadoptnode04Assert1");
            assertEquals(xmlNS, nodeNamespaceURI, "Documentadoptnode04Assert2");
            assertEquals("xml", nodePrefix, "Documentadoptnode04Assert3");
            assertNull(attrOwnerElem, "Documentadoptnode04Assert4");
            assertTrue(isSpecified, "Documentadoptnode04Assert5");
        }
    }
}
