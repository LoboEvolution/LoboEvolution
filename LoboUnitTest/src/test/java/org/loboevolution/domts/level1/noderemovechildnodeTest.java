
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

import static org.junit.Assert.assertEquals;


/**
 * Retrieve the second employee and remove its first child.
 * After the removal, the second employee should have five or twelve
 * children and the first child should now be the child
 * that used to be at the second position in the list.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066</a>
 */
public class noderemovechildnodeTest extends LoboUnitTest {

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
        Node oldChild;
        Node child;
        String childName;
        int length;
        Node removedChild;
        String removedName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        oldChild = childList.item(0);
        removedChild = employeeNode.removeChild(oldChild);
        removedName = removedChild.getNodeName();
        child = childList.item(0);
        childName = child.getNodeName();
        length = childList.getLength();

        if (length == 5) {
            assertEquals("removedNameNoWhitespace", "EMPLOYEEID",removedName);
            assertEquals("childNameNoWhitespace", "name", childName);
        } else {
            assertEquals("removedName", "#text", removedName);
            assertEquals("childName", "EMPLOYEEID",childName);
            assertEquals("length", 12, length);
        }

    }

}

