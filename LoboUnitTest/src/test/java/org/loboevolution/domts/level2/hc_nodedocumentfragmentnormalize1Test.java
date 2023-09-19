
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;


/**
 * Create a document fragment with two adjacent text nodes, normalize and see if the text nodes
 * were combined.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-B63ED1A3">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-B63ED1A3</a>
 */
public class hc_nodedocumentfragmentnormalize1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        String nodeValue;
        Text txtNode;
        Node retval;
        doc = sampleXmlFile("hc_staff.xml");
        docFragment = doc.createDocumentFragment();
        txtNode = doc.createTextNode("foo");
        retval = docFragment.appendChild(txtNode);
        txtNode = doc.createTextNode("bar");
        retval = docFragment.appendChild(txtNode);
        docFragment.normalize();
        txtNode = (Text) docFragment.getFirstChild();
        nodeValue = txtNode.getNodeValue();
        assertEquals("normalizedNodeValue", "foobar", nodeValue);
        retval = txtNode.getNextSibling();
        assertNull("singleChild", retval);
    }
}

