
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Attr.
 * The ownerElement is set to null. Specified flag is set to true.
 * Children is imported.
 * <p>
 * Create a new attribute whose name is "elem:attr1" in a different document.
 * Create a child Text node with value "importedText" for the attribute node above.
 * Invoke method importNode(importedNode,deep) on this document with
 * importedNode being the newly created attribute.
 * Method should return a node whose name matches "attr1" and a child node
 * whose value equals "importedText".
 * The returned node should belong to this document whose systemId is "staff.dtd"
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class ImportNode01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document aNewDoc;
        final Attr newAttr;
        final Node aNode;
        final Document ownerDocument;
        final Node attrOwnerElement;
        final DocumentType docType;
        final String system;
        final boolean specified;
        final String nodeName;
        final String childValue;

        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staffNS.xml");
        newAttr = aNewDoc.createAttribute( "attr1");
        newAttr.setNodeValue("importedText");
        aNode = doc.importNode(newAttr, false);
        ownerDocument = aNode.getOwnerDocument();
        docType = ownerDocument.getDoctype();
        system = docType.getSystemId();
        assertNotNull(aNode);
        assertEquals("staffNS.dtd", system);
        attrOwnerElement = ((Attr) aNode).getOwnerElement();
        assertNull(attrOwnerElement);
        specified = ((Attr) aNode).isSpecified();
        assertTrue(specified);
        nodeName = aNode.getNodeName();
        assertEquals("attr1", nodeName);
        childValue = aNode.getNodeValue();
        assertEquals("importedText", childValue);
    }
}