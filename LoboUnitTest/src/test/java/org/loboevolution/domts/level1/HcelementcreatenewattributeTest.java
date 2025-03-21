
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

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "setAttributeNode(newAttr)" method adds a new
 * attribute to the Element.
 * <p>
 * Retrieve first address element and add
 * a new attribute node to it by invoking its
 * "setAttributeNode(newAttr)" method.  This test makes use
 * of the "createAttribute(name)" method from the Document
 * interface.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class HcelementcreatenewattributeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Element testAddress;
        final Attr newAttribute;
        final Attr oldAttr;
        final Attr districtAttr;
        final String attrVal;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        testAddress = (Element) elementList.item(0);
        newAttribute = doc.createAttribute("lang");
        oldAttr = testAddress.setAttributeNode(newAttribute);
        assertNull(oldAttr, "HcelementcreatenewattributeAssert1");
        districtAttr = testAddress.getAttributeNode("lang");
        assertNotNull(districtAttr, "HcelementcreatenewattributeAssert2");
        attrVal = testAddress.getAttribute("lang");
        assertEquals("", attrVal, "HcelementcreatenewattributeAssert3");
    }
}

