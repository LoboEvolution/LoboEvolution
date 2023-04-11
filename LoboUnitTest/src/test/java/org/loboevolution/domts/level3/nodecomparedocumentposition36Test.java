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


/**
 * Using compareDocumentPosition to check if the document position of an Attribute compared with
 * the element that follows its parent as a parameter is FOLLOWING, and is PRECEDING
 * vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition36Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        HTMLCollection elemListFollows;
        Element elemFollows;
        Attr attr;
        int attrPosition;
        int elemFollowsPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        attr = elem.getAttributeNodeNS("*", "dir");
        elemListFollows = doc.getElementsByTagName("strong");
        elemFollows = (Element) elemListFollows.item(3);
        attrPosition = attr.compareDocumentPosition(elemFollows);
        assertEquals("nodecomparedocumentpositionFollowing36", 4, attrPosition);
        elemFollowsPosition = elemFollows.compareDocumentPosition(attr);
        assertEquals("nodecomparedocumentpositionPRECEEDING36", 2, elemFollowsPosition);
    }
}

