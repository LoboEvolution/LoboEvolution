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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;

/**
 * The method setTextContent has no effect when the node is defined to be null.
 * <p>
 * Using setTextContent on a new Document node, attempt to set the textContent of this
 * new Document node to textContent.  Check if it was not set by checking the nodeName
 * attribute of a new Element of this Document node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Document newDoc;
        String nodeName;
        Element elemChild;
        Element newElem;
        HTMLCollection elemList;
        DocumentType nullDocType = null;

        Node appendedChild;
        Element documentElem;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom3:elem", nullDocType);
        newElem = newDoc.createElementNS("http://www.w3.org/DOM/Test", "dom3:childElem");
        documentElem = newDoc.getDocumentElement();
        appendedChild = documentElem.appendChild(newElem);
        newDoc.setTextContent("textContent");
        elemList = newDoc.getElementsByTagNameNS("*", "childElem");
        elemChild = (Element) elemList.item(0);
        nodeName = elemChild.getNodeName();
        assertEquals("nodesettextcontent02", "dom3:childElem", nodeName);
    }
}

