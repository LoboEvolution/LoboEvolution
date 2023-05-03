
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNodeNS adds a new attribute and raises the
 * INUSE_ATTRIBUTE_ERR exception if the newAttr is already an attribute of
 * another Element object.
 * <p>
 * Retreive an attribute node of an existing element node.  Attempt to add it to an  * element node.  Check if the INUSE_ATTRIBUTE_ERR exception is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 */
public class elementsetattributenodens03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element1;
        Element element2;
        Attr attribute;
        Attr newAttribute;
        HTMLCollection elementList;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "address");
        element1 = (Element) elementList.item(1);
        attribute = element1.getAttributeNodeNS(nullNS, "street");
        element2 = (Element) elementList.item(2);

        {
            boolean success = false;
            try {
                newAttribute = element2.setAttributeNodeNS(attribute);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
            }
            assertTrue("elementsetattributenodens03", success);
        }
    }
}

