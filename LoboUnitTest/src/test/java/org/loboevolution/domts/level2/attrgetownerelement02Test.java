
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
 * The "getOwnerElement()" will return the Element node this attribute
 * is attached to or null if this attribute is not in use.
 * <p>
 * Create a new element and attribute node, attach the attribute to the element.
 * Check the value of owner element of the new attribute node
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement">http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement</a>
 */
public class attrgetownerelement02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element ownerElement;
        String ownerElementName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElement("root");
        Attr attr = doc.createAttributeNS("http://www.w3.org/DOM/L1", "L1:att");
        element.setAttributeNodeNS(attr);
        ownerElement = attr.getOwnerElement();
        ownerElementName = ownerElement.getNodeName();
        assertEquals("attrgetownerelement02", "root", ownerElementName);
    }
}

