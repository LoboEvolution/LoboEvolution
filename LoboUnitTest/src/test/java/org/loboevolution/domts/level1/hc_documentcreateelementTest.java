
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;


/**
 * The "createElement(tagName)" method creates an Element
 * of the type specified.
 * Retrieve the entire DOM document and invoke its
 * "createElement(tagName)" method with tagName="acronym".
 * The method should create an instance of an Element node
 * whose tagName is "acronym".  The NodeName, NodeType
 * and NodeValue are returned.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 */
public class hc_documentcreateelementTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element newElement;
        String newElementName;
        int newElementType;
        String newElementValue;
        doc = sampleXmlFile("hc_staff.xml");
        newElement = doc.createElement("acronym");
        newElementName = newElement.getNodeName();
        assertEquals("strong", "acronym", newElementName);
        newElementType = newElement.getNodeType();
        assertEquals("type", 1, newElementType);
        newElementValue = newElement.getNodeValue();
        assertNull("valueInitiallyNull", newElementValue);
    }

}

