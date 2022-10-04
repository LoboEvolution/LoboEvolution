
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The method createDocumentType with valid values for qualifiedName, publicId and
 * systemId should create an empty DocumentType node.
 * <p>
 * Invoke createDocument on this DOMImplementation with a valid qualifiedName and different
 * publicIds and systemIds.  Check if the the DocumentType node was created with its
 * ownerDocument attribute set to null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class domimplementationcreatedocumenttype01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        DOMImplementation domImpl;
        DocumentType newDocType;
        Document ownerDocument;
        String qualifiedName = "test:root";
        String publicId;
        String systemId;
        java.util.List publicIds = new java.util.ArrayList();
        publicIds.add("1234");
        publicIds.add("test");

        java.util.List systemIds = new java.util.ArrayList();
        systemIds.add("");
        systemIds.add("test");

        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        for (int indexN1005D = 0; indexN1005D < publicIds.size(); indexN1005D++) {
            publicId = (String) publicIds.get(indexN1005D);
            for (int indexN10061 = 0; indexN10061 < systemIds.size(); indexN10061++) {
                systemId = (String) systemIds.get(indexN10061);
                newDocType = domImpl.createDocumentType(qualifiedName, publicId, systemId);
                assertNotNull("domimplementationcreatedocumenttype01_newDocType", newDocType);
                ownerDocument = newDocType.getOwnerDocument();
                assertNull("domimplementationcreatedocumenttype01_ownerDocument", ownerDocument);
            }
        }
    }
}

