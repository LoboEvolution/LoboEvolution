
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
import org.loboevolution.html.node.EntityReference;

import static org.junit.Assert.*;


/**
 * The "createEntityReference(name)" method creates an
 * EntityReferrence node.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createEntityReference(name)" method.  It should create
 * a new EntityReference node for the Entity with the
 * given name.  The name, value and type are retrieved and
 * output.
 *
 * @author NIST
 * @author Mary Brady
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 */
public class documentcreateentityreferenceTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        EntityReference newEntRefNode;
        String entRefValue;
        String entRefName;
        int entRefType;
        doc = sampleXmlFile("staff.xml");
        newEntRefNode = doc.createEntityReference("ent1");
        assertNotNull("createdEntRefNotNull", newEntRefNode);
        entRefValue = newEntRefNode.getNodeValue();
        assertNull("value", entRefValue);
        entRefName = newEntRefNode.getNodeName();
        assertEquals("name", "ent1", entRefName);
        entRefType = newEntRefNode.getNodeType();
        assertEquals("type", 5, entRefType);
    }
}

