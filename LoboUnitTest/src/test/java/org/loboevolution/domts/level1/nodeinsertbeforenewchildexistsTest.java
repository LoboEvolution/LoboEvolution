
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
 * If the "newChild" is already in the tree, the
 * "insertBefore(newChild,refChild)" method must first
 * remove it before the insertion takes place.
 * <p>
 * Insert a node Element ("employeeId") that is already
 * present in the tree.   The existing node should be
 * removed first and the new one inserted.   The node is
 * inserted at a different position in the tree to assure
 * that it was indeed inserted.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class nodeinsertbeforenewchildexistsTest extends LoboUnitTest {

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
        int length;
        String childName;
        List<String> expectedWhitespace = new ArrayList<>();
        expectedWhitespace.add("#text");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("name");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("position");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("salary");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("gender");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("employeeId");
        expectedWhitespace.add("address");
        expectedWhitespace.add("#text");

        List<String> expectedNoWhitespace = new ArrayList<String>();
        expectedNoWhitespace.add("EMPLOYEEID");
        expectedNoWhitespace.add("NAME");
        expectedNoWhitespace.add("POSITION");
        expectedNoWhitespace.add("SALARY");
        expectedNoWhitespace.add("GENDER");
        expectedNoWhitespace.add("ADDRESS");

        List<String> expected;
        List<String> result = new ArrayList<>();

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();

        if (length == 6) {
            expected = expectedNoWhitespace;
            refChild = childList.item(5);
            newChild = childList.item(0);
        } else {
            expected = expectedWhitespace;
            refChild = childList.item(11);
            newChild = childList.item(1);
        }

        employeeNode.insertBefore(newChild, refChild);
        for (int indexN100DD = 0; indexN100DD < childList.getLength(); indexN100DD++) {
            child = childList.item(indexN100DD);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("childNames", expected, result);
    }

}

