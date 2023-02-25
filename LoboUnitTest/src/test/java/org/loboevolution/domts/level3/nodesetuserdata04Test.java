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
 * Invoke setUserData on a new Element to set its UserData to a new Text node
 * twice using different Keys.  Using getUserData with each Key and isNodeEqual
 * verify if the returned nodes are Equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Object userData;
        Object returned1;
        Object returned2;
        Object retUserData;
        boolean success;
        Element elem;
        Text txt;

        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        txt = doc.createTextNode("TEXT");
        /*Node */
        retUserData = elem.setUserData("Key1", txt, null);
        /*Node */
        retUserData = elem.setUserData("Key2", txt, null);
        returned1 = elem.getUserData("Key1");
        returned2 = elem.getUserData("Key2");
        success = ((Node) /*DOMUserData */returned1).isEqualNode(((Node) /*DOMUserData */returned2));
        assertTrue("nodesetuserdata04", success);
    }
}

