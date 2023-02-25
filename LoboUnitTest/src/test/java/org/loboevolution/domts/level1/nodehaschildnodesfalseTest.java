
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
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertFalse;


/**
 * The "hasChildNodes()" method returns false if the node
 * does not have any children.
 * <p>
 * Retrieve the Text node inside the first child of the
 * second employee and invoke the "hasChildNodes()" method.
 * It should return the boolean value "false".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-810594187">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-810594187</a>
 */
public class nodehaschildnodesfalseTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node child;
        NodeList employeeIdList;
        Node employeeNode;
        Node textNode;
        boolean state;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        child = elementList.item(1);
        employeeIdList = child.getChildNodes();
        employeeNode = employeeIdList.item(1);
        textNode = employeeNode.getFirstChild();
        state = textNode.hasChildNodes();
        assertFalse("nodeHasChildFalseAssert1", state);
    }
}

