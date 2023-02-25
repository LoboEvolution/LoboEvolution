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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setUserData on this Document to set this Documents UserData to a new
 * Element node.  Do the same with a new Text node and using isNodeEqual verify
 * the returned Element UserData object.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Object userData;
        Object retUserData;
        Object returnedUserData;
        boolean success;
        Element elem;
        Text txt;

        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:p");
        txt = doc.createTextNode("TEXT");
        /*Node */
        returnedUserData = doc.setUserData("Key1", elem, null);
        /*Node */
        retUserData = doc.setUserData("Key1", txt, null);
        success = ((Node) /*DOMUserData */retUserData).isEqualNode(elem);
        assertTrue("nodesetuserdata03", success);
    }
}

