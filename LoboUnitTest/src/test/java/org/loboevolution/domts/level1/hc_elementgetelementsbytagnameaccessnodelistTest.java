
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
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The "getElementsByTagName(name)" method returns a list
 * of all descendant Elements in the order the children
 * were encountered in a pre order traversal of the element
 * tree.
 * Create a NodeList of all the descendant elements
 * using the string "p" as the tagName.
 * The method should return a NodeList whose length is
 * "5" in the order the children were encountered.
 * Access the FOURTH element in the NodeList.  The FOURTH
 * element, the first or second should be an "em" node with
 * the content "EMP0004".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_elementgetelementsbytagnameaccessnodelistTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Node firstC;
        String childName;
        int nodeType;
        CharacterData employeeIDNode;
        String employeeID;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        testEmployee = (Element) elementList.item(3);
        firstC = testEmployee.getFirstChild();
        nodeType = firstC.getNodeType();

        while (nodeType == 3) {
            firstC = firstC.getNextSibling();
            nodeType = firstC.getNodeType();

        }
        childName = firstC.getNodeName();
        assertEquals("childName", "EM", childName);
        employeeIDNode = (CharacterData) firstC.getFirstChild();
        employeeID = employeeIDNode.getNodeValue();
        assertEquals("employeeID", "EMP0004", employeeID);
    }
}

