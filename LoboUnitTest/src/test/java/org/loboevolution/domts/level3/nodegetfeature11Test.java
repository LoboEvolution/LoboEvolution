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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * Check implementation of Node.getFeature on CDATASection.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature</a>
 */
public class nodegetfeature11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node node;
        String nullVersion = null;

        Node featureImpl;
        boolean isSupported;
        DOMImplementation domImpl;
        doc = sampleXmlFile("barfoo.xml");
        domImpl = doc.getImplementation();
        node = doc.createCDATASection("some text");
        featureImpl = node.getFeature("Core", nullVersion);
        assertSame("coreUnspecifiedVersion", node, featureImpl);
        featureImpl = node.getFeature("cOrE", nullVersion);
        assertSame("cOrEUnspecifiedVersion", node, featureImpl);
        featureImpl = node.getFeature("+cOrE", nullVersion);
        assertSame("PlusCoreUnspecifiedVersion", node, featureImpl);
        featureImpl = node.getFeature("bogus.feature", nullVersion);
        assertNull("unrecognizedFeature", featureImpl);
        featureImpl = node.getFeature("cOrE", "2.0");
        assertSame("Core20", node, featureImpl);
        featureImpl = node.getFeature("cOrE", "3.0");
        assertSame("Core30", node, featureImpl);
        isSupported = node.isSupported("XML", nullVersion);
        featureImpl = node.getFeature("XML", nullVersion);

        if (isSupported) {
            assertSame("XMLUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("SVG", nullVersion);
        featureImpl = node.getFeature("SVG", nullVersion);

        if (isSupported) {
            assertSame("SVGUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("HTML", nullVersion);
        featureImpl = node.getFeature("HTML", nullVersion);

        if (isSupported) {
            assertSame("HTMLUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("Events", nullVersion);
        featureImpl = node.getFeature("Events", nullVersion);

        if (isSupported) {
            assertSame("EventsUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("LS", nullVersion);
        featureImpl = node.getFeature("LS", nullVersion);

        if (isSupported) {
            assertSame("LSUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("LS-Async", nullVersion);
        featureImpl = node.getFeature("LS-Async", nullVersion);

        if (isSupported) {
            assertSame("LSAsyncUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("XPath", nullVersion);
        featureImpl = node.getFeature("XPath", nullVersion);

        if (isSupported) {
            assertSame("XPathUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("+HTML", nullVersion);
        featureImpl = node.getFeature("HTML", nullVersion);

        if (isSupported) {
            assertNotNull("PlusHTMLUnspecified", featureImpl);
        }
        isSupported = node.isSupported("+SVG", nullVersion);
        featureImpl = node.getFeature("SVG", nullVersion);

        if (isSupported) {
            assertNotNull("PlusSVGUnspecified", featureImpl);
        }
    }
}

