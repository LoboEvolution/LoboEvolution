
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "getPrefix()" method
 * returns the namespace prefix of this node, or null if unspecified.
 * For nodes of any type other than ELEMENT_NODE and ATTRIBUTE_NODE,
 * this is always null.
 * <p>
 * Retrieve the first emp:employeeId node and get the first child of this node.
 * Since the first child is Text node invoking the "getPrefix()"
 * method will cause "null" to be returned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class prefix02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Node textNode;
        String prefix;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("emp:employeeId");
        testEmployee = (Element)elementList.item(0);
        assertNotNull("empEmployeeNotNull", testEmployee);
        textNode = testEmployee.getFirstChild();
        prefix = textNode.getPrefix();
        assertNull("textNodePrefix", prefix);
    }
}

