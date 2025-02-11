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

package org.loboevolution.domts.level3;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Get the first "em" node, construct an equivalent in a new document and see if isEqualNode
 * returns true.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class Nodeisequalnode09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        final Document newDoc;
        final Element elem1;
        final Element elem2;
        final HTMLCollection employeeList;
        final Text text;
        final boolean isEqual;
        final Element docElem;
        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getLocalName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, null);
        employeeList = doc.getElementsByTagName("em");
        elem1 = (Element) employeeList.item(0);
        elem2 = newDoc.createElementNS("http://www.w3.org/1999/xhtml", "em");
        text = newDoc.createTextNode("EMP0001");
        elem2.appendChild(text);
        isEqual = elem1.isEqualNode(elem2);
        assertTrue(isEqual, "Nodeisequalnode09Assert2");
    }
}

