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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Rename the fourth acronym element to svg:rect and verify the
 * nodeName, namespaceURI, nodeType attributes of the renamed node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        HTMLCollection childList;
        Node renamedclass;
        String nodeName;
        int nodeType;
        String namespaceURI;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(3);
        renamedclass = doc.renameNode(element, "http://www.w3.org/DOM/Test", "qnam:renamedNode");
        nodeName = renamedclass.getNodeName();
        namespaceURI = renamedclass.getNamespaceURI();
        nodeType = renamedclass.getNodeType();
        assertEquals("documentrenamenode15_nodeName", "qnam:renamedNode", nodeName);
        assertEquals("documentrenamenode15_nodeType", 1, nodeType);
        assertEquals("documentrenamenode15_nodeValue", "http://www.w3.org/DOM/Test", namespaceURI);
    }
}

