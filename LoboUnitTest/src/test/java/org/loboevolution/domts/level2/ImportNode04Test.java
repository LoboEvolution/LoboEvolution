
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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Document_Fragment.
 * <p>
 * Create a DocumentFragment in a different document.
 * Create a Comment child node for the Document Fragment.
 * Invoke method importNode(importedNode,deep) on this document
 * with importedNode being the newly created DocumentFragment.
 * Method should return a node of type DocumentFragment whose child has
 * comment value "descendant1".

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class ImportNode04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document aNewDoc;
        final DocumentFragment docFrag;
        final Comment comment;
        Node aNode;
        final NodeList children;
        final Node child;
        final String childValue;
        doc = sampleXmlFile("staff.xml");
        aNewDoc = sampleXmlFile("staff.xml");
        docFrag = aNewDoc.createDocumentFragment();
        comment = aNewDoc.createComment("descendant1");
        docFrag.appendChild(comment);
        aNode = doc.importNode(docFrag, true);
        children = aNode.getChildNodes();
        assertEquals(1, children.getLength());
        child = aNode.getFirstChild();
        childValue = child.getNodeValue();
        assertEquals("descendant1", childValue);
    }
}

