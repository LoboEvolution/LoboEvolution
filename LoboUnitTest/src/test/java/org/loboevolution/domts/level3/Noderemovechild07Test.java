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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Attempts to remove a notation from a Document node.  Since notations are children of
 * DocumentType, not Document the operation should fail with a NOT_FOUND_ERR.  Attempting
 * to remove Document from a Notation should also fail either with a NOT_FOUND_ERR
 * or a NO_MODIFICATION_ALLOWED_ERR.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=418">http://www.w3.org/Bugs/Public/show_bug.cgi?id=418</a>
 */
public class Noderemovechild07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap notations;
        final Notation notation;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        notations = docType.getNotations();
        notation = (Notation) notations.getNamedItem("notation1");

        {
            boolean success = false;
            try {
                doc.removeChild(notation);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue(success, "Noderemovechild07Assert2");
        }

        try {
            notation.removeChild(doc);

        } catch (final DOMException ex) {
            switch (ex.getCode()) {
                case 8, 7:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

