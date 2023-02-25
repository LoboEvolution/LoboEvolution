
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
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Create and populate a new DocumentFragment object and
 * append it to the second employee.   After the
 * "appendChild(newChild)" method is invoked retrieve the
 * new nodes at the end of the list, they should be the
 * two Element nodes from the DocumentFragment.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 */
public class nodeappendchilddocfragmentTest extends LoboUnitTest {

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
        DocumentFragment newdocFragment;
        Node newChild1;
        Node newChild2;
        Node child;
        String childName;
        List<String> result = new java.util.ArrayList<String>();

        int nodeType;
        List<String> expected = new ArrayList<String>();
        expected.add("EMPLOYEEID");
        expected.add("NAME");
        expected.add("POSITION");
        expected.add("SALARY");
        expected.add("GENDER");
        expected.add("ADDRESS");
        expected.add("NEWCHILD1");
        expected.add("NEWCHILD2");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        newdocFragment = doc.createDocumentFragment();
        newChild1 = doc.createElement("newChild1");
        newChild2 = doc.createElement("newChild2");
        newdocFragment.appendChild(newChild1);
        newdocFragment.appendChild(newChild2);
        employeeNode.appendChild(newdocFragment);
        for (int indexN1009F = 0; indexN1009F < childList.getLength(); indexN1009F++) {
            child = childList.item(indexN1009F);
            nodeType = child.getNodeType();

            if (nodeType == 1) {
                childName = child.getNodeName();
                result.add(childName);
            }
        }
        assertEquals("elementNames", expected, result);
    }
}

