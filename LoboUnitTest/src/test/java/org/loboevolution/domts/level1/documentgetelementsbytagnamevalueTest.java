
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
 * The "getElementsByTagName(tagName)" method returns a
 * NodeList of all the Elements with a given tagName
 * in a pre-order traversal of the tree.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "getElementsByTagName(tagName)" method with tagName
 * equal to "name".  The method should return a NodeList
 * that contains 5 elements.  The FOURTH item in the
 * list is retrieved and output.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-A6C9094">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-A6C9094</a>
 */
public class documentgetelementsbytagnamevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        Node nameNode;
        Node firstChild;
        String childValue;
        doc = sampleXmlFile("staff.xml");
        nameList = doc.getElementsByTagName("name");
        nameNode = nameList.item(3);
        firstChild = nameNode.getFirstChild();
        childValue = firstChild.getNodeValue();
        assertEquals("documentGetElementsByTagNameValueAssert", "Jeny Oconnor", childValue);
    }
}
