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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNull;


/**
 * Import the documentElement node of this document into a new document.  Since this node is
 * now owned by the importing document, its documentURI attribute value should be null
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-documentURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-documentURI</a>
 */
public class documentgetdocumenturi03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        Document importedOwner;
        Element docElem;
        Node docElemImported;
        String docURI;
        DOMImplementation domImpl;
        DocumentType nullDocType = null;

        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        docElemImported = newDoc.importNode(docElem, false);
        importedOwner = docElemImported.getOwnerDocument();
        docURI = importedOwner.getDocumentURI();
        assertNull("documentgetdocumenturi03", docURI);
    }
}

