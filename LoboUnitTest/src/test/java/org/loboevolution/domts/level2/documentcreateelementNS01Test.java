
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * The method createElementNS creates an element of the given valid qualifiedName and NamespaceURI.
 * <p>
 * Invoke the createElementNS method on this Document object with a valid namespaceURI
 * and qualifiedName.  Check if a valid Element object is returned with the same node attributes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS</a>
 */
public class documentcreateelementNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        String namespaceURI = "http://www.w3.org/DOM/Test/level2";
        String qualifiedName = "XML:XML";
        String nodeName;
        String nsURI;
        String localName;
        String prefix;
        String tagName;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.createElementNS(namespaceURI, qualifiedName);
        nodeName = element.getNodeName();
        nsURI = element.getNamespaceURI();
        localName = element.getLocalName();
        prefix = element.getPrefix();
        tagName = element.getTagName();
        assertEquals("documentcreateelementNS01_nodeName", "XML:XML", nodeName);
        assertEquals("documentcreateelementNS01_namespaceURI", "http://www.w3.org/DOM/Test/level2", nsURI);
        assertEquals("documentcreateelementNS01_localName", "XML", localName);
        assertEquals("documentcreateelementNS01_prefix", "XML", prefix);
        assertEquals("documentcreateelementNS01_tagName", "XML:XML", tagName);
    }
}

