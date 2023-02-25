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

import static org.junit.Assert.assertEquals;


/**
 * Invoke the renameNode method on a new document node to rename a new attribute node
 * to one whose namespaceURI is http://www.w3.org/XML/1998/namespace and name is xml:dom.
 * Check if this attribute has been renamed successfully by verifying the
 * nodeName and namespaceURI attributes of the renamed node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr attr;
        Node renamedNode;
        String nodeName;
        String namespaceURI;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        attr = newDoc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        renamedNode = newDoc.renameNode(attr, "http://www.w3.org/XML/1998/namespace", "xml:dom");
        nodeName = renamedNode.getNodeName();
        namespaceURI = renamedNode.getNamespaceURI();
        assertEquals("documentrenamenode07_nodeName", "xml:dom", nodeName);
        assertEquals("documentrenamenode07_namespaceURI", "http://www.w3.org/XML/1998/namespace", namespaceURI);
    }
}

