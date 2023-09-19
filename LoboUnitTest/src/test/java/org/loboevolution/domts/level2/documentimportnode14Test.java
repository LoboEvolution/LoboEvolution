
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;


/**
 * Using the method importNode with deep=true, import the fourth employee element node of this
 * Document.  Verify if the node has been imported correctly by checking
 * if the default attribute present on this node has not been imported
 * and an explicit attribute has been imported.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=402">http://www.w3.org/Bugs/Public/show_bug.cgi?id=402</a>
 */
public class documentimportnode14Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        DocumentType nullDocType = null;

        HTMLCollection childList;
        Node imported;
        Node employeeElem;
        Attr attrNode;
        String attrValue;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        
        childList = doc.getElementsByTagNameNS("*", "employee");
        employeeElem = childList.item(3);
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(nullNS, "staff", nullDocType);
        imported = newDoc.importNode(employeeElem, true);
        attrNode = ((Element) imported).getAttributeNodeNS(nullNS, "defaultAttr");
        assertNull("defaultAttrNotImported", attrNode);
        attrValue = ((Element) imported).getAttributeNS("http://www.w3.org/2000/xmlns/", "emp");
        assertEquals("explicitAttrImported", "http://www.nist.gov", attrValue);
    }
}

