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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * The adoptNode method changes the ownerDocument of a node, its children, as well as the
 * attached attribute nodes if there are any. If the node has a parent it is first removed
 * from its parent child list.
 * Invoke the adoptNode method on this Document with the source node being an existing attribute
 * that is a part of this Document.   Verify that the returned adopted node's nodeName, nodeValue
 * and nodeType are as expected and that the ownerElement attribute of the returned attribute node
 * was set to null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode21Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element attrOwnerElem;
        Element element;
        Attr attr;
        HTMLCollection childList;
        Node adoptedTitle;
        String nodeName;
        int nodeType;
        String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(0);
        attr = element.getAttributeNode("title");
        adoptedTitle = doc.adoptNode(attr);
        nodeName = adoptedTitle.getNodeName();
        nodeValue = adoptedTitle.getNodeValue();
        nodeType = adoptedTitle.getNodeType();
        attrOwnerElem = (Element) ((Attr) adoptedTitle).getOwnerElement();
        assertEquals("documentadoptnode21_nodeName", "title", nodeName);
        assertEquals("documentadoptnode21_nodeType", 2, nodeType);
        assertEquals("documentadoptnode21_nodeValue", "Yes", nodeValue);
        assertNull("documentadoptnode21_ownerDoc", attrOwnerElem);
    }
}

