
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
 * The "replaceData(offset,count,arg)" method replaces the
 * characters starting at the specified offset with the
 * specified string.  Test the situation where the length
 * of the arg string is greater than the specified offset.
 * <p>
 * Retrieve the character data from the last child of the
 * first employee.  The "replaceData(offset,count,arg)"
 * method is then called with offset=0 and count=4 and
 * arg="260030".  The method should replace characters one
 * thru four with "260030".  Note that the length of the
 * specified string is greater that the specified offset.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-E5CBA7FB">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-E5CBA7FB</a>
 */
public class characterdatareplacedataexceedslengthofargTest extends LoboUnitTest {

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
        child.replaceData(0, 4, "260030");
        childData = child.getData();
        assertEquals("characterdataReplaceDataExceedsLengthOfArgAssert", "260030 North Ave. Dallas, Texas 98551", childData);
    }
}

