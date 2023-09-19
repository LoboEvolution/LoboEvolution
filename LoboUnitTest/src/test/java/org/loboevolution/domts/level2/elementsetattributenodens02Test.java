
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


/**
 * Test the setAttributeNodeNS method.
 * Retreive the street attribute from the second address element node.
 * Clone it and add it to the first address node.  The INUSE_ATTRIBUTE_ERR exception
 * should not be thrown. Check the name and value of the newly added node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class elementsetattributenodens02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element element2;
        Attr attribute;
        Attr attributeCloned;
        Attr newAttr;
        HTMLCollection elementList;
        String attrName;
        String attrValue;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "address");
        element = (Element) elementList.item(1);
        attribute = element.getAttributeNodeNS(nullNS, "street");
        attributeCloned = (Attr) attribute.cloneNode(true);
        element2 = (Element) elementList.item(2);
        newAttr = element2.setAttributeNodeNS(attributeCloned);
        attrName = newAttr.getNodeName();
        attrValue = newAttr.getNodeValue();
        assertEquals("elementsetattributenodens02_attrName", "street", attrName);
        assertEquals("elementsetattributenodens02_attrValue", "Yes", attrValue);
    }
}

