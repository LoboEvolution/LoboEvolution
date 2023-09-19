
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
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "createProcessingInstruction(target,data)" method
 * creates a new ProcessingInstruction node with the
 * specified name and data string.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createProcessingInstruction(target,data)" method.
 * It should create a new PI node with the specified target
 * and data.  The target, data and type are retrieved and
 * output.
 *
 * @author NIST
 * @author Mary Brady
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#</a>
 * @see <a href="http://lists.w3.org/Archives/Public/www-dom-ts/2001Apr/0020.html">http://lists.w3.org/Archives/Public/www-dom-ts/2001Apr/0020.html</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-135944439">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-135944439</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=249">http://www.w3.org/Bugs/Public/show_bug.cgi?id=249</a>
 */
public class documentcreateprocessinginstructionTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction newPINode;
        String piValue;
        String piName;
        int piType;
        doc = sampleXmlFile("staff.xml");
        newPINode = doc.createProcessingInstruction("TESTPI", "This is a new PI node");
        assertNotNull("createdPINotNull", newPINode);
        piName = newPINode.getNodeName();
        assertEquals("name", "TESTPI", piName);
        piValue = newPINode.getNodeValue();
        assertEquals("value", "This is a new PI node", piValue);
        piType = newPINode.getNodeType();
        assertEquals("type", 7, piType);
    }
}

