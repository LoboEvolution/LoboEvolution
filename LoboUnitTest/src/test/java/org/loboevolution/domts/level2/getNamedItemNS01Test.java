
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
 * The "getNamedItemNS(namespaceURI,localName)" method for a
 * NamedNodeMap should return a node specified by localName and namespaceURI
 * <p>
 * Retrieve a list of elements with tag name "address".
 * Access the second element from the list and get its attributes.
 * Try to retrieve the attribute node with local name "domestic"
 * and namespace uri "http://www.usa.com" with
 * method getNamedItemNS(namespaceURI,localName).
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095</a>
 */
public class getNamedItemNS01Test extends LoboUnitTest {

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
        Attr domesticAttr;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element)elementList.item(1);
        attributes = testEmployee.getAttributes();
        domesticAttr = (Attr) attributes.getNamedItemNS("http://www.usa.com", "domestic");
        attrName = domesticAttr.getNodeName();
        assertEquals("attrName", "dmstc:domestic", attrName);
    }
}

