
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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The "removeAttributeNS(namespaceURI,localName)" removes an attribute by
 * local name and namespace URI.  If the removed attribute has a
 * default value it is immediately replaced.  The replacing attribute has the same
 * namespace URI and local name, as well as the original prefix.
 * <p>
 * Retrieve the attribute named "local" from address
 * node, then remove the "local"
 * attribute by invoking the "removeAttributeNS(namespaceURI,localName)" method.
 * The "local" attribute has a default value defined in the
 * DTD file, that value should immediately replace the old
 * value.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElRemAtNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElRemAtNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=238">http://www.w3.org/Bugs/Public/show_bug.cgi?id=238</a>
 */
public class removeAttributeNS02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node testAddr;
        Attr addrAttr;
        String attr;
        String namespaceURI;
        String localName;
        String prefix;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        ((Element) testAddr).removeAttribute( "local1");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        addrAttr = ((Element) testAddr).getAttributeNode( "local1");
        attr = ((Element) testAddr).getAttribute( "local1");
        namespaceURI = addrAttr.getNamespaceURI();
        localName = addrAttr.getLocalName();
        assertEquals("attr", "FALSE", attr);
        assertEquals("uri", "http://www.nist.gov", namespaceURI);
        assertEquals("lname", "local1", localName);
    }
}