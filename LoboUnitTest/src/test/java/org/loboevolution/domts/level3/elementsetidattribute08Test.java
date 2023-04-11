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

