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

