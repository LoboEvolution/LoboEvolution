
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
import org.loboevolution.html.dom.Entity;
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true, import a entity node ent4
 * from this document to a new document object.  The replacement text of this entity is an element
 * node, a cdata node and a pi.  Verify if the nodes have been
 * imported correctly by checking the nodeNames of the imported element node, the data for the
 * cdata nodes and the PItarget and PIData for the pi nodes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode20Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document docImp;
        DOMImplementation domImpl;
        DocumentType docType;
        DocumentType docTypeNull = null;

        NamedNodeMap nodeMap;
        Entity entity4;
        Entity entityImp4;
        Element element;
        CharacterData cdata;
        ProcessingInstruction pi;
        NodeList childList;
        NodeList elemchildList;
        String ent4Name;
        String ent4ImpName;
        String cdataVal;
        String piTargetVal;
        String piDataVal;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        docType = doc.getDoctype();
        docImp = domImpl.createDocument("http://www.w3.org/DOM/Test", "a:b", docTypeNull);
        nodeMap = docType.getEntities();
        entity4 = (Entity) nodeMap.getNamedItem("ent4");
        entityImp4 = (Entity) docImp.importNode(entity4, true);
        childList = entityImp4.getChildNodes();
        element = (Element) childList.item(0);
        elemchildList = element.getChildNodes();
        cdata = (CharacterData) elemchildList.item(0);
        pi = (ProcessingInstruction) childList.item(1);
        ent4Name = entity4.getNodeName();
        ent4ImpName = entityImp4.getNodeName();
        cdataVal = cdata.getData();
        piTargetVal = pi.getTarget();
        piDataVal = pi.getData();
        assertEquals("documentimportnode20_Ent4NodeName", ent4Name, ent4ImpName);
        assertEquals("documentimportnode20_Cdata", "Element data", cdataVal);
        assertEquals("documentimportnode20_PITarget", "PItarget", piTargetVal);
        assertEquals("documentimportnode20_PIData", "PIdata", piDataVal);
    }
}

