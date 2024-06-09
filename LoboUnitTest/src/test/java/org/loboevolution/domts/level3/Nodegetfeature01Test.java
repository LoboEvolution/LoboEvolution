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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Check implementation of Node.getFeature on Document.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature</a>
 */
public class Nodegetfeature01Test extends LoboUnitTest {

    @Test
    public void runTest() {
        final Document doc;
        final Node node;
        Node featureImpl;
        boolean isSupported;
        doc = sampleXmlFile("barfoo.xml");
        node = doc;
        featureImpl = node.getFeature("Core", null);
        assertSame(node, featureImpl, "Nodegetfeature01Assert1");
        featureImpl = node.getFeature("cOrE", null);
        assertSame(node, featureImpl, "Nodegetfeature01Assert2");
        featureImpl = node.getFeature("+cOrE", null);
        assertSame(node, featureImpl, "Nodegetfeature01Assert3");
        featureImpl = node.getFeature("bogus.feature", null);
        assertNull(featureImpl, "Nodegetfeature01Assert4");
        featureImpl = node.getFeature("cOrE", "2.0");
        assertSame(node, featureImpl, "Nodegetfeature01Assert5");
        featureImpl = node.getFeature("cOrE", "3.0");
        assertSame(node, featureImpl, "Nodegetfeature01Assert6");
        isSupported = node.isSupported("XML", null);
        featureImpl = doc.getFeature("XML", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert7");
        }
        isSupported = node.isSupported("SVG", null);
        featureImpl = doc.getFeature("SVG", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert8");
        }
        isSupported = node.isSupported("HTML", null);
        featureImpl = doc.getFeature("HTML", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert9");
        }
        isSupported = node.isSupported("Events", null);
        featureImpl = doc.getFeature("Events", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert10");
        }
        isSupported = node.isSupported("LS", null);
        featureImpl = doc.getFeature("LS", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert11");
        }
        isSupported = node.isSupported("LS-Async", null);
        featureImpl = doc.getFeature("LS-Async", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert12");
        }
        isSupported = node.isSupported("XPath", null);
        featureImpl = doc.getFeature("XPath", null);

        if (isSupported) {
            assertSame(node, featureImpl, "Nodegetfeature01Assert13");
        }
        isSupported = node.isSupported("+HTML", null);
        featureImpl = doc.getFeature("HTML", null);

        if (isSupported) {
            assertNotNull(featureImpl, "Nodegetfeature01Assert14");
        }
        isSupported = node.isSupported("+SVG", null);
        featureImpl = doc.getFeature("SVG", null);

        if (isSupported) {
            assertNotNull(featureImpl, "Nodegetfeature01Assert15");
        }
    }
}

