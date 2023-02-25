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

import static org.junit.Assert.*;

/**
 * Invoke setIdAttributeNS on an existing namespace attribute with a namespace URI and a qualified name.  Verify by calling
 * isId on the attribute node and getElementById on document node.  Call setIdAttributeNS with isId=false to reset.
 * isId should now return false.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNS</a>
 */
public class elementsetidattributens01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element employeeElem;
        NamedNodeMap attributesMap;
        Attr attr;
        boolean id = false;
        Element elem;
        String elemName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("p");
        employeeElem = (Element) elemList.item(2);
        employeeElem.setIdAttributeNS("http://www.w3.org/2000/xmlns/", "dmstc", true);
        attributesMap = employeeElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("xmlns:dmstc");
        id = attr.isId();
        assertTrue("elementsetidattributensIsIdTrue01", id);
        elem = doc.getElementById("http://www.netzero.com");
        elemName = elem.getTagName();
        assertEquals("elementsetidattributensGetElementById01", "p", elemName);
        employeeElem.setIdAttributeNS("http://www.w3.org/2000/xmlns/", "dmstc", false);
        id = attr.isId();
        assertFalse("elementsetidattributensIsIdFalse01", id);
    }

}

