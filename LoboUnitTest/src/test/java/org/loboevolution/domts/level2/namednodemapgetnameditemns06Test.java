
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
 * Retreive the second address element node having localName=adrress.  Retreive the attributes
 * of this element into 2 nodemaps.  Create a new attribute node and add it to this element.
 * Since NamedNodeMaps are live each one should get updated, using the getNamedItemNS retreive
 * the newly created attribute from each node map.
 * Verify if the attr node has been retreived successfully by checking its nodeName atttribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS</a>
 */
public class namednodemapgetnameditemns06Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributesMap1;
        NamedNodeMap attributesMap2;
        Element element;
        Node attribute;
        Attr newAttr1;
        HTMLCollection elementList;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(1);
        attributesMap1 = element.getAttributes();
        attributesMap2 = element.getAttributes();
        newAttr1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "street");
        element.setAttributeNodeNS(newAttr1);
        attribute = attributesMap1.getNamedItemNS("http://www.w3.org/DOM/L1", "street");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapgetnameditemnsMap106", "street", attrName);
        attribute = attributesMap2.getNamedItemNS("http://www.w3.org/DOM/L1", "street");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapgetnameditemnsMap206", "street", attrName);
    }
}

