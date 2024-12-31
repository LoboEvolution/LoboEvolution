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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Create a new Element node, add new Text, Element and Processing Instruction nodes to it.
 * Using compareDocumentPosition, compare the position of the Element with respect to the Text
 * and the Text with respect to the Processing Instruction.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition34Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element elemMain;
        final Element elem;
        final Text txt;
        final ProcessingInstruction pi;
        final int elementToTxtPosition;
        final int txtToPiPosition;
        doc = sampleXmlFile("hc_staff.xml");
        elemMain = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        txt = doc.createTextNode("TEXT");
        pi = doc.createProcessingInstruction("PIT", "PID");
        elemMain.appendChild(txt);
        elemMain.appendChild(elem);
        elemMain.appendChild(pi);
        elementToTxtPosition = txt.compareDocumentPosition(elem);
        assertEquals(4, elementToTxtPosition, "Nodecomparedocumentposition34Assert2");
        txtToPiPosition = pi.compareDocumentPosition(txt);
        assertEquals(2, txtToPiPosition, "Nodecomparedocumentposition34Assert3");
    }
}

