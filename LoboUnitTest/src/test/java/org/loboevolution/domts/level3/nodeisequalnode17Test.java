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

import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if a new Attr node created in this Document is equal to
 * the imported node returned when it is imported into a new Document.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode17Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     * @throws Throwable Any uncaught exception causes test to fail
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Attr attr1;
        Attr attr2;
        boolean isEqual;
        DocumentType nullDocType = null;

        String nullNSURI = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        attr1 = doc.createAttributeNS(nullNSURI, "root");
        attr2 = (Attr) newDoc.importNode(attr1, true);
        isEqual = attr1.isEqualNode(attr2);
        assertTrue("nodeisequalnode17", isEqual);
    }
}