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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;

/**
 * Invoke lookupPrefix on an renamed new Element node with a namespace URI
 * and prefix in a new Document and using the parents namespace URI as an argument, verify if the prefix
 * returned is a valid prefix of the parent.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespacePrefix">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespacePrefix</a>
 */
public class nodelookupprefix12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Element elem;
        Element renamedNode;
        String prefix;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        renamedNode = (Element) doc.renameNode(elem, "http://www.w3.org/1999/xhtml", "ren:br");
        prefix = renamedNode.lookupPrefix("http://www.w3.org/1999/xhtml");
        assertEquals("nodelookupprefix12", "ren", prefix);
    }
}

