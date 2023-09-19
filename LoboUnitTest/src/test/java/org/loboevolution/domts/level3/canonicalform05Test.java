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
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Add a L1 element to a L2 namespace aware document and perform namespace normalization.  Should result
 * in an error.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform05Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        HTMLCollection pList;
        Element newChild;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();
        List errors;
        DOMError error;
        int errorCount = 0;
        int severity;
        Node problemNode;
        DOMLocator location;
        int lineNumber;
        int columnNumber;
        int byteOffset;
        int utf16Offset;
        String uri;
        String message;
        int length;
        boolean canSet;
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        newChild = doc.createElement("br");
        elem.appendChild(newChild);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            errors = errorMonitor.getErrors();
            for (Object o : errors) {
                error = (DOMError) o;
                severity = error.getSeverity();

                if (severity == 2) {
                    location = error.getLocation();
                    problemNode = location.getRelatedNode();
                    assertSame("relatedNodeIsL1Node", newChild, problemNode);
                    lineNumber = location.getLineNumber();
                    assertEquals("lineNumber", -1, lineNumber);
                    columnNumber = location.getColumnNumber();
                    assertEquals("columnNumber", -1, columnNumber);
                    byteOffset = location.getByteOffset();
                    assertEquals("byteOffset", -1, byteOffset);
                    utf16Offset = location.getUtf16Offset();
                    assertEquals("utf16Offset", -1, utf16Offset);
                    uri = location.getUri();
                    assertNull("uri", uri);
                    message = error.getMessage();
                    length = message.length();
                    assertTrue("messageNotEmpty", (length > 0));
                    errorCount += 1;
                } else {
                    assertEquals("anyOthersShouldBeWarnings", 1, severity);
                }

            }
            assertEquals("oneError", 1, errorCount);
        }
    }
}

