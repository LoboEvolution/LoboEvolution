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
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Using compareDocumentPosition check if the document position of two Document nodes obtained from the
 * same xml document is disconnected, implementation specific, and that the order of these two documents
 * is reserved.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document docComp;
        int documentPosition1;
        int documentPosition2;
        int documentPosition3;
        doc = sampleXmlFile("hc_staff.xml");
        docComp = sampleXmlFile("hc_staff.xml");
        documentPosition1 = doc.compareDocumentPosition(docComp);
        assertEquals("isImplSpecificDisconnected1", 33 & 57, documentPosition1 & 57);
        documentPosition2 = docComp.compareDocumentPosition(doc);
        assertNotEquals("notBothPreceding", documentPosition1 & 2, documentPosition2 & 2);
        assertNotEquals("notBothFollowing", documentPosition1 & 4, documentPosition2 & 4);
        assertEquals("isImplSpecificDisconnected2", 33 & 57, documentPosition2 & 57);
        documentPosition3 = doc.compareDocumentPosition(docComp);
        assertEquals("isConsistent", documentPosition1, documentPosition3);
    }
}

