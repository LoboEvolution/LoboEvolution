
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
 * The method setPrefix sets the namespace prefix of this node.  Note that setting this attribute,
 * when permitted, changes the nodeName attribute, which holds the qualified name, as well as the
 * tagName and name attributes of the Element and Attr interfaces, when applicable.
 * Create a new attribute node and add it to an element node with an existing attribute having
 * the same localName as this attribute but different namespaceURI.  Change the prefix of the
 * newly created attribute using setPrefix.  Check if the new attribute nodeName has changed
 * and the existing attribute is the same.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class nodesetprefix02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        Attr newAttribute;
        HTMLCollection elementList;
        String attrName;
        String newAttrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        element = (Element) elementList.item(1);
        newAttribute = doc.createAttributeNS("http://www.w3.org/DOM/Test", "test:address");
        element.setAttributeNodeNS(newAttribute);
        newAttribute.setPrefix("dom");
        attribute = element.getAttributeNodeNS("http://www.usa.com", "domestic");
        attrName = attribute.getNodeName();
        newAttrName = newAttribute.getNodeName();
        assertEquals("nodesetprefix02_attrName", "dmstc:domestic", attrName);
        assertEquals("nodesetprefix02_newAttrName", "dom:address", newAttrName);
    }
}

