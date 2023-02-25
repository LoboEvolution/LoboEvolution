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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertEquals;


/**
 * Invoke the adoptNode method on this document with the first acronym element node of this
 * Document as the source.  Verify if the node has been adopted correctly by checking the
 * length of the this elements childNode list before and after.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode23Test extends LoboUnitTest {
    /**
     * Runs the test case.
     *
     * @throws Throwable Any uncaught exception causes test to fail
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Node adoptedNode;
        Node acronymElem;
        int acronymElemLen;
        int adoptedLen;
        NodeList acronymElemChild;
        NodeList adoptedNodeChild;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        acronymElem = childList.item(0);
        adoptedNode = doc.adoptNode(acronymElem);

        if ((adoptedNode != null)) {
            acronymElemChild = acronymElem.getChildNodes();
            acronymElemLen = acronymElemChild.getLength();
            adoptedNodeChild = adoptedNode.getChildNodes();
            adoptedLen = adoptedNodeChild.getLength();
            assertEquals("documentadoptnode23", adoptedLen, acronymElemLen);
        }
    }
}

