
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


/**
 * To respecify the attribute to its default value from
 * the DTD, the attribute must be deleted.  This will then
 * make a new attribute available with the "isSpecified()"
 * method value set to false.
 * Retrieve the attribute named "street" from the last
 * child of the THIRD employee and delete it.  This
 * should then create a new attribute with its default
 * value and also cause the "isSpecified()" method to
 * return false.
 * This test uses the "removeAttribute(name)" method
 * from the Element interface and the "getNamedItem(name)"
 * method from the NamedNodeMap interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6D6AC0F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6D6AC0F9</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html">http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html</a>
 */
public class AttrSpecifiedValueRemoveTest extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection addressList;
        Element testNode;
        NamedNodeMap attributes;
        Attr streetAttr;
        boolean state;
        doc = sampleXmlFile("staff.xml");
        addressList = doc.getElementsByTagName("address");
        testNode = (Element) addressList.item(2);
        testNode.removeAttribute("street");
        attributes = testNode.getAttributes();
        streetAttr = attributes.getNamedItem("street");
        assertNotNull("streetAttrNotNull", streetAttr);
        state = streetAttr.isSpecified();
        assertFalse("attrSpecifiedValueRemoveAssert", state);
    }
}

