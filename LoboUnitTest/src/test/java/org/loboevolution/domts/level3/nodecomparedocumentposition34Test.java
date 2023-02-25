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


/**
 * Create a new Element node, add new Text, Element and Processing Instruction nodes to it.
 * Using compareDocumentPosition, compare the position of the Element with respect to the Text
 * and the Text with respect to the Processing Instruction.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition34Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elemMain;
        Element elem;
        Text txt;
        ProcessingInstruction pi;
        int elementToTxtPosition;
        int txtToPiPosition;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elemMain = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        txt = doc.createTextNode("TEXT");
        pi = doc.createProcessingInstruction("PIT", "PID");
        appendedChild = elemMain.appendChild(txt);
        appendedChild = elemMain.appendChild(elem);
        appendedChild = elemMain.appendChild(pi);
        elementToTxtPosition = txt.compareDocumentPosition(elem);
        assertEquals("nodecomparedocumentpositionFollowing34", 4, elementToTxtPosition);
        txtToPiPosition = pi.compareDocumentPosition(txt);
        assertEquals("nodecomparedocumentpositionPRECEDING34", 2, txtToPiPosition);
    }
}

