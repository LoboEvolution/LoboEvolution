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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Invoke the renameNode method to attempt to rename new Text, Comment, CDataSection,
 * ProcessingInstruction and EntityReference nodes of a new Document.
 * Check if a NOT_SUPPORTED_ERR is thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class Documentrenamenode27Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Text text;
        final Comment comment;
        final CDATASection cdata;
        final ProcessingInstruction pi;
        final EntityReference entref;
        final Element docElem;
        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, null);
        text = newDoc.createTextNode("text");
        comment = newDoc.createComment("comment");
        cdata = newDoc.createCDATASection("cdata");
        pi = newDoc.createProcessingInstruction("pit", "pid");
        entref = newDoc.createEntityReference("alpha");

        {
            boolean success = false;
            try {
                newDoc.renameNode(text, "http://www.w3.org/DOM/Test", "text");
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue(success, "Documentrenamenode27Assert2");
        }

        {
            boolean success = false;
            try {
                newDoc.renameNode(comment, "http://www.w3.org/DOM/Test", "comment");
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue(success, "Documentrenamenode27Assert3");
        }

        {
            boolean success = false;
            try {
                newDoc.renameNode(cdata, "http://www.w3.org/DOM/Test", "cdata");
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue(success, "Documentrenamenode27Assert4");
        }


        boolean success = false;
        try {
            newDoc.renameNode(pi, "http://www.w3.org/DOM/Test", "pi");
        } catch (final DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue(success, "Documentrenamenode27Assert5");


        success = false;
        try {
            newDoc.renameNode(entref, "http://www.w3.org/DOM/Test", "entref");
        } catch (final DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue(success, "Documentrenamenode27Assert6");

    }

}

