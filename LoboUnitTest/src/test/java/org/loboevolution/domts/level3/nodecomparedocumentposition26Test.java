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
import org.loboevolution.html.node.EntityReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Using compareDocumentPosition check if the EntityReference node contains and precedes it's first
 * childElement, and that the childElement is contained and follows the EntityReference node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition26Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection varList;
        Element varElem;
        EntityReference entRef;
        Element entRefChild1;
        int entRefPosition;
        int entRefChild1Position;
        doc = sampleXmlFile("hc_staff.xml");

        varList = doc.getElementsByTagName("var");
        varElem = (Element) varList.item(2);
        assertNotNull("varElemNotNull", varElem);
        entRef = (EntityReference) varElem.getFirstChild();
        assertNotNull("entRefNotNull", entRef);

        entRefChild1 = (Element) entRef.getFirstChild();
        assertNotNull("entRefChild1NotNull", entRefChild1);
        entRefPosition = entRef.compareDocumentPosition(entRefChild1);
        assertEquals("nodecomparedocumentpositionIsContainedFollowing26", 20, entRefPosition);
        entRefChild1Position = entRefChild1.compareDocumentPosition(entRef);
        assertEquals("nodecomparedocumentpositionContainsPRECEDING26", 10, entRefChild1Position);
    }
}

