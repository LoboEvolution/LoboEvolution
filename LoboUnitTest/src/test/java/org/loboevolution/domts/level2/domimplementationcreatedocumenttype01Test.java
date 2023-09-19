
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * Invoke createDocument on this DOMImplementation with a valid qualifiedName and different
 * publicIds and systemIds. Check if the DocumentType node was created with its
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
        Document doc;
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

        doc = sampleXmlFile("staffNS.xml");
        
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

