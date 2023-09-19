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
 * Invoke setIdAttribute class attribute on the second, third, and the fifth acronym element.
 * Verify by calling isID on the attributes and getElementById with the unique value "No" on document.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr</a>
 */
public class elementsetidattribute08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem1;
        Element acronymElem2;
        Element acronymElem3;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acronymElem1 = (Element) elemList.item(1);
        acronymElem2 = (Element) elemList.item(2);
        acronymElem3 = (Element) elemList.item(4);
        acronymElem1.setIdAttribute("class", true);
        acronymElem2.setIdAttribute("class", true);
        acronymElem3.setIdAttribute("class", true);
        attributesMap = acronymElem1.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId1True08", id);
        attributesMap = acronymElem2.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId2True08", id);
        attributesMap = acronymElem3.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId3True08", id);
        elem = doc.getElementById("No");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributeGetElementById08", "ACRONYM", elemName);
    }
}

