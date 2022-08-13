
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import static org.junit.Assert.assertEquals;


/**
 * If the "refChild" is null then the
 * "insertBefore(newChild,refChild)" method inserts the
 * node "newChild" at the end of the list of children.
 * <p>
 * Retrieve the second employee and invoke the
 * "insertBefore(newChild,refChild)" method with
 * refChild=null.   Since "refChild" is null the "newChild"
 * should be added to the end of the list.   The last item
 * in the list is checked after insertion.   The last Element
 * node of the list should be "newChild".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class nodeinsertbeforerefchildnullTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        Node refChild = null;

        Node newChild;
        Node child;
        String childName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        employeeNode = elementList.item(1);
        newChild = doc.createElement("newChild");
        employeeNode.insertBefore(newChild, refChild);
        child = employeeNode.getLastChild();
        childName = child.getNodeName();
        assertEquals("nodeInsertBeforeRefChildNullAssert1", "newChild", childName);
    }

}

