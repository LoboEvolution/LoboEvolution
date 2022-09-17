
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


/**
 * The method setAttributeNS adds a new attribute.
 * <p>
 * Retrieve an existing element node with attributes and add a new attribute node to it using
 * the setAttributeNS method.   Check if the attribute was correctly set by invoking the
 * getAttributeNodeNS method and checking the nodeName and nodeValue of the returned nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        HTMLCollection elementList;
        String attrName;
        String attrValue;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(0);
        element.setAttributeNS("http://www.w3.org/DOM/Test/setAttributeNS", "this:street", "Silver Street");
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/setAttributeNS", "street");
        attrName = attribute.getNodeName();
        attrValue = attribute.getNodeValue();
        assertEquals("elementsetattributens02_attrName", "this:street", attrName);
        assertEquals("elementsetattributens02_attrValue", "Silver Street", attrValue);
    }
}
