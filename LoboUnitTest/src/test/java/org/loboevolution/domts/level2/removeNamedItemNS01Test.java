
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "removeNamedItemNS(namespaceURI,localName)" method for a
 * NamedNodeMap should remove a node specified by localName and namespaceURI.
 * <p>
 * Retrieve a list of elements with tag name "address".
 * Access the second element from the list and get its attributes.
 * Try to remove the attribute node with local name "domestic"
 * and namespace uri "http://www.usa.com" with
 * method removeNamedItemNS(namespaceURI,localName).
 * Check to see if the node has been removed.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1074577549">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1074577549</a>
 */
public class removeNamedItemNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        Attr newAttr;
        Node removedNode;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(1);
        attributes = testAddress.getAttributes();
        removedNode = attributes.removeNamedItemNS("http://www.usa.com", "domestic");
        assertNotNull("retval", removedNode);
        newAttr = (Attr) attributes.getNamedItem("dmstc:domestic");
        assertNull("nodeRemoved", newAttr);
    }
}

