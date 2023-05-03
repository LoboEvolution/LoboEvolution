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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Using replaceChild on this Document node attempt to replace this DocumentType node with
 * a new DocumentType and verify the name of the replaced DocumentType node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild13Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DocumentType newDocType;
        DocumentType replaced;
        DOMImplementation domImpl;
        String nodeName;
        String nullPubId = null;

        String nullSysId = null;

        Element docElem;
        String docElemName;
        String docElemNS;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        docElemName = docElem.getTagName();
        docElemNS = docElem.getNamespaceURI();
        docType = doc.getDoctype();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(docElemName, nullPubId, nullSysId);

        try {
            replaced = (DocumentType) doc.replaceChild(newDocType, docType);

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 9:
                    return;
                default:
                    throw ex;
            }
        }
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild13", docElemName, nodeName);
    }
}

