
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Entity_Reference.
 * Only the EntityReference is copied, regardless of deep's value.
 * <p>
 * Create an entity reference whose name is "entRef1" in a different document.
 * Give it value "entRef1Value".
 * Invoke method importNode(importedNode,deep) on this document with importedNode
 * being "entRef1".
 * Method should return a node of type Entity_Reference (whose value is null) that
 * belongs to this document whose systemId is "staff.dtd".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode10Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        EntityReference entRef;
        Node aNode;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String name;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        entRef = aNewDoc.createEntityReference("entRef1");
        assertNotNull("createdEntRefNotNull", entRef);
        entRef.setNodeValue("entRef1Value");
        aNode = doc.importNode(entRef, false);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("systemId", "staffNS.dtd", system);
        name = aNode.getNodeName();
        assertEquals("nodeName", "entRef1", name);
    }
}

