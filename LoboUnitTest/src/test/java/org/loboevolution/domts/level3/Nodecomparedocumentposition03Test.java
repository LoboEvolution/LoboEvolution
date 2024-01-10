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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * Using compareDocumentPosition check if the document position of two Document nodes obtained from the
 * same xml document is disconnected, implementation specific, and that the order of these two documents
 * is reserved.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document docComp;
        final int documentPosition1;
        final int documentPosition2;
        final int documentPosition3;
        doc = sampleXmlFile("hc_staff.xml");
        docComp = sampleXmlFile("hc_staff.xml");
        documentPosition1 = doc.compareDocumentPosition(docComp);
        assertEquals(33 & 57, documentPosition1 & 57, "Nodecomparedocumentposition03Assert3");
        documentPosition2 = docComp.compareDocumentPosition(doc);
        assertNotEquals(documentPosition1 & 2, documentPosition2 & 2, "Nodecomparedocumentposition03Assert4");
        assertNotEquals(documentPosition1 & 4, documentPosition2 & 4, "Nodecomparedocumentposition03Assert5");
        assertEquals(33 & 57, documentPosition2 & 57, "Nodecomparedocumentposition03Assert6");
        documentPosition3 = doc.compareDocumentPosition(docComp);
        assertEquals(documentPosition1, documentPosition3, "Nodecomparedocumentposition03Assert7");
    }
}

