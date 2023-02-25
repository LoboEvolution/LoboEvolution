
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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * The string returned by the "getNodeValue()" method for a
 * Text Node is the content of the Text node.
 * <p>
 * Retrieve the Text node from the last child of the first
 * employee and check the string returned by the
 * "getNodeValue()" method.   It should be equal to
 * "1230 North Ave. Dallas, Texas 98551".
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 */
public class hc_nodetextnodevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element testAddr;
        Node textNode;
        String textValue;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("acronym");
        testAddr = (Element) elementList.item(0);
        textNode = testAddr.getFirstChild();
        textValue = textNode.getNodeValue();
        assertEquals("textNodeValue", "1230 North Ave. Dallas, Texas 98551", textValue);
    }
}

