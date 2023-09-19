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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Retreive the first element node whose localName is "p".  Import it into a new
 * Document with deep=false.  Using isEqualNode check if the original and the imported
 * Element Node are not equal the child nodes are different.
 * Import with deep and the should still be unequal if
 * validating since the
 * new document does not provide the same default attributes.
 * Import it into another instance of the source document
 * and then the imported node and the source should be equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=529">http://www.w3.org/Bugs/Public/show_bug.cgi?id=529</a>
 */
public class nodeisequalnode11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        HTMLCollection employeeList;
        Document newDoc;
        Document dupDoc;
        Element elem1;
        Element elem2;
        Element elem3;
        Element elem4;
        boolean isEqual;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        employeeList = doc.getElementsByTagName("p");
        elem1 = (Element) employeeList.item(0);
        elem2 = (Element) newDoc.importNode(elem1, false);
        isEqual = elem1.isEqualNode(elem2);
        assertFalse("nodeisequalnodeFalse11", isEqual);
        elem3 = (Element) newDoc.importNode(elem1, true);
        isEqual = elem1.isEqualNode(elem3);

        assertFalse("deepImportNoDTD", isEqual);
        dupDoc = sampleXmlFile("hc_staff.xml");
        elem4 = (Element) dupDoc.importNode(elem1, true);
        isEqual = elem1.isEqualNode(elem4);
        assertTrue("deepImportSameDTD", isEqual);
    }
}

