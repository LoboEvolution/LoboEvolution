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

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using insertBefore on a DocumentFragment node attempt to insert a new Element node
 * created by another Document, before this DocumentFragment's Element node and
 * verify if a WRONG_DOCUMENT_ERR is raised.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class Nodeinsertbefore13Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document docAlt;
        final DocumentFragment docFrag;
        final Element elemAlt;
        final Element elem;
        final Element docElem;
        final String rootNS;
        final String rootTagname;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootTagname = docElem.getTagName();
        docAlt = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS(rootNS, rootTagname);
        elemAlt = docAlt.createElementNS(rootNS, rootTagname);
        docFrag.appendChild(elem);

        {
            boolean success = false;
            try {
                docFrag.insertBefore(elemAlt, elem);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
            }
            assertTrue(success, "Nodeinsertbefore13Assert2");
        }
    }
}

