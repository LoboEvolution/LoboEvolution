
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Element.
 * If this document defines default attributes for this element name (importedNode),
 * those default attributes are assigned.
 * <p>
 * Create an element whose name is "emp:employee" in a different document.
 * Invoke method importNode(importedNode,deep) on this document which
 * defines default attribute for the element name "emp:employee".
 * Method should return an the imported element with an assigned default attribute.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=238">http://www.w3.org/Bugs/Public/show_bug.cgi?id=238</a>
 */
public class importNode07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        Element element;
        Element aNode;
        NamedNodeMap attributes;
        String name;
        Node attr;
        String lname;
        String namespaceURI = "http://www.nist.gov";
        String qualifiedName = "emp:employee";
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staff.xml");
        element = aNewDoc.createElementNS(namespaceURI, qualifiedName);
        aNode = (Element) doc.importNode(element, false);
        attributes = aNode.getAttributes();
        assertEquals("throw_Size", 1, attributes.getLength());
        name = aNode.getNodeName();
        assertEquals("nodeName", "emp:employee", name);
        attr = attributes.item(0);
        lname = attr.getLocalName();
        assertEquals("lname", "defaultAttr", lname);
    }
}

