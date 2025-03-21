
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * The method getNamespaceURI returns the namespace URI of this node, or null if it is unspecified
 * For nodes of any type other than ELEMENT_NODE and ATTRIBUTE_NODE and nodes created with
 * a DOM Level 1 method, such as createElement from the Document interface, this is always null.
 * <p>
 * Ceate two new element nodes and atribute nodes, with and without namespace prefixes.
 * Retreive their namespaceURI's using getNamespaceURI and verrify if it is correct.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class Nodegetnamespaceuri03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element element;
        final Element elementNS;
        final Attr attr;
        final Attr attrNS;
        final String elemNSURI;
        final String elemNSURINull;
        final String attrNSURI;
        final String attrNSURINull;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS(null, "elem");
        elementNS = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "qual:qelem");
        attr = doc.createAttributeNS(null, "attr");
        attrNS = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "qual:qattr");
        elemNSURI = elementNS.getNamespaceURI();
        elemNSURINull = element.getNamespaceURI();
        attrNSURI = attrNS.getNamespaceURI();
        attrNSURINull = attr.getNamespaceURI();
        assertEquals("http://www.w3.org/DOM/Test/elem", elemNSURI);
        assertNull(elemNSURINull);
        assertEquals("http://www.w3.org/DOM/Test/attr", attrNSURI);
        assertNull(attrNSURINull);
    }
}

