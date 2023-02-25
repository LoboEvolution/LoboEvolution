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

import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 new DocumentType having null public and system ids
 * are equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc1;
        Document doc2;
        DOMImplementation domImpl1;
        DOMImplementation domImpl2;
        DocumentType docType1;
        DocumentType docType2;
        boolean isEqual;
        String nullPubId = null;

        String nullSysId = null;

        DocumentType oldDocType;
        String rootName;
        doc1 = sampleXmlFile("barfoo.xml");
        oldDocType = doc1.getDoctype();
        rootName = oldDocType.getName();
        doc2 = sampleXmlFile("barfoo.xml");
        domImpl1 = doc1.getImplementation();
        domImpl2 = doc2.getImplementation();
        docType1 = domImpl1.createDocumentType(rootName, nullPubId, nullSysId);
        docType2 = domImpl2.createDocumentType(rootName, nullPubId, nullSysId);
        isEqual = docType1.isEqualNode(docType2);
        assertTrue("nodeisequalnode22", isEqual);
    }
}

