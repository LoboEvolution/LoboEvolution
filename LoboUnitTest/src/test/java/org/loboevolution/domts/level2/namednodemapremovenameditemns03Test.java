
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
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;


/**
 * The method removeNamedItemNS removes a node specified by local name and namespace
 * <p>
 * Create a new element node and add 2 new attribute nodes to it that have the same localName
 * but different namespaceURI's.  Remove the first attribute node from the namedNodeMap of the
 * new element node and check to see that the second attribute still exists.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193</a>
 */
public class namednodemapremovenameditemns03Test extends LoboUnitTest {

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
        Attr attribute1;
        Attr attribute2;
        String nodeName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "root");
        attribute1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "L1:att");
        
        newAttribute = element.setAttributeNodeNS(attribute1);
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/L2", "L2:att");
        
        newAttribute = element.setAttributeNodeNS(attribute2);
        attributes = element.getAttributes();
        attribute = (Attr)attributes.removeNamedItemNS("http://www.w3.org/DOM/L1", "att");
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/DOM/L2", "att");
        nodeName = attribute.getNodeName();
        assertEquals("namednodemapremovenameditemns02", "L2:att", nodeName);
    }
}

