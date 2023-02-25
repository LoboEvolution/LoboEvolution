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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The method renameNode renames an existing node. When the specified node was created
 * from a different document than this document, a WRONG_DOCUMENT_ERR exception is thrown.
 * <p>
 * Invoke the renameNode method on a new Document node to rename a new attribute node
 * created in the original Document, but later adopted by this new document node.  The
 * ownerDocument attribute of this attribute has now changed, such that the attribute node is considered to
 * be created from this new document node. Verify that no exception is thrown upon renaming and verify
 * the new nodeName of this attribute node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr attr;
        Node renamedNode;
        Node adopted;
        DocumentType nullDocType = null;

        String attrNodeName;
        doc = sampleXmlFile("hc_staff.xml");
        
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom:newD", nullDocType);
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test", "test");
        adopted = newDoc.adoptNode(attr);
        renamedNode = newDoc.renameNode(attr, "http://www.w3.org/2000/xmlns/", "xmlns:xmlns");
        attrNodeName = renamedNode.getNodeName();
        assertEquals("documentrenamenode09_1", "xmlns:xmlns", attrNodeName);
    }
}

