/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces</a>
 */
public class documentnormalizedocument05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        HTMLCollection pList;
        Element newChild;
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
        newChild = doc.createElement("br");
        retval = (Element) elem.appendChild(newChild);
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

