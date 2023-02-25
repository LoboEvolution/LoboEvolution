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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Using the method adoptNode in a new Document, adopt a newly created DocumentFragment node populated with
 * with the first acronym element of this Document as its newChild.  Since the decendants of a documentFragment
 * are recursively adopted, check if the adopted node has children.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode14Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        Element docElem;
        DOMImplementation domImpl;
        DocumentFragment docFragment;
        HTMLCollection childList;
        boolean success;
        Node acronymNode;
        Node adoptedDocFrag;
        Node appendedChild;
        DocumentType nullDocType = null;

        Node imported;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        docFragment = newDoc.createDocumentFragment();
        imported = newDoc.importNode(docElem, true);
        docElem = newDoc.getDocumentElement();
        appendedChild = docElem.appendChild(imported);
        childList = newDoc.getElementsByTagName("acronym");
        acronymNode = childList.item(0);
        appendedChild = docFragment.appendChild(acronymNode);
        adoptedDocFrag = newDoc.adoptNode(docFragment);

        if ((adoptedDocFrag != null)) {
            success = adoptedDocFrag.hasChildNodes();
            assertTrue("documentadoptnode14", success);
        }
    }
}

