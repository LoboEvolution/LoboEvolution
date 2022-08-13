
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
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * After the "splitText(offset)" method breaks the Text node
 * into two Text nodes, the original node contains all the
 * content up to the offset point.
 * <p>
 * Retrieve the textual data from the second child of the
 * third employee and invoke the "splitText(offset)" method.
 * The original Text node should contain all the content
 * up to the offset point.   The "getNodeValue()" method
 * is called to check that the original node now contains
 * the first five characters.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-38853C1D">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-38853C1D</a>
 */
public class hc_textsplittexttwoTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node nameNode;
        Text textNode;
        Text splitNode;
        String value;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("strong");
        nameNode = elementList.item(2);
        textNode = (Text) nameNode.getFirstChild();
        splitNode = textNode.splitText(5);
        value = textNode.getNodeValue();
        assertEquals("textSplitTextTwoAssert", "Roger", value);
    }
}

