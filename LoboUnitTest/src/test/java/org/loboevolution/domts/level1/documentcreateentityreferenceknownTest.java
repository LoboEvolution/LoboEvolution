
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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;


/**
 * The "createEntityReference(name)" method creates an
 * EntityReference node.  In addition, if the referenced entity
 * is known, the child list of the "EntityReference" node
 * is the same as the corresponding "Entity" node.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createEntityReference(name)" method.  It should create
 * a new EntityReference node for the Entity with the
 * given name.  The referenced entity is known, therefore the child
 * list of the "EntityReference" node is the same as the corresponding
 * "Entity" node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE</a>
 */
public class documentcreateentityreferenceknownTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc = sampleXmlFile("staff.xml");
        EntityReference newEntRefNode = doc.createEntityReference("ent3");
        assertNotNull("createdEntRefNotNull", newEntRefNode);
        assertTrue(newEntRefNode.getChildNodes().getLength() == 0);
    }
}

