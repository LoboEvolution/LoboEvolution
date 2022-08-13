
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

package org.loboevolution.domts.level1;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * The "setAttributeNode(newAttr)" method for an attribute causes the
 * DOMException NO_MODIFICATION_ALLOWED_ERR to be raised
 * if the node is readonly.
 * <p>
 * Create an entity reference and add to the THIRD "gender" element.  The elements
 * content is an entity reference.  Try to remove the "domestic" attribute
 * from the entity reference by executing the "setAttributeNode(newAttr)" method.
 * This causes a NO_MODIFICATION_ALLOWED_ERR DOMException to be thrown.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-887236154')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-887236154')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 * @see <a href="http://www.w3.org/2001/DOM-Test-Suite/level1/core/elementsetattributenodenomodificationallowederr.xml">http://www.w3.org/2001/DOM-Test-Suite/level1/core/elementsetattributenodenomodificationallowederr.xml</a>
 */
public class elementsetattributenodenomodificationallowederrEETest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection genderList;
        Node gender;
        EntityReference entRef;
        Element entElement;
        Attr newAttr;
        doc = sampleXmlFile("staff.xml");
        genderList = doc.getElementsByTagName("gender");
        gender = genderList.item(2);
        entRef = doc.createEntityReference("ent4");
        assertNotNull("createdEntRefNotNull", entRef);
        gender.appendChild(entRef);
        entElement = (Element) entRef.getFirstChild();
        assertNotNull("entElementNotNull", entElement);
        newAttr = doc.createAttribute("newAttr");

        {
            boolean success = false;
            try {
                entElement.setAttributeNode(newAttr);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

