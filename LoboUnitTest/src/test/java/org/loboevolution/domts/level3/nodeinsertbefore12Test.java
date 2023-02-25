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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * Using insertBefore on a DocumentFragment node attempt to insert a new DocumentFragment node
 * before this DocumentFragment's Element node and verify the last child is still the only child
 * appended to docFrag.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        DocumentFragment docFragNew;
        Element elem;
        Node inserted;
        Node appendedChild;
        Node last;
        String name;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        docFragNew = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/DOM/Test", "dom3:elem");
        appendedChild = docFrag.appendChild(elem);
        inserted = docFrag.insertBefore(docFragNew, elem);
        last = docFrag.getLastChild();
        name = last.getNodeName();
        assertEquals("nodeinsertbefore12", "dom3:elem", name);
    }
}

