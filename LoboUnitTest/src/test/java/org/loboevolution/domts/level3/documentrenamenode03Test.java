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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Invoke the renameNode method to rename a new attribute node to one whose
 * namespaceURI is http://www.w3.org/DOM/Test and name is pre0:fix1.
 * Check if this attribute has been renamed successfully by verifying the
 * nodeName, namespaceURI, nodeType attributes of the renamed node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Attr attr;
        Node renamedNode;
        String nodeName;
        String namespaceURI;
        String nullNSURI = null;

        doc = sampleXmlFile("hc_staff.xml");
        attr = doc.createAttributeNS(nullNSURI, "test");
        renamedNode = doc.renameNode(attr, "http://www.w3.org/DOM/Test", "pre0:fix1");
        nodeName = renamedNode.getNodeName();
        namespaceURI = renamedNode.getNamespaceURI();
        assertEquals("documentrenamenode03_nodeName", "pre0:fix1", nodeName);
        assertEquals("documentrenamenode02_namespaceURI", "http://www.w3.org/DOM/Test", namespaceURI);
    }
}
