
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The getNodeName() method of an Attribute node.
 * Retrieve the attribute named street from the last
 * child of the second employee and examine its
 * NodeName.  This test uses the getNamedItem(name) method from the NamedNodeMap
 * interface.
 *
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1112119403">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1112119403</a>
 */
public class AttrNameTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection addressList;
        final Element testNode;
        final NamedNodeMap attributes;
        final Attr streetAttr;
        String name;
        doc = sampleXmlFile("staff.xml");
        addressList = doc.getElementsByTagName("address");
        testNode = (Element) addressList.item(1);
        attributes = testNode.getAttributes();
        streetAttr = (Attr) attributes.getNamedItem("street");
        name = streetAttr.getNodeName();
        assertEquals("street", name, "AttrNameAssert1");
        name = streetAttr.getName();
        assertEquals("street", name, "AttrNameAssert2");
    }
}

