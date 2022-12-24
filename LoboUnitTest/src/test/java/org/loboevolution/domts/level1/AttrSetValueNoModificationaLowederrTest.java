
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
 * Obtain the children of the THIRD "gender" element.  The elements
 * content is an entity reference.  Get the "domestic" attribute
 * from the entity reference and execute the "setValue()" method.
 * This causes a NO_MODIFICATION_ALLOWED_ERR DOMException to be thrown.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#ID-221662474">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#ID-221662474</a>
 * @see <a href="http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-221662474')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])">http://www.w3.org/TR/2000/WD-DOM-Level-1-20000929/level-one-core#xpointer(id('ID-221662474')/setraises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NO_MODIFICATION_ALLOWED_ERR'])</a>
 * @see <a href="http://www.w3.org/DOM/updates/REC-DOM-Level-1-19981001-errata.html">http://www.w3.org/DOM/updates/REC-DOM-Level-1-19981001-errata.html</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474</a>
 */
public class AttrSetValueNoModificationaLowederrTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection genderList;
        Node gender;
        NodeList genList;
        Node gen;
        NodeList gList;
        Element g;
        NamedNodeMap attrList;
        Attr attrNode;
        doc = sampleXmlFile("staff.xml");
        genderList = doc.getElementsByTagName("gender");
        gender = genderList.item(2);
        assertNotNull("genderNotNull", gender);
        genList = gender.getChildNodes();
        gen = genList.item(0);
        assertNotNull("genderFirstChildNotNull", gen);
        gList = gen.getChildNodes();
        g = (Element) gList.item(0);
        assertNotNull("genderFirstGrandchildNotNull", g);
        attrList = g.getAttributes();
        assertNotNull("attributesNotNull", attrList);
        attrNode = (Attr)attrList.getNamedItem("domestic");
        assertNotNull("attrNotNull", attrNode);

        {
            boolean success = false;
            try {
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

