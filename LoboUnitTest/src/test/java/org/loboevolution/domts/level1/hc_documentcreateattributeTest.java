
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertEquals;


/**
 * Retrieve the entire DOM document and invoke its
 * "createAttribute(name)" method.  It should create a
 * new Attribute node with the given name. The name, value
 * and type of the newly created object are retrieved and
 * output.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1084891198">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1084891198</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=236">http://www.w3.org/Bugs/Public/show_bug.cgi?id=236</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2003Jun/0011.html">http://lists.w3.org/Archives/Public/www-dom-ts/2003Jun/0011.html</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class hc_documentcreateattributeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr newAttrNode;
        String attrValue;
        String attrName;
        int attrType;
        doc = sampleXmlFile("hc_staff.xml");
        newAttrNode = doc.createAttribute("title");
        attrValue = newAttrNode.getNodeValue();
        assertEquals("value", "", attrValue);
        attrName = newAttrNode.getNodeName();
        assertEquals( "name", "title", attrName);
        attrType = newAttrNode.getNodeType();
        assertEquals("type", 2, attrType);
    }
}

