
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
import org.loboevolution.html.dom.Entity;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Entity.
 * <p>
 * Retrieve entity "ent4" from staffNS.xml document.
 * Invoke method importNode(importedNode,deep) on this document with deep as false.
 * Method should return a node of type Entity whose descendant is copied.
 * The returned node should belong to this document whose systemId is "staffNS.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode12Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        DocumentType doc1Type;
        NamedNodeMap entityList;
        Entity entity2;
        Entity entity1;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String entityName;
        Node child;
        String childName;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        doc1Type = aNewDoc.getDoctype();
        entityList = doc1Type.getEntities();
        assertNotNull("entitiesNotNull", entityList);
        entity2 = (Entity) entityList.getNamedItem("ent4");
        entity1 = (Entity) doc.importNode(entity2, true);
        ownerDocument = entity1.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("systemId", "staffNS.dtd", system);
        entityName = entity1.getNodeName();
        assertEquals("entityName", "ent4", entityName);
        child = entity1.getFirstChild();
        assertNotNull("notnull", child);
        childName = child.getNodeName();
        assertEquals("childName", "entElement1", childName);
    }
}

