
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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The NodeList returned by the "getChildNodes()" method
 * is live.   Changes on the node's children are immediately
 * reflected on the nodes returned in the NodeList.
 * <p>
 * Create a NodeList of the children of the second employee
 * and then add a newly created element that was created
 * by the "createElement()" method(Document Interface) to
 * the second employee by using the "appendChild()" method.
 * The length of the NodeList should reflect this new
 * addition to the child list.   It should return the value 14.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=247">http://www.w3.org/Bugs/Public/show_bug.cgi?id=247</a>
 */
public class hc_nodechildnodesappendchildTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        NodeList childList;
        Node createdNode;
        Node childNode;
        String childName;
        int childType;
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        expected.add("EM");
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");
        expected.add("BR");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        createdNode = doc.createElement("br");
        employeeNode = employeeNode.appendChild(createdNode);
        for (int indexN10087 = 0; indexN10087 < childList.getLength(); indexN10087++) {
            childNode = childList.item(indexN10087);
            childName = childNode.getNodeName();
            childType = childNode.getNodeType();

            if (childType == 1) {
                actual.add(childName);
            } else {
                assertEquals("textNodeType", 3, childType);
            }

        }
        assertEquals("childElements", expected, actual);
    }
}

