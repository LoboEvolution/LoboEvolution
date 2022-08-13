
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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Notation.
 * <p>
 * Retrieve notation named "notation1" from document staffNS.xml.
 * Invoke method importNode(importedNode,deep) where importedNode
 * contains the retrieved notation and deep is false.  Method should
 * return a node of type notation whose name is "notation1".
 * The returned node should belong to this document whose systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode13Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        DocumentType doc1Type;
        NamedNodeMap notationList;
        Notation notation;
        Notation aNode;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String publicVal;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        doc1Type = aNewDoc.getDoctype();
        notationList = doc1Type.getNotations();
        assertNotNull("notationsNotNull", notationList);
        notation = (Notation) notationList.getNamedItem("notation1");
        aNode = (Notation) doc.importNode(notation, false);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("systemId", "staffNS.dtd", system);
        publicVal = aNode.getPublicId();
        assertEquals("publicId", "notation1File", publicVal);
        system = aNode.getSystemId();
        assertNull("notationSystemId", system);
    }
}

