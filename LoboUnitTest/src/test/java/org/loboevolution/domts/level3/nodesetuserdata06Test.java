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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setUserData on a new Comment to set its UserData to an Entity node
 * twice using the same key.  Verify if the UserData object that was by the
 * second setUserData is the same as original Entity.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        Node entity;
        Comment comment;
        Object userData;
        Object returned;
        Object retUserData;
        boolean success;

        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        entity = entities.getNamedItem("delta");
        comment = doc.createComment("COMMENT_NODE");
        /*Node */
        retUserData = comment.setUserData("Key1", entity, null);
        /*Node */
        returned = comment.setUserData("Key1", entity, null);
        success = ((Node) /*DOMUserData */returned).isEqualNode(entity);
        assertTrue("nodesetuserdata06", success);
    }
}

