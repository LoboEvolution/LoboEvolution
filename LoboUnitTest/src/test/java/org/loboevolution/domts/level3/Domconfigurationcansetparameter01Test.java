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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The parameter commments is turned on by default.  Check to see if this feature can be set
 * to false by invoking canSetParameter method.  Also check that this method does not change the
 * value of parameter.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-canSetParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-canSetParameter</a>
 */
public class Domconfigurationcansetparameter01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DOMConfiguration domConfig;
        final boolean canSet;
        final Comment newCommentNode;
        final Element docElem;
        final Node lastChild;
        final String commentValue;
        doc = sampleXmlFile("hc_staff.xml");
        newCommentNode = doc.createComment("This is a new Comment node");
        docElem = doc.getDocumentElement();
        docElem.appendChild(newCommentNode);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("comments", Boolean.FALSE);
        assertTrue(canSet, "Domconfigurationcansetparameter01Assert3");
        doc.normalizeDocument();
        lastChild = docElem.getLastChild();
        commentValue = lastChild.getNodeValue();
        assertEquals("This is a new Comment node", commentValue, "Domconfigurationcansetparameter01Assert4");
    }
}

