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

import static org.junit.Assert.assertEquals;


/**
 * Invoke the method getTextContent on a new Attr node and check if the value returned
 * is the attributes Value.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodegettextcontent07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        Attr attr;
        String textContent;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(3);
        elem.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
        attr = elem.getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "lang");
        textContent = attr.getTextContent();
        assertEquals("nodegettextcontent07", "en-US", textContent);
    }
}

