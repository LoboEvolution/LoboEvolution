
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=243">http://www.w3.org/Bugs/Public/show_bug.cgi?id=243</a>
 */
public class HcdocumentcreateelementcasesensitiveTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element newElement1;
        final Element newElement2;
        final String attribute1;
        final String attribute2;
        final String nodeName1;
        final String nodeName2;
        doc = sampleXmlFile("hc_staff.xml");
        newElement1 = doc.createElement("ACRONYM");
        newElement2 = doc.createElement("acronym");
        newElement1.setAttribute("lang", "EN");
        newElement2.setAttribute("title", "Dallas");
        attribute1 = newElement1.getAttribute("lang");
        attribute2 = newElement2.getAttribute("title");
        assertEquals("EN", attribute1, "HcdocumentcreateelementcasesensitiveAssert1");
        assertEquals("Dallas", attribute2, "HcdocumentcreateelementcasesensitiveAssert2");
        nodeName1 = newElement1.getNodeName();
        nodeName2 = newElement2.getNodeName();
        assertEquals("ACRONYM", nodeName1, "HcdocumentcreateelementcasesensitiveAssert3");
        assertEquals("ACRONYM", nodeName2, "HcdocumentcreateelementcasesensitiveAssert4");
    }
}

