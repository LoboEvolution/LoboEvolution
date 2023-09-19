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
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Add a L1 attribute to a L2 namespace aware document and perform namespace normalization.  Should result
 * in an error.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces</a>
 */
public class documentnormalizedocument13Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        HTMLCollection pList;
        Attr newAttr;
        Element retval;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        List<DOMError> errors;

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
        String type;
        String message;
        Object relatedException;
        Object relatedData;
        int length;
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        elem.setAttribute("title", "DOM L1 Attribute");
        newAttr = elem.getAttributeNode("title");
        domConfig = doc.getDomConfig();
        domConfig.setParameter("namespaces", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        errors = errorMonitor.getErrors();
        for (int indexN100B6 = 0; indexN100B6 < errors.size(); indexN100B6++) {
            error = errors.get(indexN100B6);
            severity = error.getSeverity();

            if (severity == 2) {
                location = error.getLocation();
                problemNode = location.getRelatedNode();
                assertSame("relatedNodeIsL1Node", newAttr, problemNode);
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
                type = error.getType();
                relatedData = error.getRelatedData();
                relatedException = error.getRelatedException();
                errorCount += 1;
            } else {
                assertEquals("anyOthersShouldBeWarnings", 1, severity);
            }

        }
        assertEquals("oneError", 1, errorCount);
    }
}

