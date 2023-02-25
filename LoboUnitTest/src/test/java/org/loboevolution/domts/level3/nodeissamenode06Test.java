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
 * Using isSameNode check if 2 Document Element nodes that reference the same object are
 * the same.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isSameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isSameNode</a>
 */
public class nodeissamenode06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element element1;
        Attr attr1;
        Attr attr2;
        HTMLCollection childList;
        boolean isSame;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("acronym");
        element = (Element) childList.item(2);
        element1 = (Element) childList.item(2);
        attr1 = element.getAttributeNode("class");
        attr2 = element1.getAttributeNode("class");
        isSame = attr1.isSameNode(attr2);
        assertTrue("nodeissamenode06", isSame);
    }
}

