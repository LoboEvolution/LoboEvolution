
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The "getElementsByTagName(name)" method may use the
 * special value "*" to match all tags in the element
 * tree.
 * Create a NodeList of all the descendant elements
 * of the last employee by using the special value "*".
 * The method should return all the descendant children(6)
 * in the order the children were encountered.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1938918D</a>
 */
public class hc_elementgetelementsbytagnamespecialvalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Element lastEmployee;
        HTMLCollection lastempList;
        Node child;
        String childName;
        List<String> result = new ArrayList<>();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("EM");
        expectedResult.add("STRONG");
        expectedResult.add("CODE");
        expectedResult.add("SUP");
        expectedResult.add("VAR");
        expectedResult.add("ACRONYM");

        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("p");
        lastEmployee = (Element) elementList.item(4);
        lastempList = lastEmployee.getElementsByTagName("*");
        for (int indexN10067 = 0; indexN10067 < lastempList.getLength(); indexN10067++) {
            child = lastempList.item(indexN10067);
            childName = child.getNodeName();
            result.add(childName);
        }
        assertEquals("element", expectedResult, result);
    }
}

