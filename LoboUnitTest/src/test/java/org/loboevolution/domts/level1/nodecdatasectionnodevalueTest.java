
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
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertEquals;


/**
 * The string returned by the "getNodeValue()" method for a
 * CDATASection Node is the content of the CDATASection.
 * <p>
 * Retrieve the CDATASection node inside the second child
 * of the second employee and check the string returned
 * by the "getNodeValue()" method.   It should be equal to
 * "This is a CDATA Section with EntityReference number 2
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-667469212">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-667469212</a>
 */
public class nodecdatasectionnodevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element cdataName;
        NodeList childList;
        Node child;
        String cdataNodeValue;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("name");
        cdataName = (Element) elementList.item(1);
        childList = cdataName.getChildNodes();
        child = childList.item(1);

        if ((child == null)) {
            child = doc.createCDATASection("This is a CDATASection with EntityReference number 2 &ent2;");
        }
        cdataNodeValue = child.getNodeValue();
        assertEquals("value", "This is a CDATASection with EntityReference number 2 &ent2;", cdataNodeValue);
    }
}

