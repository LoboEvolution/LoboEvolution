
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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Entity_Reference.
 * Only the EntityReference is copied, regardless of deep's value.
 * If the Document provides a definition for the entity name, its value is assigned.
 * <p>
 * Create an entity reference whose name is "ent3" in a different document.
 * Invoke method importNode(importedNode,deep) on this document with importedNode
 * being "ent3".
 * Method should return a node of type Entity_Reference whose first child's value is "Texas" as defined
 * in this document.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode11Test extends LoboUnitTest {

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
        String name;
        Node child;
        String childValue;
        doc = sampleXmlFile("staff.xml");
        aNewDoc = sampleXmlFile("staff.xml");
        entRef = aNewDoc.createEntityReference("ent3");
        assertNotNull("createdEntRefNotNull", entRef);
        aNode = doc.importNode(entRef, true);
        name = aNode.getNodeName();
        assertEquals("entityName", "ent3", name);
        child = aNode.getFirstChild();
        assertNotNull("child", child);
        childValue = child.getNodeValue();
        assertEquals("childValue", "Texas", childValue);
    }
}
