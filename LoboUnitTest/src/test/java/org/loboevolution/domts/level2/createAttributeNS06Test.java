
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

import static org.junit.Assert.assertTrue;


/**
 * Document.createAttributeNS with an empty qualified name should cause an INVALID_CHARACTER_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrAttrNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=525">http://www.w3.org/Bugs/Public/show_bug.cgi?id=525</a>
 */
public class createAttributeNS06Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.example.com/";
        String qualifiedName;
        Document doc;
        Attr newAttr;
        doc = sampleXmlFile("hc_staff.xml");

        {
            boolean success = false;
            try {
                newAttr = doc.createAttributeNS(namespaceURI, "");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue("throw_INVALID_CHARACTER_ERR", success);
        }
    }
}

