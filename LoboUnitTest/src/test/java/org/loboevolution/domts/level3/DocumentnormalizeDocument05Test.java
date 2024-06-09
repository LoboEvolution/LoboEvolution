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


import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;


/**
 * Add a L1 element to a L2 namespace aware document and perform namespace normalization.  Should result
 * in an error.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/namespaces-algorithms#normalizeDocumentAlgo</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces</a>
 */
public class DocumentnormalizeDocument05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element elem;
        final DOMConfiguration domConfig;
        final HTMLCollection pList;
        final Element newChild;
        final Element retval;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        final List<DOMError> errors;

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
        newChild = doc.createElement("br");
        elem.appendChild(newChild);
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
                assertSame(newChild, problemNode, "DocumentnormalizeDocument05Assert1");
                lineNumber = location.getLineNumber();
                assertEquals(-1, lineNumber, "DocumentnormalizeDocument05Assert2");
                columnNumber = location.getColumnNumber();
                assertEquals(-1, columnNumber, "DocumentnormalizeDocument05Assert3");
                byteOffset = location.getByteOffset();
                assertEquals(-1, byteOffset, "DocumentnormalizeDocument05Assert4");
                utf16Offset = location.getUtf16Offset();
                assertEquals(-1, utf16Offset, "DocumentnormalizeDocument05Assert5");
                uri = location.getUri();
                assertNull(uri, "DocumentnormalizeDocument05Assert6");
                message = error.getMessage();
                length = message.length();
                assertTrue((length > 0), "DocumentnormalizeDocument05Assert7");
                error.getType();
                error.getRelatedData();
                error.getRelatedException();
                errorCount += 1;
            } else {
                assertEquals(1, severity, "DocumentnormalizeDocument05Assert8");
            }

        }
        assertEquals(1, errorCount, "DocumentnormalizeDocument05Assert9");
    }
}

