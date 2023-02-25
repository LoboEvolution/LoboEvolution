
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

import static org.junit.Assert.assertFalse;


/**
 * The "hasAttributeNS()" method for an Element should
 * return false if the element does not have an attribute with the given local name
 * and/or a namespace URI specified on this element or does not have a default value.
 * Retrieve the first "address" element and the "hasAttributeNS()" method
 * should return false since the element has "nomatch" as the local name
 * and "http://www.usa.com" as the namespace URI.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS</a>
 */
public class hasAttributeNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String localName = "nomatch";
        String namespaceURI = "http://www.usa.com";
        Document doc;
        HTMLCollection elementList;
        Element testNode;
        boolean state;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testNode = (Element) elementList.item(0);
        state = testNode.hasAttributeNS(namespaceURI, localName);
        assertFalse("throw_False", state);
    }
}

