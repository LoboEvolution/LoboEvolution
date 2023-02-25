
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

import static org.junit.Assert.assertTrue;


/**
 * The method hasAttributeNS returns true when an attribute with a given local
 * name and namespace URI is specified on this element or has a default value,
 * false otherwise.
 * Create a new element and attribute node that belong to the same namespace.
 * Add the attribute node to the element node.  Check if the newly created element
 * node has an attribute by invoking the hasAttributeNS method with appropriate
 * values for the namespaceURI and localName parameters.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElHasAttrNS</a>
 */
public class elementhasattributens02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        boolean state;
        Attr attribute;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM", "address");
        attribute = doc.createAttributeNS("http://www.w3.org/DOM", "domestic");
        element.setAttributeNode(attribute);
        state = element.hasAttributeNS("http://www.w3.org/DOM", "domestic");
        assertTrue("hasDomesticAttr", state);
    }
}

