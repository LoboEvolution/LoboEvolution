
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * The "setAttributeNode(newAttr)" method adds a new
 * attribute to the Element.
 * <p>
 * Retrieve first address element and add
 * a new attribute node to it by invoking its
 * "setAttributeNode(newAttr)" method.  This test makes use
 * of the "createAttribute(name)" method from the Document
 * interface.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class hc_elementcreatenewattributeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddress;
        Attr newAttribute;
        Attr oldAttr;
        Attr districtAttr;
        String attrVal;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        testAddress = (Element) elementList.item(0);
        newAttribute = doc.createAttribute("lang");
        oldAttr = testAddress.setAttributeNode(newAttribute);
        assertNull("old_attr_doesnt_exist", oldAttr);
        districtAttr = testAddress.getAttributeNode("lang");
        assertNotNull("new_district_accessible", districtAttr);
        attrVal = testAddress.getAttribute("lang");
        assertEquals("attr_value", "", attrVal);
    }
}

