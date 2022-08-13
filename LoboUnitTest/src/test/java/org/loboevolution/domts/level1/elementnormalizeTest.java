
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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The "normalize()" method puts all the nodes in the full
 * depth of the sub-tree underneath this element into a
 * "normal" form.
 * <p>
 * Retrieve the third employee and access its second child.
 * This child contains a block of text that is spread
 * across multiple lines.  The content of the "name" child
 * should be parsed and treated as a single Text node.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083</a>
 */
public class elementnormalizeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element root;
        HTMLCollection elementList;
        Element testName;
        Node firstChild;
        String childValue;
        doc = sampleXmlFile("staff.xml");
        root = doc.getDocumentElement();
        root.normalize();
        elementList = root.getElementsByTagName("name");
        testName = (Element) elementList.item(2);
        firstChild = testName.getFirstChild();
        childValue = firstChild.getNodeValue();
        assertEquals("elementNormalizeAssert", "Roger\n Jones", childValue);
    }
}

