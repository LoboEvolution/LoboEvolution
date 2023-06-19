
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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * If the "newChild" is already in the tree, the
 * "insertBefore(newChild,refChild)" method must first
 * remove it before the insertion takes place.
 * <p>
 * Insert a node Element ("em") that is already
 * present in the tree.   The existing node should be
 * removed first and the new one inserted.   The node is
 * inserted at a different position in the tree to assure
 * that it was indeed inserted.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_nodeinsertbeforenewchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        HTMLCollection childList;
        Node refChild;
        Node newChild;
        Node child;
        String childName;
        List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("EM");
        expected.add("ACRONYM");

        List<String> result = new ArrayList<>();

        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = ((Element) employeeNode).getElementsByTagName("*");
        refChild = childList.item(5);
        newChild = childList.item(0);
        employeeNode.insertBefore(newChild, refChild);
        for (int indexN1008C = 0; indexN1008C < childList.getLength(); indexN1008C++) {
            child = childList.item(indexN1008C);
            nodeType = child.getNodeType();

            if (nodeType == 1) {
                childName = child.getNodeName();
                result.add(childName);
            }
        }
        assertEquals("childNames", expected, result);
    }

}

