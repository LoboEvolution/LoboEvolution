
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
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * Retrieve the last CDATASection node located inside the
 * second child of the second employee and examine its
 * content.  Since the CDATASection interface inherits
 * from the CharacterData interface(via the Text node),
 * the "getData()" method can be used to access the
 * CDATA content.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359</a>
 */
public class CDataSectionGetDataTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        Node child;
        Node lastChild;
        String data;
        int nodeType;
        doc = sampleXmlFile("staff.xml");
        nameList = doc.getElementsByTagName("name");
        child = nameList.item(1);
        lastChild = child.getLastChild();
        nodeType = lastChild.getNodeType();
        assertEquals("isCDATA", 4, nodeType);
        data = ((CharacterData) lastChild).getData();
        assertEquals("data", "This is an adjacent CDATASection with a reference to a tab &tab;", data);
    }
}

