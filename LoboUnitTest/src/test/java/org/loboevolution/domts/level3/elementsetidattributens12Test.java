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
 * Declares the attribute specified by local name and namespace URI to be of type ID. If the value of the
 * specified attribute is unique then this element node can later be retrieved using getElementById on Document.
 * Note, however, that this simply affects this node and does not change any grammar that may be in use.
 * <p>
 * Set the noNamespaceSchemaLocation attribute on the first acronym element to "No".  Invoke setIdAttributeNS on the
 * noNamespaceSchemaLocation attribute of the first, second and third acronym element.  Verify by calling isId on
 * the attributes.  Calling getElementById with "No" as a value should return the acronym element.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS</a>
 */
public class elementsetidattributens12Test extends LoboUnitTest {
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
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        acronymElem1 = (Element) elemList.item(0);
        acronymElem2 = (Element) elemList.item(1);
        acronymElem3 = (Element) elemList.item(2);
        acronymElem1.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", "No");
        acronymElem1.setIdAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", true);
        acronymElem2.setIdAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", true);
        acronymElem3.setIdAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", true);
        attributesMap = acronymElem1.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xsi:noNamespaceSchemaLocation");
        id = attr.isId();
        assertTrue("elementsetidattributensIsId1True12", id);
        attributesMap = acronymElem2.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xsi:noNamespaceSchemaLocation");
        id = attr.isId();
        assertTrue("elementsetidattributensIsId2True12", id);
        attributesMap = acronymElem3.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xsi:noNamespaceSchemaLocation");
        id = attr.isId();
        assertTrue("elementsetidattributensIsId3True12", id);
        elem = doc.getElementById("No");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributensGetElementById10", "ACRONYM", elemName);
    }
}

