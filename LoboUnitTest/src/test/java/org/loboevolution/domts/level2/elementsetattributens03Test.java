
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The method setAttributeNS adds a new attribute.
 * Retreive an existing element node with a default attribute node and
 * add two new attribute nodes that have the same local name as the
 * default attribute but different namespaceURI to it using the setAttributeNS method.
 * Check if the attribute was correctly set by invoking the getAttributeNodeNS method
 * and checking the nodeName and nodeValue of the returned nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        HTMLCollection elementList;
        String attrName;
        String attrValue;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("employee");
        element = (Element) elementList.item(0);
        assertNotNull("empEmployeeNotNull", element);
        element.setAttributeNS("http://www.w3.org/DOM/Test/1", "defaultAttr", "default1");
        element.setAttributeNS("http://www.w3.org/DOM/Test/2", "defaultAttr", "default2");
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/1", "defaultAttr");
        attrName = attribute.getNodeName();
        attrValue = attribute.getNodeValue();
        assertEquals("elementsetattributens03_attrName", "defaultAttr", attrName);
        assertEquals("elementsetattributens03_attrValue", "default1", attrValue);
    }
}