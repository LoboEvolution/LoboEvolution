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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using isEqualNode check if 2 new DocumentType having null public and system ids
 * are equal.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class Nodeisequalnode22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc1;
        final Document doc2;
        final DOMImplementation domImpl1;
        final DOMImplementation domImpl2;
        final DocumentType docType1;
        final DocumentType docType2;
        final boolean isEqual;

        final DocumentType oldDocType;
        final String rootName;
        doc1 = sampleXmlFile("barfoo.xml");
        oldDocType = doc1.getDoctype();
        rootName = oldDocType.getName();
        doc2 = sampleXmlFile("barfoo.xml");
        domImpl1 = doc1.getImplementation();
        domImpl2 = doc2.getImplementation();
        docType1 = domImpl1.createDocumentType(rootName, null, null);
        docType2 = domImpl2.createDocumentType(rootName, null, null);
        isEqual = docType1.isEqualNode(docType2);
        assertTrue(isEqual, "Nodeisequalnode22Assert2");
    }
}

