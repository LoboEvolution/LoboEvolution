
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * The "cloneNode(deep)" method returns a copy of the node
 * and the subtree under it if deep=true.
 * <p>
 * Retrieve the second employee and invoke the
 * "cloneNode(deep)" method with deep=true.   The
 * method should clone this node and the subtree under it.
 * The NodeName of each child in the returned node is
 * checked to insure the entire subtree under the second
 * employee was cloned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-3A0ED0A4</a>
 */
public class nodeclonenodetrueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        NodeList childList;
        Node clonedNode;
        NodeList clonedList;
        Node clonedChild;
        String clonedChildName;
        int length;
        List<String> result = new ArrayList<>();

        List<String> expectedWhitespace = new ArrayList<>();
        expectedWhitespace.add("#text");
        expectedWhitespace.add("employeeId");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("name");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("position");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("salary");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("gender");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("ADDRESS");
        expectedWhitespace.add("#text");

        List<String> expectedNoWhitespace = new ArrayList<>();
        expectedNoWhitespace.add("EMPLOYEEID");
        expectedNoWhitespace.add("NAME");
        expectedNoWhitespace.add("POSITION");
        expectedNoWhitespace.add("SALARY");
        expectedNoWhitespace.add("GENDER");
        expectedNoWhitespace.add("ADDRESS");

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();
        clonedNode = employeeNode.cloneNode(true);
        clonedList = clonedNode.getChildNodes();
        for (int indexN100AE = 0; indexN100AE < clonedList.getLength(); indexN100AE++) {
            clonedChild = clonedList.item(indexN100AE);
            clonedChildName = clonedChild.getNodeName();
            result.add(clonedChildName);
        }

        if (length == 6) {
            assertEquals("nowhitespace", expectedNoWhitespace, result);
        } else {
            assertEquals("whitespace", expectedWhitespace, result);
        }

    }
}

