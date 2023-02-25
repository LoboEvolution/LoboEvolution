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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNull;


/**
 * Using getBaseURI verify if the imported notation notation2 is null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Notation">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Notation</a>
 */
public class nodegetbaseuri14Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        Element docElem;
        String docElemNS;
        String docElemName;
        DOMImplementation domImpl;
        DocumentType docType;
        NamedNodeMap notationsMap;
        Notation notation;
        Notation notationImported;
        String baseURI;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        docElemNS = docElem.getNamespaceURI();
        docElemName = docElem.getLocalName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(docElemNS, docElemName, nullDocType);
        docType = doc.getDoctype();
        notationsMap = docType.getNotations();
        notation = (Notation) notationsMap.getNamedItem("notation2");
        notationImported = (Notation) newDoc.importNode(notation, true);
        baseURI = notationImported.getBaseURI();
        assertNull("nodegetbaseuri14", baseURI);
    }
}

