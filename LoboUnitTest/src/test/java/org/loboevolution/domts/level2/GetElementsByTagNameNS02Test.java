
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "getElementsByTagNameNS(namespaceURI,localName)" method for a
 * Document should return a new NodeList of all Elements with a given
 * localName and namespaceURI in the order they were encountered in a preorder
 * traversal of the document tree.
 * <p>
 * Invoke method getElementsByTagNameNS(namespaceURI,localName) on this document
 * with namespaceURI being " " and localName is "employee".
 * Method should return a new NodeList containing five Elements.
 * Retrieve the FOURTH element whose name should be "employee".

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 */
public class GetElementsByTagNameNS02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection newList;
        final Element newElement;
        final String prefix;
        final String lname;
        doc = sampleXmlFile("staffNS.xml");
        newList = doc.getElementsByTagNameNS("*", "employee");
        assertEquals(5, newList.getLength());
        newElement = (Element) newList.item(3);
        prefix = newElement.getPrefix();
        assertEquals("EMP", prefix);
        lname = newElement.getLocalName();
        assertEquals("EMPLOYEE", lname);
    }
}

