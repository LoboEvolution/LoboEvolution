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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This method declares the attribute specified by node to be of type ID. If the value of the specified attribute
 * is unique then this element node can later be retrieved using getElementById on Document. Note, however,
 * that this simply affects this node and does not change any grammar that may be in use.
 * <p>
 * Invoke setIdAttributeNode on the 2nd acronym element and 3rd p element using the title and xmlns:dmstc attributes respectively
 * as parameters .  Verify by calling isID on the attribute node and getElementById on document node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class Elementsetidattributenode08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList1;
        final HTMLCollection elemList2;
        final Element acronymElem;
        final Element pElem;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList1 = doc.getElementsByTagNameNS("*", "acronym");
        elemList2 = doc.getElementsByTagNameNS("*", "p");
        acronymElem = (Element) elemList1.item(1);
        pElem = (Element) elemList2.item(2);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("title");
        acronymElem.setIdAttributeNode(attr, true);
        id = attr.isId();
        assertTrue(id, "Elementsetidattributenode08Assert3");
        attributesMap = pElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        pElem.setIdAttributeNode(attr, true);
        id = attr.isId();
        assertTrue(id, "Elementsetidattributenode08Assert4");
        elem = doc.getElementById("Yes");
        elemName = elem.getTagName();
        assertEquals("ACRONYM", elemName, "Elementsetidattributenode08Assert5");
        elem = doc.getElementById("http://www.netzero.com");
        elemName = elem.getTagName();
        assertEquals("P", elemName, "Elementsetidattributenode08Assert6");
    }
}

