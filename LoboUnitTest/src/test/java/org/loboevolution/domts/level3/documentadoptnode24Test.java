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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The adoptNode method changes the ownerDocument of a node, its children, as well as the
 * attached attribute nodes if there are any. If the node has a parent it is first removed
 * from its parent child list.
 * For Element Nodes, specified attribute nodes of the source element are adopted, Default
 * attributes are discarded and descendants of the source element are recursively adopted.
 * Invoke the adoptNode method on a new document with the first code element node of this
 * Document as the source.  Verify if the node has been adopted correctly by checking the
 * length of the this elements childNode list before and after.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode24Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        HTMLCollection childList;
        Node adoptedNode;
        Element codeElem;
        NodeList codeElemChildren;
        NodeList adoptedChildren;
        int codeElemLen;
        int adoptedLen;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom:test", nullDocType);
        childList = doc.getElementsByTagNameNS("*", "code");
        codeElem = (Element) childList.item(0);
        adoptedNode = newDoc.adoptNode(codeElem);
        codeElemChildren = codeElem.getChildNodes();
        adoptedChildren = adoptedNode.getChildNodes();
        codeElemLen = codeElemChildren.getLength();
        adoptedLen = adoptedChildren.getLength();
        assertEquals("documentadoptnode24", adoptedLen, codeElemLen);
    }
}

