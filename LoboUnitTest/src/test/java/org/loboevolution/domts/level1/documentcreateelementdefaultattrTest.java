
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * The "createElement(tagName)" method creates an Element
 * of the type specified.  In addition, if there are known attributes
 * with default values, Attr nodes representing them are automatically
 * created and attached to the element.
 * Retrieve the entire DOM document and invoke its
 * "createElement(tagName)" method with tagName="address".
 * The method should create an instance of an Element node
 * whose tagName is "address".  The tagName "address" has an
 * attribute with default values, therefore the newly created element
 * will have them.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html">http://lists.w3.org/Archives/Public/www-dom-ts/2002Mar/0002.html</a>
 */
public class documentcreateelementdefaultattrTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element newElement;
        NamedNodeMap defaultAttr;
        Node child;
        String name;
        String value;
        doc = sampleXmlFile("staff.xml");
        newElement = doc.createElement("address");
        defaultAttr = newElement.getAttributes();
        child = defaultAttr.item(0);
        assertNotNull("defaultAttrNotNull", child);
        name = child.getNodeName();
        assertEquals("attrName", "street", name);
        value = child.getNodeValue();
        assertEquals("attrValue", "Yes", value);
    }
}

