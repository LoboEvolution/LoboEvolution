
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "getAttributeNS(namespaceURI,localName)" method retrieves an
 * attribute value by local name and NamespaceURI.
 * <p>
 * Retrieve the first "emp:address" element.
 * Create a new attribute with the "createAttributeNS()" method.
 * Add the new attribute and value with the "setAttributeNS()" method.
 * The value returned by the "getAttributeNS()" method should be
 * the string "NewValue" since the attribute had a specified value.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS</a>
 */
public class getAttributeNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.nist.gov";
        String localName = "blank";
        String qualifiedName = "emp:blank";
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        String attrValue;
        doc = sampleXmlFile("staffNS.xml");
        doc.createAttributeNS(namespaceURI, qualifiedName);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        testAddr.setAttributeNS(namespaceURI, qualifiedName, "NewValue");
        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
        assertEquals("throw_Equals", "NewValue", attrValue);
    }
}

