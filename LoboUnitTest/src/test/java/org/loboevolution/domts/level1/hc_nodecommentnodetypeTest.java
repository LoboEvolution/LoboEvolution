
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
 * The "getNodeType()" method for a Comment Node
 * returns the constant value 8.
 * <p>
 * Retrieve the nodes from the document and check for
 * a comment node and invoke the "getNodeType()" method.   This should
 * return 8.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=248">http://www.w3.org/Bugs/Public/show_bug.cgi?id=248</a>
 */
public class hc_nodecommentnodetypeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList testList;
        Node commentNode;
        String commentNodeName;
        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        testList = doc.getChildNodes();
        for (int indexN10040 = 0; indexN10040 < testList.getLength(); indexN10040++) {
            commentNode = testList.item(indexN10040);
            commentNodeName = commentNode.getNodeName();

            if ("#comment".equals(commentNodeName)) {
                nodeType = commentNode.getNodeType();
                assertEquals("existingCommentNodeType", 8, nodeType);
            }
        }
        commentNode = doc.createComment("This is a comment");
        nodeType = commentNode.getNodeType();
        assertEquals("createdCommentNodeType", 8, nodeType);
    }
}

