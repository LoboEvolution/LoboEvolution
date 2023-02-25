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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Using replaceChild on a default Attr node to replace its Text Child with a
 * new EntityReference Node and verify the value of the replaced child.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild33Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Element elem;
        Attr parent;
        Node oldChild;
        EntityReference newChild;
        Node replaced;
        String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        newChild = doc.createEntityReference("delta");
        childList = doc.getElementsByTagName("p");
        elem = (Element) childList.item(3);
        parent = elem.getAttributeNode("dir");
        oldChild = parent.getLastChild();
        replaced = parent.replaceChild(newChild, oldChild);
        nodeValue = replaced.getNodeValue();
        assertEquals("nodereplacechild33", "rtl", nodeValue);
    }
}

