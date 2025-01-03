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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using compareDocumentPosition check if the document position of the first ProcessingInstruction node compared to
 * this second newly apended ProcessingInstruction node is PRECEDING, and FOLLOWING vice versa.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition17Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final ProcessingInstruction pi1;
        final ProcessingInstruction pi2;
        final int pi1Position;
        final int pi2Position;
        doc = sampleXmlFile("hc_staff.xml");
        pi1 = doc.createProcessingInstruction("PI1", "");
        pi2 = doc.createProcessingInstruction("PI2", "");
        doc.appendChild(pi1);
        doc.appendChild(pi2);
        pi1Position = pi1.compareDocumentPosition(pi2);
        assertEquals(4, pi1Position, "Nodecomparedocumentposition17Assert2");
        pi2Position = pi2.compareDocumentPosition(pi1);
        assertEquals(2, pi2Position, "Nodecomparedocumentposition17Assert3");
    }
}

