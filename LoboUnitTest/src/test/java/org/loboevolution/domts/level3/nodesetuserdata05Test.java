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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setUserData on a new Attr to set its UserData to two Document nodes
 * obtained by parsing the same xml document.  Using getUserData and isNodeEqual
 * verify if the returned nodes are Equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document doc2;
        Object userData;
        Object returned1;
        Object returned2;
        Object retUserData;
        boolean success;
        Attr attr;

        doc = sampleXmlFile("hc_staff.xml");
        doc2 = sampleXmlFile("hc_staff.xml");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "lang");
        /*Node */
        retUserData = attr.setUserData("Key1", doc, null);
        /*Node */
        retUserData = attr.setUserData("Key2", doc2, null);
        returned1 = attr.getUserData("Key1");
        returned2 = attr.getUserData("Key2");
        success = ((Node) /*DOMUserData */returned1).isEqualNode(((Node) /*DOMUserData */returned2));
        assertTrue("nodesetuserdata05", success);
    }
}

