
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

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The "isSpecified()" method for an Attr node should return true if the
 * value of the attribute is changed.
 * Retrieve the attribute named "class" from the last
 * child of the THIRD employee and change its
 * value to "Yes"(which is the default DTD value).  This
 * should cause the "isSpecified()" method to be true.
 * This test uses the "setAttribute(name,value)" method
 * from the Element interface and the "getNamedItem(name)"
 * method from the NamedNodeMap interface.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273</a>
 */
public class HcattrspecifiedvaluechangedTest extends LoboUnitTest {

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
        final boolean state;
        doc = sampleXmlFile("hc_staff.xml");
        addressList = doc.getElementsByTagName("acronym");
        testNode = (Element) addressList.item(2);

        testNode.setAttribute("class", "Yα"); // Android-changed: GREEK LOWER CASE ALPHA
        attributes = testNode.getAttributes();
        streetAttr = (Attr) attributes.getNamedItem("class");
        state = streetAttr.isSpecified();
        assertTrue(state, "HcattrspecifiedvaluechangedAssert2");
    }
}

