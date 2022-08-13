
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
 * Prepends a document fragment to an attribute and checks if the value of
 * the attribute is changed.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-952280727</a>
 */
public class hc_attrinsertbefore4Test extends LoboUnitTest {

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
        Text terNode;
        Text dayNode;
        DocumentFragment docFrag;
        Node retval;
        Node firstChild;
        Node lastChild;
        Node refChild;
        doc = sampleXmlFile("hc_staff.xml");
        acronymList = doc.getElementsByTagName("acronym");
        testNode = (Element) acronymList.item(3);
        attributes = testNode.getAttributes();
        titleAttr = attributes.getNamedItem("title");
        terNode = doc.createTextNode("ter");
        dayNode = doc.createTextNode("day");
        docFrag = doc.createDocumentFragment();
        retval = docFrag.appendChild(terNode);
        retval = docFrag.appendChild(dayNode);
        refChild = titleAttr.getFirstChild();
        retval = titleAttr.insertBefore(docFrag, refChild);
        value = titleAttr.getValue();
        assertEquals("attrValue", "terdayYes", value);
        value = titleAttr.getNodeValue();
        assertEquals("attrNodeValue", "terdayYes", value);
        value = retval.getNodeValue();
        assertNull("retvalValue", value);
        firstChild = titleAttr.getFirstChild();
        value = firstChild.getNodeValue();
        assertEquals("firstChildValue", "ter", value);
        lastChild = titleAttr.getLastChild();
        value = lastChild.getNodeValue();
        assertEquals("lastChildValue", "Yes", value);
    }
}

