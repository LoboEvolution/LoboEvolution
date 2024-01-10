
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.NodeList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "createDocumentFragment()" method creates an empty
 * DocumentFragment object.
 * Retrieve the entire DOM document and invoke its
 * "createDocumentFragment()" method.  The content, name,
 * type and value of the newly created object are output.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5</a>
 */
public class HcdocumentcreatedocumentfragmentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment newDocFragment;
        final NodeList children;
        final int length;
        final String newDocFragmentName;
        final int newDocFragmentType;
        final String newDocFragmentValue;
        doc = sampleXmlFile("hc_staff.xml");
        newDocFragment = doc.createDocumentFragment();
        children = newDocFragment.getChildNodes();
        length = children.getLength();
        assertEquals( 0, length, "HcdocumentcreatedocumentfragmentAssert1");
        newDocFragmentName = newDocFragment.getNodeName();
        assertEquals("[object DocumentFragment]", newDocFragmentName, "HcdocumentcreatedocumentfragmentAssert2");
        newDocFragmentType = newDocFragment.getNodeType();
        assertEquals(11, newDocFragmentType, "HcdocumentcreatedocumentfragmentAssert3");
        newDocFragmentValue = newDocFragment.getNodeValue();
        assertNull(newDocFragmentValue, "HcdocumentcreatedocumentfragmentAssert4");
    }
}

