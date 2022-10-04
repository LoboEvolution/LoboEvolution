
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNull;

/**
 * The "getAttributes()" method invoked on a Comment
 * Node returns null.
 * Find any comment that is an immediate child of the root
 * and assert that Node.attributes is null.  Then create
 * a new comment node (in case they had been omitted) and
 * make the assertion.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=248">http://www.w3.org/Bugs/Public/show_bug.cgi?id=248</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=263">http://www.w3.org/Bugs/Public/show_bug.cgi?id=263</a>
 */
public class hc_nodecommentnodeattributesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        Comment commentNode;
        NodeList nodeList;
        NamedNodeMap attrList;
        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        nodeList = doc.getChildNodes();
        for (int indexN10043 = 0; indexN10043 < nodeList.getLength(); indexN10043++) {
            Node n = nodeList.item(indexN10043);
            if (n instanceof Comment) {
                commentNode = (Comment) n;
                nodeType = commentNode.getNodeType();

                if (nodeType == 8) {
                    attrList = commentNode.getAttributes();
                    assertNull("existingCommentAttributesNull", attrList);
                }
            }
        }
        commentNode = doc.createComment("This is a comment");
        attrList = commentNode.getAttributes();
        assertNull("createdCommentAttributesNull", attrList);
    }
}

