
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
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;


/**
 * If the "newChild" is a DocumentFragment object then all
 * its children are inserted in the same order before the
 * the "refChild".
 * <p>
 * Create a DocumentFragment object and populate it with
 * two Element nodes.   Retrieve the second employee and
 * insert the newly created DocumentFragment before its
 * fourth child.   The second employee should now have two
 * extra children("newChild1" and "newChild2") at
 * positions fourth and fifth respectively.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=247">http://www.w3.org/Bugs/Public/show_bug.cgi?id=247</a>
 */
public class hc_nodeinsertbeforedocfragmentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node employeeNode;
        NodeList childList;
        Node refChild;
        DocumentFragment newdocFragment;
        Node newChild1;
        Node newChild2;
        Node child;
        String childName;
        Node appendedChild;
        Node insertedNode;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        refChild = childList.item(3);
        newdocFragment = doc.createDocumentFragment();
        newChild1 = doc.createElement("br");
        newChild2 = doc.createElement("b");
        appendedChild = newdocFragment.appendChild(newChild1);
        appendedChild = newdocFragment.appendChild(newChild2);
        insertedNode = employeeNode.insertBefore(newdocFragment, refChild);
        child = childList.item(3);
        childName = child.getNodeName();
        assertEquals("childName3", "br", childName);
        child = childList.item(4);
        childName = child.getNodeName();
        assertEquals("childName4", "b", childName);
    }
}

