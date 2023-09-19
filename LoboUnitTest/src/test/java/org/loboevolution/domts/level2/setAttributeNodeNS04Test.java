
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "setAttributeNodeNS(newAttr)" adds a new attribute.
 * If an attribute with that local name and that namespaceURI is already
 * present in the element, it is replaced by the new one.
 * <p>
 * Retrieve the first address element and add a new attribute
 * to the element.  Since an attribute with the same local name
 * and namespaceURI already exists, it is replaced by the new one and
 * returns the replaced "Attr" node.
 * This test uses the "createAttributeNS(namespaceURI,localName)
 * method from the Document interface to create the new attribute to add.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095</a>
 */
public class setAttributeNodeNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        HTMLCollection elementList;
        Document doc;
        Node testAddr;
        Attr newAttr;
        Attr newAddrAttr;
        String newName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        newAttr = doc.createAttributeNS("http://www.nist.gov", "xxx:domestic");
        newAddrAttr = ((Element) testAddr).setAttributeNodeNS(newAttr);
        newName = newAddrAttr.getNodeName();
        assertEquals("nodeName", "xxx:domestic", newName);
    }
}

