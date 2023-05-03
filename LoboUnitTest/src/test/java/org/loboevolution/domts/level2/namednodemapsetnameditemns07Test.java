
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a INUSE_ATTRIBUTE_ERR Raised if arg is an Attr that is already an
 * attribute of another Element object.
 * <p>
 * Retreieve the attributes of first element whose localName is address into a named node map.
 * Retreive the attribute whose namespaceURI=http://www.usa.com and localName=domestic
 * from the NamedNodeMap.  Retreieve the attributes of second element whose localName is address
 * into a named node map.  Call the setNamedItemNS method on the second nodemap with the domestic
 * attribute that was retreived and removed from the first nodeMap as an argument.
 * Assuming that when an attribute is removed from a nodemap, it still remains in the domtree
 * his should raise an INUSE_ATTRIBIUTE_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 */
public class namednodemapsetnameditemns07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributes;
        HTMLCollection elementList;
        Element element;
        Attr attr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();
        attr = (Attr) attributes.getNamedItemNS("http://www.usa.com", "domestic");
        element = (Element) elementList.item(1);
        attributes = element.getAttributes();
        boolean success = false;
        try {
            attributes.setNamedItemNS(attr);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
        }
        assertTrue("namednodemapsetnameditemns07", success);
    }
}

