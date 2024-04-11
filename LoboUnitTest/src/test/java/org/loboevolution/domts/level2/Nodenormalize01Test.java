
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * The method "normalize" puts all Text nodes in the full depth of the sub-tree underneath
 * this Node, including attribute nodes, into a "normal" form where only structure
 * (e.g., elements, comments, processing instructions, CDATA sections, and entity references)
 * separates Text nodes, i.e., there are neither adjacent Text nodes nor empty Text nodes.
 * <p>
 * Create a dom tree consisting of elements, comments, processing instructions, CDATA sections,
 * and entity references nodes seperated by text nodes.  Check the length of the node list of each
 * before and after normalize has been called.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize</a>
 */
public class Nodenormalize01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document newDoc;
        final DOMImplementation domImpl;
        final DocumentType docTypeNull = null;

        final Element documentElement;
        final Element element1;
        final Element element2;
        final Element element3;
        final Element element4;
        final Element element5;
        final Element element6;
        final Element element7;
        Text text1;
        Text text2;
        Text text3;
        final ProcessingInstruction pi;
        final CDATASection cData;
        final Comment comment;
        EntityReference entRef;
        NodeList elementList;
        doc = sampleXmlFile("staffNS.xml");

        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom:root", docTypeNull);
        element1 = newDoc.createElement("element1");
        element2 = newDoc.createElement("element2");
        element3 = newDoc.createElement("element3");
        element4 = newDoc.createElement("element4");
        element5 = newDoc.createElement("element5");
        element6 = newDoc.createElement("element6");
        element7 = newDoc.createElement("element7");
        text1 = newDoc.createTextNode("text1");
        text2 = newDoc.createTextNode("text2");
        text3 = newDoc.createTextNode("text3");
        cData = newDoc.createCDATASection("Cdata");
        comment = newDoc.createComment("comment");
        pi = newDoc.createProcessingInstruction("PITarget", "PIData");
        entRef = newDoc.createEntityReference("EntRef");
        assertNotNull(entRef);
        documentElement = newDoc.getDocumentElement();
        documentElement.appendChild(element1);
        element2.appendChild(text1);
        element2.appendChild(text2);
        element2.appendChild(text3);
        element1.appendChild(element2);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        element3.appendChild(entRef);
        element3.appendChild(text1);
        element3.appendChild(text2);
        element1.appendChild(element3);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        element4.appendChild(cData);
        element4.appendChild(text1);
        element4.appendChild(text2);
        element1.appendChild(element4);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        element5.appendChild(comment);
        element5.appendChild(text2);
        element5.appendChild(text3);
        element1.appendChild(element5);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        element6.appendChild(pi);
        element6.appendChild(text2);
        element6.appendChild(text3);
        element1.appendChild(element6);
        entRef = (EntityReference) entRef.cloneNode(false);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        element7.appendChild(entRef);
        element7.appendChild(text1);
        element7.appendChild(text2);
        element7.appendChild(text3);
        element1.appendChild(element7);
        elementList = element1.getChildNodes();
        assertEquals(6, elementList.getLength());
        elementList = element2.getChildNodes();
        assertEquals(3, elementList.getLength());
        elementList = element3.getChildNodes();
        assertEquals(3, elementList.getLength());
        elementList = element4.getChildNodes();
        assertEquals(3, elementList.getLength());
        elementList = element5.getChildNodes();
        assertEquals(3, elementList.getLength());
        elementList = element6.getChildNodes();
        assertEquals(3, elementList.getLength());
        elementList = element7.getChildNodes();
        assertEquals(4, elementList.getLength());
        newDoc.normalize();
        elementList = element1.getChildNodes();
        assertEquals(6, elementList.getLength());
        elementList = element2.getChildNodes();
        assertEquals(1, elementList.getLength());
        elementList = element3.getChildNodes();
        assertEquals(2, elementList.getLength());
        elementList = element4.getChildNodes();
        assertEquals(2, elementList.getLength());
        elementList = element5.getChildNodes();
        assertEquals(2, elementList.getLength());
        elementList = element6.getChildNodes();
        assertEquals(2, elementList.getLength());
        elementList = element7.getChildNodes();
        assertEquals(2, elementList.getLength());
    }
}

