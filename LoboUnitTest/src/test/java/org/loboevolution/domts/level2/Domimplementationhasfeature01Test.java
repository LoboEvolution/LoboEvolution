
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
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The method "hasFeature(feature,version)" tests if the DOMImplementation implements
 * a specific feature and if so returns true.
 * <p>
 * Call the hasFeature method on this DOMImplementation with a combination of features
 * versions as below.  Valid feature names are case insensitive and versions "2.0",
 * "1.0" and if the version is not specified, supporting any version of the feature
 * should return true.  Check if the value returned value was true.

 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7</a>
 */
public class Domimplementationhasfeature01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DOMImplementation domImpl;
        final String version = "";
        final String version1 = "1.0";
        final String version2 = "2.0";
        String featureCore;
        String featureXML;
        boolean success;
        final List<String> featuresXML = new ArrayList<>();
        featuresXML.add("XML");
        featuresXML.add("xmL");

        final List<String> featuresCore = new ArrayList<>();
        featuresCore.add("Core");
        featuresCore.add("CORE");

        doc = sampleXmlFile("staffNS.xml");

        domImpl = doc.getImplementation();
        for (final String o : featuresXML) {
            featureXML = o;
            success = domImpl.hasFeature(featureXML, version);
            assertTrue(success);
            success = domImpl.hasFeature(featureXML, version1);
            assertTrue(success);
        }
        for (final String o : featuresCore) {
            featureCore = o;
            success = domImpl.hasFeature(featureCore, version);
            assertTrue(success);
            success = domImpl.hasFeature(featureCore, version2);
            assertTrue(success);
        }
    }
}

