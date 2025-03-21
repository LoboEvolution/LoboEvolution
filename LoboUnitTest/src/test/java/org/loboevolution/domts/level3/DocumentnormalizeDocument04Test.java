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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Append a Comment node and normalize with "comments" set to false.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=416">http://www.w3.org/Bugs/Public/show_bug.cgi?id=416</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-comments">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-comments</a>
 */
public class DocumentnormalizeDocument04Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        Element elem;
        final Comment newComment;
        Node lastChild;
        String nodeName;
        final DOMConfiguration domConfig;
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        HTMLCollection pList;
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        newComment = doc.createComment("COMMENT_NODE");
        elem.appendChild(newComment);
        domConfig = doc.getDomConfig();
        domConfig.setParameter("comments", Boolean.TRUE);
        /*DOMErrorMonitor */
        domConfig.setParameter("error-handler", errorMonitor);
        doc.normalizeDocument();
        assertTrue(errorMonitor.assertLowerSeverity(2), "DocumentnormalizeDocument04Assert3");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        lastChild = elem.getLastChild();
        nodeName = lastChild.getNodeName();
        assertEquals("#comment", nodeName, "DocumentnormalizeDocument04Assert4");
        domConfig.setParameter("comments", Boolean.FALSE);
        doc.normalizeDocument();
        assertTrue(errorMonitor.assertLowerSeverity(2), "DocumentnormalizeDocument04Assert5");
        pList = doc.getElementsByTagName("p");
        elem = (Element) pList.item(0);
        lastChild = elem.getLastChild();
        nodeName = lastChild.getNodeName();
        assertEquals("#text", nodeName, "DocumentnormalizeDocument04Assert6");
    }
}

