
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=false, import a newly created attribute node,
 * into the another document.
 * Check the nodeName, nodeType and nodeValue namespaceURI of the imported node to
 * verify if it has been imported correctly.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document docImported;
        Attr attr;
        Node importedAttr;
        String nodeName;
        int nodeType;
        String nodeValue;
        String namespaceURI;
        doc = sampleXmlFile("staffNS.xml");
        docImported = sampleXmlFile("staff.xml");
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test", "a_:b0");
        importedAttr = docImported.importNode(attr, false);
        nodeName = importedAttr.getNodeName();
        nodeValue = importedAttr.getNodeValue();
        nodeType = importedAttr.getNodeType();
        namespaceURI = importedAttr.getNamespaceURI();
        assertEquals("documentimportnode05_nodeName", "a_:b0", nodeName);
        assertEquals("documentimportnode05_nodeType", 2, nodeType);
        assertEquals("documentimportnode05_nodeValue", "", nodeValue);
        assertEquals("documentimportnode05_namespaceURI", "http://www.w3.org/DOM/Test", namespaceURI);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/documentimportnode05";
    }
}

