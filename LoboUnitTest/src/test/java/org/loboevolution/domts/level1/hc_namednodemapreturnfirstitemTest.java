
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * The "item(index)" method returns the indexth item in
 * the map(test for first item).
 * <p>
 * Retrieve the second "acronym" get the NamedNodeMap of the attributes. Since the
 * DOM does not specify an order of these nodes the contents
 * of the FIRST node can contain either "title", "class" or "dir".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-349467F9</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=236">http://www.w3.org/Bugs/Public/show_bug.cgi?id=236</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2003Jun/0011.html">http://lists.w3.org/Archives/Public/www-dom-ts/2003Jun/0011.html</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=184">http://www.w3.org/Bugs/Public/show_bug.cgi?id=184</a>
 */
public class hc_namednodemapreturnfirstitemTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddress;
        NamedNodeMap attributes;
        Node child;
        String nodeName;
        java.util.Collection<String> htmlExpected = new java.util.ArrayList<String>();
        htmlExpected.add("title");
        htmlExpected.add("class");

        java.util.Collection<String> expected = new java.util.ArrayList<String>();
        expected.add("title");
        expected.add("class");
        expected.add("dir");

        java.util.Collection<String> actual = new java.util.ArrayList<>();

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        testAddress = (Element) elementList.item(1);
        attributes = testAddress.getAttributes();
        for (int indexN10070 = 0; indexN10070 < attributes.getLength(); indexN10070++) {
            child = attributes.item(indexN10070);
            nodeName = child.getNodeName();
            actual.add(nodeName);
        }
        assertEquals("attrName", expected, actual);

    }
}

