
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


/**
 * If the "newChild" is already in the tree, it is first
 * removed before the new one is added.
 * <p>
 * Retrieve the second "p" and replace "acronym" with its "em".

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=246">http://www.w3.org/Bugs/Public/show_bug.cgi?id=246</a>
 */
public class HcnodereplacechildnewchildexistsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Node employeeNode;
        HTMLCollection childList;
        final Node oldChild;
        final Node newChild;
        String childName;
        Node childNode;
        final List<String> actual = new ArrayList<>();

        final List<String> expected = new ArrayList<>();
        expected.add("STRONG");
        expected.add("CODE");
        expected.add("SUP");
        expected.add("VAR");
        expected.add("EM");

        final Node replacedChild;
        int nodeType;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = ((Element) employeeNode).getElementsByTagName("*");
        newChild = childList.item(0);
        oldChild = childList.item(5);
        replacedChild = employeeNode.replaceChild(newChild, oldChild);
        assertSame(oldChild, replacedChild, "HcnodereplacechildnewchildexistsAssert1");
        childList = ((Element) employeeNode).getElementsByTagName("*");
        for (int indexN10094 = 0; indexN10094 < childList.getLength(); indexN10094++) {
            childNode = childList.item(indexN10094);
            childName = childNode.getNodeName();
            nodeType = childNode.getNodeType();

            if (nodeType == 1) {
                actual.add(childName);
            } else {
                assertEquals(3, nodeType, "HcnodereplacechildnewchildexistsAssert2");
                assertEquals("#text", childName, "HcnodereplacechildnewchildexistsAssert3");
            }

        }
        assertEquals(expected, actual, "HcnodereplacechildnewchildexistsAssert4");
    }
}

