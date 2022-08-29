
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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * The "createDocument(namespaceURI,qualifiedName,doctype)" method for a
 * DOMImplementation should return a new xml Document object of the
 * specified type with its document element given that all parameters are
 * valid and correctly formed.
 * <p>
 * Invoke method createDocument(namespaceURI,qualifiedName,doctype) on
 * this domimplementation. namespaceURI is "http://www.ecommerce.org/schema"
 * qualifiedName is "y:x" and doctype is null.
 * Method should return a new xml Document as specified by the listed parameters.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class createDocument07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.ecommerce.org/schema";
        String qualifiedName = "y:x";
        Document doc;
        DocumentType docType = null;

        DOMImplementation domImpl;
        Document aNewDoc;
        String nodeName;
        String nodeValue;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        aNewDoc = domImpl.createDocument(namespaceURI, qualifiedName, docType);
        nodeName = aNewDoc.getNodeName();
        nodeValue = aNewDoc.getNodeValue();
        assertEquals("nodeName", "[object HTMLDocument]", nodeName);
        assertNull("nodeValue", nodeValue);
    }
}

