
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The "getNextSibling()" method returns the node immediately
 * following this node.
 * <p>
 * Retrieve the first child of the second employee and
 * invoke the "getNextSibling()" method.   It should return
 * a node with the NodeName of "#text".

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6AC54C2F">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6AC54C2F</a>
 */
public class HcnodegetnextsiblingTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elementList;
        final Node emNode;
        final Node nsNode;
        final String nsName;
        doc = sampleXmlFile("hc_staff.xml");
        elementList = doc.getElementsByTagName("em");
        emNode = elementList.item(1);
        nsNode = emNode.getNextSibling();
        nsName = nsNode.getNodeName();
        assertEquals("#text", nsName, "HcnodegetnextsiblingAssert1");
    }
}

