
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


/**
 * The "getElementsByTagName(name)" method may use the
 * special value "*" to match all tags in the element
 * tree.
 * Create a NodeList of all the descendant elements
 * of the last employee by using the special value "*".
 * The method should return all the descendant children(6)
 * in the order the children were encountered.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D</a>
 */
public class HcelementgetelementsbytagnamespecialvalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Element lastEmployee;
        final HTMLCollection lastempList;
        Node child;
        String childName;
        final List<String> result = new ArrayList<>();

        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add("EM");
        expectedResult.add("STRONG");
        expectedResult.add("CODE");
        expectedResult.add("SUP");
        expectedResult.add("VAR");
        expectedResult.add("ACRONYM");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        lastEmployee = (Element) elementList.item(4);
        lastempList = lastEmployee.getElementsByTagName("*");
        for (int indexN10067 = 0; indexN10067 < lastempList.getLength(); indexN10067++) {
            child = lastempList.item(indexN10067);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals(expectedResult, result, "HcelementgetelementsbytagnamespecialvalueAssert1");
    }
}

