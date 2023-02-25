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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Create two entity reference nodes. Using compareDocumentPosition to check if the child of the first Entity
 * Ref node precedes the child of the second Entity Ref node, and that the child of the second Entity Ref node
 * follows the child of the first Entity Ref node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition29Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        EntityReference entRef1;
        EntityReference entRef2;
        Element entRefChild1;
        ProcessingInstruction entRefChild2;
        int entRefChild1Position;
        int entRefChild2Position;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        entRef1 = doc.createEntityReference("ent4");
        entRef2 = doc.createEntityReference("ent4");
        docElem = doc.getDocumentElement();
        appendedChild = docElem.appendChild(entRef1);
        appendedChild = docElem.appendChild(entRef2);
        entRefChild1 = (Element) entRef1.getFirstChild();
        assertNotNull("entRefChild1NotNull", entRefChild1);
        entRefChild2 = (ProcessingInstruction) entRef2.getLastChild();
        assertNotNull("entRefChild2NotNull", entRefChild2);
        entRefChild1Position = entRefChild1.compareDocumentPosition(entRefChild2);
        assertEquals("nodecomparedocumentpositionFollowing29", 4, entRefChild1Position);
        entRefChild2Position = entRefChild2.compareDocumentPosition(entRefChild1);
        assertEquals("nodecomparedocumentpositionPRECEDING29", 2, entRefChild2Position);
    }
}

