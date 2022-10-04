
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
 * The "insertBefore(newChild,refChild)" method inserts the
 * node "newChild" before the node "refChild".
 * <p>
 * Insert a newly created Element node before the second
 * sup element in the document and check the "newChild"
 * and "refChild" after insertion for correct placement.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=247">http://www.w3.org/Bugs/Public/show_bug.cgi?id=247</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=261">http://www.w3.org/Bugs/Public/show_bug.cgi?id=261</a>
 */
public class hc_nodeinsertbeforeTest extends LoboUnitTest {

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
        String childName;
        Node insertedNode;
        List<String> actual = new ArrayList<>();

        List<String> expected = new ArrayList<>();
        expected.add("EM");
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("BR");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");

        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("sup");
        refChild = elementList.item(2);
        employeeNode = refChild.getParentNode();
        childList = employeeNode.getChildNodes();
        newChild = doc.createElement("BR");
        insertedNode = employeeNode.insertBefore(newChild, refChild);
        for (int indexN10091 = 0; indexN10091 < childList.getLength(); indexN10091++) {
            child = childList.item(indexN10091);
            nodeType = child.getNodeType();

            if (nodeType == 1) {
                childName = child.getNodeName();
                actual.add(childName);
            }
        }
        assertEquals("nodeNames", expected, actual);
    }
}

