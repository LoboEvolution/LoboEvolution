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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Using compareDocumentPosition to check if the document position of the class's attribute
 * when compared with a new attribute node is implementation_specific
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition40Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        Attr attr1;
        Attr attr2;
        int attrPosition;
        int swappedPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        elem = (Element) elemList.item(3);
        attr1 = elem.getAttributeNode("class");
        elem.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "FR-fr");
        attr2 = elem.getAttributeNode("xml:lang");
        attrPosition = attr1.compareDocumentPosition(attr2);
        assertEquals("isImplementationSpecific", 32 & 32, attrPosition & 32);
        assertEquals("otherBitsZero", 0 & 25, attrPosition & 25);
        assertNotEquals("eitherFollowingOrPreceding", 0 & 6, attrPosition & 6);
        swappedPosition = attr2.compareDocumentPosition(attr1);
        assertNotEquals("onlyOnePreceding", swappedPosition & 2, attrPosition & 2);
        assertNotEquals("onlyOneFollowing", swappedPosition & 4, attrPosition & 4);
    }
}
