
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
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "createProcessingInstruction(target,data)" method
 * creates a new ProcessingInstruction node with the
 * specified name and data string.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createProcessingInstruction(target,data)" method.
 * It should create a new PI node with the specified target
 * and data.  The target, data and type are retrieved and
 * output.
 *
 * @author NIST
 * @author Mary Brady
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2001Apr/0020.html">http://lists.w3.org/Archives/Public/www-dom-ts/2001Apr/0020.html</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-135944439">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-135944439</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=249">http://www.w3.org/Bugs/Public/show_bug.cgi?id=249</a>
 */
public class documentcreateprocessinginstructionTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction newPINode;
        String piValue;
        String piName;
        int piType;
        doc = sampleXmlFile("staff.xml");
        newPINode = doc.createProcessingInstruction("TESTPI", "This is a new PI node");
        assertNotNull("createdPINotNull", newPINode);
        piName = newPINode.getNodeName();
        assertEquals("name", "TESTPI", piName);
        piValue = newPINode.getNodeValue();
        assertEquals("value", "This is a new PI node", piValue);
        piType = newPINode.getNodeType();
        assertEquals("type", 7, piType);
    }
}

