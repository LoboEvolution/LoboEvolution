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
 * First use setAttribute to create a new attribute on the third strong element.  Invoke setIdAttribute
 * on the new  attribute. Verify by calling isID on the new attribute and getElementById on document.
 * Invoke setIdAttribute again to reset. Calling isID should return false.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr</a>
 */
public class Elementsetidattribute04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element nameElem;
        final NamedNodeMap attributesMap;
        final Attr attr;
        boolean id;
        final Element elem;
        final String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("strong");
        nameElem = (Element) elemList.item(2);
        nameElem.setAttribute("hasMiddleName", "Antoine");
        nameElem.setIdAttribute("hasMiddleName", true);
        attributesMap = nameElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("hasMiddleName");
        id = attr.isId();
        assertTrue(id, "Elementsetidattribute04Assert1");
        elem = doc.getElementById("Antoine");
        elemName = elem.getTagName();
        assertEquals("STRONG", elemName, "Elementsetidattribute04Assert2");
        nameElem.setIdAttribute("hasMiddleName", false);
        id = attr.isId();
        assertFalse(id, "Elementsetidattribute04Assert3");
    }
}

