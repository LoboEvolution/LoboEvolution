
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import static org.junit.Assert.assertEquals;


/**
 * The "getData()" method returns the content of the
 * processing instruction.  It starts at the first non
 * white character following the target and ends at the
 * character immediately preceding the "?&#62;".
 * <p>
 * Retrieve the ProcessingInstruction node located
 * immediately after the prolog.  Create a nodelist of the
 * child nodes of this document.  Invoke the "getData()"
 * method on the first child in the list. This should
 * return the content of the ProcessingInstruction.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393</a>
 */
public class processinginstructiongetdataTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList childNodes;
        ProcessingInstruction piNode;
        String data;
        doc = sampleXmlFile("staff.xml");
        childNodes = doc.getChildNodes();
        piNode = (ProcessingInstruction) childNodes.item(0);
        data = piNode.getData();
        assertEquals("processinginstructionGetTargetAssert", "PIDATA", data);
    }

}

