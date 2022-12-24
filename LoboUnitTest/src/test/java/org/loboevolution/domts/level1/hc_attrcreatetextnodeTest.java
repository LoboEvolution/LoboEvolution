
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertEquals;


/**
 * The "setValue()" method for an attribute creates a
 * Text node with the unparsed content of the string.
 * Retrieve the attribute named "class" from the last
 * child of the fourth employee and assign the "Y&#38;ent1;"
 * string to its value attribute.  This value is not yet
 * parsed and therefore should still be the same upon
 * retrieval. This test uses the "getNamedItem(name)" method
 * from the NamedNodeMap interface.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-221662474</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2002Apr/0057.html">http://lists.w3.org/Archives/Public/www-dom-ts/2002Apr/0057.html</a>
 */
public class hc_attrcreatetextnodeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection addressList;
        Element testNode;
        NamedNodeMap attributes;
        Attr streetAttr;
        String value;
        doc = sampleXmlFile("hc_staff.xml");
        addressList = doc.getElementsByTagName("acronym");
        testNode = (Element) addressList.item(3);
        attributes = testNode.getAttributes();
        streetAttr = (Attr) attributes.getNamedItem("class");
        streetAttr.setValue("Y&ent1;");
        value = streetAttr.getValue();
        assertEquals("value", "Y&ent1;", value);
        value = streetAttr.getNodeValue();
        assertEquals("nodeValue", "Y&ent1;", value);
    }
}

