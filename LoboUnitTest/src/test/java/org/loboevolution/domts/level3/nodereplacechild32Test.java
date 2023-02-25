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
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on an Attr node to replace its EntityReference Child with a
 * new Text Node and verify the name of the replaced child.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild32Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Element elem;
        Attr parent;
        Node oldChild;
        Text newChild;
        Node replaced;
        String nodeName;
        int nodeType;
        EntityReference enRef;
        EntityReference enRefChild;
        String reference = "entity1";
        doc = sampleXmlFile("hc_staff.xml");
        newChild = doc.createTextNode("Text");
        childList = doc.getElementsByTagNameNS("*", "acronym");
        elem = (Element) childList.item(3);
        parent = elem.getAttributeNode("class");
        enRef = doc.createEntityReference(reference);
        enRefChild = (EntityReference) parent.appendChild(enRef);
        replaced = parent.replaceChild(newChild, enRefChild);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild32", "entity1", nodeName);
    }
}

