
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * Testing Element.setAttributeNodeNS: If an attribute with that local name
 * and that namespace URI is already present in the element, it is replaced
 * by the new one.
 * Create a new element and two new attribute nodes (in the same namespace
 * and same localNames).
 * Add the two new attribute nodes to the element node using the
 * setAttributeNodeNS method.  Check that only one attribute is added, check
 * the value of this attribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute1;
        Attr attribute2;
        Attr attrNode;
        String attrName;
        String attrNS;
        NamedNodeMap attributes;
        int length;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test/Level2", "new:element");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/Test/att1", "p1:att");
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/Test/att1", "p2:att");
        attribute2.setValue("value2");
        element.setAttributeNodeNS(attribute1);
        element.setAttributeNodeNS(attribute2);
        attrNode = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/att1", "att");
        attrName = attrNode.getNodeName();
        attrNS = attrNode.getNamespaceURI();
        assertEquals("elementsetattributenodens01_attrName", "p2:att", attrName);
        assertEquals("elementsetattributenodens01_attrNS", "http://www.w3.org/DOM/Test/att1", attrNS);
        attributes = element.getAttributes();
        length = attributes.getLength();
        assertEquals("length", 1, length);
    }
}

