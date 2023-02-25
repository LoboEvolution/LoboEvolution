
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * Returns a NodeList of all the Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of the Document tree.
 * Create a new element node ('root') and append three newly created child nodes (all have
 * local name 'child' and defined in different namespaces).
 * Test 1: invoke getElementsByTagNameNS to retrieve one of the children.
 * Test 2: invoke getElementsByTagNameNS with the value of namespace equals to '*', and
 * verify that the node list has length of 3.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class elementgetelementsbytagnamens04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element child1;
        Element child2;
        Element child3;
        HTMLCollection elementList;

        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM", "root");
        child1 = doc.createElementNS("http://www.w3.org/DOM/Level1", "dom:child");
        child2 = doc.createElementNS(null, "child");
        child3 = doc.createElementNS("http://www.w3.org/DOM/Level2", "dom:child");
        element.appendChild(child1);
        element.appendChild(child2);
        element.appendChild(child3);
        elementList = element.getElementsByTagNameNS(null, "child");
        assertEquals( "elementgetelementsbytagnamens04_1", 1, elementList.getLength());
        elementList = element.getElementsByTagNameNS("*", "child");
        assertEquals( "elementgetelementsbytagnamens04_2", 3, elementList.getLength());
    }
}

