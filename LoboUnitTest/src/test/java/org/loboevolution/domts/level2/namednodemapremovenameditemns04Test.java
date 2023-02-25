
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNull;


/**
 * The method removeNamedItemNS removes a node specified by local name and namespace
 * <p>
 * Attempt to remove the xmlns and dmstc attributes of the first element node with the localName
 * employee.  Verify if the 2 attributes were successfully removed.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-D58B193</a>
 */
public class namednodemapremovenameditemns04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributes;
        Element element;
        Node attribute;
        HTMLCollection elementList;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "employee");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/2000/xmlns/", "xmlns");
        assertNull("namednodemapremovenameditemns04_1", attribute);
        attribute = (Attr) attributes.getNamedItemNS("http://www.w3.org/2000/xmlns/", "dmstc");
        assertNull("namednodemapremovenameditemns04_2", attribute);
    }
}

