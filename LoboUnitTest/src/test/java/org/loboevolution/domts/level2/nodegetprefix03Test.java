
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

package org.loboevolution.domts.level2;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The method getPrefix returns the namespace prefix of this node, or null if it is unspecified.
 * <p>
 * Ceate two new element nodes and atribute nodes, with and without namespace prefixes.
 * Retreive the prefix part of their qualified names using getPrefix and verify
 * if it is correct.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class nodegetprefix03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final Element element;
        final Element qelement;
        final Attr attr;
        final Attr qattr;
        final String elemNoPrefix;
        final String elemPrefix;
        final String attrNoPrefix;
        final String attrPrefix;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "elem");
        qelement = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "qual:qelem");
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "attr");
        qattr = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "qual:qattr");
        elemNoPrefix = element.getPrefix();
        elemPrefix = qelement.getPrefix();
        attrNoPrefix = attr.getPrefix();
        attrPrefix = qattr.getPrefix();
        assertNull( elemNoPrefix);
        assertEquals( "qual", elemPrefix);
        assertNull( attrNoPrefix);
        assertEquals( "qual", attrPrefix);
    }
}

