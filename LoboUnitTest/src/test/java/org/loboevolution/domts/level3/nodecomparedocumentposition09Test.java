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

import static org.junit.Assert.assertEquals;

/**
 * Using compareDocumentPosition check if the Element node is contained and follows the appended Document node, and
 * if the Document node contains and precedes the Element node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition09Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element elem;
        Element newElem;
        HTMLCollection elemList;
        int documentPosition;
        int documentElementPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        elem.appendChild(newElem);
        documentPosition = doc.compareDocumentPosition(newElem);
        assertEquals("nodecomparedocumentpositionIsContainedFollowing09", 20, documentPosition);
        documentElementPosition = newElem.compareDocumentPosition(doc);
        assertEquals("nodecomparedocumentpositionContainsPRECEDING09", 10, documentElementPosition);
    }
}