
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


/**
 * The tagName parameter in the "createElement(tagName)"
 * method is case-sensitive for XML documents.
 * Retrieve the entire DOM document and invoke its
 * "createElement(tagName)" method twice.  Once for tagName
 * equal to "acronym" and once for tagName equal to "ACRONYM"
 * Each call should create a distinct Element node.  The
 * newly created Elements are then assigned attributes
 * that are retrieved.
 * Modified on 27 June 2003 to avoid setting an invalid style
 * values and checked the node names to see if they matched expectations.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class hc_documentcreateelementcasesensitiveTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element newElement1;
        Element newElement2;
        String attribute1;
        String attribute2;
        String nodeName1;
        String nodeName2;
        doc = sampleXmlFile("hc_staff.xml");
        newElement1 = doc.createElement("ACRONYM");
        newElement2 = doc.createElement("acronym");
        newElement1.setAttribute("lang", "EN");
        newElement2.setAttribute("title", "Dallas");
        attribute1 = newElement1.getAttribute("lang");
        attribute2 = newElement2.getAttribute("title");
        assertEquals("attrib1", "EN", attribute1);
        assertEquals("attrib2", "Dallas", attribute2);
        nodeName1 = newElement1.getNodeName();
        nodeName2 = newElement2.getNodeName();
        assertEquals("nodeName1", "ACRONYM", nodeName1);
        assertEquals("nodeName2", "acronym", nodeName2);
    }
}

