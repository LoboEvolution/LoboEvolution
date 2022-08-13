
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

import static org.junit.Assert.assertEquals;


/**
 * Element.getElementsByTagName("employee") should return a NodeList whose length is
 * "5" in the order the children were encountered.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D</a>
 */
public class elementgetelementsbytagnameaccessnodelistTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Node child;
        String childName;
        String childValue;
        int childType;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        testEmployee = (Element) elementList.item(3);
        child = testEmployee.getFirstChild();
        childType = child.getNodeType();

        if (childType == 3) {
            child = child.getNextSibling();
        }
        childName = child.getNodeName();
        assertEquals("nodename", "EMPLOYEEID",childName);
        child = child.getFirstChild();
        childValue = child.getNodeValue();
        assertEquals("emp0004", "EMP0004", childValue);
    }
}

