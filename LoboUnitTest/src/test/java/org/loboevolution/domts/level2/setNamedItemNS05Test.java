
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

import static org.junit.Assert.assertEquals;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should replace an existing node n1 found in the map with arg if n1
 * has the same namespaceURI and localName as arg and return n1.
 * <p>
 * Create an attribute node in with namespaceURI "http://www.usa.com"
 * and qualifiedName "dmstc:domestic" whose value is "newVal".
 * Invoke method setNamedItemNS((Attr)arg) on the map of the first "address"
 * element. Method should return the old attribute node identified
 * by namespaceURI and qualifiedName from above,whose value is "Yes".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class setNamedItemNS05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.usa.com";
        String qualifiedName = "dmstc:domestic";
        Document doc;
        Node arg;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        Node retnode;
        String value;
        doc = sampleXmlFile("staffNS.xml");
        arg = doc.createAttributeNS(namespaceURI, qualifiedName);
        arg.setNodeValue("newValue");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(0);
        attributes = testAddress.getAttributes();
        retnode = attributes.setNamedItemNS((Attr)arg);
        value = retnode.getNodeValue();
        assertEquals("throw_Equals", "Yes", value);
    }
}

