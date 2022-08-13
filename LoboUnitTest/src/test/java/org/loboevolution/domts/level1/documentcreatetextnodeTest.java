
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
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * The "createTextNode(data)" method creates a Text node
 * given the specfied string.
 * Retrieve the entire DOM document and invoke its
 * "createTextNode(data)" method.  It should create a
 * new Text node whose "data" is the specified string.
 * The NodeName and NodeType are also checked.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1975348127">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1975348127</a>
 */
public class documentcreatetextnodeTest extends LoboUnitTest {
    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Text newTextNode;
        String newTextName;
        String newTextValue;
        int newTextType;
        doc = sampleXmlFile("staff.xml");
        newTextNode = doc.createTextNode("This is a new Text node");
        newTextValue = newTextNode.getNodeValue();
        assertEquals("value", "This is a new Text node", newTextValue);
        newTextName = newTextNode.getNodeName();
        assertEquals("name", "#text", newTextName);
        newTextType = newTextNode.getNodeType();
        assertEquals("type", 3, newTextType);
    }
}

