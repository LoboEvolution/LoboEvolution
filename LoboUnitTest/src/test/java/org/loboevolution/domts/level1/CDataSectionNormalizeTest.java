
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

