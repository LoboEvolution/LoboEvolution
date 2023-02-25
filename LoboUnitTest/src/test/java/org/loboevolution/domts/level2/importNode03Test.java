
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
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Comment.
 * <p>
 * Create a comment node with value being the string "this is a comment" in
 * a different document.  Invoke method importNode(importedNode,deep) on
 * this document.  Method should return a comment node whose value matches
 * the above string. The returned node should belong to this document whose
 * systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class importNode03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        Comment comment;
        Node aNode;
        Document ownerDocument;
        DocumentType docType;
        String system;
        String value;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        comment = aNewDoc.createComment("this is a comment");
        aNode = doc.importNode(comment, false);
        ownerDocument = aNode.getOwnerDocument();
        assertNotNull("ownerDocumentNotNull", ownerDocument);
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("systemId", "staffNS.dtd", system);
        value = aNode.getNodeValue();
        assertEquals("nodeValue", "this is a comment", value);
    }
}

