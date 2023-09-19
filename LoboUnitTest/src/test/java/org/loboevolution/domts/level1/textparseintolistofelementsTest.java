
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * Retrieve the textual data from the last child of the
 * second employee.   That node is composed of two
 * EntityReference nodes and two Text nodes.   After
 * the content node is parsed, the "address" Element
 * should contain four children with each one of the
 * EntityReferences containing one child.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-11C98490">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-11C98490</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-745549614">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-745549614</a>
 */
public class textparseintolistofelementsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node addressNode;
        NodeList childList;
        Node child;
        int length;
        String value;
        Node grandChild;
        java.util.List result = new java.util.ArrayList();

        java.util.List expectedNormal = new java.util.ArrayList();
        expectedNormal.add("1900 Dallas Road");
        expectedNormal.add(" Dallas, ");
        expectedNormal.add("Texas");
        expectedNormal.add("\n 98554");

        java.util.List expectedExpanded = new java.util.ArrayList();
        expectedExpanded.add("1900 Dallas Road Dallas, Texas\n 98554");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        addressNode = elementList.item(1);
        childList = addressNode.getChildNodes();
        length = childList.getLength();
        for (int indexN1007F = 0; indexN1007F < childList.getLength(); indexN1007F++) {
            child = childList.item(indexN1007F);
            value = child.getNodeValue();

            if ((value == null)) {
                grandChild = child.getFirstChild();
                assertNotNull("grandChildNotNull", grandChild);
                value = grandChild.getNodeValue();
                result.add(value);
            } else {
                result.add(value);
            }

        }

        if (length == 4) {
            assertEquals("assertEqNormal", expectedNormal, result);
        } else {
            assertEquals("assertEqCoalescing", expectedExpanded, result);
        }

    }
}

