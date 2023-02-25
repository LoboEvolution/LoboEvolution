
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNS(namespaceURI,qualifiedName,Value)" method raises a
 * INVALID_CHARACTER_ERR DOMException if the specified
 * prefix contains an illegal character.
 * <p>
 * Attempt to add a new attribute on the first employee node.
 * An exception should be raised since the "qualifiedName" has an invalid
 * character.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INVALID_CHARACTER_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='INVALID_CHARACTER_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])</a>
 */
public class setAttributeNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.nist.gov";
        String qualifiedName = "qual?name";
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("employee");
        testAddr = (Element) elementList.item(0);
        boolean success = false;
        try {
            testAddr.setAttributeNS(namespaceURI, qualifiedName, "newValue");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
        }
        assertTrue("throw_INVALID_CHARACTER_ERR", success);
    }
}

