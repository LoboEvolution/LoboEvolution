
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=false, import the default Attribute attribute,
 * "defaultAttr" of the second element node whose namespaceURI="http://www.nist.gov" and
 * localName="defaultAttr", into the same document.
 * Check the parentNode, nodeName, nodeType and nodeValue of the imported node to
 * verify if it has been imported correctly.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode03Test extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attr;
        HTMLCollection childList;
        Node importedAttr;
        String nodeName;
        int nodeType;
        String nodeValue;
        doc = sampleXmlFile("staffNS.xml");
        childList = doc.getElementsByTagName( "employee");
        element = (Element) childList.item(1);
        attr = element.getAttributeNode("defaultAttr");
        importedAttr = doc.importNode(attr, false);
        nodeName = importedAttr.getNodeName();
        nodeValue = importedAttr.getNodeValue();
        nodeType = importedAttr.getNodeType();
        assertEquals("documentimportnode03_nodeName", "defaultAttr", nodeName);
        assertEquals("documentimportnode03_nodeType", 2, nodeType);
        assertEquals("documentimportnode03_nodeValue", "defaultVal", nodeValue);
    }
}

