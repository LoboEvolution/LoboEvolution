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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Using replaceChild on this Document node attempt to replace this DocumentType node with
 * a new DocumentType and verify the name of the replaced DocumentType node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild13Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DocumentType newDocType;
        DocumentType replaced;
        DOMImplementation domImpl;
        String nodeName;
        String nullPubId = null;

        String nullSysId = null;

        Element docElem;
        String docElemName;
        String docElemNS;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        docElemName = docElem.getTagName();
        docElemNS = docElem.getNamespaceURI();
        docType = doc.getDoctype();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(docElemName, nullPubId, nullSysId);

        try {
            replaced = (DocumentType) doc.replaceChild(newDocType, docType);

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 9:
                    return;
                default:
                    throw ex;
            }
        }
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild13", docElemName, nodeName);
    }
}

