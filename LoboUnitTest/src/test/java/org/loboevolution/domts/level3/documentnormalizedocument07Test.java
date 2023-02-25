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
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Add a CDATASection containing "]]>" and perform normalization with split-cdata-sections=false.  Should result
 * in an error.
 *
 * @author Curt Arnold
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
public class documentnormalizedocument07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        HTMLCollection elemList;
        CDATASection newChild;
        Node oldChild;
        Node retval;
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
        elemList = doc.getElementsByTagName("p");
        elem = (Element) elemList.item(0);
        oldChild = elem.getFirstChild();
        newChild = doc.createCDATASection("this is not ]]> good");
        retval = elem.replaceChild(newChild, oldChild);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("split-cdata-sections", Boolean.FALSE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        errors = errorMonitor.getErrors();
        for (int indexN100E0 = 0; indexN100E0 < errors.size(); indexN100E0++) {
            error = errors.get(indexN100E0);
            severity = error.getSeverity();

            if (severity == 2) {
                location = error.getLocation();
                problemNode = location.getRelatedNode();
                assertSame("relatedNode", newChild, problemNode);
                lineNumber = location.getLineNumber();
                columnNumber = location.getColumnNumber();
                byteOffset = location.getByteOffset();
                utf16Offset = location.getUtf16Offset();
                uri = location.getUri();
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

