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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Add a CDATASection containing "]]>" perform normalization with split-cdata-sections=true.  Should result
 * in an warning.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-split-cdata-sections</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-severity">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-severity</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-message">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-message</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-type">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-type</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-relatedException">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-relatedException</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-relatedData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-relatedData</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-location">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ERROR-DOMError-location</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-line-number">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-line-number</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-column-number">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-column-number</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-byteOffset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-byteOffset</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-utf16Offset">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-utf16Offset</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-node">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-node</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-uri">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMLocator-uri</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=542">http://www.w3.org/Bugs/Public/show_bug.cgi?id=542</a>
 */
public class DocumentnormalizeDocument06Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final Element elem;
        final DOMConfiguration domConfig;
        final HTMLCollection elemList;
        CDATASection newChild;
        final Node oldChild;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();
        final List<DOMError> errors;
        int splittedCount = 0;
        int severity;
        Node problemNode;
        DOMLocator location;
        String type;
        String message;
        Object relatedData;
        int length;
        final int nodeType;
        final String nodeValue;
        doc = sampleXmlFile("barfoo.xml");
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        newChild = doc.createCDATASection("this is not ]]> good");
        oldChild = elem.getFirstChild();
        elem.replaceChild(newChild, oldChild);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("split-cdata-sections", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        newChild = (CDATASection) elem.getFirstChild();
        nodeValue = newChild.getNodeValue();
        nodeType = newChild.getNodeType();
        assertFalse((nodeType == 4 & (nodeValue.contains("]]>"))), "DocumentnormalizeDocument06Assert1");
        errors = errorMonitor.getErrors();
        for (final DOMError error : errors) {
            type = error.getType();
            severity = error.getSeverity();

            if ("cdata-sections-splitted".equals(type)) {
                relatedData = error.getRelatedData();
                assertSame(newChild, relatedData, "DocumentnormalizeDocument06Assert2");
                assertEquals(1, severity, "DocumentnormalizeDocument06Assert3");
                message = error.getMessage();
                length = message.length();
                assertTrue((length > 0), "DocumentnormalizeDocument06Assert4");
                error.getRelatedException();
                location = error.getLocation();
                problemNode = location.getRelatedNode();
                assertSame(newChild, problemNode, "DocumentnormalizeDocument06Assert5");
                location.getLineNumber();
                location.getColumnNumber();
                location.getByteOffset();
                location.getUtf16Offset();
                location.getUri();
                splittedCount += 1;
            } else {
                assertEquals(1, severity, "DocumentnormalizeDocument06Assert6");
            }
        }
        assertEquals(1, splittedCount, "DocumentnormalizeDocument06Assert7");
    }
}

