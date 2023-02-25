
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "getEntities()" method is a NamedNodeMap that contains
 * the general entities for this document.
 * <p>
 * Retrieve the Document Type for this document and create
 * a NamedNodeMap of all its entities.  The entire map is
 * traversed and the names of the entities are retrieved.
 * There should be 5 entities.  Duplicates should be ignored.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1788794630">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1788794630</a>
 */
public class documenttypegetentitiesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entityList;
        String name;
        java.util.Collection expectedResult = new java.util.ArrayList();
        expectedResult.add("ent1");
        expectedResult.add("ent2");
        expectedResult.add("ent3");
        expectedResult.add("ent4");
        expectedResult.add("ent5");

        java.util.Collection expectedResultSVG = new java.util.ArrayList();
        expectedResultSVG.add("ent1");
        expectedResultSVG.add("ent2");
        expectedResultSVG.add("ent3");
        expectedResultSVG.add("ent4");
        expectedResultSVG.add("ent5");
        expectedResultSVG.add("svgunit");
        expectedResultSVG.add("svgtest");

        Collection nameList = new ArrayList();

        Node entity;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        entityList = docType.getEntities();
        assertNotNull("entitiesNotNull", entityList);
        for (int indexN1007B = 0; indexN1007B < entityList.getLength(); indexN1007B++) {
            entity = entityList.item(indexN1007B);
            name = entity.getNodeName();
            nameList.add(name);
        }

        assertEquals("entityNames", expectedResult, nameList);

    }
}

