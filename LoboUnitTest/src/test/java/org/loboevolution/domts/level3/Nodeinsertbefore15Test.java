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
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * A NO_MODIFICATION_ALLOWED_ERR is raised if the node is read-only.
 * <p>
 * Using insertBefore on a new EntityReference node attempt to insert Element, Text,
 * Comment, ProcessingInstruction and CDATASection nodes before an element child
 * and verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class Nodeinsertbefore15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final EntityReference entRef;
        final Node elemChild;
        final Text txt;
        final Element elem;
        final Comment comment;
        final ProcessingInstruction pi;
        final CDATASection cdata;
        doc = sampleXmlFile("hc_staff.xml");
        entRef = doc.createEntityReference("delta");
        elemChild = entRef.getFirstChild();
        cdata = doc.createCDATASection("CDATASection");

        {
            boolean success = false;
            try {
                entRef.insertBefore(cdata, elemChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success, "Nodeinsertbefore15Assert2");
        }
        pi = doc.createProcessingInstruction("target", "data");

        {
            boolean success = false;
            try {
                entRef.insertBefore(pi, elemChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success, "Nodeinsertbefore15Assert3");
        }
        comment = doc.createComment("Comment");

        {
            boolean success = false;
            try {
                entRef.insertBefore(comment, elemChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success, "Nodeinsertbefore15Assert4");
        }
        txt = doc.createTextNode("Text");

        {
            boolean success = false;
            try {
                entRef.insertBefore(txt, elemChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success, "Nodeinsertbefore15Assert5");
        }
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "body");

        {
            boolean success = false;
            try {
                entRef.insertBefore(elem, elemChild);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue(success, "Nodeinsertbefore15Assert6");
        }
    }
}

