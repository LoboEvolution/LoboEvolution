/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Invoke the renameNode method on a new document node to rename a new attribute node
 * to one whose namespaceURI is http://www.w3.org/XML/1998/namespace and name is xml:dom.
 * Check if this attribute has been renamed successfully by verifying the
 * nodeName and namespaceURI attributes of the renamed node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class Documentrenamenode07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final Attr attr;
        final Node renamedNode;
        final String nodeName;
        final String namespaceURI;
        final DocumentType nullDocType = null;

        final Element docElem;
        final String rootNS;
        final String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        attr = newDoc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        renamedNode = newDoc.renameNode(attr, "http://www.w3.org/XML/1998/namespace", "xml:dom");
        nodeName = renamedNode.getNodeName();
        namespaceURI = renamedNode.getNamespaceURI();
        assertEquals("xml:dom", nodeName, "Documentrenamenode07Assert2");
        assertEquals("http://www.w3.org/XML/1998/namespace", namespaceURI, "Documentrenamenode07Assert3");
    }
}

