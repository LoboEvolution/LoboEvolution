
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

