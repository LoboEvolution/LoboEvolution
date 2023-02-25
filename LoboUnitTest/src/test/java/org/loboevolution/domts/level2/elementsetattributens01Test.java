
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

import static org.junit.Assert.*;


/**
 * The method setAttributeNS adds a new attribute.
 * Create a new element and add a new attribute node to it using the setAttributeNS method.
 * Check if the attribute was correctly set by invoking the getAttributeNodeNS method
 * and checking the nodeName and nodeValue of the returned nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        String attrName;
        String attrValue;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM", "dom:elem");
        element.setAttributeNS("http://www.w3.org/DOM/Test/setAttributeNS", "attr", "value");
        attribute = element.getAttributeNodeNS("http://www.w3.org/DOM/Test/setAttributeNS", "attr");
        attrName = attribute.getNodeName();
        attrValue = attribute.getNodeValue();
        assertEquals("elementsetattributens01_attrName", "attr", attrName);
        assertEquals("elementsetattributens01_attrValue", "value", attrValue);
    }
}

