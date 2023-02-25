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
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;

/**
 * Invoke lookupNamespaceURI on the default attribute node of the p node with
 * a namespaceURI and a node prefix and check if the value of the namespaceURI returned by
 * using its prefix as a parameter is valid.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespaceURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespaceURI</a>
 */
public class nodelookupnamespaceuri18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        HTMLCollection elemList;
        NamedNodeMap attributesMap;
        Attr attr;
        String namespaceURI;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        attributesMap = elem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("dir");
        namespaceURI = attr.lookupNamespaceURI("nm");
        assertEquals("nodelookupnamespaceuri18", "http://www.altavista.com", namespaceURI);
    }
}

