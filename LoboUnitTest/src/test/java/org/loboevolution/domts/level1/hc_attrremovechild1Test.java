
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

import static org.junit.Assert.*;


/**
 * Removes the child node of an attribute and checks that the value is empty.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1734834066</a>
 */
public class hc_attrremovechild1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection acronymList;
        Element testNode;
        NamedNodeMap attributes;
        Attr titleAttr;
        String value;
        Text textNode;
        Node retval;
        Node firstChild;
        doc = sampleXmlFile("hc_staff.xml");
        acronymList = doc.getElementsByTagName("acronym");
        testNode = (Element) acronymList.item(3);
        attributes = testNode.getAttributes();
        titleAttr = (Attr) attributes.getNamedItem("title");
        textNode = (Text) titleAttr.getFirstChild();
        assertNotNull("attrChildNotNull", textNode);
        retval = titleAttr.removeChild(textNode);
        value = titleAttr.getValue();
        assertEquals("attrValue", "", value);
        value = titleAttr.getNodeValue();
        assertEquals("attrNodeValue", "", value);
        value = retval.getNodeValue();
        assertEquals("retvalValue", "Yes", value);
        firstChild = titleAttr.getFirstChild();
        assertNull("firstChildNull", firstChild);
    }
}

