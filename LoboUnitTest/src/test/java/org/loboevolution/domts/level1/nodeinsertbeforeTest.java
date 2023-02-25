
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
 * The "insertBefore(newChild,refChild)" method inserts the
 * node "newChild" before the node "refChild".
 * <p>
 * Insert a newly created Element node before the eigth
 * child of the second employee and check the "newChild"
 * and "refChild" after insertion for correct placement.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class nodeinsertbeforeTest extends LoboUnitTest {

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
        Node refChild;
        Node newChild;
        Node child;
        String childName;
        int length;
        List<String> actual = new java.util.ArrayList<>();

        List<String> expectedWithWhitespace = new ArrayList<>();
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("employeeId");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("name");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("position");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("newChild");
        expectedWithWhitespace.add("salary");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("gender");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("ADDRESS");
        expectedWithWhitespace.add("#text");

        List<String> expectedWithoutWhitespace = new ArrayList<>();
        expectedWithoutWhitespace.add("EMPLOYEEID");
        expectedWithoutWhitespace.add("NAME");
        expectedWithoutWhitespace.add("POSITION");
        expectedWithoutWhitespace.add("newChild");
        expectedWithoutWhitespace.add("SALARY");
        expectedWithoutWhitespace.add("GENDER");
        expectedWithoutWhitespace.add("ADDRESS");

        List<String> expected;

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();

        if (length == 6) {
            refChild = childList.item(3);
            expected = expectedWithoutWhitespace;
        } else {
            refChild = childList.item(7);
            expected = expectedWithWhitespace;
        }

        newChild = doc.createElement("newChild");
        employeeNode.insertBefore(newChild, refChild);
        for (int indexN100DC = 0; indexN100DC < childList.getLength(); indexN100DC++) {
            child = childList.item(indexN100DC);
            childName = child.getNodeName();
            actual.add(childName);
        }
        assertEquals("nodeNames", expected, actual);
    }

}

