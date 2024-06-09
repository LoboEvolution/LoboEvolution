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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Check implementation of Node.getFeature on non-namespace attribute.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature</a>
 */
public class Nodegetfeature06Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Node node;
        Node featureImpl;
        boolean isSupported;
        final DOMImplementation domImpl;
        doc = sampleXmlFile("barfoo.xml");
        doc.getImplementation();
        node = doc.createAttribute("title");
        featureImpl = node.getFeature("Core", null);
        assertSame(node, featureImpl, "Nodegetfeature06Assert1");
        featureImpl = node.getFeature("cOrE", null);
        assertSame(node, featureImpl, "Nodegetfeature06Assert2");
        featureImpl = node.getFeature("+cOrE", null);
        assertSame(node, featureImpl, "Nodegetfeature06Assert3");
        featureImpl = node.getFeature("bogus.feature", null);
        assertNull(featureImpl, "Nodegetfeature06Assert4");
        featureImpl = node.getFeature("cOrE", "2.0");
        assertSame(node, featureImpl, "Nodegetfeature06Assert5");
        featureImpl = node.getFeature("cOrE", "3.0");
        assertSame(node, featureImpl, "Nodegetfeature06Assert6");
        isSupported = node.isSupported("XML", null);
        featureImpl = node.getFeature("XML", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert7");
        }
        isSupported = node.isSupported("SVG", null);
        featureImpl = node.getFeature("SVG", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert8");
        }
        isSupported = node.isSupported("HTML", null);
        featureImpl = node.getFeature("HTML", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert9");
        }
        isSupported = node.isSupported("Events", null);
        featureImpl = node.getFeature("Events", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert10");
        }
        isSupported = node.isSupported("LS", null);
        featureImpl = node.getFeature("LS", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert11");
        }
        isSupported = node.isSupported("LS-Async", null);
        featureImpl = node.getFeature("LS-Async", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert12");
        }
        isSupported = node.isSupported("XPath", null);
        featureImpl = node.getFeature("XPath", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature06Assert13");
        }
        isSupported = node.isSupported("+HTML", null);
        featureImpl = node.getFeature("HTML", null);

        if (isSupported) {
            assertNotNull(featureImpl, "Nodegetfeature06Assert14");
        }
        isSupported = node.isSupported("+SVG", null);
        featureImpl = node.getFeature("SVG", null);

        if (isSupported) {
            assertNotNull(featureImpl, "Nodegetfeature06Assert15");
        }
    }
}

