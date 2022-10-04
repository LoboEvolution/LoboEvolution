
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import static org.junit.Assert.assertEquals;


/**
 * Retrieve the second acronym element and invoke
 * the cloneNode method.   The
 * duplicate node returned by the method should copy the
 * attributes associated with this node.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=236">http://www.w3.org/Bugs/Public/show_bug.cgi?id=236</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=184">http://www.w3.org/Bugs/Public/show_bug.cgi?id=184</a>
 */
public class hc_nodecloneattributescopiedTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node addressNode;
        Element clonedNode;
        NamedNodeMap attributes;
        Node attributeNode;
        String attributeName;
        java.util.Collection result = new java.util.ArrayList();

        java.util.Collection htmlExpected = new java.util.ArrayList();
        htmlExpected.add("class");
        htmlExpected.add("title");

        java.util.Collection expected = new java.util.ArrayList();
        expected.add("title");
        expected.add("class");
        expected.add("dir");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        addressNode = elementList.item(1);
        clonedNode = (Element) addressNode.cloneNode(false);
        attributes = clonedNode.getAttributes();
        for (int indexN10076 = 0; indexN10076 < attributes.getLength(); indexN10076++) {
            attributeNode = attributes.item(indexN10076);
            attributeName = attributeNode.getNodeName();
            result.add(attributeName);
        }

        assertEquals("nodeNames", expected, result);

    }
}

