
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The "normalize()" method puts all the nodes in the full
 * depth of the sub-tree underneath this element into a
 * "normal" form.
 * <p>
 * Retrieve the third employee and access its second child.
 * This child contains a block of text that is spread
 * across multiple lines.   The content of the "name" child
 * should be parsed and treated as a single Text node.
 * This appears to be a duplicate of elementnormalize.xml in DOM L1 Test Suite
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-72AB8359">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-72AB8359</a>
 */
public class normalize01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element root;
        HTMLCollection elementList;
        Node firstChild;
        NodeList textList;
        CharacterData textNode;
        String data;
        doc = sampleXmlFile("staff.xml");
        root = doc.getDocumentElement();
        root.normalize();
        elementList = root.getElementsByTagName("name");
        firstChild = elementList.item(2);
        textList = firstChild.getChildNodes();
        textNode = (CharacterData) textList.item(0);
        data = textNode.getData();
        assertEquals("data", "Roger\n Jones", data);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/normalize01";
    }
}

