
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertTrue;


/**
 * The "setNamedItemNS((Attr)arg)" method for a
 * NamedNodeMap should raise WRONG_DOCUMENT_ERR DOMException if arg was
 * created from a different document than the one that created this map.
 * <p>
 * Create an attr node in a different document with qualifiedName equals
 * "dmstc:domestic" and namespaceURI is "http://www.usa.com".
 * Access the namednodemap of the first "address" element in this document.
 * Invoke method setNamedItemNS((Attr)arg) with arg being the attr node from above.
 * Method should raise WRONG_DOCUMENT_ERR DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='WRONG_DOCUMENT_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='WRONG_DOCUMENT_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='WRONG_DOCUMENT_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-setNamedItemNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='WRONG_DOCUMENT_ERR'])</a>
 */
public class setNamedItemNS02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.usa.com";
        String qualifiedName = "dmstc:domestic";
        Document doc;
        Document anotherDoc;
        Attr arg;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        doc = sampleXmlFile("staffNS.xml");
        anotherDoc = sampleXmlFile("staffNS.xml");
        arg = anotherDoc.createAttributeNS(namespaceURI, qualifiedName);
        arg.setNodeValue("Maybe");
        elementList = doc.getElementsByTagName("address");
        testAddress = (Element) elementList.item(0);
        attributes = testAddress.getAttributes();
        boolean success = false;
        try {
            attributes.setNamedItemNS(arg);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
        }
        assertTrue("throw_WRONG_DOCUMENT_ERR", success);

    }
}

