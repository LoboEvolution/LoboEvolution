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
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Retreive the doctype node, create a new Doctype node, call replaceChild and try replacing the
 * docType node with a new docType node.  Check if the docType node was correctly replaced with
 * the new one.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-B63ED1A31">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-B63ED1A31</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class Documentgetdoctype01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final DocumentType newDocType;
        final DocumentType replacedDocType;
        final DOMImplementation domImpl;
        final String newSysID;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        rootName = docType.getName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, null, null);
        try {
            doc.replaceChild(newDocType, docType);
        } catch (final DOMException ex) {
            if (ex.getCode() == 9) {
                return;
            }
            throw ex;
        }
        replacedDocType = doc.getDoctype();
        newSysID = replacedDocType.getSystemId();
        assertNull(newSysID, "Documentgetdoctype01Assert3");
    }
}

