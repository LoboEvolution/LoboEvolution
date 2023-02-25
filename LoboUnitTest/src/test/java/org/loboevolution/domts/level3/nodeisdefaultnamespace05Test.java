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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Using isDefaultNamespace on a DocumentElement of a new Document node with the value of the
 * namespaceURI parameter equal to the namespaceURI of the newly created Document and check if the
 * value returned is false.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace</a>
 */
public class nodeisdefaultnamespace05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        DOMImplementation domImpl;
        Document newDoc;
        boolean isDefault;
        DocumentType nullDocType = null;

        String nullNSURI = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getLocalName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        elem = newDoc.getDocumentElement();
        isDefault = elem.isDefaultNamespace(rootNS);
        assertTrue("nodeisdefaultnamespace05_1", isDefault);
        isDefault = elem.isDefaultNamespace(nullNSURI);
        assertFalse("nodeisdefaultnamespace05_2", isDefault);
    }
}

