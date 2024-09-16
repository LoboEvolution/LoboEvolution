
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "insertBefore(newChild,refChild)" method inserts the
 * node "newChild" before the node "refChild".
 * <p>
 * Insert a newly created Element node before the eigth
 * child of the second employee and check the "newChild"
 * and "refChild" after insertion for correct placement.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class NodeinsertbeforeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Node employeeNode;
        final NodeList childList;
        final Node refChild;
        final Node newChild;
        Node child;
        String childName;
        final int length;
        final List<String> actual = new java.util.ArrayList<>();

        final List<String> expectedWithWhitespace = getStrings();

        final List<String> expectedWithoutWhitespace = new ArrayList<>();
        expectedWithoutWhitespace.add("EMPLOYEEID");
        expectedWithoutWhitespace.add("NAME");
        expectedWithoutWhitespace.add("POSITION");
        expectedWithoutWhitespace.add("NEWCHILD");
        expectedWithoutWhitespace.add("SALARY");
        expectedWithoutWhitespace.add("GENDER");
        expectedWithoutWhitespace.add("ADDRESS");

        final List<String> expected;

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();

        if (length == 6) {
            refChild = childList.item(3);
            expected = expectedWithoutWhitespace;
        } else {
            refChild = childList.item(7);
            expected = expectedWithWhitespace;
        }

        newChild = doc.createElement("newChild");
        employeeNode.insertBefore(newChild, refChild);
        for (int indexN100DC = 0; indexN100DC < childList.getLength(); indexN100DC++) {
            child = childList.item(indexN100DC);
            childName = child.getNodeName();
            actual.add(childName);
        }
        assertEquals(expected, actual, "NodeinsertbeforeAssert1");
    }

    private static List<String> getStrings() {
        final List<String> expectedWithWhitespace = new ArrayList<>();
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("employeeId");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("name");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("position");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("newChild");
        expectedWithWhitespace.add("salary");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("gender");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("ADDRESS");
        expectedWithWhitespace.add("#text");
        return expectedWithWhitespace;
    }

}

