
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
 * The "setValue()" method for an attribute causes the
 * DOMException NO_MODIFICATION_ALLOWED_ERR to be raised
 * if the node is readonly.
 * Create an entity reference using document.createEntityReference()
 * Get the "domestic" attribute from the entity
 * reference and execute the "setValue()" method.
 * This causes a NO_MODIFICATION_ALLOWED_ERR DOMException to be thrown.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#ID-221662474">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#ID-221662474</a>
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-221662474')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-221662474')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/DOM/updates/REC-DOM-Level-1-19981001-errata.html">http://www.w3.org/DOM/updates/REC-DOM-Level-1-19981001-errata.html</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474</a>
 * @see <a href="http://www.w3.org/2001/DOM-Test-Suite/level1/core/attrsetvaluenomodificationallowederr.xml">http://www.w3.org/2001/DOM-Test-Suite/level1/core/attrsetvaluenomodificationallowederr.xml</a>
 */
public class AttrSetValueNoModificationalLowederrEETest extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRef;
        Element entElement;
        NamedNodeMap attrList;
        Attr attrNode;
        Node gender;
        HTMLCollection genderList;
        Node appendedChild;
        doc = sampleXmlFile("staff.xml");
        genderList = doc.getElementsByTagName("gender");
        gender = genderList.item(2);
        assertNotNull("genderNotNull", gender);
        entRef = doc.createEntityReference("ent4");
        assertNotNull("entRefNotNull", entRef);
        appendedChild = gender.appendChild(entRef);
        entElement = (Element) entRef.getFirstChild();
        assertNotNull("entElementNotNull", entElement);
        attrList = entElement.getAttributes();
        attrNode = attrList.getNamedItem("domestic");

        {
            boolean success = false;
            try {
                /*Node */
                attrNode.setValue("newvalue");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("setValue_throws_NO_MODIFICATION", success);
        }

        {
            boolean success = false;
            try {
                attrNode.setNodeValue("newvalue2");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("setNodeValue_throws_NO_MODIFICATION", success);
        }
    }
}

