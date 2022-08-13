
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

import static org.junit.Assert.assertTrue;


/**
 * The "getSpecified()" method for an Attr node should return true if the
 * value of the attribute is changed.
 * Retrieve the attribute named "street" from the last
 * child of the THIRD employee and change its
 * value to "Yes"(which is the default DTD value).  This
 * should cause the "getSpecified()" method to be true.
 * This test uses the "setAttribute(name,value)" method
 * from the Element interface and the "getNamedItem(name)"
 * method from the NamedNodeMap interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273</a>
 */
public class AttrSpecifiedValueChangedTest extends LoboUnitTest {
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
        testNode.setAttribute("street", "Yes");
        attributes = testNode.getAttributes();
        streetAttr = attributes.getNamedItem("street");
        state = streetAttr.getSpecified();
        assertTrue("streetSpecified", state);
    }
}

