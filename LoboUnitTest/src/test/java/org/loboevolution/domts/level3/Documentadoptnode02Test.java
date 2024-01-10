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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Adopt the class attribute node of the fourth acronym element.  Check if this attribute has been adopted
 * successfully by verifying the nodeName, nodeType, ownerElement, specified attributes and child nodes
 * of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode02Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Element attrOwnerElem;
        final Element element;
        final Attr attr;
        final HTMLCollection childList;
        final Node adoptedclass;
        final String nodeName;
        final int nodeType;
        final String nodeValue;
        final boolean isSpecified;
        final DocumentType nullDocType = null;

        final Text firstChild;
        final String firstChildValue;
        final EntityReference secondChild;
        final int secondChildType;
        final String secondChildName;
        final Element docElem;
        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(3);
        attr = element.getAttributeNode("class");
        adoptedclass = newDoc.adoptNode(attr);

        if ((adoptedclass != null)) {
            nodeName = adoptedclass.getNodeName();
            nodeValue = adoptedclass.getNodeValue();
            nodeType = adoptedclass.getNodeType();
            attrOwnerElem = (Element) ((Attr) /*Node */adoptedclass).getOwnerElement();
            isSpecified = ((Attr) /*Node */adoptedclass).isSpecified();
            assertEquals("class", nodeName, "Documentadoptnode02Assert1");
            assertEquals(2, nodeType, "Documentadoptnode02Assert2");
            assertNull(attrOwnerElem, "Documentadoptnode02Assert3");
            assertTrue(isSpecified, "Documentadoptnode02Assert4");
            firstChild = (Text) adoptedclass.getFirstChild();
            assertNotNull(firstChild, "Documentadoptnode02Assert5");
            firstChildValue = firstChild.getNodeValue();

            if ("Y".equals(firstChildValue)) {
                secondChild = (EntityReference) firstChild.getNextSibling();
                assertNotNull(secondChild, "Documentadoptnode02Assert6");
                secondChildType = secondChild.getNodeType();
                assertEquals(5, secondChildType, "Documentadoptnode02Assert7");
                secondChildName = secondChild.getNodeName();
                assertEquals("alpha", secondChildName, "Documentadoptnode02Assert8");
            } else {
                assertEquals("Yα", nodeValue, "Documentadoptnode02Assert9");
            }

        }
    }
}

