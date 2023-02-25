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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition check if the document compared contains and precedes the new
 * newElement, and the newElement is contained and follows the document.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition07Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        Element newElem;
        int documentPosition;
        int documentElementPosition;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        docElem.appendChild(newElem);
        documentPosition = doc.compareDocumentPosition(newElem);
        assertEquals("nodecomparedocumentpositionIsContainedFollowing07", 20, documentPosition);
        documentElementPosition = newElem.compareDocumentPosition(doc);
        assertEquals("nodecomparedocumentpositionContainedPRECEDING07", 10, documentElementPosition);
    }
}

