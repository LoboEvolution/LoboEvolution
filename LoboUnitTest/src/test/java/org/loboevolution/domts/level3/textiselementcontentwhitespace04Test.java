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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertFalse;


/**
 * Replace the text node child of the "p" element in barfoo with whitespace and normalize with validation.
 * isElementContentWhitespace should be false since the node is not in element content.
 *
 * @author Curt Arnold
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-isElementContentWhitespace</a>
 */
public class textiselementcontentwhitespace04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection pList;
        Element pElem;
        Text textNode;
        Text blankNode;
        Node returnedNode;
        boolean isElemContentWhitespace;
        DOMConfiguration domConfig;
        boolean canSetValidation;
        Node replacedNode;
        doc = sampleXmlFile("barfoo.xml");
        domConfig = doc.getDomConfig();
        canSetValidation = domConfig.canSetParameter("validate", Boolean.TRUE);

        if (canSetValidation) {
            domConfig.setParameter("validate", Boolean.TRUE);
            pList = doc.getElementsByTagName("p");
            pElem = (Element) pList.item(0);
            textNode = (Text) pElem.getFirstChild();
            blankNode = doc.createTextNode("   ");
            replacedNode = pElem.replaceChild(blankNode, textNode);
            doc.normalizeDocument();
            textNode = (Text) pElem.getFirstChild();
            isElemContentWhitespace = textNode.isElementContentWhitespace();
            assertFalse("notElemContent", isElemContentWhitespace);
        }
    }
}

