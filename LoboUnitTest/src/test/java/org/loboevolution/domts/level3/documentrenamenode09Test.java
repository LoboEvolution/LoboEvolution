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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The method renameNode renames an existing node. When the specified node was created
 * from a different document than this document, a WRONG_DOCUMENT_ERR exception is thrown.
 * <p>
 * Invoke the renameNode method on a new Document node to rename a new attribute node
 * created in the original Document, but later adopted by this new document node.  The
 * ownerDocument attribute of this attribute has now changed, such that the attribute node is considered to
 * be created from this new document node. Verify that no exception is thrown upon renaming and verify
 * the new nodeName of this attribute node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr attr;
        Node renamedNode;
        Node adopted;
        DocumentType nullDocType = null;

        String attrNodeName;
        doc = sampleXmlFile("hc_staff.xml");
        
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom:newD", nullDocType);
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test", "test");
        adopted = newDoc.adoptNode(attr);
        renamedNode = newDoc.renameNode(attr, "http://www.w3.org/2000/xmlns/", "xmlns:xmlns");
        attrNodeName = renamedNode.getNodeName();
        assertEquals("documentrenamenode09_1", "xmlns:xmlns", attrNodeName);
    }
}

