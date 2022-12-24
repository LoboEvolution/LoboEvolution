
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The "getElementsByTagNameNS(namespaceURI,localName)" method returns a NodeList
 * of all descendant Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of this Element tree.
 * <p>
 * Create a NodeList of all the descendant elements
 * using the special value "*" as the namespaceURI and "address" as the
 * localName.
 * The method should return a NodeList of Elements that have
 * "address" as the local name.
 * This test is derived from getElementsByTagNameNS04
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D</a>
 */
public class getElementsByTagNameNS11Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        HTMLCollection elementList;
        Node child;
        String childName;
        List<String> result = new ArrayList<>();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("ADDRESS");
        expectedResult.add("ADDRESS");
        expectedResult.add("ADDRESS");
        expectedResult.add("ADDRESS");
        expectedResult.add("ADDRESS");

        doc = sampleXmlFile("staffNS.xml");
        docElem = doc.getDocumentElement();
        elementList = docElem.getElementsByTagNameNS("*", "address");
        for (int indexN1005E = 0; indexN1005E < elementList.getLength(); indexN1005E++) {
            child = elementList.item(indexN1005E);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("nodeNames", expectedResult, result);
    }
}

