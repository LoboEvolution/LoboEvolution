
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
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_nodeclonenodetrueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        Node clonedNode;
        NodeList clonedList;
        Node clonedChild;
        String clonedChildName;
        NodeList origList;
        Node origChild;
        String origChildName;
        java.util.List result = new java.util.ArrayList();

        java.util.List expected = new java.util.ArrayList();

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        origList = employeeNode.getChildNodes();
        for (int indexN10065 = 0; indexN10065 < origList.getLength(); indexN10065++) {
            origChild = origList.item(indexN10065);
            origChildName = origChild.getNodeName();
            expected.add(origChildName);
        }
        clonedNode = employeeNode.cloneNode(true);
        clonedList = clonedNode.getChildNodes();
        for (int indexN1007B = 0; indexN1007B < clonedList.getLength(); indexN1007B++) {
            clonedChild = clonedList.item(indexN1007B);
            clonedChildName = clonedChild.getNodeName();
            result.add(clonedChildName);
        }
        assertEquals("clone", expected, result);
    }
}

