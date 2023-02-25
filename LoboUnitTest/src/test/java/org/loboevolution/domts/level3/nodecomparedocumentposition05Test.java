/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Using compareDocumentPosition check if the document position of a Document and a new Document node
 * are disconnected, implementation-specific and preceding/following.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        int documentPosition1;
        int documentPosition2;
        int documentPosition3;
        DocumentType nullDocType = null;

        String rootName;
        String rootNS;
        Element docElem;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        documentPosition1 = doc.compareDocumentPosition(newDoc);
        assertEquals("isImplSpecificDisconnected1", 33 & 57, documentPosition1 & 57);
        documentPosition2 = newDoc.compareDocumentPosition(doc);
        assertEquals("isImplSpecificDisconnected2", 33 & 57, documentPosition2 & 57);
        assertNotEquals("notBothPreceding", documentPosition1 & 2, documentPosition2 & 2);
        assertNotEquals("notBothFollowing", documentPosition1 & 4, documentPosition2 & 4);
        documentPosition3 = doc.compareDocumentPosition(newDoc);
        assertEquals("isConsistent", documentPosition1, documentPosition3);
    }
}

