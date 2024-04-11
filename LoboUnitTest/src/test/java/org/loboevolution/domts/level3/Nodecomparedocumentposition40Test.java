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

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using compareDocumentPosition to check if the document position of the class's attribute
 * when compared with a new attribute node is implementation_specific

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition40Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element elem;
        final Attr attr1;
        final Attr attr2;
        final int attrPosition;
        final int swappedPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        elem = (Element) elemList.item(3);
        attr1 = elem.getAttributeNode("class");
        elem.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "FR-fr");
        attr2 = elem.getAttributeNode("xml:lang");
        attrPosition = attr1.compareDocumentPosition(attr2);
        assertEquals(32, attrPosition & 32, "Nodecomparedocumentposition40Assert2");
        assertEquals(0, attrPosition & 25, "Nodecomparedocumentposition40Assert3");
        swappedPosition = attr2.compareDocumentPosition(attr1);
        assertEquals(swappedPosition & 2, attrPosition & 2, "Nodecomparedocumentposition40Assert4");
    }
}
