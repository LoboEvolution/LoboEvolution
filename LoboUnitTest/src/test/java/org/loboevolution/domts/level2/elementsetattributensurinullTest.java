
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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNS(namespaceURI,qualifiedName,value)" method raises a
 * NAMESPACE_ERR DOMException if the specified
 * qualifiedName has a prefix and the namespaceURI is null.
 * <p>
 * Attempt to add a new attribute on the first employee node.
 * An exception should be raised since the "qualifiedName" has a
 * prefix and the namespaceURI is null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-ElSetAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NAMESPACE_ERR'])</a>
 */
public class elementsetattributensurinullTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        String qualifiedName = "qualifiedName";
        Document doc;
        HTMLCollection elementList;
        Node testAddr;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        testAddr = elementList.item(0);
        boolean success = false;
        try {
            ((Element) testAddr).setAttributeNS(null, qualifiedName, "newValue");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue("throw_NAMESPACE_ERR", success);
    }
}

