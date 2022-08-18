
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "getOwnerElement()" will return the Element node this attribute is attached to or
 * null if this attribute is not in use.
 * Import an attribute node to another document.  If an Attr node is imported, its
 * ownerElement attribute should be set to null.  Verify if the ownerElement has been set
 * to null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement">http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement</a>
 */
public class attrgetownerelement04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document docImp;
        Node ownerElement;
        Element element;
        Attr attr;
        Attr attrImp;
        HTMLCollection addresses;
        doc = sampleXmlFile("staffNS.xml");
        docImp = sampleXmlFile("staff.xml");
        addresses = doc.getElementsByTagName( "address");
        element = (Element) addresses.item(1);
        assertNotNull("empAddressNotNull", element);
        attr = element.getAttributeNode( "zone");
        attrImp = (Attr) docImp.importNode(attr, true);
        ownerElement = attrImp.getOwnerElement();
        assertNull("attrgetownerelement04", ownerElement);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/attrgetownerelement04";
    }
}

