/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * Invoke adoptNode on a new document to adopt the a new Attribute node having a Text and an EntityReference
 * child.  Check if this attribute has been adopted successfully by verifying the nodeName, namespaceURI, prefix,
 * specified and ownerElement attributes of the adopted node.  Also verify the ownerDocument attribute
 * of the adopted node and the adopted children of the attribute node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode06Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr newAttr;
        Text newText;
        EntityReference newEntRef;
        Attr adoptedAttr;
        Text adoptText;
        EntityReference adoptEntRef;
        NodeList nodeList;
        String nodeName;
        String nodeNamespaceURI;
        String nodePrefix;
        Node attrOwnerElem;
        boolean isSpecified;
        String adoptedTextNodeValue;
        String adoptedEntRefNodeValue;
        DocumentType nullDocType = null;

        Node appendedChild;
        Element docElem;
        String rootNS;
        String rootName;
        String xmlNS = "http://www.w3.org/XML/1998/namespace";
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        newAttr = doc.createAttributeNS(xmlNS, "xml:lang");
        newText = doc.createTextNode("Text Node");
        newEntRef = doc.createEntityReference("alpha");
        appendedChild = newAttr.appendChild(newText);
        appendedChild = newAttr.appendChild(newEntRef);
        adoptedAttr = (Attr) newDoc.adoptNode(newAttr);

        if ((adoptedAttr != null)) {
            nodeName = adoptedAttr.getNodeName();
            nodeNamespaceURI = adoptedAttr.getNamespaceURI();
            nodePrefix = adoptedAttr.getPrefix();
            attrOwnerElem = adoptedAttr.getOwnerElement();
            isSpecified = adoptedAttr.isSpecified();
            assertEquals("documentadoptnode06_nodeName", "xml:lang", nodeName);
            assertEquals("documentadoptnode06_namespaceURI", xmlNS, nodeNamespaceURI);
            assertEquals("documentadoptnode06_prefix", "xml", nodePrefix);
            assertNull("documentadoptnode06_ownerDoc", attrOwnerElem);
            assertTrue("documentadoptnode06_specified", isSpecified);
            nodeList = adoptedAttr.getChildNodes();
            adoptText = (Text) nodeList.item(0);
            adoptEntRef = (EntityReference) nodeList.item(1);
            adoptedTextNodeValue = adoptText.getNodeValue();
            adoptedEntRefNodeValue = adoptEntRef.getNodeName();
            assertEquals("documentadoptnode06_TextNodeValue", "Text Node", adoptedTextNodeValue);
            assertEquals("documentadoptnode06_EntRefNodeValue", "alpha", adoptedEntRefNodeValue);
        }
    }
}