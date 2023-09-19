
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.CharacterData;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;


/**
 * The "normalize()" method puts all the nodes in the full
 * depth of the sub-tree underneath this element into a
 * "normal" form.
 * <p>
 * Retrieve the third employee and access its second child.
 * This child contains a block of text that is spread
 * across multiple lines.   The content of the "name" child
 * should be parsed and treated as a single Text node.
 * This appears to be a duplicate of elementnormalize.xml in DOM L1 Test Suite
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-normalize</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-72AB8359">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-72AB8359</a>
 */
public class normalize01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element root;
        HTMLCollection elementList;
        Node firstChild;
        NodeList textList;
        CharacterData textNode;
        String data;
        doc = sampleXmlFile("staff.xml");
        root = doc.getDocumentElement();
        root.normalize();
        elementList = root.getElementsByTagName("name");
        firstChild = elementList.item(2);
        textList = firstChild.getChildNodes();
        textNode = (CharacterData) textList.item(0);
        data = textNode.getData();
        assertEquals("data", "Roger\n Jones", data);
    }
}

