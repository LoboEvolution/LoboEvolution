
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertTrue;


/**
 * The method "isSupported(feature,version)" Tests whether the DOM implementation
 * implements a specific feature and that feature is supported by this node.
 * <p>
 * Call the isSupported method on a new attribute node with a combination of features
 * versions and versions as below.  Valid feature names are case insensitive and versions
 * "2.0", "1.0" and if the version is not specified, supporting any version of the feature
 * should return true.  Check if the value returned value was true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports</a>
 */
public class nodeissupported02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr attribute;
        String version = "";
        String version1 = "1.0";
        String version2 = "2.0";
        String featureCore;
        String featureXML;
        boolean success;
        java.util.List featuresXML = new java.util.ArrayList();
        featuresXML.add("XML");
        featuresXML.add("xmL");

        java.util.List featuresCore = new java.util.ArrayList();
        featuresCore.add("Core");
        featuresCore.add("CORE");

        doc = sampleXmlFile("staffNS.xml");
        attribute = doc.createAttribute("TestAttr");
        for (int indexN10064 = 0; indexN10064 < featuresXML.size(); indexN10064++) {
            featureXML = (String) featuresXML.get(indexN10064);
            success = attribute.isSupported(featureXML, version);
            assertTrue("nodeissupported02_XML1", success);
            success = attribute.isSupported(featureXML, version1);
            assertTrue("nodeissupported02_XML2", success);
        }
        for (int indexN1007D = 0; indexN1007D < featuresCore.size(); indexN1007D++) {
            featureCore = (String) featuresCore.get(indexN1007D);
            success = attribute.isSupported(featureCore, version);
            assertTrue("nodeissupported02_Core1", success);
            success = attribute.isSupported(featureCore, version1);
            success = attribute.isSupported(featureCore, version2);
            assertTrue("nodeissupported02_Core3", success);
        }
    }
}

