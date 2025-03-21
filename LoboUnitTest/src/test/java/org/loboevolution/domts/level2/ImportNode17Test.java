
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

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should raise NOT_SUPPORTED_ERR DOMException if
 * the type of node being imported is Document.
 * <p>
 * Retrieve staff.xml document.
 * Invoke method importNode(importedNode,deep) where importedNode
 * contains staff.xml and deep is true.
 * Method should raise NOT_SUPPORTED_ERR DOMException.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NOT_SUPPORTED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NOT_SUPPORTED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Core-Document-importNode')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NOT_SUPPORTED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Core-Document-importNode')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NOT_SUPPORTED_ERR'])</a>
 */
public class ImportNode17Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document anotherDoc;
        doc = sampleXmlFile("staffNS.xml");
        anotherDoc = sampleXmlFile("staffNS.xml");

        {
            boolean success = false;
            try {
                doc.importNode(anotherDoc, false);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue(success);
        }
    }
}

