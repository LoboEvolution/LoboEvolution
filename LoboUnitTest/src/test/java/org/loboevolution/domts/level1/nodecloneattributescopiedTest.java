
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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * If the cloneNode method is used to clone an
 * Element node, all the attributes of the Element are
 * copied along with their values.
 * <p>
 * Retrieve the last child of the second employee and invoke
 * the cloneNode method.   The
 * duplicate node returned by the method should copy the
 * attributes associated with this node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 */
public class nodecloneattributescopiedTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node addressNode;
        Element clonedNode;
        NamedNodeMap attributes;
        Node attributeNode;
        String attributeName;
        java.util.Collection result = new java.util.ArrayList();

        java.util.Collection expectedResult = new java.util.ArrayList();
        expectedResult.add("domestic");
        expectedResult.add("street");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        addressNode = elementList.item(1);
        clonedNode = (Element) addressNode.cloneNode(false);
        attributes = clonedNode.getAttributes();
        for (int indexN10065 = 0; indexN10065 < attributes.getLength(); indexN10065++) {
            attributeNode = attributes.item(indexN10065);
            attributeName = attributeNode.getNodeName();
            result.add(attributeName);
        }
        assertEquals("nodeCloneAttributesCopiedAssert1", expectedResult, result);
    }
}

