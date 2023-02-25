
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNode(newAttr)" method raises an
 * "INUSE_ATTRIBUTE_ERR DOMException if the "newAttr"
 * is already an attribute of another element.
 * <p>
 * Retrieve the last child of the second employee and append
 * a newly created element.  The "createAttribute(name)"
 * and "setAttributeNode(newAttr)" methods are invoked
 * to create and add a new attribute to the newly created
 * Element.  The "setAttributeNode(newAttr)" method is
 * once again called to add the new attribute causing an
 * exception to be raised since the attribute is already
 * an attribute of another element.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-887236154')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-887236154')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])</a>
 */
public class elementinuseattributeerrTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        Attr newAttribute;
        HTMLCollection addressElementList;
        Element testAddress;
        Element newElement;
        doc = sampleXmlFile("staff.xml");
        addressElementList = doc.getElementsByTagName("address");
        testAddress = (Element) addressElementList.item(1);
        newElement = doc.createElement("newElement");
        testAddress.appendChild(newElement);
        newAttribute = doc.createAttribute("newAttribute");
        newElement.setAttributeNode(newAttribute);

        {
            boolean success = false;
            try {
                testAddress.setAttributeNode(newAttribute);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
            }
            assertTrue("throw_INUSE_ATTRIBUTE_ERR", success);
        }
    }
}

