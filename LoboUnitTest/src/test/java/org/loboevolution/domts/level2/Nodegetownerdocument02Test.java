
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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The method getOwnerDocument returns the Document object associated with this node
 * <p>
 * Create a new Document node.  Since this node is not used with any Document yet
 * verify if the ownerDocument is null.  Create a new element Node on the new Document
 * object.  Check the ownerDocument of the new element node.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#node-ownerDoc">http://www.w3.org/TR/DOM-Level-2-Core/core#node-ownerDoc</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class Nodegetownerdocument02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final Element newElem;
        final Document ownerDocDoc;
        final Document ownerDocElem;
        final DOMImplementation domImpl;
        final DocumentType docType;
        doc = sampleXmlFile("staff.xml");
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType("mydoc", null, null);
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "mydoc", docType);
        ownerDocDoc = newDoc.getOwnerDocument();
        assertNull(ownerDocDoc);
        newElem = newDoc.createElementNS("http://www.w3.org/DOM/Test", "myelem");
        ownerDocElem = newElem.getOwnerDocument();
        assertNotNull(ownerDocElem);
    }
}

