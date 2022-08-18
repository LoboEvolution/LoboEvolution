
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "setAttributeNS(namespaceURI,qualifiedName,value)" method adds a new attribute.
 * If an attribute with the same local name and namespace URI is already present
 * on the element, its prefix is changed to be the prefix part of the "qualifiedName",
 * and its vale is changed to be the "value" paramter.
 * null value if no previously existing Attr node with the
 * same name was replaced.
 * <p>
 * Add a new attribute to the "address" element.
 * Check to see if the new attribute has been successfully added to the document.
 * The new attribute "<newValue>" contains markup and therefore is escaped
 * by the implementation.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS</a>
 */
public class setAttributeNS05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String localName = "newAttr";
        String namespaceURI = "http://www.newattr.com";
        String qualifiedName = "newAttr";
        Document doc;
        HTMLCollection elementList;
        Node testAddr;
        Attr addrAttr;
        String resultAttr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        ((Element) /*Node */testAddr).setAttributeNS(namespaceURI, qualifiedName, "<newValue>");
        resultAttr = ((Element) /*Node */testAddr).getAttributeNS(namespaceURI, localName);
        assertEquals("throw_Equals", "<newValue>", resultAttr);
    }
}

