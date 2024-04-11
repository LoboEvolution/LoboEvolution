/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Invoke setIdAttributeNode on the 3rd p element using the title attribute as a parameter .  Verify by calling
 * isID on the attribute node and getElementById on document node. Call setIdAttributeNode again with isId=false
 * to reset.  Invoke isId on the attribute node should return false.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class Elementsetidattributenode01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element employeeElem;
        final NamedNodeMap attributesMap;
        final Attr attr;
        boolean id;
        final Element elem;
        final String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        employeeElem = (Element) elemList.item(2);
        attributesMap = employeeElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        employeeElem.setIdAttributeNode(attr, true);
        id = attr.isId();
        assertTrue(id, "Elementsetidattributenode01Assert1");
        elem = doc.getElementById("http://www.netzero.com");
        elemName = elem.getTagName();
        assertEquals("P", elemName, "Elementsetidattributenode01Assert2");
        elem.setIdAttributeNode(attr, false);
        id = attr.isId();
        assertFalse(id, "Elementsetidattributenode01Assert3");
    }
}
