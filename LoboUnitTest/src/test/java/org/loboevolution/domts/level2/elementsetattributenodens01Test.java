
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * Testing Element.setAttributeNodeNS: If an attribute with that local name
 * and that namespace URI is already present in the element, it is replaced
 * by the new one.
 * Create a new element and two new attribute nodes (in the same namespace
 * and same localNames).
 * Add the two new attribute nodes to the element node using the
 * setAttributeNodeNS method.  Check that only one attribute is added, check
 * the value of this attribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute1;
        Attr attribute2;
        Attr attrNode;
        String attrName;
        String attrNS;
        NamedNodeMap attributes;
        int length;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test/Level2", "new:element");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/Test/att1", "p1:att");
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/Test/att1", "p2:att");
        attribute2.setValue("value2");
        element.setAttributeNodeNS(attribute1);
        element.setAttributeNodeNS(attribute2);
        attrNode = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/att1", "att");
        attrName = attrNode.getNodeName();
        attrNS = attrNode.getNamespaceURI();
        assertEquals("elementsetattributenodens01_attrName", "p2:att", attrName);
        assertEquals("elementsetattributenodens01_attrNS", "http://www.w3.org/DOM/Test/att1", attrNS);
        attributes = element.getAttributes();
        length = attributes.getLength();
        assertEquals("length", 1, length);
    }
}

