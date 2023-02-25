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

import static org.junit.Assert.assertNull;


/**
 * Invoke setUserData on this Entity node to set this its UserData to a new
 * Attr node and using getUserData with an invalid Key check if the returned
 * UserData object is Null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getUserData</a>
 */
public class nodegetuserdata05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entities;
        Node entity;
        Attr attr;
        Object userData;
        Object retUserData;

        Object prevUserData;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entities = docType.getEntities();
        entity = entities.getNamedItem("delta");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "lang");
        /*Node */
        prevUserData = entity.setUserData("key", attr, null);
        retUserData = entity.getUserData("Key");
        assertNull("nodegetuserdata05", retUserData);
    }
}

