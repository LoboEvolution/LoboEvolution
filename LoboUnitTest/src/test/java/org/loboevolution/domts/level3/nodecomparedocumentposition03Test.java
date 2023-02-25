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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Using compareDocumentPosition check if the document position of two Document nodes obtained from the
 * same xml document is disconnected, implementation specific, and that the order of these two documents
 * is reserved.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document docComp;
        int documentPosition1;
        int documentPosition2;
        int documentPosition3;
        doc = sampleXmlFile("hc_staff.xml");
        docComp = sampleXmlFile("hc_staff.xml");
        documentPosition1 = doc.compareDocumentPosition(docComp);
        assertEquals("isImplSpecificDisconnected1", 33 & 57, documentPosition1 & 57);
        documentPosition2 = docComp.compareDocumentPosition(doc);
        assertNotEquals("notBothPreceding", documentPosition1 & 2, documentPosition2 & 2);
        assertNotEquals("notBothFollowing", documentPosition1 & 4, documentPosition2 & 4);
        assertEquals("isImplSpecificDisconnected2", 33 & 57, documentPosition2 & 57);
        documentPosition3 = doc.compareDocumentPosition(docComp);
        assertEquals("isConsistent", documentPosition1, documentPosition3);
    }
}

