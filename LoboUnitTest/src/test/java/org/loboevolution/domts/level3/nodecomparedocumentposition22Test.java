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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;

/**
 * Using compareDocumentPosition check if the Entity node precedes the Notation node and the Notation
 * node follows the Entity node.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        NamedNodeMap notationsMap;
        Node entity;
        Notation notation;
        int entityPosition;
        int notationPosition;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        notationsMap = docType.getNotations();
        entity = entitiesMap.getNamedItem("alpha");
        notation = (Notation) notationsMap.getNamedItem("notation1");
        entityPosition = entity.compareDocumentPosition(notation);
        assertEquals("nodecomparedocumentpositionFollowing22", 4, entityPosition);
        notationPosition = (int) notation.compareDocumentPosition(entity);
        assertEquals("nodecomparedocumentpositionPRECEDING22", 2, notationPosition);
    }
}

