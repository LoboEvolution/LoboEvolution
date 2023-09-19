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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition to check if invoking the method on the first name node with
 * a new node appended to the second position node as a parameter is FOLLOWING, and is PRECEDING vice versa
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        HTMLCollection positionList;
        Element strong;
        Element code;
        Element newElem;
        int namePosition;
        int elemPosition;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        nameList = doc.getElementsByTagName("strong");
        strong = (Element) nameList.item(0);
        positionList = doc.getElementsByTagName("code");
        code = (Element) positionList.item(1);
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        appendedChild = code.appendChild(newElem);
        namePosition = strong.compareDocumentPosition(newElem);
        assertEquals("nodecomparedocumentpositionFollowing31", 4, namePosition);
        elemPosition = newElem.compareDocumentPosition(strong);
        assertEquals("nodecomparedocumentpositionPRECEDING31", 2, elemPosition);
    }
}

