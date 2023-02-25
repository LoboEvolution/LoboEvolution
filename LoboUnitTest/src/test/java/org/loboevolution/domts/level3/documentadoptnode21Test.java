/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

