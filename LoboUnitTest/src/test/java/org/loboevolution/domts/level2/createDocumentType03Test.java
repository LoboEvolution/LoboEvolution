
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * The "createDocumentType(qualifiedName,publicId,systemId)" method for a
 * DOMImplementation should return a new DocumentType node
 * given that qualifiedName is valid and correctly formed.
 * <p>
 * Invoke method createDocumentType(qualifiedName,publicId,systemId) on
 * this domimplementation with qualifiedName "prefix:myDoc".
 * Method should return a new DocumentType node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 */
public class createDocumentType03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://ecommerce.org/schema";
        String qualifiedName = "prefix:myDoc";
        String publicId = "http://www.localhost.com";
        String systemId = "myDoc.dtd";
        DocumentImpl doc;
        DOMImplementation domImpl;
        DocumentType newType = null;

        String nodeName;
        String nodeValue;
        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        newType = domImpl.createDocumentType(qualifiedName, publicId, systemId);
        nodeName = newType.getNodeName();
        assertEquals("nodeName", "prefix:myDoc", nodeName);
        nodeValue = newType.getNodeValue();
        assertNull("nodeValue", nodeValue);
    }
}

