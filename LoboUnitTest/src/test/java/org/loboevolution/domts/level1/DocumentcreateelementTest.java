
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
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The "createElement(tagName)" method creates an Element
 * of the type specified.
 * Retrieve the entire DOM document and invoke its
 * "createElement(tagName)" method with tagName="address".
 * The method should create an instance of an Element node
 * whose tagName is "address".  The NodeName, NodeType
 * and NodeValue are returned.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547</a>
 */
public class DocumentcreateelementTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element newElement;
        final String newElementName;
        final int newElementType;
        final String newElementValue;
        doc = sampleXmlFile("staff.xml");
        newElement = doc.createElement("address");
        newElementName = newElement.getNodeName();
        assertEquals("ADDRESS", newElementName, "DocumentcreateelementAssert1");
        newElementType = newElement.getNodeType();
        assertEquals(1, newElementType, "DocumentcreateelementAssert2");
        newElementValue = newElement.getNodeValue();
        assertNull(newElementValue, "DocumentcreateelementAssert3");
    }
}

