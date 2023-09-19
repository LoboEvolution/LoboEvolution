
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
import org.loboevolution.html.node.Element;

import static org.junit.Assert.*;


/**
 * The method setPrefix sets the namespace prefix of this node.  Note that setting this attribute,
 * when permitted, changes the nodeName attribute, which holds the qualified name, as well as the
 * tagName and name attributes of the Element and Attr interfaces, when applicable.
 * <p>
 * Create a new element node with a namespace prefix.  Add it to a new DocumentFragment Node without
 * a prefix.  Call setPrefix on the elemen node.  Check if the prefix was set correctly on the element.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class nodesetprefix01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        Element element;
        String elementTagName;
        String elementNodeName;
        doc = sampleXmlFile("staff.xml");
        docFragment = doc.createDocumentFragment();
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "address");
        docFragment.appendChild(element);
        element.setPrefix("dmstc");
        elementTagName = element.getTagName();
        elementNodeName = element.getNodeName();
        assertEquals("nodesetprefix01_tagname", "dmstc:address", elementTagName);
        assertEquals("nodesetprefix01_nodeName", "dmstc:address", elementNodeName);
    }
}

