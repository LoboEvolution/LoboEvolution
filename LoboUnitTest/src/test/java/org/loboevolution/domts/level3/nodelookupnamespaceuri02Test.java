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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;

/**
 * Using lookupNamespaceURI on a new Document node with a namespaceURI and prefix
 * and check if the value returned is the same namespaceURI.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespaceURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-lookupNamespaceURI</a>
 */
public class nodelookupnamespaceuri02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Document newDoc;
        String namespaceURI;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        String qname;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        qname = "dom3:" + rootName;
        newDoc = domImpl.createDocument(rootNS, qname, nullDocType);
        namespaceURI = newDoc.lookupNamespaceURI("dom3");
        assertEquals("nodelookupnamespaceuri02", rootNS, namespaceURI);
    }
}