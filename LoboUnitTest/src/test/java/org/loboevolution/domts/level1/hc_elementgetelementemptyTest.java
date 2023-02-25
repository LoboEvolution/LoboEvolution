
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

import static org.junit.Assert.*;


/**
 * The "getAttribute(name)" method returns an empty
 * string if no value was assigned to an attribute and
 * no default value was given in the DTD file.
 * <p>
 * Retrieve the last child of the last employee, then
 * invoke "getAttribute(name)" method, where "strong" is an
 * attribute without a specified or DTD default value.
 * The "getAttribute(name)" method should return the empty
 * string.  This method makes use of the
 * "createAttribute(newAttr)" method from the Document
 * interface.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-666EE0F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-666EE0F9</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class hc_elementgetelementemptyTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr newAttribute;
        HTMLCollection elementList;
        Element testEmployee;
        Attr domesticAttr;
        String attrValue;
        doc = sampleXmlFile("hc_staff.xml");
        newAttribute = doc.createAttribute("lang");
        elementList = doc.getElementsByTagName("acronym");
        testEmployee = (Element) elementList.item(3);
        domesticAttr = testEmployee.setAttributeNode(newAttribute);
        attrValue = testEmployee.getAttribute("lang");
        assertEquals("elementGetElementEmptyAssert", null, attrValue);
    }
}

