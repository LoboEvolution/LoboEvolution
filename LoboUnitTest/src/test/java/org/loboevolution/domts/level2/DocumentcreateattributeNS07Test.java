
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
 * The method createAttributeNS raises a NAMESPACE_ERR if the qualifiedName is xmlns and
 * the namespaceURI is different from <a href="http://www.w3.org/2000/xmlns">...</a>
 * <p>
 * Invoke the createAttributeNS method on this DOMImplementation object with
 * the qualifiedName as xmlns and namespaceURI as <a href="http://www.W3.org/2000/xmlns">...</a>.
 * Check if the NAMESPACE_ERR exception is thrown.
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class DocumentcreateattributeNS07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final String namespaceURI = "http://www.W3.org/2000/xmlns";
        final String qualifiedName = "xmlns";
        doc = sampleXmlFile("staffNS.xml");

        {
            boolean success = false;
            try {
                doc.createAttributeNS(namespaceURI, qualifiedName);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue(success);
        }
    }
}

