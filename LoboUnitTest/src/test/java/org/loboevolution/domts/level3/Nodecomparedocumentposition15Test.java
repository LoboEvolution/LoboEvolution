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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Using compareDocumentPosition check if the Element node precedes and contains its Attr child, and that the Attr child
 * is contained and follows the Element node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment docFrag;
        final Element docElem;
        final Attr attr;
        final Node docFragChild;
        final int attrPosition;
        final int docFragChildPosition;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        docFrag = doc.createDocumentFragment();
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        docElem.setAttributeNodeNS(attr);
        docFrag.appendChild(docElem);
        docFragChild = docFrag.getFirstChild();
        docFragChildPosition = docFragChild.compareDocumentPosition(attr);
        assertEquals(20, docFragChildPosition, "Nodecomparedocumentposition15Assert2");
        attrPosition = attr.compareDocumentPosition(docFragChild);
        assertEquals(10, attrPosition, "Nodecomparedocumentposition15Assert3");
    }
}

