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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Using removeChild on a new Element node attempt to remove a new ProcessingInstruction child
 * and verify the name of the returned node that was removed.  Now to remove the child
 * using removeChild on the parent and verify if a NOT_FOUND_ERR is thrown.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class Noderemovechild23Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Element parent;
        final ProcessingInstruction child;
        final ProcessingInstruction removed;
        final String removedName;
        doc = sampleXmlFile("hc_staff.xml");
        parent = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        child = doc.createProcessingInstruction("TARGET", "DATA");
        parent.appendChild(child);
        removed = (ProcessingInstruction) parent.removeChild(child);
        removedName = removed.getTarget();
        assertEquals("TARGET", removedName, "Noderemovechild23Assert3");

        {
            boolean success = false;
            try {
                parent.removeChild(child);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue(success, "Noderemovechild23Assert4");
        }
    }
}

