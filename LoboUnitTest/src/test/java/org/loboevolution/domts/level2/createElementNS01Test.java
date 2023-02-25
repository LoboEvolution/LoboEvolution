
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
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertTrue;


/**
 * The "createElementNS(namespaceURI,qualifiedName)" method for a
 * Document should raise NAMESPACE_ERR DOMException if
 * qualifiedName is malformed.
 * <p>
 * Invoke method createElementNS(namespaceURI,qualifiedName) on
 * the XMLNS Document with namespaceURI being the literal string
 * "http://www.ecommerce.org/", and qualifiedName as "prefix::local".
 * Method should raise NAMESPACE_ERR DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrElNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrElNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])</a>
 */
public class createElementNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.ecommerce.org/";
        String malformedName = "prefix::local";
        Document doc;
        doc = sampleXmlFile("staffNS.xml");
        boolean success = false;
        try {
             doc.createElementNS(namespaceURI, malformedName);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue("throw_NAMESPACE_ERR", success);

    }
}

