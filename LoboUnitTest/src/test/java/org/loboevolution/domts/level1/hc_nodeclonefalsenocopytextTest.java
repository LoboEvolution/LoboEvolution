
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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertNull;


/**
 * The "cloneNode(deep)" method does not copy text unless it
 * is deep cloned.(Test for deep=false)
 * <p>
 * Retrieve the fourth child of the second employee and
 * the "cloneNode(deep)" method with deep=false.   The
 * duplicate node returned by the method should not copy
 * any text data contained in this node.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4</a>
 */
public class hc_nodeclonefalsenocopytextTest extends LoboUnitTest {

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
        Node childNode;
        Node clonedNode;
        Node lastChildNode;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        childNode = childList.item(3);
        clonedNode = childNode.cloneNode(false);
        lastChildNode = clonedNode.getLastChild();
        assertNull("nodeCloneFalseNoCopyTextAssert1", lastChildNode);
    }
}

