
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * Returns a NodeList of all the Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of the Document tree.
 * Create a new element node ('root') and append three newly created child nodes (all have
 * local name 'child' and defined in different namespaces).
 * Test 1: invoke getElementsByTagNameNS to retrieve one of the children.
 * Test 2: invoke getElementsByTagNameNS with the value of namespace equals to '*', and
 * verify that the node list has length of 3.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class elementgetelementsbytagnamens04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element child1;
        Element child2;
        Element child3;
        HTMLCollection elementList;

        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS("http://www.w3.org/DOM", "root");
        child1 = doc.createElementNS("http://www.w3.org/DOM/Level1", "dom:child");
        child2 = doc.createElementNS(null, "child");
        child3 = doc.createElementNS("http://www.w3.org/DOM/Level2", "dom:child");
        element.appendChild(child1);
        element.appendChild(child2);
        element.appendChild(child3);
        elementList = element.getElementsByTagNameNS(null, "child");
        assertEquals( "elementgetelementsbytagnamens04_1", 1, elementList.getLength());
        elementList = element.getElementsByTagNameNS("*", "child");
        assertEquals( "elementgetelementsbytagnamens04_2", 3, elementList.getLength());
    }
}

