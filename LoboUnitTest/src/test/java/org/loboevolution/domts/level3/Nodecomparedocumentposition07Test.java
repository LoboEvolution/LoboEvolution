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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using compareDocumentPosition check if the document compared contains and precedes the new
 * newElement, and the newElement is contained and follows the document.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition07Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final Element docElem;
        final Element newElem;
        final int documentPosition;
        final int documentElementPosition;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        newElem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        docElem.appendChild(newElem);
        documentPosition = doc.compareDocumentPosition(newElem);
        assertEquals(20, documentPosition, "Nodecomparedocumentposition07Assert2");
        documentElementPosition = newElem.compareDocumentPosition(doc);
        assertEquals(10, documentElementPosition, "Nodecomparedocumentposition07Assert3");
    }
}

