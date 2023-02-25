
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
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "item(index)" method returns the indexth item in
 * the map(test for last item).
 * <p>
 * Retrieve the second employee and create a NamedNodeMap
 * listing of the attributes of the last child. Since the
 * DOM does not specify an order of these nodes the contents
 * of the LAST node can contain either "domestic" or "street".
 * The test should return "true" if the LAST node is either
 * of these values.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9</a>
 */
public class namednodemapreturnlastitemTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        NamedNodeMap attributes;
        Node child;
        String name;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(1);
        attributes = testEmployee.getAttributes();
        child = attributes.item(1);
        name = child.getNodeName();
        assertTrue("namednodemapReturnFirstItemAssert",
                ("domestic".equals(name) | "street".equals(name))
        );
    }
}

