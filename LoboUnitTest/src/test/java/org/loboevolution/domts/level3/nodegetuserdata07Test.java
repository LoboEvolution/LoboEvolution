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
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertTrue;


/**
 * Invoke setUserData on a new PI node to set this its UserData to itself
 * and using getUserData with an valid Key and isEqualsNode check if the
 * returned UserData object is the same as that was set.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getUserData</a>
 */
public class nodegetuserdata07Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction pi;
        Object userData;
        Object retUserData;
        boolean success;

        Object prevUserData;
        doc = sampleXmlFile("hc_staff.xml");
        pi = doc.createProcessingInstruction("PITARGET", "PIDATA");
        /*Node */
        prevUserData = pi.setUserData("key", pi, null);
        retUserData = pi.getUserData("key");
        success = ((Node) /*DOMUserData */retUserData).isEqualNode(pi);
        assertTrue("nodegetuserdata07", success);
    }
}

