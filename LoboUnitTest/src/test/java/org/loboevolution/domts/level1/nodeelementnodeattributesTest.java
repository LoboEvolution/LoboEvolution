
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * The "getAttributes()" method invoked on an Element
 * Node returns a NamedNodeMap containing the attributes
 * of this node.
 * <p>
 * Retrieve the last child of the third employee and
 * invoke the "getAttributes()" method.   It should return
 * a NamedNodeMap containing the attributes of the Element
 * node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 */
public class nodeelementnodeattributesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        NamedNodeMap addrAttr;
        Node attrNode;
        String attrName;
        java.util.Collection attrList = new java.util.ArrayList();

        java.util.Collection expected = new java.util.ArrayList();
        expected.add("domestic");
        expected.add("street");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = (Element) elementList.item(2);
        addrAttr = testAddr.getAttributes();
        for (int indexN1005C = 0; indexN1005C < addrAttr.getLength(); indexN1005C++) {
            attrNode = addrAttr.item(indexN1005C);
            attrName = attrNode.getNodeName();
            attrList.add(attrName);
        }
        assertEquals("nodeElementNodeValueAssert1", expected, attrList);
    }
}

