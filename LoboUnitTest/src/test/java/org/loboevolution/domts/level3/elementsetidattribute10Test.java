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

import static org.junit.Assert.*;


/**
 * Invoke setIdAttribute on the third acronym element's class attribute consecutively with different
 * isId values. Verify by calling isId on the attribute.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttr</a>
 */
public class elementsetidattribute10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acronymElem = (Element) elemList.item(2);
        acronymElem.setIdAttribute("class", true);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");
        id = attr.isId();
        assertTrue("elementsetidattributeIsId1True10", id);
        acronymElem.setIdAttribute("class", true);
        id = attr.isId();
        assertTrue("elementsetidattributeIsId2True10", id);
        acronymElem.setIdAttribute("class", false);
        id = attr.isId();
        assertFalse("elementsetidattributeIsIdFalse10", id);
        elem = doc.getElementById("No");
        assertNull("elementsetidattributeGetElementByIdNull10", elem);
    }
}

