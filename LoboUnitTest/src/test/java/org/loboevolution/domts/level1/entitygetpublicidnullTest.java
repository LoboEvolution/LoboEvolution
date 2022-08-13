
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
import org.loboevolution.html.dom.Entity;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * The "getPublicId()" method of an Entity node contains
 * the public identifier associated with the entity, if
 * one was not specified a null value should be returned.
 * <p>
 * Retrieve the entity named "ent1" and access its
 * public identifier.  Since a public identifier was not
 * specified for this entity, the "getPublicId()" method
 * should return null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025</a>
 */
public class entitygetpublicidnullTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entityList;
        Entity entityNode;
        String publicId;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        entityList = docType.getEntities();
        assertNotNull("entitiesNotNull", entityList);
        entityNode = (Entity) entityList.getNamedItem("ent1");
        publicId = entityNode.getPublicId();
        assertNull("entityGetPublicIdNullAssert", publicId);
    }
}

