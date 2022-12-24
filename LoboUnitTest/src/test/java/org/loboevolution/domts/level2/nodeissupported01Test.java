
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method "isSupported(feature,version)" Tests whether the DOM implementation
 * implements a specific feature and that feature is supported by this node.
 * <p>
 * Call the isSupported method on the document element node with a combination of features
 * versions and versions as below.  Valid feature names are case insensitive and versions
 * "2.0", "1.0" and if the version is not specified, supporting any version of the feature
 * should return true.  Check if the value returned value was true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports</a>
 */
public class nodeissupported01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
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
        element = doc.getDocumentElement();
        for (int indexN10063 = 0; indexN10063 < featuresXML.size(); indexN10063++) {
            featureXML = (String) featuresXML.get(indexN10063);
            success = element.isSupported(featureXML, version);
            assertTrue("nodeissupported01_XML1", success);
            success = element.isSupported(featureXML, version1);
            assertTrue("nodeissupported01_XML2", success);
        }
        for (int indexN1007C = 0; indexN1007C < featuresCore.size(); indexN1007C++) {
            featureCore = (String) featuresCore.get(indexN1007C);
            success = element.isSupported(featureCore, version);
            assertTrue("nodeissupported01_Core1", success);
            success = element.isSupported(featureCore, version1);
            success = element.isSupported(featureCore, version2);
            assertTrue("nodeissupported01_Core3", success);
        }
    }
}

