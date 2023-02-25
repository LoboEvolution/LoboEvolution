
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNodeNS Adds a new attribute and raises
 * an WRONG_DOCUMENT_ERR if newAttr was created from a different document
 * than the one that created the element.
 * Create new element and attribute nodes in different documents.
 * Attempt to add the attribute node to the element node.
 * Check if an WRONG_DOCUMENT_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        Document docAlt;
        Element element;
        Attr attribute;
        doc = sampleXmlFile("staffNS.xml");
        docAlt = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "elem1");
        attribute = docAlt.createAttributeNS("http://www.w3.org/DOM/Test", "attr");
        boolean success = false;
        try {
            element.setAttributeNodeNS(attribute);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
        }
        assertTrue("throw_WRONG_DOCUMENT_ERR", success);
    }
}

