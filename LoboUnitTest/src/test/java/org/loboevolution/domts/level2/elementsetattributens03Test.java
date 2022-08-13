
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
 * The method setAttributeNS adds a new attribute.
 * Retreive an existing element node with a default attribute node and
 * add two new attribute nodes that have the same local name as the
 * default attribute but different namespaceURI to it using the setAttributeNS method.
 * Check if the attribute was correctly set by invoking the getAttributeNodeNS method
 * and checking the nodeName and nodeValue of the returned nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens03Test extends LoboUnitTest {

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
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("emp:employee");
        element = (Element) elementList.item(0);
        assertNotNull("empEmployeeNotNull", element);
        element.setAttributeNS("http://www.w3.org/DOM/Test/1", "defaultAttr", "default1");
        element.setAttributeNS("http://www.w3.org/DOM/Test/2", "defaultAttr", "default2");
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/1", "defaultAttr");
        attrName = attribute.getNodeName();
        attrValue = attribute.getNodeValue();
        assertEquals("elementsetattributens03_attrName", "defaultAttr", attrName);
        assertEquals("elementsetattributens03_attrValue", "default1", attrValue);
    }
}

