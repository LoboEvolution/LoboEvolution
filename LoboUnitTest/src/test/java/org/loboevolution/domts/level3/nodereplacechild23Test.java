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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using replaceChild on a new EntityReference node attempt to replace an Element, Text,
 * Comment, ProcessingInstruction and CDATASection nodes with each other and in each case
 * verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild23Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final EntityReference entRef;
        final Text txt;
        final Element elem;
        final Comment comment;
        final ProcessingInstruction pi;
        final CDATASection cdata;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        entRef = doc.createEntityReference("delta");
        txt = doc.createTextNode("Text");
        comment = doc.createComment("Comment");
        cdata = doc.createCDATASection("CDATASection");
        pi = doc.createProcessingInstruction("target", "data");
        elem.appendChild(entRef);
        elem.appendChild(txt);
        elem.appendChild(comment);
        elem.appendChild(pi);
        elem.appendChild(cdata);

        {
            boolean success = false;
            try {
                entRef.replaceChild(cdata, elem);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success);
        }

        {
            boolean success = false;
            try {
                entRef.replaceChild(pi, cdata);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success);
        }

        {
            boolean success = false;
            try {
                entRef.replaceChild(comment, pi);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success);
        }

        {
            boolean success = false;
            try {
                entRef.replaceChild(txt, comment);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success);
        }

        {
            boolean success = false;
            try {
                entRef.replaceChild(elem, txt);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success);
        }
    }
}