
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * The "getChildNodes()" method returns a NodeList
 * that contains all children of this node.
 * <p>
 * Retrieve the second employee and check the NodeList
 * returned by the "getChildNodes()" method.   The
 * length of the list should be 13.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_nodechildnodesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        Node childNode;
        NodeList childNodes;
        int nodeType;
        String childName;
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        expected.add("EM");
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childNodes = employeeNode.getChildNodes();
        for (int indexN1006C = 0; indexN1006C < childNodes.getLength(); indexN1006C++) {
            childNode = childNodes.item(indexN1006C);
            nodeType = childNode.getNodeType();
            childName = childNode.getNodeName();

            if (nodeType == 1) {
                actual.add(childName);
            } else {
                assertEquals("textNodeType", 3, nodeType);
            }

        }
        assertEquals("elementNames", expected, actual);
    }
}

