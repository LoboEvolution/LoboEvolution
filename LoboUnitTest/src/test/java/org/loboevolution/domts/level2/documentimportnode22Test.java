
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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
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
        Document doc;
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
        doc = sampleXmlFile("staffNS.xml");
        
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

