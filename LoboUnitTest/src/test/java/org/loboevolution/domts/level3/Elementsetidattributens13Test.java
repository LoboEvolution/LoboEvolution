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


import org.htmlunit.cssparser.dom.DOMException;
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
 * Invoke setIdAttributeNS on newly added attribute on the third strong element.  Verify by calling
 * isID on the attribute node and getElementById on document node.
 * Call setIdAttributeNS on the same element to reset ID but with a non-existing attribute should generate
 * NOT_FOUND_ERR

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS</a>
 */
public class Elementsetidattributens13Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        final Element nameElem;
        final NamedNodeMap attributesMap;
        final Attr attr;
        boolean id = false;
        final Element elem;
        final String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("strong");
        nameElem = (Element) elemList.item(2);
        nameElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:newAttr", "newValue");
        nameElem.setIdAttributeNS("http://www.w3.org/2000/xmlns/", "newAttr", true);
        attributesMap = nameElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:newAttr");
        id = attr.isId();
        assertTrue(id, "Elementsetidattributens13Assert3");
        elem = doc.getElementById("newValue");
        elemName = elem.getTagName();
        assertEquals("STRONG", elemName, "Elementsetidattributens13Assert4");

        {
            boolean success = false;
            try {
                nameElem.setIdAttributeNS("http://www.w3.org/XML/1998/namespace", "lang", false);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue(success, "Elementsetidattributens13Assert5");
        }
    }
}

