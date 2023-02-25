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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setUserData on a Notation to set its UserData to a Comment node
 * twice using the same key.  Verify if the UserData object that was returned
 * by second setUserData is the Comment node set in the first setUserData call.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap notations;
        Notation notation;
        Comment comment;
        Object returned;
        boolean success;

        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        notations = docType.getNotations();
        notation = (Notation) notations.getNamedItem("notation1");
        comment = doc.createComment("COMMENT_NODE");
        /*Node */
        notation.setUserData("Key1", comment, null);
        /*Node */
        returned = notation.setUserData("Key1", comment, null);
        success = ((Node) /*DOMUserData */returned).isEqualNode(comment);
        assertTrue("nodesetuserdata07", success);
    }
}

