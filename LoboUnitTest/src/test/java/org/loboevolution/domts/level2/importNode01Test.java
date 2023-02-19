
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
import org.loboevolution.html.node.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Attr.
 * The ownerElement is set to null. Specified flag is set to true.
 * Children is imported.
 * <p>
 * Create a new attribute whose name is "elem:attr1" in a different document.
 * Create a child Text node with value "importedText" for the attribute node above.
 * Invoke method importNode(importedNode,deep) on this document with
 * importedNode being the newly created attribute.
 * Method should return a node whose name matches "attr1" and a child node
 * whose value equals "importedText".
 * The returned node should belong to this document whose systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        Attr newAttr;
        Node aNode;
        Document ownerDocument;
        Node attrOwnerElement;
        DocumentType docType;
        String system;
        boolean specified;
        String nodeName;
        String childValue;

        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        newAttr = aNewDoc.createAttribute( "attr1");
        newAttr.setNodeValue("importedText");
        aNode = doc.importNode(newAttr, false);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertNotNull("aNode", aNode);
        assertEquals("systemId", "staffNS.dtd", system);
        attrOwnerElement = ((Attr) aNode).getOwnerElement();
        assertNull("ownerElement", attrOwnerElement);
        specified = ((Attr) aNode).isSpecified();
        assertTrue("specified", specified);
        nodeName = aNode.getNodeName();
        assertEquals("nodeName", "attr1", nodeName);
        childValue = aNode.getNodeValue();
        assertEquals("childValue", "importedText", childValue);
    }
}