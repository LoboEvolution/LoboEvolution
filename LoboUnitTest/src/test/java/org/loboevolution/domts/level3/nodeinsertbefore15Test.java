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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * A NO_MODIFICATION_ALLOWED_ERR is raised if the node is read-only.
 * <p>
 * Using insertBefore on a new EntityReference node attempt to insert Element, Text,
 * Comment, ProcessingInstruction and CDATASection nodes before an element child
 * and verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRef;
        Node elemChild;
        Text txt;
        Element elem;
        Comment comment;
        ProcessingInstruction pi;
        CDATASection cdata;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        entRef = doc.createEntityReference("delta");
        elemChild = entRef.getFirstChild();
        cdata = doc.createCDATASection("CDATASection");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(cdata, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_1", success);
        }
        pi = doc.createProcessingInstruction("target", "data");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(pi, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_2", success);
        }
        comment = doc.createComment("Comment");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(comment, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_3", success);
        }
        txt = doc.createTextNode("Text");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(txt, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_4", success);
        }
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "body");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(elem, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_5", success);
        }
    }
}

