/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * Check implementation of Node.getFeature on Document.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-getFeature</a>
 */
public class nodegetfeature01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node node;
        String nullVersion = null;

        Node featureImpl;
        boolean isSupported;
        doc = sampleXmlFile("barfoo.xml");
        node = doc;
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
        featureImpl = doc.getFeature("XML", nullVersion);

        if (isSupported) {
            assertSame("XMLUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("SVG", nullVersion);
        featureImpl = doc.getFeature("SVG", nullVersion);

        if (isSupported) {
            assertSame("SVGUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("HTML", nullVersion);
        featureImpl = doc.getFeature("HTML", nullVersion);

        if (isSupported) {
            assertSame("HTMLUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("Events", nullVersion);
        featureImpl = doc.getFeature("Events", nullVersion);

        if (isSupported) {
            assertSame("EventsUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("LS", nullVersion);
        featureImpl = doc.getFeature("LS", nullVersion);

        if (isSupported) {
            assertSame("LSUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("LS-Async", nullVersion);
        featureImpl = doc.getFeature("LS-Async", nullVersion);

        if (isSupported) {
            assertSame("LSAsyncUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("XPath", nullVersion);
        featureImpl = doc.getFeature("XPath", nullVersion);

        if (isSupported) {
            assertSame("XPathUnspecified", node, featureImpl);
        }
        isSupported = node.isSupported("+HTML", nullVersion);
        featureImpl = doc.getFeature("HTML", nullVersion);

        if (isSupported) {
            assertNotNull("PlusHTMLUnspecified", featureImpl);
        }
        isSupported = node.isSupported("+SVG", nullVersion);
        featureImpl = doc.getFeature("SVG", nullVersion);

        if (isSupported) {
            assertNotNull("PlusSVGUnspecified", featureImpl);
        }
    }
}

