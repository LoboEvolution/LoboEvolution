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
 * Using removeChild on this Document node attempt to remove a new ProcessingInstruction node and
 * verify the target of the removed ProcessingInstruction node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction pi;
        ProcessingInstruction removedPi;
        String target;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        pi = doc.createProcessingInstruction("PIT", "PID");
        appendedChild = doc.appendChild(pi);
        removedPi = (ProcessingInstruction) doc.removeChild(pi);
        target = removedPi.getTarget();
        assertEquals("noderemovechild09", "PIT", target);
    }
}

