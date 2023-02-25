
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNull;


/**
 * If the "setNamedItem(arg)" method does not replace an
 * existing node with the same name then it returns null.
 * <p>
 * Retrieve the third employee and create a NamedNodeMap
 * object from the attributes of the last child.
 * Once the list is created the "setNamedItem(arg)" method
 * is invoked with arg=newAttr, where newAttr is a
 * newly created Attr Node and whose node name
 * already exists in the map.  The "setNamedItem(arg)"
 * method should add the new node and return null.
 * This test uses the "createAttribute(name)" method from
 * the document interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9</a>
 */
public class namednodemapsetnameditemwithnewvalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Attr newAttribute;
        Element testAddress;
        NamedNodeMap attributes;
        Node newNode;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element)elementList.item(2);
        newAttribute = doc.createAttribute("district");
        attributes = testAddress.getAttributes();
        newNode = attributes.setNamedItem(newAttribute);
        assertNull("returnedNodeNull", newNode);
    }
}

