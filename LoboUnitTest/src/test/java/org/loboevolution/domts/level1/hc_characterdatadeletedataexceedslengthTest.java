
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
 * If the sum of the offset and count used in the
 * "deleteData(offset,count) method is greater than the
 * length of the character data then all the characters
 * from the offset to the end of the data are deleted.
 * <p>
 * Retrieve the character data from the last child of the
 * first employee.  The "deleteData(offset,count)"
 * method is then called with offset=4 and count=50.
 * The method should delete the characters from position 4
 * to the end of the data since the offset+count(50+4)
 * is greater than the length of the character data(35).
 * The new value of the character data should be "1230".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-7C603781">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-7C603781</a>
 */
public class hc_characterdatadeletedataexceedslengthTest extends LoboUnitTest {

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
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        nameNode = elementList.item(0);
        child = (CharacterData) nameNode.getFirstChild();
        child.deleteData(4, 50);
        childData = child.getData();
        assertEquals("characterdataDeleteDataExceedsLengthAssert", "1230", childData);
    }
}

