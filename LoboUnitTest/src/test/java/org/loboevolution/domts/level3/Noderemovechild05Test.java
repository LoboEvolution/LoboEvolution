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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Using removeChild on this Document node attempt to remove a new DocumentType node and
 * verify if the DocumentType node is null.  Attempting to remove the DocumentType
 * a second type should result in a NOT_FOUND_ERR.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=417">http://www.w3.org/Bugs/Public/show_bug.cgi?id=417</a>
 */
public class Noderemovechild05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final DocumentType removedDocType;
        final Node removedChild;
        doc = sampleXmlFile("barfoo.xml");
        docType = doc.getDoctype();

        try {
            removedChild = doc.removeChild(docType);

        } catch (final DOMException ex) {
            if (ex.getCode() == 9) {
                return;
            }
            throw ex;
        }
        assertNotNull(removedChild, "Noderemovechild05Assert1");
        removedDocType = doc.getDoctype();
        assertNull(removedDocType, "Noderemovechild05Assert2");

        {
            boolean success = false;
            try {
                docType.removeChild(doc);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue(success, "Noderemovechild05Assert3");
        }
    }
}

