
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNodeNS Adds a new attribute and raises an WRONG_DOCUMENT_ERR if this node
 * is readonly.
 * Attempt to add an attribute node to an element node which is part of the replacement text of
 * a read-only EntityReference node.
 * Check if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens06Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        Attr attribute2;
        EntityReference entRef;
        NodeList elementList;
        Node newAttribute;
        Node newChild;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "elem1");
        attribute = doc.createAttributeNS("http://www.w3.org/DOM/Test", "attr");
        entRef = doc.createEntityReference("ent4");
        newChild = attribute.appendChild(entRef);
        newAttribute = element.setAttributeNodeNS(attribute);
        elementList = entRef.getChildNodes();
        element = (Element) elementList.item(0);
        attribute2 = doc.createAttributeNS("http://www.w3.org/DOM/Test", "attr2");

        {
            boolean success = false;
            try {
                newAttribute = element.setAttributeNodeNS(attribute2);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("elementsetattributenodens06", success);
        }
    }
}

