
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
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * If the sum of the "offset" and "count" exceeds the
 * "length" then the "substringData(offset,count)" method
 * returns all the characters to the end of the data.
 * <p>
 * Retrieve the character data from the second child
 * of the first employee and access part of the data
 * by using the substringData(offset,count) method
 * with offset=9 and count=10.  The method should return
 * the substring "Martin" since offset+count &#62;length
 * (19 &#62;15).
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6531BCCF">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6531BCCF</a>
 */
public class characterdatasubstringexceedsvalueTest extends LoboUnitTest {

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
        String substring;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("name");
        nameNode = elementList.item(0);
        child = (CharacterData) nameNode.getFirstChild();
        substring = child.substringData(9, 6);
        assertEquals("characterdataSubStringExceedsValueAssert", "Martin", substring);
    }
}

