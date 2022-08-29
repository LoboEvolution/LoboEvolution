
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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "setAttributeNodeNS(newAttr)" adds a new attribute.
 * If an attribute with that local name and that namespaceURI is already
 * present in the element, it is replaced by the new one.
 * <p>
 * Retrieve the first address element and add a new attribute
 * to the element.  Since an attribute with the same local name
 * and namespaceURI as the newly created attribute does not exist
 * the value "null" is returned.
 * This test uses the "createAttributeNS(namespaceURI,localName)
 * method from the Document interface to create the new attribute to add.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class setAttributeNodeNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.newattr.com";
        String qualifiedName = "newAttr";
        Document doc;
        HTMLCollection elementList;
        Node testAddr;
        Attr newAttr;
        Attr newAddrAttr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        newAttr = doc.createAttributeNS(namespaceURI, qualifiedName);
        newAddrAttr = ((Element) testAddr).setAttributeNodeNS(newAttr);
        assertNull("throw_Null", newAddrAttr);
    }
}

