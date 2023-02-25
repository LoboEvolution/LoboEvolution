
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
 * The "deleteData(offset,count)" method removes a range of
 * characters from the node.  Delete data at the beginning
 * of the character data.
 * Retrieve the character data from the last child of the
 * first employee.  The "deleteData(offset,count)"
 * method is then called with offset=0 and count=16.
 * The method should delete the characters from position
 * 0 thru position 16.  The new value of the character data
 * should be "Dallas, Texas 98551".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-7C603781">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-7C603781</a>
 */
public class characterdatadeletedatabeginingTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node nameNode;
        CharacterData child;
        String childData;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        nameNode = elementList.item(0);
        child = (CharacterData) nameNode.getFirstChild();
        child.deleteData(0, 16);
        childData = child.getData();
        assertEquals("characterdataDeleteDataBeginingAssert", "Dallas, Texas 98551", childData);
    }

}

