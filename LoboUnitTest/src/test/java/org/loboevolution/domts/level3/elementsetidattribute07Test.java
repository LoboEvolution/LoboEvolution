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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * First use setAttribute to create two new attribute of the second and third strong element with different values.
 * Invoke setIdAttribute on the new  attributes. Verify by calling isID on the new attributes and getElementById
 * with two different values on document.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr</a>
 */
public class elementsetidattribute07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element nameElem1;
        Element nameElem2;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("strong");
        nameElem1 = (Element) elemList.item(2);
        nameElem2 = (Element) elemList.item(3);
        nameElem1.setAttribute("hasMiddleName", "Antoine");
        nameElem1.setIdAttribute("hasMiddleName", true);
        nameElem2.setAttribute("hasMiddleName", "Neeya");
        nameElem2.setIdAttribute("hasMiddleName", true);
        attributesMap = nameElem1.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("hasMiddleName");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId1True07", id);
        attributesMap = nameElem2.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("hasMiddleName");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId2True07", id);
        elem = doc.getElementById("Antoine");
        elemName = elem.getTagName();
        assertEquals("elementsetidattribute1GetElementById07", "STRONG", elemName);
        elem = doc.getElementById("Neeya");
        elemName = elem.getTagName();
        assertEquals("elementsetidattribute2GetElementById07", "STRONG", elemName);
    }
}

