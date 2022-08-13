
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The string returned by the "getNodeValue()" method for an
 * EntityReference Node is null.
 * <p>
 * Retrieve the first Entity Reference node from the last
 * child of the second employee and check the string
 * returned by the "getNodeValue()" method.   It should be
 * equal to null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 */
public class nodeentityreferencenodevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element entRefAddr;
        Node entRefNode;
        String entRefValue;
        int nodeType;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        entRefAddr = (Element) elementList.item(1);
        entRefNode = entRefAddr.getFirstChild();
        nodeType = entRefNode.getNodeType();

        if (nodeType == 3) {
            entRefNode = doc.createEntityReference("ent2");
            assertNotNull("createdEntRefNotNull", entRefNode);
        }
        entRefValue = entRefNode.getNodeValue();
        assertNull("entRefNodeValue", entRefValue);
    }
}

