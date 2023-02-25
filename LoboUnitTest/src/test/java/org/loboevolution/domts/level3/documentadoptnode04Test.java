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
 * Invoke adoptNode on a new document to adopt a new namespace aware attribute node created by
 * this document.  Check if this attribute has been adopted successfully by verifying the nodeName,
 * namespaceURI, prefix, specified and ownerElement attributes of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode04Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr newAttr;
        Attr adoptedAttr;
        String nodeName;
        String nodeNamespaceURI;
        String nodePrefix;
        Node attrOwnerElem;
        boolean isSpecified;
        DocumentType nullDocType = null;

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
        adoptedAttr = (Attr) newDoc.adoptNode(newAttr);

        if ((adoptedAttr != null)) {
            nodeName = adoptedAttr.getNodeName();
            nodeNamespaceURI = adoptedAttr.getNamespaceURI();
            nodePrefix = adoptedAttr.getPrefix();
            attrOwnerElem = adoptedAttr.getOwnerElement();
            isSpecified = adoptedAttr.isSpecified();
            assertEquals("documentadoptnode04_nodeName", "xml:lang", nodeName);
            assertEquals("documentadoptnode04_namespaceURI", xmlNS, nodeNamespaceURI);
            assertEquals("documentadoptnode04_prefix", "xml", nodePrefix);
            assertNull("documentadoptnode04_ownerDoc", attrOwnerElem);
            assertTrue("documentadoptnode04_specified", isSpecified);
        }
    }
}

