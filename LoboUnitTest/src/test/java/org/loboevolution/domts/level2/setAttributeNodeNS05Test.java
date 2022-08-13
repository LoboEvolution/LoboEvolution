
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

import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNodeNS(newAttr)" method raises an
 * "WRONG_DOCUMENT_ERR DOMException if the "newAttr"
 * was created from a different document than the one that
 * created this document.
 * <p>
 * Retrieve the first emp:address and attempt to set a new
 * attribute node.  The new
 * attribute was created from a document other than the
 * one that created this element, therefore a
 * WRONG_DOCUMENT_ERR DOMException should be raised.
 * This test uses the "createAttributeNS(newAttr)" method
 * from the Document interface.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='WRONG_DOCUMENT_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='WRONG_DOCUMENT_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAtNodeNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='WRONG_DOCUMENT_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAtNodeNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='WRONG_DOCUMENT_ERR'])</a>
 */
public class setAttributeNodeNS05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.newattr.com";
        String qualifiedName = "emp:newAttr";
        Document doc1;
        Document doc2;
        Attr newAttr;
        HTMLCollection elementList;
        Node testAddr;
        Attr setAttr1;
        doc1 = sampleXmlFile("staffNS.xml");
        doc2 = sampleXmlFile("staffNS.xml");
        newAttr = doc2.createAttributeNS(namespaceURI, qualifiedName);
        elementList = doc1.getElementsByTagName("emp:address");
        testAddr = elementList.item(0);

        {
            boolean success = false;
            try {
                setAttr1 = ((Element) /*Node */testAddr).setAttributeNodeNS(newAttr);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
            }
            assertTrue("throw_WRONG_DOCUMENT_ERR", success);
        }
    }
}

