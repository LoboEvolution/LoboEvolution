
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * If an Attr is explicitly assigned any value, then that value is the attributes effective value.
 * Retrieve the attribute named "domestic" from the last child of the first employee
 * and examine its nodeValue attribute.  This test uses the "getNamedItem(name)" method
 * from the NamedNodeMap interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1074577549">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1074577549</a>
 */
public class AttrEffectiveValueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection addressList;
        Element testNode;
        NamedNodeMap attributes;
        Attr domesticAttr;
        String value;
        doc = sampleXmlFile("staff.xml");
        addressList = doc.getElementsByTagName("address");
        testNode = (Element) addressList.item(0);
        attributes = testNode.getAttributes();
        domesticAttr = (Attr) attributes.getNamedItem("domestic");
        value = domesticAttr.getNodeValue();
        assertEquals("attrEffectiveValueAssert", "Yes", value);
    }

}

