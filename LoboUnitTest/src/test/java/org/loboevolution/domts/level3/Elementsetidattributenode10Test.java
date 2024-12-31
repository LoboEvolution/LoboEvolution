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
 * This method declares the attribute specified by node to be of type ID. If the value of the specified attribute
 * is unique then this element node can later be retrieved using getElementById on Document. Note, however,
 * that this simply affects this node and does not change any grammar that may be in use.
 * <p>
 * Invoke setIdAttributeNode on the 4th acronym element using the class attribute (containing entity reference)
 * as a parameter .  Verify by calling isId on the attribute node and getElementById on document node.
 * Reset by invoking setIdAttributeNode with isId=false.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class Elementsetidattributenode10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element acronymElem;
        final NamedNodeMap attributesMap;
        final Attr attr;
        boolean id;
        final Element elem;
        final String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        acronymElem = (Element) elemList.item(3);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        acronymElem.setIdAttributeNode(attr, true);
        id = attr.isId();
        assertTrue(id, "Elementsetidattributenode10Assert1");
        elem = doc.getElementById("Yα");
        elemName = elem.getTagName();
        assertEquals("ACRONYM", elemName, "Elementsetidattributenode10Assert2");
        acronymElem.setIdAttributeNode(attr, false);
        id = attr.isId();
        assertFalse(id, "Elementsetidattributenode10Assert3");
    }
}

