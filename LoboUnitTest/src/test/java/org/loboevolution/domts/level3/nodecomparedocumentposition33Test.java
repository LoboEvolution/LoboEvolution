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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Create a new Element node, add a new atttribute node to it.  Compare the position
 * of the Element and the Document.  This should return disconnected, implementation specific, and that
 * the order of these two nodes is preserved.
 * Also compare the position of the Element node with respect to the Attr node and this should
 * be PRECEDING and contains, and the Attr node follows and is contained by the Element node
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition33Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        Attr attr;
        int position1;
        int position2;
        int position3;
        int position4;
        int position5;
        Attr replacedAttr;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        replacedAttr = elem.setAttributeNodeNS(attr);
        position4 = elem.compareDocumentPosition(attr);
        assertEquals("nodecomparedocumentposition3FollowingisContained33", 20, position4);
        position5 = attr.compareDocumentPosition(elem);
        assertEquals("nodecomparedocumentposition4ContainsPRECEDING33", 10, position5);
        position1 = doc.compareDocumentPosition(elem);
        assertEquals("isImplSpecificDisconnected1", 33 & 57, position1 & 57);
        position2 = elem.compareDocumentPosition(doc);
        assertNotEquals("notBothPreceding", position1 & 2, position2 & 2);
        assertNotEquals("notBothFollowing", position1 & 4, position2 & 4);
        assertEquals("isImplSpecificDisconnected2", 33 & 57, position2 & 57);
        position3 = doc.compareDocumentPosition(elem);
        assertEquals("isConsistent", position1, position3);
    }
}

