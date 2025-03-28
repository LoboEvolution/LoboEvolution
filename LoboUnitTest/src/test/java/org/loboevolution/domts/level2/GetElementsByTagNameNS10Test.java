
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "getElementsByTagNameNS(namespaceURI,localName)" method returns a NodeList
 * of all descendant Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of this Element tree.
 * <p>
 * Create a NodeList of all the descendant elements of the document element
 * using the "<a href="http://www.nist.gov">...</a>" as the namespaceURI and the special value "*" as the
 * localName.
 * The method should return a NodeList of elements that have "<a href="http://www.nist.gov">...</a>
 * as a namespace URI.
 * Derived from getElementsByTagNameNS03
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-1938918D</a>
 */
public class GetElementsByTagNameNS10Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element docElem;
        final HTMLCollection elementList;
        Node child;
        String childName;
        final List<String> result = new ArrayList<>();

        final List<String> expectedResult = new ArrayList<>();
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
        docElem = doc.getDocumentElement();
        elementList = docElem.getElementsByTagName("*");
        for (int indexN1007E = 0; indexN1007E < elementList.getLength(); indexN1007E++) {
            child = elementList.item(indexN1007E);
            childName = child.getLocalName();
            result.add(childName);
        }
        assertEquals(expectedResult, result);
    }
}

