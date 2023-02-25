
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The method createDocumentType with valid values for qualifiedName, publicId and
 * systemId should create an empty DocumentType node.
 * <p>
 * Invoke createDocument on this DOMImplementation with a different valid qualifiedNames
 * and a valid publicId and systemId.  Check if the the DocumentType node was created
 * with its ownerDocument attribute set to null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 */
public class domimplementationcreatedocumenttype02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        DocumentType newDocType;
        Document ownerDocument;
        String publicId = "http://www.w3.org/DOM/Test/dom2.dtd";
        String systemId = "dom2.dtd";
        String qualifiedName;
        java.util.List qualifiedNames = new java.util.ArrayList();
        qualifiedNames.add("_:_");
        qualifiedNames.add("_:h0");
        qualifiedNames.add("_:test");
        qualifiedNames.add("_:_.");
        qualifiedNames.add("_:a-");
        qualifiedNames.add("l_:_");
        qualifiedNames.add("ns:_0");
        qualifiedNames.add("ns:a0");
        qualifiedNames.add("ns0:test");
        qualifiedNames.add("ns:EEE.");
        qualifiedNames.add("ns:_-");
        qualifiedNames.add("a.b:c");
        qualifiedNames.add("a-b:c.j");
        qualifiedNames.add("a-b:c");

        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        for (int indexN10077 = 0; indexN10077 < qualifiedNames.size(); indexN10077++) {
            qualifiedName = (String) qualifiedNames.get(indexN10077);
            newDocType = domImpl.createDocumentType(qualifiedName, publicId, systemId);
            assertNotNull("domimplementationcreatedocumenttype02_newDocType", newDocType);
            ownerDocument = newDocType.getOwnerDocument();
            assertNull("domimplementationcreatedocumenttype02_ownerDocument", ownerDocument);
        }
    }
}

