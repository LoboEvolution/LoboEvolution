
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
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The method getOwnerDocument returns the Document object associated with this node
 * <p>
 * Create a new Document node.  Since this node is not used with any Document yet
 * verify if the ownerDocument is null.  Create a new element Node on the new Document
 * object.  Check the ownerDocument of the new element node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#node-ownerDoc">http://www.w3.org/TR/DOM-Level-2-Core/core#node-ownerDoc</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class nodegetownerdocument02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        Document newDoc;
        Element newElem;
        Document ownerDocDoc;
        Document ownerDocElem;
        DOMImplementation domImpl;
        DocumentType docType;
        String nullNS = null;

        doc = (DocumentImpl) sampleXmlFile("staff.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType("mydoc", nullNS, nullNS);
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "mydoc", docType);
        ownerDocDoc = newDoc.getOwnerDocument();
        assertNull("nodegetownerdocument02_1", ownerDocDoc);
        newElem = newDoc.createElementNS("http://www.w3.org/DOM/Test", "myelem");
        ownerDocElem = newElem.getOwnerDocument();
        assertNotNull("nodegetownerdocument02_2", ownerDocElem);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodegetownerdocument02";
    }
}

