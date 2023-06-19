
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
import static org.junit.Assert.assertSame;


/**
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is added.
 * <p>
 * Retrieve the second "p" and replace "acronym" with its "em".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class hc_nodereplacechildnewchildexistsTest extends LoboUnitTest {

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
        Node oldChild;
        Node newChild;
        String childName;
        Node childNode;
        List<String> actual = new ArrayList<String>();

        List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("EM");

        Node replacedChild;
        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = ((Element) employeeNode).getElementsByTagName("*");
        newChild = childList.item(0);
        oldChild = childList.item(5);
        replacedChild = employeeNode.replaceChild(newChild, oldChild);
        assertSame("return_value_same", oldChild, replacedChild);
        childList = ((Element) employeeNode).getElementsByTagName("*");
        for (int indexN10094 = 0; indexN10094 < childList.getLength(); indexN10094++) {
            childNode = childList.item(indexN10094);
            childName = childNode.getNodeName();
            nodeType = childNode.getNodeType();

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

