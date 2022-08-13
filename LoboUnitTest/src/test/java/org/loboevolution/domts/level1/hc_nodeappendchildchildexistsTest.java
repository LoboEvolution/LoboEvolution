
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
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is appended.
 * <p>
 * Retrieve the "em" second employee and
 * append the first child to the end of the list.   After
 * the "appendChild(newChild)" method is invoked the first
 * child should be the one that was second and the last
 * child should be the one that was first.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_nodeappendchildchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        HTMLCollection childList;
        Node childNode;
        Node newChild;
        Node memberNode;
        String memberName;
        java.util.List<String> refreshedActual = new java.util.ArrayList<>();

        java.util.List<String> actual = new java.util.ArrayList<>();

        int nodeType;
        List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");
        expected.add("EM");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        childNode = elementList.item(1);
        childList = ((Element)childNode).getElementsByTagName("*");
        newChild = childList.item(0);
        childNode.appendChild(newChild);
        for (int indexN10085 = 0; indexN10085 < childList.getLength(); indexN10085++) {
            memberNode = childList.item(indexN10085);
            memberName = memberNode.getNodeName();
            actual.add(memberName);
        }
        assertEquals("liveByTagName", expected, actual);
        NodeList childList2 = childNode.getChildNodes();
        for (int indexN1009C = 0; indexN1009C < childList2.getLength(); indexN1009C++) {
            memberNode = childList2.item(indexN1009C);
            nodeType = memberNode.getNodeType();

            if (nodeType == 1) {
                memberName = memberNode.getNodeName();
                refreshedActual.add(memberName);
            }
        }
        assertEquals("refreshedChildNodes", expected, refreshedActual);
    }
}

