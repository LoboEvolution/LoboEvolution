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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Invoke the renameNode method to attempt to rename new Text, Comment, CDataSection,
 * ProcessingInstruction and EntityReference nodes of a new Document.
 * Check if a NOT_SUPPORTED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode27Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Text text;
        Comment comment;
        CDATASection cdata;
        ProcessingInstruction pi;
        EntityReference entref;
        Node renamedTxt;
        Node renamedComment;
        Node renamedCdata;
        Node renamedPi;
        Node renamedEntRef;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        text = newDoc.createTextNode("text");
        comment = newDoc.createComment("comment");
        cdata = newDoc.createCDATASection("cdata");
        pi = newDoc.createProcessingInstruction("pit", "pid");
        entref = newDoc.createEntityReference("alpha");

        {
            boolean success = false;
            try {
                renamedTxt = newDoc.renameNode(text, "http://www.w3.org/DOM/Test", "text");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue("throw_NOT_SUPPORTED_ERR_1", success);
        }

        {
            boolean success = false;
            try {
                renamedComment = newDoc.renameNode(comment, "http://www.w3.org/DOM/Test", "comment");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue("throw_NOT_SUPPORTED_ERR_2", success);
        }

        {
            boolean success = false;
            try {
                renamedCdata = newDoc.renameNode(cdata, "http://www.w3.org/DOM/Test", "cdata");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue("throw_NOT_SUPPORTED_ERR_3", success);
        }


        boolean success = false;
        try {
            renamedPi = newDoc.renameNode(pi, "http://www.w3.org/DOM/Test", "pi");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("throw_NOT_SUPPORTED_ERR_4", success);


        success = false;
        try {
            renamedEntRef = newDoc.renameNode(entref, "http://www.w3.org/DOM/Test", "entref");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("throw_NOT_SUPPORTED_ERR_5", success);

    }

}

