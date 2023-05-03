
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;

import static org.junit.Assert.assertTrue;


/**
 * The "createDocumentType(qualifiedName,publicId,systemId)" method for a
 * DOMImplementation should raise NAMESPACE_ERR DOMException if
 * qualifiedName is malformed.
 * <p>
 * Retrieve the DOMImplementation on the XMLNS Document.
 * Invoke method createDocumentType(qualifiedName,publicId,systemId)
 * on the retrieved DOMImplementation with qualifiedName being the literal
 * string "prefix::local", publicId as "STAFF", and systemId as "staff".
 * Method should raise NAMESPACE_ERR DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NAMESPACE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Level-2-Core-DOM-createDocType')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Level-2-Core-DOM-createDocType')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])</a>
 */
public class createDocumentType01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String publicId = "STAFF";
        String systemId = "staff.xml";
        String malformedName = "prefix::local";
        Document doc;
        DOMImplementation domImpl;
        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();

        boolean success = false;
        try {
            domImpl.createDocumentType(malformedName, publicId, systemId);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue("throw_NAMESPACE_ERR", success);

    }
}

