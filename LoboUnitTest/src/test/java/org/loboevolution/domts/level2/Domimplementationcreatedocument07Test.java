
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
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The createDocument method should raise a NAMESPACE_ERR if the qualifiedName is malformed
 * <p>
 * Invoke the createDocument method on this DOMImplementation object with null values
 * for namespaceURI and docType and a malformed qualifiedName.
 * The NAMESPACE_ERR should be raised.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class Domimplementationcreatedocument07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        final String namespaceURI = "http://www.w3.org/DOMTest/level2";

        doc = sampleXmlFile("staffNS.xml");

        domImpl = doc.getImplementation();

        {
            boolean success = false;
            try {
                domImpl.createDocument(namespaceURI, ":", null);
            } catch (final DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue(success);
        }
    }
}

