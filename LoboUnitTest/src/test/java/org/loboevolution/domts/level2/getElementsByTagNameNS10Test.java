
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

import static org.junit.Assert.assertEquals;


/**
 * The "getElementsByTagNameNS(namespaceURI,localName)" method returns a NodeList
 * of all descendant Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of this Element tree.
 * <p>
 * Create a NodeList of all the descendant elements of the document element
 * using the "http://www.nist.gov" as the namespaceURI and the special value "*" as the
 * localName.
 * The method should return a NodeList of elements that have "http://www.nist.gov
 * as a namespace URI.
 * Derived from getElementsByTagNameNS03
 *
 * @author Curt Arnold
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D</a>
 */
public class getElementsByTagNameNS10Test extends LoboUnitTest {

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
        java.util.List result = new java.util.ArrayList();

        java.util.List expectedResult = new java.util.ArrayList();
        expectedResult.add("employee");
        expectedResult.add("employeeId");
        expectedResult.add("name");
        expectedResult.add("position");
        expectedResult.add("salary");
        expectedResult.add("gender");
        expectedResult.add("address");
        expectedResult.add("emp:employee");
        expectedResult.add("emp:employeeId");
        expectedResult.add("emp:position");
        expectedResult.add("emp:salary");
        expectedResult.add("emp:gender");
        expectedResult.add("emp:address");
        expectedResult.add("address");

        doc = sampleXmlFile("staffNS.xml");
        docElem = doc.getDocumentElement();
        elementList = docElem.getElementsByTagNameNS("http://www.nist.gov", "*");
        for (int indexN1007E = 0; indexN1007E < elementList.getLength(); indexN1007E++) {
            child = elementList.item(indexN1007E);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("nodeNames", expectedResult, result);
    }
}

