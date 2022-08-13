
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


/**
 * Creates an new attribute node and appends a text node.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107</a>
 */
public class hc_attrappendchild6Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr titleAttr;
        String value;
        Text textNode;
        Node retval;
        Node lastChild;
        doc = sampleXmlFile("hc_staff.xml");
        titleAttr = doc.createAttribute("title");
        textNode = doc.createTextNode("Yesterday");
        retval = titleAttr.appendChild(textNode);
        value = titleAttr.getValue();
        assertEquals("attrValue", "Yesterday", value);
        value = titleAttr.getNodeValue();
        assertEquals("attrNodeValue", "Yesterday", value);
        value = retval.getNodeValue();
        assertEquals("retvalValue", "Yesterday", value);
        lastChild = titleAttr.getLastChild();
        value = lastChild.getNodeValue();
        assertEquals("lastChildValue", "Yesterday", value);
    }
}

