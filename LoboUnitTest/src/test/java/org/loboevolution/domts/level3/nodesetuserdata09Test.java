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

import static org.junit.Assert.assertNull;


/**
 * Invoke setUserData on this documentElement node to set its UserData to
 * this Document node.  Invoke getUserData on this Document node with the same
 * key of the UserData that was just set on the documentElement node and verify
 * if the returned node is null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata09Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        Object returned;

        Object retUserData;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        /*Node */
        retUserData = docElem.setUserData("Key1", doc, null);
        returned = doc.getUserData("Key1");
        assertNull("nodesetuserdata09", returned);
    }
}

