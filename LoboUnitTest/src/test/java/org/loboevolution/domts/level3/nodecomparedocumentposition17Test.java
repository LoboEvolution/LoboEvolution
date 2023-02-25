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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition check if the document position of the first ProcessingInstruction node compared to
 * this second newly apended ProcessingInstruction node is PRECEDING, and FOLLOWING vice versa.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition17Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction pi1;
        ProcessingInstruction pi2;
        int pi1Position;
        int pi2Position;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        pi1 = doc.createProcessingInstruction("PI1", "");
        pi2 = doc.createProcessingInstruction("PI2", "");
        appendedChild = doc.appendChild(pi1);
        appendedChild = doc.appendChild(pi2);
        pi1Position = pi1.compareDocumentPosition(pi2);
        assertEquals("nodecomparedocumentpositionFollowing17", 4, pi1Position);
        pi2Position = pi2.compareDocumentPosition(pi1);
        assertEquals("nodecomparedocumentpositionPRECEDING17", 2, pi2Position);
    }
}

