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
 * Using isSameNode check if 2 Notation nodes that reference the same object are
 * the same.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isSameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isSameNode</a>
 */
public class nodeissamenode08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Notation notation1;
        Notation notation2;
        boolean isSame;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getNotations();
        notation1 = (Notation) entitiesMap.getNamedItem("notation1");
        notation2 = (Notation) entitiesMap.getNamedItem("notation1");
        isSame = notation1.isSameNode(notation2);
        assertTrue("nodeissamenode08", isSame);
    }
}

