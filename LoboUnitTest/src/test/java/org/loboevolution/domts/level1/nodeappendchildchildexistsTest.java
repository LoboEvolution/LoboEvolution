
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

import static org.junit.Assert.*;


/**
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is appended.
 * <p>
 * Retrieve the first child of the second employee and
 * append the first child to the end of the list.   After
 * the "appendChild(newChild)" method is invoked the first
 * child should be the one that was second and the last
 * child should be the one that was first.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 */
public class nodeappendchildchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node childNode;
        Node newChild;
        Node lchild;
        Node fchild;
        String lchildName;
        String fchildName;
        Node appendedChild;
        String initialName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        childNode = elementList.item(1);
        newChild = childNode.getFirstChild();
        initialName = newChild.getNodeName();
        appendedChild = childNode.appendChild(newChild);
        fchild = childNode.getFirstChild();
        fchildName = fchild.getNodeName();
        lchild = childNode.getLastChild();
        lchildName = lchild.getNodeName();

        if ("employeeId".equals(initialName)) {
            assertEquals("assert1_nowhitespace", "name", fchildName);
            assertEquals("assert2_nowhitespace", "EMPLOYEEID",lchildName);
        } else {
            assertEquals("assert1", "EMPLOYEEID", fchildName);
            assertEquals("assert2", "#text", lchildName);
        }

    }
}

