
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Element.
 * <p>
 * Retrieve element "address" from staffNS.xml document.
 * Invoke method importNode(importedNode,deep) on this document
 * with importedNode being the element from above and deep is false.
 * Method should return an element node whose name matches "address"
 * and whose children are not imported. The returned node should
 * belong to this document whose systemId is "staff.dtd"

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class ImportNode05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document aNewDoc;
        final Element element;
        final Node aNode;
        final boolean hasChild;
        final Document ownerDocument;
        final DocumentType docType;
        final String system;
        final String name;
        final HTMLCollection addresses;
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        addresses = aNewDoc.getElementsByTagName("address");
        element = (Element) addresses.item(0);
        assertNotNull(element);
        aNode = doc.importNode(element, false);
        hasChild = aNode.hasChildNodes();
        assertFalse(hasChild);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertEquals("staffNS.dtd", system);
        name = aNode.getNodeName();
        assertEquals("ADDRESS", name);
    }
}

