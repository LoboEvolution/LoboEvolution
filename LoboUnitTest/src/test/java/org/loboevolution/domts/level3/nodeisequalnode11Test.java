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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Retreive the first element node whose localName is "p".  Import it into a new
 * Document with deep=false.  Using isEqualNode check if the original and the imported
 * Element Node are not equal the child nodes are different.
 * Import with deep and the should still be unequal if
 * validating since the
 * new document does not provide the same default attributes.
 * Import it into another instance of the source document
 * and then the imported node and the source should be equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=529">http://www.w3.org/Bugs/Public/show_bug.cgi?id=529</a>
 */
public class nodeisequalnode11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        HTMLCollection employeeList;
        Document newDoc;
        Document dupDoc;
        Element elem1;
        Element elem2;
        Element elem3;
        Element elem4;
        boolean isEqual;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        employeeList = doc.getElementsByTagName("p");
        elem1 = (Element) employeeList.item(0);
        elem2 = (Element) newDoc.importNode(elem1, false);
        isEqual = elem1.isEqualNode(elem2);
        assertFalse("nodeisequalnodeFalse11", isEqual);
        elem3 = (Element) newDoc.importNode(elem1, true);
        isEqual = elem1.isEqualNode(elem3);

        assertFalse("deepImportNoDTD", isEqual);
        dupDoc = sampleXmlFile("hc_staff.xml");
        elem4 = (Element) dupDoc.importNode(elem1, true);
        isEqual = elem1.isEqualNode(elem4);
        assertTrue("deepImportSameDTD", isEqual);
    }
}

