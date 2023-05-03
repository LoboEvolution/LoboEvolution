
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

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should raise INUSE_ATTRIBUTE_ERR DOMException if
 * arg is an Attr that is already an attribute of another Element object.
 * <p>
 * Retrieve an attr node from the third "address" element whose local name
 * is "domestic" and namespaceURI is "http://www.netzero.com".
 * Invoke method setNamedItemNS((Attr)arg) on the map of the first "address" element with
 * arg being the attr node from above.  Method should raise
 * INUSE_ATTRIBUTE_ERR DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])</a>
 */
public class setNamedItemNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element anotherElement;
        NamedNodeMap anotherMap;
        Node arg;
        Element testAddress;
        NamedNodeMap map;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        anotherElement = (Element) elementList.item(2);
        anotherMap = anotherElement.getAttributes();
        arg = anotherMap.getNamedItemNS("", "domestic");
        testAddress = (Element) elementList.item(0);
        map = testAddress.getAttributes();
        boolean success = false;
        try {
            map.setNamedItemNS(arg);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
        }
        assertTrue("throw_INUSE_ATTRIBUTE_ERR", success);
    }
}