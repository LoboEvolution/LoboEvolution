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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Using insertBefore on an Element node attempt to insert a new Element, node before its
 * first element child and verify the name of the new first child node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore16Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element newElem;
        Element refElem;
        Node firstChild;
        Element insertedElem;
        HTMLCollection childList;
        String nodeName;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("p");
        element = (Element) childList.item(0);
        firstChild = element.getFirstChild();
        refElem = (Element) firstChild.getNextSibling();
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:br");
        inserted = element.insertBefore(newElem, refElem);
        childList = doc.getElementsByTagName("p");
        element = (Element) childList.item(0);
        firstChild = element.getFirstChild();
        insertedElem = (Element) firstChild.getNextSibling();
        nodeName = insertedElem.getNodeName();
        assertEquals("nodeinsertbefore16", "xhtml:br", nodeName);
    }
}

