
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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertTrue;


/**
 * The method createDocumentType should raise a INVALID_CHARACTER_ERR if the qualifiedName
 * contains an illegal characters.
 * <p>
 * Invoke createDocument on this DOMImplementation with qualifiedNames having illegal characters.
 * Check if an INVALID_CHARACTER_ERR is raised in each case.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 */
public class domimplementationcreatedocumenttype04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        DocumentType newDocType;
        String publicId = "http://www.w3.org/DOM/Test/dom2.dtd";
        String systemId = "dom2.dtd";
        String qualifiedName;
        java.util.List qualifiedNames = new java.util.ArrayList();
        qualifiedNames.add("{");
        qualifiedNames.add("}");
        qualifiedNames.add("'");
        qualifiedNames.add("~");
        qualifiedNames.add("`");
        qualifiedNames.add("@");
        qualifiedNames.add("#");
        qualifiedNames.add("$");
        qualifiedNames.add("%");
        qualifiedNames.add("^");
        qualifiedNames.add("&");
        qualifiedNames.add("*");
        qualifiedNames.add("(");
        qualifiedNames.add(")");

        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        for (int indexN10073 = 0; indexN10073 < qualifiedNames.size(); indexN10073++) {
            qualifiedName = (String) qualifiedNames.get(indexN10073);

            {
                boolean success = false;
                try {
                    newDocType = domImpl.createDocumentType(qualifiedName, publicId, systemId);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("domimplementationcreatedocumenttype04", success);
            }
        }
    }
}

