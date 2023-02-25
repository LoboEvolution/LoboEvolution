
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "hasChildNodes()" method returns true if the node
 * has children.
 * <p>
 * Retrieve the root node("staff") and invoke the
 * "hasChildNodes()" method.   It should return the boolean
 * value "true".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-810594187">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-810594187</a>
 */
public class hc_nodehaschildnodesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        boolean state;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        state = employeeNode.hasChildNodes();
        assertTrue("nodeHasChildAssert1", state);
    }
}

