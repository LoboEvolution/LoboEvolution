
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The method createAttributeNS raises an INVALID_CHARACTER_ERR if the specified
 * qualified name contains an illegal character
 * <p>
 * Invoke the createAttributeNS method on this Document object with a valid value for
 * namespaceURI, and qualifiedNames that contain illegal characters.  Check if the an
 * INVALID_CHARACTER_ERR was thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class documentcreateattributeNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr attribute;
        String namespaceURI = "http://www.w3.org/DOM/Test/Level2";
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
        for (int indexN1005A = 0; indexN1005A < qualifiedNames.size(); indexN1005A++) {
            qualifiedName = qualifiedNames.get(indexN1005A);

            {
                boolean success = false;
                try {
                    attribute = doc.createAttributeNS(namespaceURI, qualifiedName);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("documentcreateattributeNS03", success);
            }
        }
    }
}

