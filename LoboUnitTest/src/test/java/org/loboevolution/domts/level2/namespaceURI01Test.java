
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * The "getNamespaceURI()" method for an Attribute
 * returns the namespace URI of this node, or null if unspecified.
 * <p>
 * Retrieve the first "address" node which has an attribute of "district"
 * that is specified in the DTD.
 * Invoke the "getNamespaceURI()" method on the attribute.
 * The method should return "http://www.nist.gov".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=238">http://www.w3.org/Bugs/Public/show_bug.cgi?id=238</a>
 */
public class namespaceURI01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        Attr addrAttr;
        String attrNamespaceURI;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = (Element) elementList.item(0);
        addrAttr = testAddr.getAttributeNode( "district");
        attrNamespaceURI = addrAttr.getNamespaceURI();
        assertEquals("namespaceURI", "http://www.nist.gov", attrNamespaceURI);
    }
}

