
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
 * using the "http://www.nist.gov" as the namespaceURI and the special value " " as the
 * localName.
 * The method should return a NodeList of elements that have "http://www.nist.gov
 * as a namespace URI.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 */
public class getElementsByTagNameNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node child;
        String childName;
        List<String> result = new ArrayList<>();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("EMPLOYEE");
        expectedResult.add("EMPLOYEEID");
        expectedResult.add("NAME");
        expectedResult.add("POSITION");
        expectedResult.add("SALARY");
        expectedResult.add("GENDER");
        expectedResult.add("ADDRESS");
        expectedResult.add("EMPLOYEE");
        expectedResult.add("EMPLOYEEID");
        expectedResult.add("NAME");
        expectedResult.add("POSITION");
        expectedResult.add("SALARY");
        expectedResult.add("GENDER");
        expectedResult.add("ADDRESS");
        expectedResult.add("EMPLOYEE");
        expectedResult.add("EMPLOYEEID");
        expectedResult.add("NAME");
        expectedResult.add("POSITION");
        expectedResult.add("SALARY");
        expectedResult.add("GENDER");
        expectedResult.add("ADDRESS");
        expectedResult.add("EMPLOYEE");
        expectedResult.add("EMPLOYEEID");
        expectedResult.add("NAME");
        expectedResult.add("POSITION");
        expectedResult.add("SALARY");
        expectedResult.add("GENDER");
        expectedResult.add("ADDRESS");
        expectedResult.add("EMPLOYEE");
        expectedResult.add("EMPLOYEEID");
        expectedResult.add("NAME");
        expectedResult.add("POSITION");
        expectedResult.add("SALARY");
        expectedResult.add("GENDER");
        expectedResult.add("ADDRESS");

        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName( "*");
        for (int indexN10076 = 0; indexN10076 < elementList.getLength(); indexN10076++) {
            child = elementList.item(indexN10076);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("nodeNames", expectedResult, result);
    }
}

