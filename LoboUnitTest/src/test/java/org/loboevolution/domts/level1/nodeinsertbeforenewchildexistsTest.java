
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
 * If the "newChild" is already in the tree, the
 * "insertBefore(newChild,refChild)" method must first
 * remove it before the insertion takes place.
 * <p>
 * Insert a node Element ("employeeId") that is already
 * present in the tree.   The existing node should be
 * removed first and the new one inserted.   The node is
 * inserted at a different position in the tree to assure
 * that it was indeed inserted.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class nodeinsertbeforenewchildexistsTest extends LoboUnitTest {

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
        Node refChild;
        Node newChild;
        Node child;
        int length;
        String childName;
        List<String> expectedWhitespace = new ArrayList<>();
        expectedWhitespace.add("#text");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("name");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("position");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("salary");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("gender");
        expectedWhitespace.add("#text");
        expectedWhitespace.add("employeeId");
        expectedWhitespace.add("ADDRESS");
        expectedWhitespace.add("#text");

        List<String> expectedNoWhitespace = new ArrayList<String>();
        expectedNoWhitespace.add("EMPLOYEEID");
        expectedNoWhitespace.add("NAME");
        expectedNoWhitespace.add("POSITION");
        expectedNoWhitespace.add("SALARY");
        expectedNoWhitespace.add("GENDER");
        expectedNoWhitespace.add("ADDRESS");

        List<String> expected;
        List<String> result = new ArrayList<>();

        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();

        if (length == 6) {
            expected = expectedNoWhitespace;
            refChild = childList.item(5);
            newChild = childList.item(0);
        } else {
            expected = expectedWhitespace;
            refChild = childList.item(11);
            newChild = childList.item(1);
        }

        employeeNode.insertBefore(newChild, refChild);
        for (int indexN100DD = 0; indexN100DD < childList.getLength(); indexN100DD++) {
            child = childList.item(indexN100DD);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("childNames", expected, result);
    }

}

