
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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true/false, import a entity nodes ent2 and ent6
 * from this document to a new document object.  Verify if the nodes have been
 * imported correctly by checking the nodeNames of the imported nodes and public and system ids.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode19Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        DocumentType docTypeNull = null;

        Document docImp;
        DOMImplementation domImpl;
        DocumentType docType;
        NamedNodeMap nodeMap;
        EntityReference entity2;
        EntityReference entity6;
        EntityReference entityImp2;
        EntityReference entityImp6;
        String nodeName;
        String systemId;
        String notationName;
        String nodeNameImp;
        String systemIdImp;
        String notationNameImp;
        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        docType = doc.getDoctype();
        docImp = domImpl.createDocument("http://www.w3.org/DOM/Test", "a:b", docTypeNull);
        nodeMap = docType.getEntities();
        assertNotNull("entitiesNotNull", nodeMap);
        entity2 = (EntityReference) nodeMap.getNamedItem("ent2");
        entity6 = (EntityReference) nodeMap.getNamedItem("ent6");
        entityImp2 = (EntityReference) docImp.importNode(entity2, false);
        entityImp6 = (EntityReference) docImp.importNode(entity6, true);
        nodeName = entity2.getNodeName();
        nodeNameImp = entityImp2.getNodeName();
        assertEquals("documentimportnode19_Ent2NodeName", nodeName, nodeNameImp);
        nodeName = entity6.getNodeName();
        nodeNameImp = entityImp6.getNodeName();
        assertEquals("documentimportnode19_Ent6NodeName", nodeName, nodeNameImp);
        systemId = entity2.getSystemId();
        systemIdImp = entityImp2.getSystemId();
        assertEquals("documentimportnode19_Ent2SystemId", systemId, systemIdImp);
        systemId = entity6.getSystemId();
        systemIdImp = entityImp6.getSystemId();
        assertEquals("documentimportnode19_Ent6SystemId", systemId, systemIdImp);
        notationName = entity2.getNotationName();
        notationNameImp = entityImp2.getNotationName();
        assertEquals("documentimportnode19_Ent2NotationName", notationName, notationNameImp);
        notationName = entity6.getNotationName();
        notationNameImp = entityImp6.getNotationName();
        assertEquals("documentimportnode19_Ent6NotationName", notationName, notationNameImp);
    }

}

