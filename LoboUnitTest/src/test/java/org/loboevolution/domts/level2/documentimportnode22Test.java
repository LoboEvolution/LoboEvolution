
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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true/false, import two notaiton nodes into the
 * same and different documnet objects.  In each case check if valid public and systemids
 * are returned if any and if none, check if a null value was returned.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode22Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        DocumentType docTypeNull = null;

        Document docImp;
        DOMImplementation domImpl;
        DocumentType docType;
        NamedNodeMap nodeMap;
        Notation notation1;
        Notation notation2;
        String publicId1;
        String publicId1Imp;
        String publicId1NewImp;
        String publicId2Imp;
        String systemId1Imp;
        String systemId1NewImp;
        String systemId2;
        String systemId2Imp;
        String systemId2NewImp;
        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        docType = doc.getDoctype();
        docImp = domImpl.createDocument("http://www.w3.org/DOM/Test", "a:b", docTypeNull);
        nodeMap = docType.getNotations();
        assertNotNull("notationsNotNull", nodeMap);
        notation1 = (Notation) nodeMap.getNamedItem("notation1");
        notation2 = (Notation) nodeMap.getNamedItem("notation2");
        doc.importNode(notation1, true);
        doc.importNode(notation2, false);
        docImp.importNode(notation1, false);
        docImp.importNode(notation2, true);
        publicId1 = notation1.getPublicId();
        publicId1Imp = notation1.getPublicId();
        publicId1NewImp = notation1.getPublicId();
        systemId1Imp = notation1.getSystemId();
        systemId1NewImp = notation1.getSystemId();
        publicId2Imp = notation2.getPublicId();
        systemId2 = notation2.getSystemId();
        systemId2Imp = notation2.getSystemId();
        systemId2NewImp = notation2.getSystemId();
        assertEquals("documentimportnode22_N1PID", publicId1, publicId1Imp);
        assertEquals("documentimportnode22_N1NPID", publicId1, publicId1NewImp);
        assertNull("documentimportnode22_N1SID", systemId1Imp);
        assertNull("documentimportnode22_N1NSID", systemId1NewImp);
        assertEquals("documentimportnode22_N2SID", systemId2, systemId2Imp);
        assertEquals("documentimportnode22_N2NSID", systemId2, systemId2NewImp);
        assertNull("documentimportnode22_N2PID", publicId2Imp);
        assertNull("documentimportnode22_N2NPID", publicId2Imp);
    }
}

