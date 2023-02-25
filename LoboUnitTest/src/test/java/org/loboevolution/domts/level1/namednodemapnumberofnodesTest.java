
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

import static org.junit.Assert.*;


/**
 * The "getLength()" method returns the number of nodes
 * in the map.
 * <p>
 * Retrieve the second employee and create a NamedNodeMap
 * listing of the attributes of the last child.  Once the
 * list is created an invocation of the "getLength()"
 * method is executed.  The number of nodes should be 2.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6D0FB19E">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6D0FB19E</a>
 */
public class namednodemapnumberofnodesTest extends LoboUnitTest {

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
        int length;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(2);
        attributes = testEmployee.getAttributes();
        length = attributes.getLength();
        assertEquals("length", 2, length);
    }

}

