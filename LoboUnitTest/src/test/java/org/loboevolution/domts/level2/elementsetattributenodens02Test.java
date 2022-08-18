
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Test the setAttributeNodeNS method.
 * Retreive the street attribute from the second address element node.
 * Clone it and add it to the first address node.  The INUSE_ATTRIBUTE_ERR exception
 * should not be thrown. Check the name and value of the newly added node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAtNodeNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class elementsetattributenodens02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element element2;
        Attr attribute;
        Attr attributeCloned;
        Attr newAttr;
        HTMLCollection elementList;
        String attrName;
        String attrValue;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "address");
        element = (Element) elementList.item(1);
        attribute = element.getAttributeNodeNS(nullNS, "street");
        attributeCloned = (Attr) attribute.cloneNode(true);
        element2 = (Element) elementList.item(2);
        newAttr = element2.setAttributeNodeNS(attributeCloned);
        attrName = newAttr.getNodeName();
        attrValue = newAttr.getNodeValue();
        assertEquals("elementsetattributenodens02_attrName", "street", attrName);
        assertEquals("elementsetattributenodens02_attrValue", "Yes", attrValue);
    }
}

