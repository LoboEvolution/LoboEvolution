
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

import static org.junit.Assert.assertTrue;


/**
 * The method hasAttributes returns whether this node (if it is an element) has any attributes.
 * Create a new Document, Element and Attr node.  Add the Attr to the Element and append the
 * Element to the Document.  Retreive the newly created element node from the document and check
 * if it has attributes using hasAttributes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeHasAttrs">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeHasAttrs</a>
 */
public class nodehasattributes04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DocumentType docType = null;

        DOMImplementation domImpl;
        Element element;
        Element elementTest;
        Element elementDoc;
        Attr attribute;
        Node setNode;
        Node appendedChild;
        HTMLCollection elementList;
        boolean hasAttributes;
        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "test", docType);
        element = newDoc.createElementNS("http://www.w3.org/DOM/Test", "dom:elem");
        attribute = newDoc.createAttribute("attr");
        setNode = element.setAttributeNode(attribute);
        elementDoc = newDoc.getDocumentElement();
        appendedChild = elementDoc.appendChild(element);
        elementList = newDoc.getElementsByTagNameNS("http://www.w3.org/DOM/Test", "elem");
        elementTest = (Element) elementList.item(0);
        hasAttributes = elementTest.hasAttributes();
        assertTrue("nodehasattributes04", hasAttributes);
    }
}

