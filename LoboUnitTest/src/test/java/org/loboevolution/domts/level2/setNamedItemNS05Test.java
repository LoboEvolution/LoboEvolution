
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

import static org.junit.Assert.*;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should replace an existing node n1 found in the map with arg if n1
 * has the same namespaceURI and localName as arg and return n1.
 * <p>
 * Create an attribute node in with namespaceURI "http://www.usa.com"
 * and qualifiedName "dmstc:domestic" whose value is "newVal".
 * Invoke method setNamedItemNS((Attr)arg) on the map of the first "address"
 * element. Method should return the old attribute node identified
 * by namespaceURI and qualifiedName from above,whose value is "Yes".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class setNamedItemNS05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.usa.com";
        String qualifiedName = "dmstc:domestic";
        Document doc;
        Attr arg;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        Node retnode;
        String value;
        doc = sampleXmlFile("staffNS.xml");
        arg = doc.createAttributeNS(namespaceURI, qualifiedName);
        arg.setNodeValue("newValue");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(0);
        attributes = testAddress.getAttributes();
        retnode = attributes.setNamedItemNS(arg);
        value = retnode.getNodeValue();
        assertEquals("throw_Equals", "Yes", value);
    }
}

