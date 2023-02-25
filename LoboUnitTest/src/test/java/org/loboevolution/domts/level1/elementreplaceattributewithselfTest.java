
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertSame;


/**
 * This test calls setAttributeNode to replace an attribute with itself.
 * Since the node is not an attribute of another Element, it would
 * be inappropriate to throw an INUSE_ATTRIBUTE_ERR.
 * This test was derived from elementinuserattributeerr which
 * inadvertanly made this test.
 *
 * @author Curt Arnold
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-887236154</a>
 */
public class elementreplaceattributewithselfTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testEmployee;
        Attr streetAttr;
        Attr replacedAttr;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("address");
        testEmployee = (Element) elementList.item(2);
        streetAttr = testEmployee.getAttributeNode("street");
        replacedAttr = testEmployee.setAttributeNode(streetAttr);
        assertSame("replacedAttr", streetAttr, replacedAttr);
    }
}

