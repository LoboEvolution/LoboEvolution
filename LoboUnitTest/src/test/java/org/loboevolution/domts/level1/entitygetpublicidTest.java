
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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "getPublicId()" method of an Entity node contains
 * the public identifier associated with the entity, if
 * one was specified.
 * <p>
 * Retrieve the entity named "ent5" and access its
 * public identifier.  The string "entityURI" should be
 * returned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6ABAEB38">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6ABAEB38</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7C29F3E">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7C29F3E</a>
 */
public class entitygetpublicidTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entityList;
        EntityReference entityNode;
        String publicId;
        String notation;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        entityList = docType.getEntities();
        assertNotNull("entitiesNotNull", entityList);
        entityNode = (EntityReference) entityList.getNamedItem("ent5");
        publicId = entityNode.getPublicId();
        assertEquals("publicId", "entityURI", publicId);
        entityNode.getSystemId();
        notation = entityNode.getNotationName();
        assertEquals("notation", "notation1", notation);
    }
}

