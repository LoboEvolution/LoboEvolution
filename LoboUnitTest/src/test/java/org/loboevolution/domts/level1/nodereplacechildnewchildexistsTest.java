
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;


/**
 * Retrieve the second employee and replace its TWELFTH
 * child(address) with its SECOND child(employeeId).   After the
 * replacement the second child should now be the one that used
 * to be at the third position and the TWELFTH child should be the
 * one that used to be at the SECOND position.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 */
public class nodereplacechildnewchildexistsTest extends LoboUnitTest {

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
        Node oldChild = null;

        Node newChild = null;

        String childName;
        Node childNode;
        int length;
        java.util.List actual = new java.util.ArrayList();

        java.util.List expected = new java.util.ArrayList();

        java.util.List expectedWithoutWhitespace = new java.util.ArrayList();
        expectedWithoutWhitespace.add("name");
        expectedWithoutWhitespace.add("position");
        expectedWithoutWhitespace.add("salary");
        expectedWithoutWhitespace.add("gender");
        expectedWithoutWhitespace.add("employeeId");

        java.util.List expectedWithWhitespace = new java.util.ArrayList();
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("name");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("position");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("salary");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("gender");
        expectedWithWhitespace.add("#text");
        expectedWithWhitespace.add("employeeId");
        expectedWithWhitespace.add("#text");

        Node replacedChild;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        length = childList.getLength();

        if (length == 13) {
            newChild = childList.item(1);
            oldChild = childList.item(11);
            expected = expectedWithWhitespace;
        } else {
            newChild = childList.item(0);
            oldChild = childList.item(5);
            expected = expectedWithoutWhitespace;
        }

        replacedChild = employeeNode.replaceChild(newChild, oldChild);
        assertSame("return_value_same", oldChild, replacedChild);
        for (int indexN100DE = 0; indexN100DE < childList.getLength(); indexN100DE++) {
            childNode = childList.item(indexN100DE);
            childName = childNode.getNodeName();
            actual.add(childName);
        }
        assertEquals("childNames", expected, actual);
    }

}

