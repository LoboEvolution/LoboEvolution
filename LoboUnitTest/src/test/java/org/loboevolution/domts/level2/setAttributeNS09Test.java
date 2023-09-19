
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
 * The "setAttributeNS(namespaceURI,qualifiedName,value)" method adds a new attribute.
 * If an attribute with the same local name and namespace URI is already present
 * on the element, its prefix is changed to be the prefix part of the "qualifiedName",
 * and its vale is changed to be the "value" paramter.
 * null value if no previously existing Attr node with the
 * same name was replaced.
 * <p>
 * Add a new attribute to the "address" element.
 * Check to see if the new attribute has been successfully added to the document
 * by getting the attributes value, namespace URI, local Name and prefix.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class setAttributeNS09Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String localName = "newAttr";
        String namespaceURI = "http://www.newattr.com";
        String qualifiedName = "newAttr";
        Document doc;
        HTMLCollection elementList;
        Node testAddr;
        Attr addrAttr;
        String resultAttr;
        String resultNamespaceURI;
        String resultLocalName;
        String resultPrefix;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        ((Element) testAddr).setAttributeNS(namespaceURI, qualifiedName, "newValue");
        addrAttr = ((Element) testAddr).getAttributeNodeNS(namespaceURI, localName);
        resultAttr = ((Element) testAddr).getAttributeNS(namespaceURI, localName);
        assertEquals("attrValue", "newValue", resultAttr);
        resultNamespaceURI = addrAttr.getNamespaceURI();
        assertEquals("nsuri", "http://www.newattr.com", resultNamespaceURI);
        resultLocalName = addrAttr.getLocalName();
        assertEquals("lname", "newAttr", resultLocalName);
        resultPrefix = addrAttr.getPrefix();
        assertEquals("prefix", "emp", resultPrefix);
    }
}

