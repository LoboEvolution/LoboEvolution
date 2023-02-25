
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * The method setPrefix sets the namespace prefix of this node.  Note that setting this attribute,
 * when permitted, changes the nodeName attribute, which holds the qualified name, as well as the
 * tagName and name attributes of the Element and Attr interfaces, when applicable.
 * <p>
 * Create a new element node with a namespace prefix.  Add it to a new DocumentFragment Node without
 * a prefix.  Call setPrefix on the elemen node.  Check if the prefix was set correctly on the element.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class nodesetprefix01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        Element element;
        String elementTagName;
        String elementNodeName;
        doc = sampleXmlFile("staff.xml");
        docFragment = doc.createDocumentFragment();
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "address");
        docFragment.appendChild(element);
        element.setPrefix("dmstc");
        elementTagName = element.getTagName();
        elementNodeName = element.getNodeName();
        assertEquals("nodesetprefix01_tagname", "dmstc:address", elementTagName);
        assertEquals("nodesetprefix01_nodeName", "dmstc:address", elementNodeName);
    }
}

