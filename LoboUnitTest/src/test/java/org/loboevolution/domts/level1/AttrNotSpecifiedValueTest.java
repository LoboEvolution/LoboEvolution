
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


/**
 * The "getSpecified()" method for an Attr node should
 * be set to false if the attribute was not explicitly given
 * a value.
 * Retrieve the attribute named "street" from the last
 * child of the first employee and examine the value
 * returned by the "getSpecified()" method.  This test uses
 * the "getNamedItem(name)" method from the NamedNodeMap
 * interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-862529273</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html">http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html</a>
 */
public class AttrNotSpecifiedValueTest extends LoboUnitTest {


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
        testNode = (Element) addressList.item(0);
        attributes = testNode.getAttributes();
        streetAttr = attributes.getNamedItem("street");
        state = streetAttr.getSpecified();
        assertFalse("streetNotSpecified", state);
    }
}

