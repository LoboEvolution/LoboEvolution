
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The string returned by the "getNodeName()" method for a
 * Comment Node is "#comment".
 * <p>
 * Retrieve the Comment node in the XML file
 * and check the string returned by the "getNodeName()"
 * method.   It should be equal to "#comment".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1728279322</a>
 */
public class NodecommentnodenameTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final NodeList elementList;
        Node commentNode;
        int nodeType;
        String commentNodeName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getChildNodes();
        for (int indexN10040 = 0; indexN10040 < elementList.getLength(); indexN10040++) {
            commentNode = elementList.item(indexN10040);
            nodeType = commentNode.getNodeType();

            if (nodeType == 8) {
                commentNodeName = commentNode.getNodeName();
                assertEquals("#comment", commentNodeName, "NodecommentnodenameAssert1");
            }
        }
    }
}

