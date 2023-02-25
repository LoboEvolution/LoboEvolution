
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName. If a node with
 * that namespace URI and that local name is already present in this map, it is replaced
 * by the new one.
 * <p>
 * Retreive the first element whose localName is address and namespaceURI http://www.nist.gov",
 * and put its attributes into a named node map.  Create a new attribute node and add it to this map.
 * Verify if the attr node was successfully added by checking the nodeName of the retreived atttribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS</a>
 */
public class namednodemapsetnameditemns01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributes;
        Element element;
        Attr attribute;
        Attr newAttribute;
        Attr newAttr1;
        HTMLCollection elementList;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "address");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();
        newAttr1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "streets");
        
        newAttribute = element.setAttributeNodeNS(newAttr1);
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/DOM/L1", "streets");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapsetnameditemns01", "streets", attrName);
    }
}

