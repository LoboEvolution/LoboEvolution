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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Invoke the renameNode method on a new document node and try to rename the default
 * attribute "dir"
 * Check if a WRONG_DOCUMENT_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Element element;
        Attr attr;
        HTMLCollection childList;
        DocumentType nullDocType = null;

        Element docElem;
        String docElemNS;
        String docElemName;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("p");
        element = (Element) childList.item(3);
        attr = element.getAttributeNodeNS("*", "dir");
        domImpl = doc.getImplementation();
        docElem = doc.getDocumentElement();
        docElemNS = docElem.getNamespaceURI();
        docElemName = docElem.getTagName();
        newDoc = domImpl.createDocument(docElemNS, docElemName, nullDocType);

        boolean success = false;
        try {
            newDoc.renameNode(attr, "http://www.w3.org/XML/1998/namespace", "xml:lang");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.WRONG_DOCUMENT_ERR);
        }
        assertTrue("documentrenamenode08_WRONG_DOCUMENT_ERR", success);

    }
}

