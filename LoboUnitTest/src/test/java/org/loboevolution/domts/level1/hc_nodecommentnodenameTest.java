
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertEquals;


/**
 * The string returned by the "getNodeName()" method for a
 * Comment Node is "#comment".
 * <p>
 * Retrieve the Comment node in the XML file
 * and check the string returned by the "getNodeName()"
 * method.   It should be equal to "#comment".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=248">http://www.w3.org/Bugs/Public/show_bug.cgi?id=248</a>
 */
public class hc_nodecommentnodenameTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList elementList;
        Node commentNode;
        int nodeType;
        String commentNodeName;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getChildNodes();
        for (int indexN10044 = 0; indexN10044 < elementList.getLength(); indexN10044++) {
            commentNode = elementList.item(indexN10044);
            nodeType = commentNode.getNodeType();

            if (nodeType == 8) {
                commentNodeName = commentNode.getNodeName();
                assertEquals("existingNodeName", "#comment", commentNodeName);
            }
        }
        commentNode = doc.createComment("This is a comment");
        commentNodeName = commentNode.getNodeName();
        assertEquals("createdNodeName", "#comment", commentNodeName);
    }
}

