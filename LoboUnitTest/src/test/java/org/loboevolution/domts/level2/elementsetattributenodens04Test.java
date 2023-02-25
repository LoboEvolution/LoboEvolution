
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNodeNS Adds a new attribute and raises an INUSE_ATTRIBUTE_ERR
 * if newAttr is already an attribute of another Element object.
 * <p>
 * Create two new element nodes and a new attribute node.  Attempt to add the same attribute
 * node to the same two element nodes.
 * Check if an INUSE_ATTRIBUTE_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element1;
        Element element2;
        Attr attribute;
        doc = sampleXmlFile("staffNS.xml");
        element1 = doc.createElementNS("http://www.w3.org/DOM/Test", "elem1");
        element2 = doc.createElementNS("http://www.w3.org/DOM/Test", "elem2");
        attribute = doc.createAttributeNS("http://www.w3.org/DOM/Test", "attr");
        element1.setAttributeNodeNS(attribute);

        {
            boolean success = false;
            try {
                element2.setAttributeNodeNS(attribute);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
            }
            assertTrue("elementsetattributenodens04", success);
        }
    }
}

