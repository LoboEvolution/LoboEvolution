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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Invoke the adoptNode method on another document using a new CDataSection node created in this
 * Document as the source.  Verify if the node has been adopted correctly by checking the nodeValue
 * of the adopted node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode32Test extends LoboUnitTest {    
    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Document docAdopter;
        final Node newCDATA;
        final Node adoptedCDATA;
        final String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        docAdopter = sampleXmlFile("hc_staff.xml");
        newCDATA = doc.createCDATASection("Document.adoptNode test for a CDATASECTION_NODE");
        adoptedCDATA = docAdopter.adoptNode(newCDATA);

        if ((adoptedCDATA != null)) {
            nodeValue = adoptedCDATA.getNodeValue();
            assertEquals("Document.adoptNode test for a CDATASECTION_NODE", nodeValue, "Documentadoptnode32Assert2");
        }
    }
}

