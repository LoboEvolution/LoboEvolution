
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

import static org.junit.Assert.assertFalse;


/**
 * The method removeAttributeNS removes an attribute by local name and namespace URI.
 * Create a new element and add a new attribute node to it.
 * Remove the attribute node using the removeAttributeNodeNS method.
 * Check if the attribute was remove by invoking the hasAttributeNS
 * method on the element and check if it returns false.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElRemAtNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElRemAtNS</a>
 */
public class elementremoveattributens01Test extends LoboUnitTest {

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
        element = doc.createElementNS("http://www.w3.org/DOM", "elem");
        attribute = doc.createAttributeNS("http://www.w3.org/DOM/Test/createAttributeNS", "attr");
        element.setAttributeNodeNS(attribute);
        element.removeAttributeNS("http://www.w3.org/DOM/Test/createAttributeNS", "attr");
        state = element.hasAttributeNS("http://www.w3.org/DOM/Test/createAttributeNS", "attr");
        assertFalse("elementremoveattributens01", state);
    }
}

