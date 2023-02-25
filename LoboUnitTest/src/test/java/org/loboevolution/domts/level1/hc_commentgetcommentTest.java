
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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


/**
 * A comment is all the characters between the starting
 * '<!--' and ending '-->'
 * Retrieve the nodes of the DOM document.  Search for a
 * comment node and the content is its value.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1334481328">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1334481328</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=509">http://www.w3.org/Bugs/Public/show_bug.cgi?id=509</a>
 */
public class hc_commentgetcommentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList elementList;
        Node child;
        String childName;
        String childValue;
        int commentCount = 0;
        int childType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getChildNodes();
        for (int indexN1005E = 0; indexN1005E < elementList.getLength(); indexN1005E++) {
            child = elementList.item(indexN1005E);
            childType = child.getNodeType();

            if (childType == 8) {
                childName = child.getNodeName();
                assertEquals("nodeName", "#comment", childName);
                childValue = child.getNodeValue();
                assertEquals("nodeValue", " This is comment number 1.", childValue);
                commentCount += 1;
            }
        }
        assertTrue("atMostOneComment", (commentCount < 2));
    }

}

