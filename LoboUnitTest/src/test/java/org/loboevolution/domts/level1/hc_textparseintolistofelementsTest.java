
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * Retrieve the textual data from the last child of the
 * second employee.   That node is composed of two
 * EntityReference nodes and two Text nodes.   After
 * the content node is parsed, the "acronym" Element
 * should contain four children with each one of the
 * EntityReferences containing one child.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-11C98490">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-11C98490</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-745549614">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-745549614</a>
 */
public class hc_textparseintolistofelementsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node addressNode;
        NodeList childList;
        Node child;
        String value;
        Node grandChild;
        int length;
        java.util.List<String> result = new java.util.ArrayList<>();

        java.util.List<String> expectedNormal = new java.util.ArrayList<>();
        expectedNormal.add("\u03b2"); // Android-changed: GREEK LOWER CASE BETA
        expectedNormal.add(" Dallas, ");
        expectedNormal.add("\u03b3"); // Android-changed: GREEK LOWER CASE GAMMA
        expectedNormal.add("\n 98554");

        java.util.List<String> expectedExpanded = new java.util.ArrayList<String>();
        expectedExpanded.add("\u03b2 Dallas, \u03b3\n 98554"); // Android-changed: GREEK LOWER CASE BETA, GREEK LOWER CASE GAMMA

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        addressNode = elementList.item(1);
        childList = addressNode.getChildNodes();
        length = childList.getLength();
        for (int indexN1007C = 0; indexN1007C < childList.getLength(); indexN1007C++) {
            child = childList.item(indexN1007C);
            value = child.getNodeValue();

            if ((value == null)) {
                grandChild = child.getFirstChild();
                assertNotNull("grandChildNotNull", grandChild);
                value = grandChild.getNodeValue();
                result.add(value);
            } else {
                result.add(value);
            }

        }

        if (length == 1) {
            assertEquals("assertEqCoalescing", expectedExpanded, result);
        } else {
            assertEquals("assertEqNormal", expectedNormal, result);
        }

    }
}

