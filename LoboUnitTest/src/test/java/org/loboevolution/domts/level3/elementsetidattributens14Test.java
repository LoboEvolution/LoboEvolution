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
 * Invoke setIdAttributeNS on two existing attributes of the second p element and the third
 * acronym element.  Verify by calling isId on the attributes and getElementById with different values on document node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS</a>
 */
public class elementsetidattributens14Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element pElem;
        Element acronymElem;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "p");
        pElem = (Element) elemList.item(1);
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        acronymElem = (Element) elemList.item(2);
        pElem.setIdAttributeNS("http://www.usa.com", "dmstc", true);
        acronymElem.setIdAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", true);
        attributesMap = pElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        id = attr.isId();
        assertTrue("elementsetidattributensIsId1True14", id);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xsi:noNamespaceSchemaLocation");
        id = attr.isId();
        assertTrue("elementsetidattributensIsId2True14", id);
        elem = doc.getElementById("Yes");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributens1GetElementById14", "ACRONYM", elemName);
        elem = doc.getElementById("http://www.usa.com");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributens2GetElementById14", "P", elemName);
    }

}

