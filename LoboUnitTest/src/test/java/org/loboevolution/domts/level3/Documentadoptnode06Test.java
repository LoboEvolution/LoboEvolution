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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Invoke adoptNode on a new document to adopt the a new Attribute node having a Text and an EntityReference
 * child.  Check if this attribute has been adopted successfully by verifying the nodeName, namespaceURI, prefix,
 * specified and ownerElement attributes of the adopted node.  Also verify the ownerDocument attribute
 * of the adopted node and the adopted children of the attribute node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode06Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Attr newAttr;
        final Text newText;
        final EntityReference newEntRef;
        final Attr adoptedAttr;
        final Text adoptText;
        final EntityReference adoptEntRef;
        final NodeList nodeList;
        final String nodeName;
        final String nodeNamespaceURI;
        final String nodePrefix;
        final Node attrOwnerElem;
        final boolean isSpecified;
        final String adoptedTextNodeValue;
        final String adoptedEntRefNodeValue;
        final Element docElem;
        final String rootNS;
        final String rootName;
        final String xmlNS = "http://www.w3.org/XML/1998/namespace";
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, null);
        newAttr = doc.createAttributeNS(xmlNS, "xml:lang");
        newText = doc.createTextNode("Text Node");
        newEntRef = doc.createEntityReference("alpha");
        newAttr.appendChild(newText);
        newAttr.appendChild(newEntRef);
        adoptedAttr = (Attr) newDoc.adoptNode(newAttr);

        if ((adoptedAttr != null)) {
            nodeName = adoptedAttr.getNodeName();
            nodeNamespaceURI = adoptedAttr.getNamespaceURI();
            nodePrefix = adoptedAttr.getPrefix();
            attrOwnerElem = adoptedAttr.getOwnerElement();
            isSpecified = adoptedAttr.isSpecified();
            assertEquals("xml:lang", nodeName, "Documentadoptnode06Assert1");
            assertEquals(xmlNS, nodeNamespaceURI, "Documentadoptnode06Assert2");
            assertEquals("xml", nodePrefix, "Documentadoptnode06Assert3");
            assertNull(attrOwnerElem, "Documentadoptnode06Assert4");
            assertTrue(isSpecified, "Documentadoptnode06Assert5");
            nodeList = adoptedAttr.getChildNodes();
            adoptText = (Text) nodeList.item(0);
            adoptEntRef = (EntityReference) nodeList.item(1);
            adoptedTextNodeValue = adoptText.getNodeValue();
            adoptedEntRefNodeValue = adoptEntRef.getNodeName();
            assertEquals("Text Node", adoptedTextNodeValue, "Documentadoptnode06Assert6");
            assertEquals("alpha", adoptedEntRefNodeValue, "Documentadoptnode06Assert7");
        }
    }
}
