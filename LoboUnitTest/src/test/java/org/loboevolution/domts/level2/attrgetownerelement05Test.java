
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The "getOwnerElement()" will return the Element node this attribute is attached to
 * or null if this attribute is not in use.
 * <p>
 * Retreive an element and its attributes.  Then remove the element and check the name of
 * the ownerElement of attribute of the attribute "street".
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement">http://www.w3.org/TR/DOM-Level-2-Core/core#Attr-ownerElement</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class attrgetownerelement05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element ownerElement;
        Element parentElement;
        HTMLCollection elementList;
        String ownerElementName;
        Attr attr;
        Node removedChild;
        NamedNodeMap nodeMap;
        String nullNS = null;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(1);
        parentElement = (Element) element.getParentNode();
        nodeMap = element.getAttributes();
        removedChild = parentElement.removeChild(element);
        attr = nodeMap.getNamedItemNS(nullNS, "street");
        ownerElement = attr.getOwnerElement();
        ownerElementName = ownerElement.getNodeName();
        assertEquals("attrgetownerelement05", "ADDRESS", ownerElementName);
    }
}

