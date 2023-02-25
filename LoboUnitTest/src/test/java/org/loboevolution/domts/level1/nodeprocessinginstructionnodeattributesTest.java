
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
import org.loboevolution.html.dom.domimpl.HTMLProcessingInstruction;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertNull;


/**
 * The "getAttributes()" method invoked on a Processing
 * Instruction Node returns null.
 * <p>
 * Retrieve the Processing Instruction node and invoke
 * the "getAttributes()" method.   It should return null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 */
public class nodeprocessinginstructionnodeattributesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList testList;
        HTMLProcessingInstruction piNode;
        NamedNodeMap attrList;
        doc = sampleXmlFile("staff.xml");
        testList = doc.getChildNodes();
        piNode = (HTMLProcessingInstruction) testList.item(0);
        attrList = piNode.getAttributes();
        assertNull("nodeProcessingInstructionNodeAttrAssert1", attrList);
    }

}

