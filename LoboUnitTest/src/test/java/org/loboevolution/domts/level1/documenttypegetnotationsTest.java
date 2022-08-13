
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "getNotations()" method creates a NamedNodeMap that
 * contains all the notations declared in the DTD.
 * <p>
 * Retrieve the Document Type for this document and create
 * a NamedNodeMap object of all the notations.  There
 * should be two items in the list (notation1 and notation2).
 *
 * @author NIST
 * @author Mary Brady
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D46829EF">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D46829EF</a>
 */
public class documenttypegetnotationsTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap notationList;
        Node notation;
        String notationName;
        java.util.Collection actual = new java.util.ArrayList();

        java.util.Collection expected = new java.util.ArrayList();
        expected.add("notation1");
        expected.add("notation2");

        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        notationList = docType.getNotations();
        assertNotNull("notationsNotNull", notationList);
        for (int indexN1005B = 0; indexN1005B < notationList.getLength(); indexN1005B++) {
            notation = notationList.item(indexN1005B);
            notationName = notation.getNodeName();
            actual.add(notationName);
        }
        assertEquals("names", expected, actual);
    }
}

