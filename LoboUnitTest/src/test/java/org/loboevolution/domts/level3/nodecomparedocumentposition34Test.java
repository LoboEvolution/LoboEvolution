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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * Create a new Element node, add new Text, Element and Processing Instruction nodes to it.
 * Using compareDocumentPosition, compare the position of the Element with respect to the Text
 * and the Text with respect to the Processing Instruction.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition34Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elemMain;
        Element elem;
        Text txt;
        ProcessingInstruction pi;
        int elementToTxtPosition;
        int txtToPiPosition;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elemMain = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        txt = doc.createTextNode("TEXT");
        pi = doc.createProcessingInstruction("PIT", "PID");
        appendedChild = elemMain.appendChild(txt);
        appendedChild = elemMain.appendChild(elem);
        appendedChild = elemMain.appendChild(pi);
        elementToTxtPosition = txt.compareDocumentPosition(elem);
        assertEquals("nodecomparedocumentpositionFollowing34", 4, elementToTxtPosition);
        txtToPiPosition = pi.compareDocumentPosition(txt);
        assertEquals("nodecomparedocumentpositionPRECEDING34", 2, txtToPiPosition);
    }
}

