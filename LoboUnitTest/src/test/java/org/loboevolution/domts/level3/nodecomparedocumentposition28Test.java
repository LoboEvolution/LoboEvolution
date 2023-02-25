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
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Using compareDocumentPosition check the document position of the EntityReference node ent4's
 * first child and last child.  Invoke compareDocumentPositon on first child with last child as a parameter
 * should return FOLLOWING, and should return PRECEDING vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection varList;
        Element varElem;
        EntityReference entRef;
        Element entRefChild1;
        ProcessingInstruction entRefChild2;
        int entRefChild1Position;
        int entRefChild2Position;
        doc = sampleXmlFile("hc_staff.xml");

            varList = doc.getElementsByTagName("var");
            varElem = (Element) varList.item(2);
            assertNotNull("varElemNotNull", varElem);
            entRef = (EntityReference) varElem.getFirstChild();
            assertNotNull("entRefNotNull", entRef);

        entRefChild1 = (Element) entRef.getFirstChild();
        assertNotNull("entRefChild1NotNull", entRefChild1);
        entRefChild2 = (ProcessingInstruction) entRef.getLastChild();
        assertNotNull("entRefChild2NotNull", entRefChild2);
        entRefChild1Position = entRefChild1.compareDocumentPosition(entRefChild2);
        assertEquals("nodecomparedocumentpositionFollowing28", 4, entRefChild1Position);
        entRefChild2Position = entRefChild2.compareDocumentPosition(entRefChild1);
        assertEquals("nodecomparedocumentpositionPRECEDING28", 2, entRefChild2Position);
    }
}

