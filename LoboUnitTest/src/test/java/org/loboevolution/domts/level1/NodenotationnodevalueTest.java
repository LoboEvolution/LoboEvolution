
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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The string returned by the "getNodeValue()" method for a
 * Notation Node is null.
 * Retrieve the Notation declaration inside the
 * DocumentType node and check the string returned
 * by the "getNodeValue()" method.   It should be equal to
 * null.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 */
public class NodenotationnodevalueTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap notations;
        final Node notationNode;
        final String notationValue;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull(docType, "NodenotationnodevalueAssert3");
        notations = docType.getNotations();
        assertNotNull(notations, "NodenotationnodevalueAssert4");
        notationNode = notations.getNamedItem("notation1");
        assertNotNull(notationNode, "NodenotationnodevalueAssert5");
        notationValue = notationNode.getNodeValue();
        assertNull(notationValue, "NodenotationnodevalueAssert6");
    }

}

