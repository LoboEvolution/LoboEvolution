
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Element.
 * <p>
 * Retrieve element "address" from staffNS.xml document.
 * Invoke method importNode(importedNode,deep) on this document
 * with importedNode being the element from above and deep is false.
 * Method should return an element node whose name matches "address"
 * and whose children are not imported. The returned node should
 * belong to this document whose systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        Element element;
        Node aNode;
        boolean hasChild;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String name;
        HTMLCollection addresses;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        addresses = aNewDoc.getElementsByTagName("address");
        element = (Element) addresses.item(0);
        assertNotNull("empAddressNotNull", element);
        aNode = doc.importNode(element, false);
        hasChild = aNode.hasChildNodes();
        assertFalse("hasChild", hasChild);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("dtdSystemId", "staffNS.dtd", system);
        name = aNode.getNodeName();
        assertEquals("nodeName", "ADDRESS", name);
    }
}

