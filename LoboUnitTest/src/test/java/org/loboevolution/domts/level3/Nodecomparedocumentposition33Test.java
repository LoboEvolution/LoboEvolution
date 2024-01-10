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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * Create a new Element node, add a new atttribute node to it.  Compare the position
 * of the Element and the Document.  This should return disconnected, implementation specific, and that
 * the order of these two nodes is preserved.
 * Also compare the position of the Element node with respect to the Attr node and this should
 * be PRECEDING and contains, and the Attr node follows and is contained by the Element node
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition33Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element elem;
        final Attr attr;
        final int position1;
        final int position2;
        final int position3;
        final int position4;
        final int position5;
        final Attr replacedAttr;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "br");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        replacedAttr = elem.setAttributeNodeNS(attr);
        position4 = elem.compareDocumentPosition(attr);
        assertEquals(20, position4, "Nodecomparedocumentposition33Assert3");
        position5 = attr.compareDocumentPosition(elem);
        assertEquals(10, position5, "Nodecomparedocumentposition33Assert4");
        position1 = doc.compareDocumentPosition(elem);
        assertEquals(33 & 57, position1 & 57, "Nodecomparedocumentposition33Assert5");
        position2 = elem.compareDocumentPosition(doc);
        assertNotEquals(position1 & 2, position2 & 2, "Nodecomparedocumentposition33Assert6");
        assertNotEquals(position1 & 4, position2 & 4, "Nodecomparedocumentposition33Assert7");
        assertEquals(33 & 57, position2 & 57, "Nodecomparedocumentposition33Assert8");
        position3 = doc.compareDocumentPosition(elem);
        assertEquals(position1, position3, "Nodecomparedocumentposition33Assert9");
    }
}

