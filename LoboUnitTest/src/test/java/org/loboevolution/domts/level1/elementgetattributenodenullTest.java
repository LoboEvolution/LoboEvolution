
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

import static org.junit.Assert.assertNull;


/**
 * The "getAttributeNode(name)" method retrieves an
 * attribute node by name.  It should return null if the
 * "name" attribute does not exist.
 * <p>
 * Retrieve the last child of the first employee and attempt
 * to retrieve a non-existing attribute.  The method should
 * return "null".  The non-existing attribute to be used
 * is "invalidAttribute".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-217A91B8">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-217A91B8</a>
 */
public class elementgetattributenodenullTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Attr domesticAttr;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(0);
        domesticAttr = testEmployee.getAttributeNode("invalidAttribute");
        assertNull("elementGetAttributeNodeNullAssert", domesticAttr);
    }
}

