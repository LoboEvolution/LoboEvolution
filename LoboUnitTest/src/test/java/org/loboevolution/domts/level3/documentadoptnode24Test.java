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

