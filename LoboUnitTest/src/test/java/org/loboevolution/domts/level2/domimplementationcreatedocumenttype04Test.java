
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

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The method createDocumentType should raise a INVALID_CHARACTER_ERR if the qualifiedName
 * contains an illegal characters.
 * <p>
 * Invoke createDocument on this DOMImplementation with qualifiedNames having illegal characters.
 * Check if an INVALID_CHARACTER_ERR is raised in each case.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 */
public class domimplementationcreatedocumenttype04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        String publicId = "http://www.w3.org/DOM/Test/dom2.dtd";
        String systemId = "dom2.dtd";
        String qualifiedName;
        List<String> qualifiedNames = new ArrayList<>();
        qualifiedNames.add("{");
        qualifiedNames.add("}");
        qualifiedNames.add("'");
        qualifiedNames.add("~");
        qualifiedNames.add("`");
        qualifiedNames.add("@");
        qualifiedNames.add("#");
        qualifiedNames.add("$");
        qualifiedNames.add("%");
        qualifiedNames.add("^");
        qualifiedNames.add("&");
        qualifiedNames.add("*");
        qualifiedNames.add("(");
        qualifiedNames.add(")");

        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        for (int indexN10073 = 0; indexN10073 < qualifiedNames.size(); indexN10073++) {
            qualifiedName = qualifiedNames.get(indexN10073);

            {
                boolean success = false;
                try {
                    domImpl.createDocumentType(qualifiedName, publicId, systemId);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("domimplementationcreatedocumenttype04", success);
            }
        }
    }
}

