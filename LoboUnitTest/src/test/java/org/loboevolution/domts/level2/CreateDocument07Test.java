
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The "createDocument(namespaceURI,qualifiedName,doctype)" method for a
 * DOMImplementation should return a new xml Document object of the
 * specified type with its document element given that all parameters are
 * valid and correctly formed.
 * <p>
 * Invoke method createDocument(namespaceURI,qualifiedName,doctype) on
 * this domimplementation. namespaceURI is "<a href="http://www.ecommerce.org/schema">...</a>"
 * qualifiedName is "y:x" and doctype is null.
 * Method should return a new xml Document as specified by the listed parameters.
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class CreateDocument07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final String namespaceURI = "http://www.ecommerce.org/schema";
        final String qualifiedName = "y:x";
        final Document doc;
        final DOMImplementation domImpl;
        final Document aNewDoc;
        final String nodeName;
        final String nodeValue;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        aNewDoc = domImpl.createDocument(namespaceURI, qualifiedName, null);
        nodeName = aNewDoc.getNodeName();
        nodeValue = aNewDoc.getNodeValue();
        assertEquals("[object HTMLDocument]", nodeName);
        assertNull(nodeValue);
    }
}

