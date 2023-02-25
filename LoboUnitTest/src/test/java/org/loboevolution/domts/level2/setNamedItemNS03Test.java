
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should add a node using its namespaceURI and localName given that
 * there is no existing node with the same namespaceURI and localName in the map.
 * <p>
 * Create an attr node with namespaceURI "http://www.nist.gov",qualifiedName
 * "prefix:newAttr" and value "newValue".
 * Invoke method setNamedItemNS((Attr)arg) on the map of the first "address"
 * element where arg is identified by the namespaceURI and qualifiedName
 * from above.  Method should return the newly added attr node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D080">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D080</a>
 */
public class setNamedItemNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.nist.gov";
        String qualifiedName = "prefix:newAttr";
        Document doc;
        Attr arg;
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
        attributes.setNamedItemNS(arg);
        retnode = attributes.getNamedItemNS(namespaceURI, "newAttr");
        value = retnode.getNodeValue();
        assertEquals("throw_Equals", "newValue", value);
    }
}

