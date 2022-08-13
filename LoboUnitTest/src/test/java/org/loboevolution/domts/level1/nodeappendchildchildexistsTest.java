
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

import static org.junit.Assert.assertEquals;


/**
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is appended.
 * <p>
 * Retrieve the first child of the second employee and
 * append the first child to the end of the list.   After
 * the "appendChild(newChild)" method is invoked the first
 * child should be the one that was second and the last
 * child should be the one that was first.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 */
public class nodeappendchildchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node childNode;
        Node newChild;
        Node lchild;
        Node fchild;
        String lchildName;
        String fchildName;
        Node appendedChild;
        String initialName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        childNode = elementList.item(1);
        newChild = childNode.getFirstChild();
        initialName = newChild.getNodeName();
        appendedChild = childNode.appendChild(newChild);
        fchild = childNode.getFirstChild();
        fchildName = fchild.getNodeName();
        lchild = childNode.getLastChild();
        lchildName = lchild.getNodeName();

        if ("employeeId".equals(initialName)) {
            assertEquals("assert1_nowhitespace", "name", fchildName);
            assertEquals("assert2_nowhitespace", "EMPLOYEEID",lchildName);
        } else {
            assertEquals("assert1", "EMPLOYEEID", fchildName);
            assertEquals("assert2", "#text", lchildName);
        }

    }
}

