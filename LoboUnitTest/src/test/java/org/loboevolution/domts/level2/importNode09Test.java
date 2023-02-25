
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Entity.
 * <p>
 * Retrieve entity "ent6" from staffNS.xml document.
 * Invoke method importNode(importedNode,deep) on this document.
 * Method should return a node of type Entity whose publicId, systemId and
 * notationName attributes are copied.
 * The returned node should belong to this document whose systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode09Test extends LoboUnitTest {

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
        EntityReference entity2;
        EntityReference entity1;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String entityName;
        String publicVal;
        String notationName;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        docType = aNewDoc.getDoctype();
        entityList = docType.getEntities();
        assertNotNull("entitiesNotNull", entityList);
        entity2 = (EntityReference) entityList.getNamedItem("ent6");
        entity1 = (EntityReference) doc.importNode(entity2, false);
        ownerDocument = entity1.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("dtdSystemId", "staffNS.dtd", system);
        entityName = entity1.getNodeName();
        assertEquals("entityName", "ent6", entityName);
        publicVal = entity1.getPublicId();
        assertEquals("entityPublicId", "uri", publicVal);
        system = entity1.getSystemId();
        assertEquals("entitySystemId", "file", system);
        notationName = entity1.getNotationName();
        assertEquals("notationName", "notation2", notationName);
    }
}

