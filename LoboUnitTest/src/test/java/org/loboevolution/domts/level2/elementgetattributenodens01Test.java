
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

import static org.junit.Assert.*;


/**
 * The method getAttributeNodeNS retrieves an Attr node by local name and namespace URI.
 * Create a new element node and add 2 new attribute nodes to it that have the same
 * local name but different namespaceURIs and prefixes.
 * Retrieve an attribute using namespace and localname and check its value, name and
 * namespaceURI.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS</a>
 */
public class elementgetattributenodens01Test extends LoboUnitTest {

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
        Attr attribute;
        String attrValue;
        String attrName;
        String attNodeName;
        String attrLocalName;
        String attrNS;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("namespaceURI", "root");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/Level2", "l2:att");
        element.setAttributeNodeNS(attribute1);
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/Level1", "att");
        element.setAttributeNodeNS(attribute2);
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Level2", "l2:att");
        attrValue = attribute.getNodeValue();
        attrName = attribute.getName();
        attNodeName = attribute.getNodeName();
        attrLocalName = attribute.getLocalName();
        attrNS = attribute.getNamespaceURI();
        assertEquals("elementgetattributenodens01_attrValue", null, attrValue);
        assertEquals("elementgetattributenodens01_attrName", "l2:att", attrName);
        assertEquals("elementgetattributenodens01_attrNodeName", "l2:att", attNodeName);
        assertEquals("elementgetattributenodens01_attrLocalName", "att", attrLocalName);
        assertEquals("elementgetattributenodens01_attrNs", "http://www.w3.org/DOM/Level2", attrNS);
    }
}

