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


/**
 * Invoke the adoptNode method on this document using the "p" element with the default
 * Attribute "dir" as the source.  Verify if the node has been adopted correctly by
 * checking the nodeName of the adopted Element and by checking if the attribute was adopted.
 * Note the default attribute should be adopted in this case.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Node adoptedNode;
        Node employeeElem;
        Attr attrImp;
        String nodeName;
        String nullNSURI = null;

        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("p");
        employeeElem = childList.item(3);
        adoptedNode = doc.adoptNode(employeeElem);

        if ((adoptedNode != null)) {
            attrImp = ((Element) /*Node */adoptedNode).getAttributeNode("dir");
            nodeName = attrImp.getNodeName();
            assertEquals("documentadoptnode28", "dir", nodeName);
        }
    }
}

