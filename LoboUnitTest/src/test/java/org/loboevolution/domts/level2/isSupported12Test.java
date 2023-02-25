
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "feature" parameter in the
 * isSupported(feature,version)" method is the name
 * of the feature and the version is the version number of the
 * feature to test.   CORE is a legal value for the feature parameter
 * (Test for CORE, upper case).
 * Legal values for the version parameter are 1.0 and 2.0
 * (Test for 1.0).
 * <p>
 * Retrieve the root node of the DOM document by invoking
 * the "getDocumentElement()" method.   This should create a
 * node object on which the "isSupported(feature,version)"
 * method is invoked with "feature" equal to "CORE" and the version equal to 1.0.
 * The method should return a boolean "true".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports</a>
 */
public class isSupported12Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        java.util.List features = new java.util.ArrayList();
        features.add("Core");
        features.add("XML");
        features.add("HTML");
        features.add("Views");
        features.add("StyleSheets");
        features.add("CSS");
        features.add("CSS2");
        features.add("Events");
        features.add("UIEvents");
        features.add("MouseEvents");
        features.add("MutationEvents");
        features.add("HTMLEvents");
        features.add("Range");
        features.add("Traversal");
        features.add("bogus.bogus.bogus");

        Document doc;
        Node rootNode;
        String featureElement;
        boolean state;
        doc = sampleXmlFile("staff.xml");
        rootNode = doc.getDocumentElement();
        state = rootNode.isSupported("Core", "2.0");
        assertTrue("Core2", state);
        for (int indexN10078 = 0; indexN10078 < features.size(); indexN10078++) {
            featureElement = (String) features.get(indexN10078);
            state = rootNode.isSupported(featureElement, "1.0");
        }
        for (int indexN10083 = 0; indexN10083 < features.size(); indexN10083++) {
            featureElement = (String) features.get(indexN10083);
            state = rootNode.isSupported(featureElement, "2.0");
        }
    }
}

