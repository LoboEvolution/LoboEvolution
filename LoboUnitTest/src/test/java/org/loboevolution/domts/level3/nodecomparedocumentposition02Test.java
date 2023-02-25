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

import static org.junit.Assert.assertEquals;


/**
 * Using compareDocumentPosition to check if a Document node contains and precedes its new DocumentType and
 * node and if the new DocumentType Node is contained and follows its Document node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        DocumentType newDocType;
        DocumentType docType;
        int documentPositionDoc;
        int documentPositionDocType;
        String nullPubId = null;
        String nullSysId = null;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        rootName = docType.getName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, nullPubId, nullSysId);
        doc.replaceChild(newDocType, docType);
        documentPositionDoc = doc.compareDocumentPosition(newDocType);
        assertEquals("nodecomparedocumentpositionIsContainedFollowing02", 20, documentPositionDoc);
        documentPositionDocType = newDocType.compareDocumentPosition(doc);
        assertEquals("nodecomparedocumentpositionContainsPRECEDING02", 10, documentPositionDocType);
    }
}

