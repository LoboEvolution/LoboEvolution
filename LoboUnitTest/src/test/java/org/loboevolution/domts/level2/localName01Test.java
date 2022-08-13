
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
 * The "getLocalName()" method for a Node
 * returns the local part of the qualified name of this node,
 * and for nodes of any type other than ELEMENT_NODE and ATTRIBUTE_NODE
 * and nodes created with a DOM Level 1 method, this is null.
 * <p>
 * Retrieve the first emp:address node and get the attributes of this node."
 * Then apply the getLocalName() method to the emp:domestic attribute.
 * The method should return "domestic".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSLocalN">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSLocalN</a>
 */
public class localName01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        Attr addrAttr;
        String localName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        addrAttr = testAddr.getAttributeNode("emp:domestic");
        localName = addrAttr.getLocalName();
        assertEquals("localName", "domestic", localName);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/localName01";
    }
}

