
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should raise NO_MODIFICATION_ALLOWED_ERR DOMException if
 * this map is readonly.
 * <p>
 * Retrieve a list of "gender" elements. Get access to the THIRD element
 * which contains an ENTITY_REFERENCE child node.  Get access to the node's
 * map. Try to add an attribute node specified by arg with
 * method setNamedItemNS((Attr)arg).  This should result in NO_MODIFICATION_ALLOWED_ERR
 * DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])</a>
 */
public class setNamedItemNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.w3.org/2000/xmlns/";
        String localName = "local1";
        Document doc;
        HTMLCollection elementList;
        Element testAddress;
        NodeList nList;
        Node child;
        NodeList n2List;
        Element child2;
        NamedNodeMap attributes;
        Node arg;
        Node setNode;
        int nodeType;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("gender");
        testAddress = (Element)elementList.item(2);
        nList = testAddress.getChildNodes();
        child = nList.item(0);
        nodeType = child.getNodeType();

        if (nodeType == 1) {
            child = doc.createEntityReference("ent4");
            assertNotNull("createdEntRefNotNull", child);
        }
        n2List = child.getChildNodes();
        child2 = (Element) n2List.item(0);
        assertNotNull("notnull", child2);
        attributes = child2.getAttributes();
        arg = attributes.getNamedItemNS(namespaceURI, localName);

        {
            boolean success = false;
            try {
                setNode = attributes.setNamedItemNS((Attr) arg);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

