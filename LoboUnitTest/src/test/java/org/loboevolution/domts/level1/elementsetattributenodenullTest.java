
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
 * The "setAttributeNode(newAttr)" method returns the
 * null value if no previously existing Attr node with the
 * same name was replaced.
 * <p>
 * Retrieve the last child of the third employee and add a
 * new attribute to it.  The new attribute node added is
 * "district", which is not part of this Element.  The
 * method should return the null value.
 * This test uses the "createAttribute(name)"
 * method from the Document interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 */
public class elementsetattributenodenullTest extends LoboUnitTest {

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
        Attr districtAttr;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(2);
        newAttribute = doc.createAttribute("district");
        districtAttr = testEmployee.setAttributeNode(newAttribute);
        assertNull("elementSetAttributeNodeNullAssert", districtAttr);
    }
}

