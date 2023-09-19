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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * Adopt the class attribute node of the fourth acronym element.  Check if this attribute has been adopted
 * successfully by verifying the nodeName, nodeType, ownerElement, specified attributes and child nodes
 * of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode02Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Element attrOwnerElem;
        Element element;
        Attr attr;
        HTMLCollection childList;
        Node adoptedclass;
        String nodeName;
        int nodeType;
        String nodeValue;
        boolean isSpecified;
        DocumentType nullDocType = null;

        Text firstChild;
        String firstChildValue;
        EntityReference secondChild;
        int secondChildType;
        String secondChildName;
        Element docElem;
        String rootNS;
        String rootName;
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
            assertEquals("documentadoptnode02_nodeName", "class", nodeName);
            assertEquals("documentadoptnode02_nodeType", 2, nodeType);
            assertNull("documentadoptnode02_ownerDoc", attrOwnerElem);
            assertTrue("documentadoptnode02_specified", isSpecified);
            firstChild = (Text) adoptedclass.getFirstChild();
            assertNotNull("firstChildNotNull", firstChild);
            firstChildValue = firstChild.getNodeValue();

            if ("Y".equals(firstChildValue)) {
                secondChild = (EntityReference) firstChild.getNextSibling();
                assertNotNull("secondChildNotNull", secondChild);
                secondChildType = secondChild.getNodeType();
                assertEquals("secondChildIsEntityReference", 5, secondChildType);
                secondChildName = secondChild.getNodeName();
                assertEquals("secondChildIsEnt1Reference", "alpha", secondChildName);
            } else {
                assertEquals("documentadoptnode02_nodeValue", "Yα", nodeValue);
            }

        }
    }
}

