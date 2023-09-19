
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
 * The "insertBefore(newChild,refChild)" method inserts the
 * node "newChild" before the node "refChild".
 * <p>
 * Insert a newly created Element node before the second
 * sup element in the document and check the "newChild"
 * and "refChild" after insertion for correct placement.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=247">http://www.w3.org/Bugs/Public/show_bug.cgi?id=247</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=261">http://www.w3.org/Bugs/Public/show_bug.cgi?id=261</a>
 */
public class hc_nodeinsertbeforeTest extends LoboUnitTest {

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
        String childName;
        Node insertedNode;
        List<String> actual = new ArrayList<>();

        List<String> expected = new ArrayList<>();
        expected.add("EM");
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("BR");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");

        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("sup");
        refChild = elementList.item(2);
        employeeNode = refChild.getParentNode();
        childList = employeeNode.getChildNodes();
        newChild = doc.createElement("BR");
        insertedNode = employeeNode.insertBefore(newChild, refChild);
        for (int indexN10091 = 0; indexN10091 < childList.getLength(); indexN10091++) {
            child = childList.item(indexN10091);
            nodeType = child.getNodeType();

            if (nodeType == 1) {
                childName = child.getNodeName();
                actual.add(childName);
            }
        }
        assertEquals("nodeNames", expected, actual);
    }
}

