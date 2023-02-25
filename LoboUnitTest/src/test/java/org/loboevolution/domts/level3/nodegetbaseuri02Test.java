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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Using getBaseURI check if the baseURI attribute of a new Document node is null
 * and if affected by changes in Document.documentURI.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Document">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Document</a>
 */
public class nodegetbaseuri02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        String baseURI;
        String rootNS;
        String rootName;
        Element docElem;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        baseURI = newDoc.getBaseURI();
        assertNull("baseURIIsNull", baseURI);
        newDoc.setDocumentURI("http://www.example.com/sample.xml");
        baseURI = newDoc.getBaseURI();
        assertTrue("baseURISameAsDocURI", "http://www.example.com/sample.xml".equalsIgnoreCase(baseURI));
    }
}

