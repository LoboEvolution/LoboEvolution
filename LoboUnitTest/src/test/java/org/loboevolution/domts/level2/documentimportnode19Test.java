
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true/false, import a entity nodes ent2 and ent6
 * from this document to a new document object.  Verify if the nodes have been
 * imported correctly by checking the nodeNames of the imported nodes and public and system ids.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode19Test extends LoboUnitTest {

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
        EntityReference entity2;
        EntityReference entity6;
        EntityReference entityImp2;
        EntityReference entityImp6;
        String nodeName;
        String systemId;
        String notationName;
        String nodeNameImp;
        String systemIdImp;
        String notationNameImp;
        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        docType = doc.getDoctype();
        docImp = domImpl.createDocument("http://www.w3.org/DOM/Test", "a:b", docTypeNull);
        nodeMap = docType.getEntities();
        assertNotNull("entitiesNotNull", nodeMap);
        entity2 = (EntityReference) nodeMap.getNamedItem("ent2");
        entity6 = (EntityReference) nodeMap.getNamedItem("ent6");
        entityImp2 = (EntityReference) docImp.importNode(entity2, false);
        entityImp6 = (EntityReference) docImp.importNode(entity6, true);
        nodeName = entity2.getNodeName();
        nodeNameImp = entityImp2.getNodeName();
        assertEquals("documentimportnode19_Ent2NodeName", nodeName, nodeNameImp);
        nodeName = entity6.getNodeName();
        nodeNameImp = entityImp6.getNodeName();
        assertEquals("documentimportnode19_Ent6NodeName", nodeName, nodeNameImp);
        systemId = entity2.getSystemId();
        systemIdImp = entityImp2.getSystemId();
        assertEquals("documentimportnode19_Ent2SystemId", systemId, systemIdImp);
        systemId = entity6.getSystemId();
        systemIdImp = entityImp6.getSystemId();
        assertEquals("documentimportnode19_Ent6SystemId", systemId, systemIdImp);
        notationName = entity2.getNotationName();
        notationNameImp = entityImp2.getNotationName();
        assertEquals("documentimportnode19_Ent2NotationName", notationName, notationNameImp);
        notationName = entity6.getNotationName();
        notationNameImp = entityImp6.getNotationName();
        assertEquals("documentimportnode19_Ent6NotationName", notationName, notationNameImp);
    }

}

