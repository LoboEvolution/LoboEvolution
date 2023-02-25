
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.*;


/**
 * The "createCDATASection(data)" method creates a new
 * CDATASection node whose value is the specified string.
 * Retrieve the entire DOM document and invoke its
 * "createCDATASection(data)" method.  It should create a
 * new CDATASection node whose "data" is the specified
 * string.  The content, name and type are retrieved and
 * output.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D26C0AF8">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D26C0AF8</a>
 */
public class documentcreatecdatasectionTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        CDATASection newCDATASectionNode;
        String newCDATASectionValue;
        String newCDATASectionName;
        int newCDATASectionType;
        doc = sampleXmlFile("staff.xml");
        newCDATASectionNode = doc.createCDATASection("This is a new CDATASection node");
        newCDATASectionValue = newCDATASectionNode.getNodeValue();
        assertEquals("nodeValue", "This is a new CDATASection node", newCDATASectionValue);
        newCDATASectionName = newCDATASectionNode.getNodeName();
        assertEquals("nodeName", "#cdata-section", newCDATASectionName);
        newCDATASectionType = newCDATASectionNode.getNodeType();
        assertEquals("nodeType", 4, newCDATASectionType);
    }
}

