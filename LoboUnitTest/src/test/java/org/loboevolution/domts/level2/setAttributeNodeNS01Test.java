
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNode(newAttr)" method raises an
 * "INUSE_ATTRIBUTE_ERR DOMException if the "newAttr"
 * is already an attribute of another element.
 * <p>
 * Retrieve the first address and append
 * a newly created element.  The "createAttributeNS(namespaceURI,qualifiedName)"
 * and "setAttributeNodeNS(newAttr)" methods are invoked
 * to create and add a new attribute to the newly created
 * Element.  The "setAttributeNodeNS(newAttr)" method is
 * once again called to add the new attribute causing an
 * exception to be raised since the attribute is already
 * an attribute of another element.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAtNodeNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAtNodeNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])</a>
 */
public class setAttributeNodeNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.newattr.com";
        String qualifiedName = "newAttr";
        Document doc;
        Element newElement;
        Attr newAttr;
        HTMLCollection elementList;
        Node testAddr;
        Node appendedChild;
        Attr setAttr1;
        Attr setAttr2;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        testAddr = elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        newElement = doc.createElement("newElement");
        appendedChild = testAddr.appendChild(newElement);
        newAttr = doc.createAttributeNS(namespaceURI, qualifiedName);
        setAttr1 = newElement.setAttributeNodeNS(newAttr);

        {
            boolean success = false;
            try {
                setAttr2 = ((Element) /*Node */testAddr).setAttributeNodeNS(newAttr);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
            }
            assertTrue("throw_INUSE_ATTRIBUTE_ERR", success);
        }
    }
}

