
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

import static org.junit.Assert.assertEquals;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Document_Fragment.
 * <p>
 * Create a DocumentFragment in a different document.
 * Create a Comment child node for the Document Fragment.
 * Invoke method importNode(importedNode,deep) on this document
 * with importedNode being the newly created DocumentFragment.
 * Method should return a node of type DocumentFragment whose child has
 * comment value "descendant1".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        DocumentFragment docFrag;
        Comment comment;
        Node aNode;
        NodeList children;
        Node child;
        String childValue;
        doc = sampleXmlFile("staff.xml");
        aNewDoc = sampleXmlFile("staff.xml");
        docFrag = aNewDoc.createDocumentFragment();
        comment = aNewDoc.createComment("descendant1");
        aNode = docFrag.appendChild(comment);
        aNode = doc.importNode(docFrag, true);
        children = aNode.getChildNodes();
        assertEquals( "throw_Size", 1, children);
        child = aNode.getFirstChild();
        childValue = child.getNodeValue();
        assertEquals("descendant1", "descendant1", childValue);
    }
}

