
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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;


/**
 * Setting the nodeValue should change the value returned by
 * nodeValue and ProcessingInstruction.getData.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1004215813">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1004215813</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=181">http://www.w3.org/Bugs/Public/show_bug.cgi?id=181</a>
 */
public class nodeprocessinginstructionsetnodevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList testList;
        Node piNode;
        String piValue;
        doc = sampleXmlFile("staff.xml");
        testList = doc.getChildNodes();
        piNode = testList.item(0);
        piNode.setNodeValue("Something different");
        piValue = piNode.getNodeValue();
        assertEquals("nodeValue", "Something different", piValue);
        piValue = ((ProcessingInstruction) piNode).getData();
        assertEquals("data", "Something different", piValue);
    }

}

