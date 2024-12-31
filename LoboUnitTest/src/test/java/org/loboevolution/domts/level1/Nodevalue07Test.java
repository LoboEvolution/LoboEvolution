
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

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * An Entity is accessed, setNodeValue is called with a non-null argument, but getNodeValue
 * should still return null.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-527DCFF2">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-527DCFF2</a>
 */
public class Nodevalue07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Node newNode;
        String newValue;
        final NamedNodeMap nodeMap;
        final DocumentType docType;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull(docType, "Nodevalue07Assert1");
        nodeMap = docType.getEntities();
        assertNotNull(nodeMap, "Nodevalue07Assert2");
        newNode = nodeMap.getNamedItem("ent1");
        assertNotNull(newNode, "Nodevalue07Assert3");
        newValue = newNode.getNodeValue();
        assertNotNull(newValue, "Nodevalue07Assert4");
        boolean success = false;
        try {
            newNode.setNodeValue("This should have no effect");
        } catch (final DOMException ex) {
            success = (ex.getCode() == DOMException.INVALID_MODIFICATION_ERR);
        }
        assertTrue(success, "Nodevalue07Assert5");
        newValue = newNode.getNodeValue();
        assertNull(newValue, "Nodevalue07Assert6");
    }

}

