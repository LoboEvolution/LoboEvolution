
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
 * Create a new element node and add a new attribute node to it.  Using the getAttributeNodeNS,
 * retrieve the newly added attribute node and check its value.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAtNodeNS</a>
 */
public class elementgetattributenodens02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        String attrValue;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("namespaceURI", "root");
        attribute = doc.createAttributeNS("http://www.w3.org/DOM/Level2", "l2:att");
        element.setAttributeNodeNS(attribute);
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Level2", "att");
        attrValue = attribute.getNodeValue();
        assertEquals("elementgetattributenodens02", "", attrValue);
    }
}

