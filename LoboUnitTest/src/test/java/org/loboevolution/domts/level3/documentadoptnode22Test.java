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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Invoke the adoptNode method on this document with the documentElement as the source.
 * Verify if the node has been adopted correctly by its nodeName.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode22Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element docElement;
        Node adoptedNode;
        boolean success;
        String nodeNameOrig;
        String nodeName;
        doc = sampleXmlFile("hc_staff.xml");
        docElement = doc.getDocumentElement();
        adoptedNode = doc.adoptNode(docElement);

        if ((adoptedNode != null)) {
            success = adoptedNode.hasChildNodes();
            assertTrue("documentadoptnode22_1", success);
            nodeName = adoptedNode.getNodeName();
            nodeNameOrig = docElement.getNodeName();
            assertEquals("documentadoptnode22_2", nodeName, nodeNameOrig);
        }
    }
}

