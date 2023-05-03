
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
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNS adds a new attribute and raises a NAMESPACE_ERR
 * if the qualifiedName, or its prefix, is "xmlns" and the namespaceURI is
 * different from "http://www.w3.org/2000/xmlns/".
 * <p>
 * Invoke the setAttributeNS method on a new Element object with namespaceURI that is
 * http://www.w3.org/DOMTest/level2 and a qualifiedName that has the prefix xmlns and once
 * again with a qualifiedName that is xmlns.
 * Check if the NAMESPACE_ERR was thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens08Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOMTest/level2", "dom:elem");

        {
            boolean success = false;
            try {
                element.setAttributeNS("http://www.w3.org/DOMTest/level2", "xmlns", "test");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue("elementsetattributens08_Err1", success);
        }

        {
            boolean success = false;
            try {
                element.setAttributeNS("http://www.w3.org/DOMTest/level2", "xmlns:root", "test");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue("elementsetattributens08_Err2", success);
        }
    }
}

