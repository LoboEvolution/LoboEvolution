
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
 * The method getNamedItemNS retrieves a node specified by local name and namespace URI.
 * <p>
 * Create a new Element node and add 2 new attribute nodes having the same local name but different
 * namespace names and namespace prefixes to it.  Using the getNamedItemNS retreive the second attribute node.
 * Verify if the attr node has been retreived successfully by checking its nodeName atttribute.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getNamedItemNS</a>
 */
public class namednodemapgetnameditemns03Test extends LoboUnitTest {

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
        Attr newAttr1;
        Attr newAttr2;
        Attr newAttribute;
        String attrName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "root");
        newAttr1 = doc.createAttributeNS("http://www.w3.org/DOM/L1", "L1:att");
        
        newAttribute = element.setAttributeNodeNS(newAttr1);
        newAttr2 = doc.createAttributeNS("http://www.w3.org/DOM/L2", "L2:att");
        
        newAttribute = element.setAttributeNodeNS(newAttr2);
        attributes = element.getAttributes();
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/DOM/L2", "att");
        attrName = attribute.getNodeName();
        assertEquals("namednodemapgetnameditemns03", "L2:att", attrName);
    }
}

