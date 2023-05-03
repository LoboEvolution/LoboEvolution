
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

package org.loboevolution.domts.level1;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * The "setNamedItem(arg)" method raises a
 * INUSE_ATTRIBUTE_ERR DOMException if "arg" is an
 * Attr that is already in an attribute of another Element.
 * Create a NamedNodeMap object from the attributes of the
 * last child of the third employee and attempt to add
 * an attribute that is already being used by the first
 * employee.  This should raise the desired exception.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='INUSE_ATTRIBUTE_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-1025163788')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-1025163788')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INUSE_ATTRIBUTE_ERR'])</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=249">http://www.w3.org/Bugs/Public/show_bug.cgi?id=249</a>
 */
public class hc_namednodemapinuseattributeerrTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element firstNode;
        Element testNode;
        NamedNodeMap attributes;
        Attr domesticAttr;
        Attr setAttr;
        Node setNode;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        firstNode = (Element) elementList.item(0);
        domesticAttr = doc.createAttribute("title");
        domesticAttr.setValue("Y\u03b1"); // Android-changed: GREEK LOWER CASE ALPHA
        setAttr = firstNode.setAttributeNode(domesticAttr);
        elementList = doc.getElementsByTagName("acronym");
        testNode = (Element) elementList.item(2);
        attributes = testNode.getAttributes();

        {
            boolean success = false;
            try {
                setNode = attributes.setNamedItem(domesticAttr);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
            }
            assertTrue("throw_INUSE_ATTRIBUTE_ERR", success);
        }
    }
}

