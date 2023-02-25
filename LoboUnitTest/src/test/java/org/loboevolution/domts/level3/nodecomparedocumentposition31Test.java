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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition to check if invoking the method on the first name node with
 * a new node appended to the second position node as a parameter is FOLLOWING, and is PRECEDING vice versa
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        HTMLCollection positionList;
        Element strong;
        Element code;
        Element newElem;
        int namePosition;
        int elemPosition;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        nameList = doc.getElementsByTagName("strong");
        strong = (Element) nameList.item(0);
        positionList = doc.getElementsByTagName("code");
        code = (Element) positionList.item(1);
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        appendedChild = code.appendChild(newElem);
        namePosition = strong.compareDocumentPosition(newElem);
        assertEquals("nodecomparedocumentpositionFollowing31", 4, namePosition);
        elemPosition = newElem.compareDocumentPosition(strong);
        assertEquals("nodecomparedocumentpositionPRECEDING31", 2, elemPosition);
    }
}

