
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
 * The "cloneNode(deep)" method returns a copy of the node
 * and the subtree under it if deep=true.
 * <p>
 * Retrieve the second employee and invoke the
 * "cloneNode(deep)" method with deep=true.   The
 * method should clone this node and the subtree under it.
 * The NodeName of each child in the returned node is
 * checked to insure the entire subtree under the second
 * employee was cloned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4</a>
 */
public class nodeclonenodetrueTest extends LoboUnitTest {

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
        Node clonedNode;
        NodeList clonedList;
        Node clonedChild;
        String clonedChildName;
        int length;
        List<String> result = new ArrayList<>();

        List<String> expectedWhitespace = new ArrayList<>();
        expectedWhitespace.add("#text");
        expectedWhitespace.add("employeeId");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("name");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("position");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("salary");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("gender");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("ADDRESS");
        expectedWhitespace.add("#text");

        List<String> expectedNoWhitespace = new ArrayList<>();
        expectedNoWhitespace.add("EMPLOYEEID");
        expectedNoWhitespace.add("NAME");
        expectedNoWhitespace.add("POSITION");
        expectedNoWhitespace.add("SALARY");
        expectedNoWhitespace.add("GENDER");
        expectedNoWhitespace.add("ADDRESS");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();
        clonedNode = employeeNode.cloneNode(true);
        clonedList = clonedNode.getChildNodes();
        for (int indexN100AE = 0; indexN100AE < clonedList.getLength(); indexN100AE++) {
            clonedChild = clonedList.item(indexN100AE);
            clonedChildName = clonedChild.getNodeName();
            result.add(clonedChildName);
        }

        if (length == 6) {
            assertEquals("nowhitespace", expectedNoWhitespace, result);
        } else {
            assertEquals("whitespace", expectedWhitespace, result);
        }

    }
}

