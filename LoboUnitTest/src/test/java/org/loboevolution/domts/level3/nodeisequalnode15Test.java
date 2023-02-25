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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 Attr nodes having the same nodeName and a null namespaceURI
 * attribute, one created using createAttributeNS and the other retreived from this document
 * are equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Attr attr1;
        Attr attr2;
        Element addrElement;
        HTMLCollection elementList;
        boolean isEqual;
        String nullNS = null;

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        addrElement = (Element) elementList.item(3);
        attr1 = addrElement.getAttributeNodeNS(nullNS, "title");
        attr2 = doc.createAttributeNS(nullNS, "title");
        attr2.setValue("Yes");
        isEqual = attr1.isEqualNode(attr2);
        assertTrue("nodeisequalnode15", isEqual);
    }
}

