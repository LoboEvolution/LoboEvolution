
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
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * If the "newChild" is a DocumentFragment object then all
 * its children are inserted in the same order before the
 * the "refChild".
 * <p>
 * Create a DocumentFragment object and populate it with
 * two Element nodes.   Retrieve the second employee and
 * insert the newly created DocumentFragment before its
 * fourth child.   The second employee should now have two
 * extra children("newChild1" and "newChild2") at
 * positions fourth and fifth respectively.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=247">http://www.w3.org/Bugs/Public/show_bug.cgi?id=247</a>
 */
public class HcnodeinsertbeforedocfragmentTest extends LoboUnitTest {

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
        final DocumentFragment newdocFragment;
        final Node newChild1;
        final Node newChild2;
        Node child;
        String childName;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        refChild = childList.item(3);
        newdocFragment = doc.createDocumentFragment();
        newChild1 = doc.createElement("br");
        newChild2 = doc.createElement("b");
        newdocFragment.appendChild(newChild1);
        newdocFragment.appendChild(newChild2);
        employeeNode.insertBefore(newdocFragment, refChild);
        child = childList.item(3);
        childName = child.getNodeName();
        assertEquals("BR", childName, "HcnodeinsertbeforedocfragmentAssert1");
        child = childList.item(4);
        childName = child.getNodeName();
        assertEquals("b", childName, "HcnodeinsertbeforedocfragmentAssert2");
    }
}

