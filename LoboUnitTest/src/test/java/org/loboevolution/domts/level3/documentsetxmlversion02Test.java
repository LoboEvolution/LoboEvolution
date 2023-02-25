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
 * Set the value of the version attribute of the XML declaration of a new document to "1.0"
 * and check if it was correctly set.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version</a>
 */
public class documentsetxmlversion02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        String versionValue;
        Document newDoc;
        DOMImplementation domImpl;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        newDoc.setXmlVersion("1.0");
        versionValue = newDoc.getXmlVersion();
        assertEquals("documentsetxmlversion02", "1.0", versionValue);
    }
}

