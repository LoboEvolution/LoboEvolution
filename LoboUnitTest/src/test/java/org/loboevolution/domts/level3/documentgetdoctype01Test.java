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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNull;


/**
 * Retreive the doctype node, create a new Doctype node, call replaceChild and try replacing the
 * docType node with a new docType node.  Check if the docType node was correctly replaced with
 * the new one.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-B63ED1A31">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-B63ED1A31</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class documentgetdoctype01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DocumentType newDocType;
        DocumentType replacedDocType;
        DOMImplementation domImpl;
        String newSysID;
        String nullPubID = null;
        String nullSysID = null;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        rootName = docType.getName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, nullPubID, nullSysID);
        try {
             doc.replaceChild(newDocType, docType);
        } catch (DOMException ex) {
            if (ex.getCode() == 9) {
                return;
            }
            throw ex;
        }
        replacedDocType = doc.getDoctype();
        newSysID = replacedDocType.getSystemId();
        assertNull("newSysIdNull", newSysID);
    }
}

