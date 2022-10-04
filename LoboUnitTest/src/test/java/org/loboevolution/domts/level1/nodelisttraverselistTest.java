
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
 * The range of valid child node indices is 0 thru length -1
 * <p>
 * Create a list of all the children elements of the third
 * employee and traverse the list from index=0 thru
 * length -1.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-203510337">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-203510337</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-844377136">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-844377136</a>
 */
public class nodelisttraverselistTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        NodeList employeeList;
        Node child;
        String childName;
        java.util.List<String> result = new java.util.ArrayList<String>();

        int length;
        java.util.List<String> expectedWhitespace = new java.util.ArrayList<>();
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
        employeeNode = elementList.item(2);
        employeeList = employeeNode.getChildNodes();
        length = employeeList.getLength();
        for (int indexN100A4 = 0; indexN100A4 < employeeList.getLength(); indexN100A4++) {
            child = employeeList.item(indexN100A4);
            childName = child.getNodeName();
            result.add(childName);
        }

        if (length == 6) {
            assertEquals("nowhitespace", expectedNoWhitespace, result);
        } else {
            assertEquals("whitespace", expectedWhitespace, result);
        }

    }

}

