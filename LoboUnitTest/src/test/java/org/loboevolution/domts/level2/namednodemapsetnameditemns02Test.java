
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName. If a node with
 * that namespace URI and that local name is already present in this map, it is replaced
 * by the new one.
 * <p>
 * Create a new element and attribute Node and add the newly created attribute node to the elements
 * NamedNodeMap.  Verify if the new attr node has been successfully added to the map by checking
 * the nodeName of the retreived atttribute from the list of attribute nodes in this map.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 */
public class namednodemapsetnameditemns02Test extends LoboUnitTest {

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
        Attr attribute1;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "root");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "L1:att");
        attributes = element.getAttributes();
        attributes.setNamedItemNS(attribute1);
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/DOM/L1", "att");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapsetnameditemns02", "L1:att", attrName);
    }
}

