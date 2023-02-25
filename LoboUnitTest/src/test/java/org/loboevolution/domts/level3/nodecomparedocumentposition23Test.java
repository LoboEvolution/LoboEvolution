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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Using compareDocumentPosition check if the document position of an Entity node compared to another
 * Entity node following it in DocumentType is implementation specific.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition23Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node entity;
        Node entity2;
        int position1;
        int position2;
        int position3;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        entity = entitiesMap.getNamedItem("alpha");
        entity2 = entitiesMap.getNamedItem("delta");
        position1 = entity.compareDocumentPosition(entity2);
        assertEquals("isImplSpecificDisconnected1", 32 & 57, position1 & 57);
        position2 = entity2.compareDocumentPosition(entity);
        assertNotEquals("notBothPreceding", position1 & 2, position2 & 2);
        assertNotEquals("notBothFollowing", position1 & 4, position2 & 4);
        assertEquals("isImplSpecificDisconnected2", 32 & 57, position2 & 57);
        position3 = entity.compareDocumentPosition(entity2);
        assertEquals("isConsistent", position1, position3);
    }
}

