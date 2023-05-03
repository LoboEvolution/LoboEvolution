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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setIdAttributeNode on the third strong element but with the class attribute of the acronym
 * element as a parameter.  Verify that NOT_FOUND_ERR is raised.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-ElSetIdAttrNode</a>
 */
public class elementsetidattributenode05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList1;
        HTMLCollection elemList2;
        Element nameElem;
        Element acronymElem;
        NamedNodeMap attributesMap;
        Attr attr;
        doc = sampleXmlFile("hc_staff.xml");
        elemList1 = doc.getElementsByTagName("strong");
        elemList2 = doc.getElementsByTagName("acronym");
        nameElem = (Element) elemList1.item(1);
        acronymElem = (Element) elemList2.item(1);
        attributesMap = acronymElem.getAttributes();
        attr = (Attr) attributesMap.getNamedItem("class");

        boolean success = false;
        try {
            nameElem.setIdAttributeNode(attr, true);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
        }
        assertTrue("throw_NOT_FOUND_ERR", success);

    }
}

