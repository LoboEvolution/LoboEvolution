
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * The method getAttributeNodeNS retrieves an Attr node by local name and namespace URI.
 * Create a new element node and add 2 new attribute nodes to it that have the same
 * local name but different namespaceURIs and prefixes.
 * Retrieve an attribute using namespace and localname and check its value, name and
 * namespaceURI.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS</a>
 */
public class elementgetattributenodens01Test extends LoboUnitTest {

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
        Attr attribute;
        String attrValue;
        String attrName;
        String attNodeName;
        String attrLocalName;
        String attrNS;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("namespaceURI", "root");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/Level2", "l2:att");
         element.setAttributeNodeNS(attribute1);
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/Level1", "att");
        element.setAttributeNodeNS(attribute2);
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Level2", "att");
        attrValue = attribute.getNodeValue();
        attrName = attribute.getName();
        attNodeName = attribute.getNodeName();
        attrLocalName = attribute.getLocalName();
        attrNS = attribute.getNamespaceURI();
        assertEquals("elementgetattributenodens01_attrValue", "", attrValue);
        assertEquals("elementgetattributenodens01_attrName", "l2:att", attrName);
        assertEquals("elementgetattributenodens01_attrNodeName", "l2:att", attNodeName);
        assertEquals("elementgetattributenodens01_attrLocalName", "att", attrLocalName);
        assertEquals("elementgetattributenodens01_attrNs", "http://www.w3.org/DOM/Level2", attrNS);
    }
}

