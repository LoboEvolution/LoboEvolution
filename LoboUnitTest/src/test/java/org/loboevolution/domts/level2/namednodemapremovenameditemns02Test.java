
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
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The method removeNamedItemNS removes a node specified by local name and namespace
 * A removed attribute may be known to have a default value when this map contains the
 * attributes attached to an element, as returned by the attributes attribute of the Node
 * interface. If so, an attribute immediately appears containing the default value as well
 * as the corresponding namespace URI, local name, and prefix when applicable.
 * <p>
 * Retreive a default attribute node.  Remove it from the NodeMap.  Check if a new one immediately
 * appears containing the default value.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class namednodemapremovenameditemns02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributes;
        Element element;
        Attr attribute;
        HTMLCollection elementList;
        String attrValue;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "employee");
        element = (Element) elementList.item(1);
        attributes = element.getAttributes();
        attribute = attributes.getNamedItemNS(nullNS, "defaultAttr");
        attrValue = attribute.getNodeValue();
        assertEquals("namednodemapremovenameditemns02_attrValue", "defaultVal", attrValue);
        attribute = attributes.removeNamedItemNS(nullNS, "defaultAttr");
        assertNotNull("namednodemapremovenameditemns02", attribute);
    }
}

