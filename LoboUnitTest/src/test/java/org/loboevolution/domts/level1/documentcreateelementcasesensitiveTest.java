
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * The tagName parameter in the "createElement(tagName)"
 * method is case-sensitive for XML documents.
 * Retrieve the entire DOM document and invoke its
 * "createElement(tagName)" method twice.  Once for tagName
 * equal to "address" and once for tagName equal to "ADDRESS"
 * Each call should create a distinct Element node.  The
 * newly created Elements are then assigned attributes
 * that are retrieved.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 */
public class documentcreateelementcasesensitiveTest extends LoboUnitTest {


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
        doc = sampleXmlFile("staff.xml");
        newElement1 = doc.createElement("ADDRESS");
        newElement2 = doc.createElement("address");
        newElement1.setAttribute("district", "Fort Worth");
        newElement2.setAttribute("county", "Dallas");
        attribute1 = newElement1.getAttribute("district");
        attribute2 = newElement2.getAttribute("county");
        assertEquals("attrib1", "Fort Worth", attribute1);
        assertEquals("attrib2", "Dallas", attribute2);
    }

}

