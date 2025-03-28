
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is appended.
 * <p>
 * Retrieve the "em" second employee and
 * append the first child to the end of the list.   After
 * the "appendChild(newChild)" method is invoked the first
 * child should be the one that was second and the last
 * child should be the one that was first.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class HcnodeappendchildchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final HTMLCollection childList;
        final Node childNode;
        final Node newChild;
        Node memberNode;
        String memberName;
        final java.util.List<String> refreshedActual = new java.util.ArrayList<>();

        final java.util.List<String> actual = new java.util.ArrayList<>();

        int nodeType;
        final List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("ACRONYM");
        expected.add("EM");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        childNode = elementList.item(1);
        childList = ((Element) childNode).getElementsByTagName("*");
        newChild = childList.item(0);
        childNode.appendChild(newChild);
        for (int indexN10085 = 0; indexN10085 < childList.getLength(); indexN10085++) {
            memberNode = childList.item(indexN10085);
            memberName = memberNode.getNodeName();
            actual.add(memberName);
        }
        assertEquals(expected, actual, "HcnodeappendchildchildexistsAssert1");
        final NodeList childList2 = childNode.getChildNodes();
        for (int indexN1009C = 0; indexN1009C < childList2.getLength(); indexN1009C++) {
            memberNode = childList2.item(indexN1009C);
            nodeType = memberNode.getNodeType();

            if (nodeType == 1) {
                memberName = memberNode.getNodeName();
                refreshedActual.add(memberName);
            }
        }
        assertEquals(expected, refreshedActual, "HcnodeappendchildchildexistsAssert2");
    }
}

