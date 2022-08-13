
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true, retreive the entity refs present in the
 * second element node whose tagName is address and import these nodes into another document.
 * Verify if the nodes have been imported correctly by checking the nodeNames of the
 * imported nodes, since they are imported into a new document which doesnot have thes defined,
 * the imported nodes should not have any children.
 * Now import the entityRef nodes into the same document and verify if the nodes have been
 * imported correctly by checking the nodeNames of the imported nodes, and by checking the
 * value of the replacement text of the imported nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode21Test extends LoboUnitTest {

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
        HTMLCollection addressList;
        NodeList addressChildList;
        Element element;
        EntityReference entRef2;
        EntityReference entRefImp2;
        EntityReference entRef3;
        EntityReference entRefImp3;
        String nodeName2;
        String nodeName3;
        String nodeNameImp2;
        String nodeNameImp3;
        NodeList nodes;
        Node nodeImp3;
        Node nodeImp2;
        String nodeValueImp2;
        String nodeValueImp3;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        docImp = domImpl.createDocument("http://www.w3.org/DOM/Test", "a:b", docTypeNull);
        addressList = doc.getElementsByTagName("address");
        element = (Element) addressList.item(1);
        addressChildList = element.getChildNodes();
        entRef2 = (EntityReference) addressChildList.item(0);
        entRef3 = (EntityReference) addressChildList.item(2);
        entRefImp2 = (EntityReference) docImp.importNode(entRef2, true);
        entRefImp3 = (EntityReference) docImp.importNode(entRef3, false);
        nodeName2 = entRef2.getNodeName();
        nodeName3 = entRef3.getNodeName();
        nodeNameImp2 = entRefImp2.getNodeName();
        nodeNameImp3 = entRefImp3.getNodeName();
        assertEquals("documentimportnode21_Ent2NodeName", nodeName2, nodeNameImp2);
        assertEquals("documentimportnode21_Ent3NodeName", nodeName3, nodeNameImp3);
        entRefImp2 = (EntityReference) doc.importNode(entRef2, true);
        entRefImp3 = (EntityReference) doc.importNode(entRef3, false);
        nodes = entRefImp2.getChildNodes();
        nodeImp2 = nodes.item(0);
        nodeValueImp2 = nodeImp2.getNodeValue();
        nodes = entRefImp3.getChildNodes();
        nodeImp3 = nodes.item(0);
        nodeValueImp3 = nodeImp3.getNodeValue();
        assertEquals("documentimportnode21_Ent2NodeValue", "1900 Dallas Road", nodeValueImp2);
        assertEquals("documentimportnode21_Ent3Nodevalue", "Texas", nodeValueImp3);
    }
}

