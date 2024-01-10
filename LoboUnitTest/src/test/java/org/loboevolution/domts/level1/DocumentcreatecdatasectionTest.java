
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.*;


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
public class DocumentcreatecdatasectionTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final CDATASection newCDATASectionNode;
        final String newCDATASectionValue;
        final String newCDATASectionName;
        final int newCDATASectionType;
        doc = sampleXmlFile("staff.xml");
        newCDATASectionNode = doc.createCDATASection("This is a new CDATASection node");
        newCDATASectionValue = newCDATASectionNode.getNodeValue();
         assertEquals( "This is a new CDATASection node", newCDATASectionValue, "DocumentcreatecdatasectionAssert1");
        newCDATASectionName = newCDATASectionNode.getNodeName();
         assertEquals( "#cdata-section", newCDATASectionName, "DocumentcreatecdatasectionAssert2");
        newCDATASectionType = newCDATASectionNode.getNodeType();
        assertEquals(4, newCDATASectionType, "DocumentcreatecdatasectionAssert3");
    }
}

