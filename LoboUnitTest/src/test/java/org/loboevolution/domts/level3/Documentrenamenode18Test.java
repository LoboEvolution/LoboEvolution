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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Invoke the renameNode method on this document and try to rename a new element
 * node of a new document.
 * Check if a WRONG_DOCUMENT_ERR gets thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class Documentrenamenode18Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Element element;
        final Node renamedNode;
        final Element docElem;
        final String rootNS;
        final String rootTagname;
        final DocumentType nullDocType = null;

        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootTagname, nullDocType);
        element = newDoc.createElementNS("http://www.w3.org/1999/xhtml", "body");

        {
            boolean success = false;
            try {
                renamedNode = doc.renameNode(element, "http://www.w3.org/1999/xhtml", "head");
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
            }
            assertTrue(success, "Documentrenamenode18Assert2");
        }
    }
}

