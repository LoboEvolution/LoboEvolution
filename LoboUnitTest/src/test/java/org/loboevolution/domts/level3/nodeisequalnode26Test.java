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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 NotationNode having the same name of two DocumnotationType nodes
 * returned by parsing the same xml documnotation are equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode26Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc1;
        Document doc2;
        DocumentType docType1;
        DocumentType docType2;
        NamedNodeMap notationsMap1;
        NamedNodeMap notationsMap2;
        Notation notation1;
        Notation notation2;
        boolean isEqual;
        doc1 = sampleXmlFile("hc_staff.xml");
        doc2 = sampleXmlFile("hc_staff.xml");
        docType1 = doc1.getDoctype();
        docType2 = doc2.getDoctype();
        notationsMap1 = docType1.getNotations();
        notationsMap2 = docType2.getNotations();
        notation1 = (Notation) notationsMap1.getNamedItem("notation1");
        notation2 = (Notation) notationsMap2.getNamedItem("notation1");
        isEqual = notation1.isEqualNode(notation2);
        assertTrue("nodeisequalnode26", isEqual);
    }
}

