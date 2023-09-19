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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The method renameNode renames an existing node and raises a NAMESPACE_ERR
 * if the qualifiedName is malformed per the Namespaces in XML specification.
 * <p>
 * Invoke the renameNode method on a new document node to rename a node to nodes
 * with malformed qualifiedNames.
 * Check if a NAMESPACE_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode19Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Element element;
        DocumentType nullDocType = null;

        List<String> qualifiedNames = new ArrayList<>();
        qualifiedNames.add("a_:");
        qualifiedNames.add("_:");
        qualifiedNames.add(":");
        qualifiedNames.add("::0;");
        qualifiedNames.add("a:-:c");

        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        domImpl.createDocument("http://www.w3.org/DOM/Test", "newD", nullDocType);
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "test");
        for (String qualifiedName : qualifiedNames) {
            boolean success = false;
            try {
                doc.renameNode(element, "http://www.w3.org/2000/XMLNS", qualifiedName);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue("documentrenamenode19_NAMESPACE_ERR", success);

        }
    }
}

