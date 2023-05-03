
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNS adds a new attribute and raises a INVALID_CHARACTER_ERR if
 * the specified qualified name contains an illegal character.
 * Invoke the setAttributeNS method on this Element object with a valid value for
 * namespaceURI, and qualifiedNames that contain illegal characters.  Check if the an
 * INVALID_CHARACTER_ERR was thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 */
public class elementsetattributens04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        String qualifiedName;
        List<String> qualifiedNames = new ArrayList<>();
        qualifiedNames.add("/");
        qualifiedNames.add("//");
        qualifiedNames.add("\\");
        qualifiedNames.add(";");
        qualifiedNames.add("&");
        qualifiedNames.add("*");
        qualifiedNames.add("]]");
        qualifiedNames.add(">");
        qualifiedNames.add("<");

        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test/L2", "dom:elem");
        for (int indexN10058 = 0; indexN10058 < qualifiedNames.size(); indexN10058++) {
            qualifiedName = qualifiedNames.get(indexN10058);

            {
                boolean success = false;
                try {
                    element.setAttributeNS("http://www.w3.org/DOM/Test/L2", qualifiedName, "test");
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("elementsetattributens04", success);
            }
        }
    }
}

