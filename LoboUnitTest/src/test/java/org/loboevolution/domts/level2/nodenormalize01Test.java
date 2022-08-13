
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The method "normalize" puts all Text nodes in the full depth of the sub-tree underneath
 * this Node, including attribute nodes, into a "normal" form where only structure
 * (e.g., elements, comments, processing instructions, CDATA sections, and entity references)
 * separates Text nodes, i.e., there are neither adjacent Text nodes nor empty Text nodes.
 * <p>
 * Create a dom tree consisting of elements, comments, processing instructions, CDATA sections,
 * and entity references nodes seperated by text nodes.  Check the length of the node list of each
 * before and after normalize has been called.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize</a>
 */
public class nodenormalize01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        DocumentType docType;
        DocumentType docTypeNull = null;

        Element documentElement;
        Element element1;
        Element element2;
        Element element3;
        Element element4;
        Element element5;
        Element element6;
        Element element7;
        Text text1;
        Text text2;
        Text text3;
        ProcessingInstruction pi;
        CDATASection cData;
        Comment comment;
        EntityReference entRef;
        NodeList elementList;
        Node appendedChild;
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
        assertNotNull("createdEntRefNotNull", entRef);
        documentElement = newDoc.getDocumentElement();
        appendedChild = documentElement.appendChild(element1);
        appendedChild = element2.appendChild(text1);
        appendedChild = element2.appendChild(text2);
        appendedChild = element2.appendChild(text3);
        appendedChild = element1.appendChild(element2);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        appendedChild = element3.appendChild(entRef);
        appendedChild = element3.appendChild(text1);
        appendedChild = element3.appendChild(text2);
        appendedChild = element1.appendChild(element3);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        appendedChild = element4.appendChild(cData);
        appendedChild = element4.appendChild(text1);
        appendedChild = element4.appendChild(text2);
        appendedChild = element1.appendChild(element4);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        appendedChild = element5.appendChild(comment);
        appendedChild = element5.appendChild(text2);
        appendedChild = element5.appendChild(text3);
        appendedChild = element1.appendChild(element5);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        appendedChild = element6.appendChild(pi);
        appendedChild = element6.appendChild(text2);
        appendedChild = element6.appendChild(text3);
        appendedChild = element1.appendChild(element6);
        entRef = (EntityReference) entRef.cloneNode(false);
        text1 = (Text) text1.cloneNode(false);
        text2 = (Text) text2.cloneNode(false);
        text3 = (Text) text3.cloneNode(false);
        appendedChild = element7.appendChild(entRef);
        appendedChild = element7.appendChild(text1);
        appendedChild = element7.appendChild(text2);
        appendedChild = element7.appendChild(text3);
        appendedChild = element1.appendChild(element7);
        elementList = element1.getChildNodes();
        assertEquals( "nodeNormalize01_1Bef", 6, elementList.getLength());
        elementList = element2.getChildNodes();
        assertEquals( "nodeNormalize01_2Bef", 3, elementList.getLength());
        elementList = element3.getChildNodes();
        assertEquals( "nodeNormalize01_3Bef", 3, elementList.getLength());
        elementList = element4.getChildNodes();
        assertEquals( "nodeNormalize01_4Bef", 3, elementList.getLength());
        elementList = element5.getChildNodes();
        assertEquals( "nodeNormalize01_5Bef", 3, elementList.getLength());
        elementList = element6.getChildNodes();
        assertEquals( "nodeNormalize01_6Bef", 3, elementList.getLength());
        elementList = element7.getChildNodes();
        assertEquals( "nodeNormalize01_7Bef", 4, elementList.getLength());
        newDoc.normalize();
        elementList = element1.getChildNodes();
        assertEquals( "nodeNormalize01_1Aft", 6, elementList.getLength());
        elementList = element2.getChildNodes();
        assertEquals( "nodeNormalize01_2Aft", 1, elementList.getLength());
        elementList = element3.getChildNodes();
        assertEquals( "nodeNormalize01_3Aft", 2, elementList.getLength());
        elementList = element4.getChildNodes();
        assertEquals( "nodeNormalize01_4Aft", 2, elementList.getLength());
        elementList = element5.getChildNodes();
        assertEquals( "nodeNormalize01_5Aft", 2, elementList.getLength());
        elementList = element6.getChildNodes();
        assertEquals( "nodeNormalize01_6Aft", 2, elementList.getLength());
        elementList = element7.getChildNodes();
        assertEquals( "nodeNormalize01_7Aft", 2, elementList.getLength());
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodenormalize01";
    }
}

