
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertNull;


/**
 * The "removeChild(oldChild)" method removes the child node
 * indicated by "oldChild" from the list of children and
 * returns it.
 * <p>
 * Remove the first employee by invoking the
 * "removeChild(oldChild)" method an checking the
 * node returned by the "getParentNode()" method.   It
 * should be set to null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066</a>
 */
public class noderemovechildTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element rootNode;
        NodeList childList;
        Node childToRemove;
        Node removedChild;
        Node parentNode;
        doc = sampleXmlFile("staff.xml");
        rootNode = doc.getDocumentElement();
        childList = rootNode.getChildNodes();
        childToRemove = childList.item(1);
        removedChild = rootNode.removeChild(childToRemove);
        parentNode = removedChild.getParentNode();
        assertNull("nodeRemoveChildAssert1", parentNode);
    }

}

