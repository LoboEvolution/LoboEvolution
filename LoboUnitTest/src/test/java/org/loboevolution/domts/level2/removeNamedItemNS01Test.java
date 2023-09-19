
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "removeNamedItemNS(namespaceURI,localName)" method for a
 * NamedNodeMap should remove a node specified by localName and namespaceURI.
 * <p>
 * Retrieve a list of elements with tag name "address".
 * Access the second element from the list and get its attributes.
 * Try to remove the attribute node with local name "domestic"
 * and namespace uri "http://www.usa.com" with
 * method removeNamedItemNS(namespaceURI,localName).
 * Check to see if the node has been removed.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1074577549">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1074577549</a>
 */
public class removeNamedItemNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        Attr newAttr;
        Node removedNode;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(1);
        attributes = testAddress.getAttributes();
        removedNode = attributes.removeNamedItemNS("http://www.usa.com", "domestic");
        assertNotNull("retval", removedNode);
        newAttr = (Attr) attributes.getNamedItem("dmstc:domestic");
        assertNull("nodeRemoved", newAttr);
    }
}

