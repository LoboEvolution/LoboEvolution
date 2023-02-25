
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * Adjacent CDATASection nodes cannot be merged together by
 * use of the "normalize()" method from the Element interface.
 * Retrieve second child of the second employee and invoke
 * the "normalize()" method.  The Element under contains
 * two CDATASection nodes that should not be merged together
 * by the "normalize()" method.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-162CF083</a>
 */
public class CDataSectionNormalizeTest extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        Element lChild;
        NodeList childNodes;
        CDATASection cdataN;
        String data;
        doc = sampleXmlFile("staff.xml");
        nameList = doc.getElementsByTagName("name");
        lChild = (Element) nameList.item(1);
        lChild.normalize();
        childNodes = lChild.getChildNodes();
        cdataN = (CDATASection) childNodes.item(1);
        assertNotNull("firstCDATASection", cdataN);
        data = cdataN.getData();
        assertEquals("data1", "This is a CDATASection with EntityReference number 2 &ent2;", data);
        cdataN = (CDATASection) childNodes.item(3);
        assertNotNull("secondCDATASection", cdataN);
        data = cdataN.getData();
        assertEquals("data3", "This is an adjacent CDATASection with a reference to a tab &tab;", data);
    }
}

