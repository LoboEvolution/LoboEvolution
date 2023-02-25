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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * The method isId returns whether this attribute is known to be of type ID or not.
 * <p>
 * Add a new attribute of type ID to the third acronym element node of this document. Verify that the method
 * isId returns true. The use of Element.setIdAttributeNS() makes 'isId' a user-determined ID attribute.
 * Import the newly created attribute node into this document.
 * Since user data assocated to the imported node is not carried over, verify that the method isId
 * returns false on the imported attribute node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-isId">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-isId</a>
 */
public class attrisid07Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem;
        Attr attr;
        Attr attrImported;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        acronymElem = (Element) elemList.item(2);
        acronymElem.setAttributeNS("http://www.w3.org/DOM", "dom3:newAttr", "null");
        acronymElem.setIdAttributeNS("http://www.w3.org/DOM", "newAttr", true);
        attr = acronymElem.getAttributeNodeNS("http://www.w3.org/DOM", "newAttr");
        assertTrue("AttrIsIDTrue07_1", attr.isId());
        attrImported = (Attr) doc.importNode(attr, false);
        assertFalse("AttrIsID07_isFalseforImportedNode", attrImported.isId());
    }
}