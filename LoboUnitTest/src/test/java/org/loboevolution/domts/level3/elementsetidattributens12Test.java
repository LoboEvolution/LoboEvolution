/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

