
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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The "removeChild(oldChild)" method removes the node
 * indicated by "oldChild".
 * <p>
 * Retrieve the second p element and remove its first child.
 * After the removal, the second p element should have 5 element
 * children and the first child should now be the child
 * that used to be at the second position in the list.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_noderemovechildnodeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        HTMLCollection emList;
        Node employeeNode;
        NodeList childList;
        Node oldChild;
        Node child;
        String childName;
        Node removedChild;
        String removedName;
        int nodeType;
        List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");

        java.util.List<String> actual = new java.util.ArrayList<>();

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        emList = ((Element) /*Node */employeeNode).getElementsByTagName("em");
        oldChild = emList.item(0);
        removedChild = employeeNode.removeChild(oldChild);
        removedName = removedChild.getNodeName();
        assertEquals("removedName", "EM", removedName);
        for (int indexN10098 = 0; indexN10098 < childList.getLength(); indexN10098++) {
            child = childList.item(indexN10098);
            nodeType = child.getNodeType();
            childName = child.getNodeName();

            if (nodeType == 1) {
                actual.add(childName);
            } else {
                assertEquals("textNodeType", 3, nodeType);
                assertEquals("textNodeName", "#text", childName);
            }

        }
        assertEquals("childNames", expected, actual);
    }
}

