
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

