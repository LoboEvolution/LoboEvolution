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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.EntityReference;

import static org.junit.Assert.assertEquals;


/**
 * Using replaceChild on a DocumentFragment node attempt to replace an Element node with
 * its EntityReference child verify the nodeName of the replaced node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild19Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        EntityReference entRef;
        Element elem;
        Element replaced;
        String nodeName;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        entRef = doc.createEntityReference("alpha");
        elem.appendChild(entRef);
        docFrag.appendChild(elem);
        replaced = (Element) docFrag.replaceChild(entRef, elem);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild19", "dom3:p", nodeName);
    }
}

