
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
 * On successful invocation of the "appendData(arg)"
 * method the "getData()" method provides access to the
 * concatentation of data and the specified string.
 * <p>
 * Retrieve the character data from the second child
 * of the first employee.  The appendData(arg) method is
 * called with arg=", Esquire".  The method should append
 * the specified data to the already existing character
 * data.  The new value return by the "getData()" method
 * should be "Margaret Martin, Esquire".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-72AB8359</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-32791A2F">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-32791A2F</a>
 */
public class hc_characterdataappenddatagetdataTest extends LoboUnitTest {

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
        elementList = doc.getElementsByTagName("strong");
        nameNode = elementList.item(0);
        child = (CharacterData) nameNode.getFirstChild();
        child.appendData(", Esquire");
        childData = child.getData();
        assertEquals("characterdataAppendDataGetDataAssert", "Margaret Martin, Esquire", childData);
    }
}

