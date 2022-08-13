
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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Add an empty text node to an existing attribute node, normalize the containing element
 * and check that the attribute node has eliminated the empty text.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=482">http://www.w3.org/Bugs/Public/show_bug.cgi?id=482</a>
 */
public class hc_elementnormalize2Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element root;
        HTMLCollection elementList;
        Element element;
        Node firstChild;
        Node secondChild;
        String childValue;
        Text emptyText;
        Attr attrNode;
        Node retval;
        doc = sampleXmlFile("hc_staff.xml");
        root = doc.getDocumentElement();
        emptyText = doc.createTextNode("");
        elementList = root.getElementsByTagName("acronym");
        element = (Element) elementList.item(0);
        attrNode = element.getAttributeNode("title");
        retval = attrNode.appendChild(emptyText);
        element.normalize();
        attrNode = element.getAttributeNode("title");
        firstChild = attrNode.getFirstChild();
        childValue = firstChild.getNodeValue();
        assertEquals("firstChild", "Yes", childValue);
        secondChild = firstChild.getNextSibling();
        assertNull("secondChildNull", secondChild);
    }
}

