
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * The createDocument method with valid arguments, should create a DOM Document of
 * the specified type.
 * <p>
 * Call the createDocument on this DOMImplementation with
 * createDocument ("<a href="http://www.w3.org/DOMTest/L2">...</a>",see the array below for valid QNames,null).
 * Check if the returned Document object is empty with no Document Element.
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class Domimplementationcreatedocument03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        Document newDoc;

        final String namespaceURI = "http://www.w3.org/DOMTest/L2";
        String qualifiedName;
        final List<String> qualifiedNames = new ArrayList<>();
        qualifiedNames.add("_:_");
        qualifiedNames.add("_:h0");
        qualifiedNames.add("_:test");
        qualifiedNames.add("l_:_");
        qualifiedNames.add("ns:_0");
        qualifiedNames.add("ns:a0");
        qualifiedNames.add("ns0:test");
        qualifiedNames.add("a.b:c");
        qualifiedNames.add("a-b:c");
        qualifiedNames.add("a-b:c");

        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        for (final String name : qualifiedNames) {
            qualifiedName = name;
            newDoc = domImpl.createDocument(namespaceURI, qualifiedName, null);
            assertNotNull(newDoc);
        }
    }
}

