
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * The "getAttributeNS(namespaceURI,localName)" method retrieves an
 * attribute value by local name and NamespaceURI.
 * <p>
 * Retrieve the first "address" element.
 * Create a new attribute with the "createAttributeNS()" method.
 * Add the new attribute and value with the "setAttributeNS()" method.
 * The value returned by the "getAttributeNS()" method should be
 * the string "NewValue" since the attribute had a specified value.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS</a>
 */
public class GetAttributeNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final String namespaceURI = "http://www.nist.gov";
        final String localName = "blank";
        final String qualifiedName = "blank";
        final Document doc;
        final HTMLCollection elementList;
        final Element testAddr;
        final String attrValue;
        doc = sampleXmlFile("staffNS.xml");
        doc.createAttributeNS(namespaceURI, qualifiedName);
        elementList = doc.getElementsByTagName("address");
        testAddr = (Element) elementList.item(0);
        assertNotNull(testAddr);
        testAddr.setAttributeNS(namespaceURI, qualifiedName, "NewValue");
        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
        assertEquals("NewValue", attrValue);
    }
}

