
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertNull;


/**
 * The "removeAttributeNode(oldAttr)" method removes the
 * specified attribute.
 * <p>
 * Retrieve the last child of the third employee, add a
 * new "district" node to it and then try to remove it.
 * To verify that the node was removed use the
 * "getNamedItem(name)" method from the NamedNodeMap
 * interface.  It also uses the "getAttributes()" method
 * from the Node interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D589198">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D589198</a>
 */
public class elementremoveattributeaftercreateTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Attr newAttribute;
        NamedNodeMap attributes;
        Attr districtAttr;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(2);
        newAttribute = doc.createAttribute("district");
        districtAttr = testEmployee.setAttributeNode(newAttribute);
        districtAttr = testEmployee.removeAttributeNode(newAttribute);
        attributes = testEmployee.getAttributes();
        districtAttr = (Attr) attributes.getNamedItem("district");
        assertNull("elementRemoveAttributeAfterCreateAssert", districtAttr);
    }
}

