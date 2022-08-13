
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

import static org.junit.Assert.assertEquals;


/**
 * The "setAttributeNode(newAttr)" method adds a new
 * attribute to the Element.  If the "newAttr" Attr node is
 * already present in this element, it should replace the
 * existing one.
 * <p>
 * Retrieve the last child of the third employee and add a
 * new attribute node by invoking the "setAttributeNode(new
 * Attr)" method.  The new attribute node to be added is
 * "class", which is already present in this element.  The
 * method should replace the existing Attr node with the
 * new one.  This test uses the "createAttribute(name)"
 * method from the Document interface.
 *
 * @author Curt Arnold
 */
public class hc_elementreplaceexistingattributeTest extends LoboUnitTest {

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
        String strong;
        Attr setAttr;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        testEmployee = (Element) elementList.item(2);
        newAttribute = doc.createAttribute("class");
        setAttr = testEmployee.setAttributeNode(newAttribute);
        strong = testEmployee.getAttribute("class");
        assertEquals("replacedValue", "", strong);
    }
}

