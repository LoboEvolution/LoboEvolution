
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
 * The "getAttributes()" method invoked on an Element
 * Node returns a NamedNodeMap containing the attributes
 * of this node.
 * <p>
 * Retrieve the last child of the third employee and
 * invoke the "getAttributes()" method.   It should return
 * a NamedNodeMap containing the attributes of the Element
 * node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 */
public class nodeelementnodeattributesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        NamedNodeMap addrAttr;
        Node attrNode;
        String attrName;
        java.util.Collection attrList = new java.util.ArrayList();

        java.util.Collection expected = new java.util.ArrayList();
        expected.add("domestic");
        expected.add("street");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = (Element) elementList.item(2);
        addrAttr = testAddr.getAttributes();
        for (int indexN1005C = 0; indexN1005C < addrAttr.getLength(); indexN1005C++) {
            attrNode = addrAttr.item(indexN1005C);
            attrName = attrNode.getNodeName();
            attrList.add(attrName);
        }
        assertEquals("nodeElementNodeValueAssert1", expected, attrList);
    }
}

