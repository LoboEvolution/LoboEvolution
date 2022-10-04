
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;


/**
 * The "getOwnerElement()" will return the Element node this attribute
 * is attached to or null if this attribute is not in use.
 * Get the "domestic" attribute from the first "address" node.
 * Apply the "getOwnerElement()" method to get the Element associated
 * with the attribute.  The value returned should be "address".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095</a>
 */
public class ownerElement01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection addressList;
        Element testNode;
        NamedNodeMap attributes;
        Attr domesticAttr;
        Element elementNode;
        String name;
        doc = sampleXmlFile("staff.xml");
        addressList = doc.getElementsByTagName("address");
        testNode = (Element) addressList.item(0);
        attributes = testNode.getAttributes();
        domesticAttr = attributes.getNamedItem("domestic");
        elementNode = domesticAttr.getOwnerElement();
        name = elementNode.getNodeName();
        assertEquals("throw_Equals", "ADDRESS", name);
    }
}

