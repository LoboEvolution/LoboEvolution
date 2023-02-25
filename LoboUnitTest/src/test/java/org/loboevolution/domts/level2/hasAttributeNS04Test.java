
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The "hasAttributeNS()" method for an Element should
 * return true if the attribute with the given local name
 * and namespace URI has a default value.
 * Retrieve the first "address" element.
 * The boolean value returned by the "hasAttributeNS()" should be true
 * since the attribute has a default value.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS</a>
 */
public class hasAttributeNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String localName = "district";
        String namespaceURI = "http://www.nist.gov";
        Document doc;
        HTMLCollection elementList;
        Element testNode;
        boolean state;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testNode = (Element) elementList.item(0);
        assertNotNull("empAddressNotNull", testNode);
        state = testNode.hasAttributeNS(namespaceURI, localName);
        assertTrue("hasAttribute", state);
    }
}

