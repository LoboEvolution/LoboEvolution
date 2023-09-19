
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
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.*;


/**
 * The "getData()" method returns the content of the
 * processing instruction.  It starts at the first non
 * white character following the target and ends at the
 * character immediately preceding the "?&#62;".
 * <p>
 * Retrieve the ProcessingInstruction node located
 * immediately after the prolog.  Create a nodelist of the
 * child nodes of this document.  Invoke the "getData()"
 * method on the first child in the list. This should
 * return the content of the ProcessingInstruction.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-837822393</a>
 */
public class processinginstructiongetdataTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NodeList childNodes;
        ProcessingInstruction piNode;
        String data;
        doc = sampleXmlFile("staff.xml");
        childNodes = doc.getChildNodes();
        piNode = (ProcessingInstruction) childNodes.item(0);
        data = piNode.getData();
        assertEquals("processinginstructionGetTargetAssert", "PIDATA", data);
    }

}

