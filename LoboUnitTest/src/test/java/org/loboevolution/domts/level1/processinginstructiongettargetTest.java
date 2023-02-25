
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.*;


/**
 * The "getTarget()" method returns the target of the
 * processing instruction.  It is the first token following
 * the markup that begins the processing instruction.
 * <p>
 * Retrieve the ProcessingInstruction node located
 * immediately after the prolog.  Create a nodelist of the
 * child nodes of this document.  Invoke the "getTarget()"
 * method on the first child in the list. This should
 * return the target of the ProcessingInstruction.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1478689192">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1478689192</a>
 */
public class processinginstructiongettargetTest extends LoboUnitTest {
    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList childNodes;
        ProcessingInstruction piNode;
        String target;
        doc = sampleXmlFile("staff.xml");
        childNodes = doc.getChildNodes();
        piNode = (ProcessingInstruction) childNodes.item(0);
        target = piNode.getTarget();
        assertEquals("processinginstructionGetTargetAssert", "TEST-STYLE", target);
    }
}

