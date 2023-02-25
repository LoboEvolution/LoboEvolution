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
 * Using setTextContent on a new Attr node with a null value, attempt to set its value to NA.  Retreive
 * the textContent and verify if it is was set to NA.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        Attr attr;
        Attr attrNode;
        String textContent;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        attrNode = elem.setAttributeNodeNS(attr);
        attr.setTextContent("NA");
        textContent = attr.getTextContent();
        assertEquals("nodesettextcontent06", "NA", textContent);
    }
}

