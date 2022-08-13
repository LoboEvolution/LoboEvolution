
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
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertNull;


/**
 * The "getAttributes()" method invoked on a CDATASection
 * Node returns null.
 * Retrieve the CDATASection node contained inside the
 * second child of the second employee and invoke the
 * "getAttributes()" method on the CDATASection node.
 * It should return null.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-84CF096</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-667469212">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-667469212</a>
 */
public class nodecdatasectionnodeattributeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element cdataName;
        Element cdataNode;
        NamedNodeMap attrList;
        int nodeType;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("name");
        cdataName = (Element) elementList.item(1);
        cdataNode = (Element) cdataName.getLastChild();
        nodeType = cdataNode.getNodeType();

        if (nodeType != 4) {
            cdataNode = (Element) doc.createCDATASection("");
        }
        attrList = cdataNode.getAttributes();
        assertNull("cdataSection", attrList);
    }
}

