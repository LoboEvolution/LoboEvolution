
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

import static org.junit.Assert.assertEquals;


/**
 * The method createAttributeNS creates an attribute of the given qualified name and namespace URI
 * <p>
 * Invoke the createAttributeNS method on this Document object with a valid values for
 * namespaceURI, and a qualifiedName as below.  This should return a valid Attr node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class documentcreateattributeNS02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr attribute1;
        Attr attribute2;
        String name;
        String nodeName;
        String nodeValue;
        String prefix;
        String namespaceURI;
        doc = sampleXmlFile("staffNS.xml");
        attribute1 = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:xml");
        name = attribute1.getName();
        nodeName = attribute1.getNodeName();
        nodeValue = attribute1.getNodeValue();
        prefix = attribute1.getPrefix();
        namespaceURI = attribute1.getNamespaceURI();
        assertEquals("documentcreateattributeNS02_att1_name", "xml:xml", name);
        assertEquals("documentcreateattributeNS02_att1_nodeName", "xml:xml", nodeName);
        assertEquals("documentcreateattributeNS02_att1_nodeValue", "", nodeValue);
        assertEquals("documentcreateattributeNS02_att1_prefix", "xml", prefix);
        assertEquals("documentcreateattributeNS02_att1_namespaceURI", "http://www.w3.org/XML/1998/namespace", namespaceURI);
        attribute2 = doc.createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
        name = attribute2.getName();
        nodeName = attribute2.getNodeName();
        nodeValue = attribute2.getNodeValue();
        namespaceURI = attribute2.getNamespaceURI();
        assertEquals("documentcreateattributeNS02_att2_name", "xmlns", name);
        assertEquals("documentcreateattributeNS02_att2_nodeName", "xmlns", nodeName);
        assertEquals("documentcreateattributeNS02_att2_nodeValue", "", nodeValue);
        assertEquals("documentcreateattributeNS02_att2_namespaceURI", "http://www.w3.org/2000/xmlns/", namespaceURI);
    }

}

