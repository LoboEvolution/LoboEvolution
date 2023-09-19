
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
 * Retreive the second address element node having localName=adrress.  Retreive the attributes
 * of this element into 2 nodemaps.  Create a new attribute node and add it to this element.
 * Since NamedNodeMaps are live each one should get updated, using the getNamedItemNS retreive
 * the newly created attribute from each node map.
 * Verify if the attr node has been retreived successfully by checking its nodeName atttribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS</a>
 */
public class namednodemapgetnameditemns06Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributesMap1;
        NamedNodeMap attributesMap2;
        Element element;
        Node attribute;
        Attr newAttr1;
        HTMLCollection elementList;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(1);
        attributesMap1 = element.getAttributes();
        attributesMap2 = element.getAttributes();
        newAttr1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "street");
        element.setAttributeNodeNS(newAttr1);
        attribute = attributesMap1.getNamedItemNS("http://www.w3.org/DOM/L1", "street");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapgetnameditemnsMap106", "street", attrName);
        attribute = attributesMap2.getNamedItemNS("http://www.w3.org/DOM/L1", "street");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapgetnameditemnsMap206", "street", attrName);
    }
}

