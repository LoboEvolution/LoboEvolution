
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=false, import the attribute, "zone" of the
 * element node which is retreived by its elementId="CANADA", into the another document.
 * Check the parentNode, nodeName, nodeType and nodeValue of the imported node to
 * verify if it has been imported correctly.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document docImported;
        Element element;
        Attr attr;
        Node importedAttr;
        String nodeName;
        int nodeType;
        String nodeValue;
        HTMLCollection addresses;
        Node attrsParent;
        doc = sampleXmlFile("staffNS.xml");
        docImported = sampleXmlFile("staff.xml");
        addresses = doc.getElementsByTagName( "address");
        element = (Element) addresses.item(1);
        attr = element.getAttributeNode( "zone");
        importedAttr = docImported.importNode(attr, false);
        nodeName = importedAttr.getNodeName();
        nodeType = importedAttr.getNodeType();
        nodeValue = importedAttr.getNodeValue();
        attrsParent = importedAttr.getParentNode();
        assertNull("documentimportnode02_parentNull", attrsParent);
        assertEquals("documentimportnode02_nodeName", "zone", nodeName);
        assertEquals("documentimportnode02_nodeType", 2, nodeType);
        assertEquals("documentimportnode02_nodeValue", "CANADA", nodeValue);
    }
}

