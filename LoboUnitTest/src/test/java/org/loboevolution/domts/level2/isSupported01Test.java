
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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertFalse;


/**
 * The "feature" parameter in the
 * isSupported(feature,version)" method is the name
 * of the feature and the version is the version number of the
 * feature to test.   XXX is NOT a legal value for the feature parameter.
 * The method should return "false" since XXX is not a valid feature.
 * <p>
 * Retrieve the root node of the DOM document by invoking
 * the "getDocumentElement()" method.   This should create a
 * node object on which the "isSupported(feature,version)"
 * method is invoked with "feature" equal to "XXX" and version to "1.0".
 * The method should return a boolean "false" since XXX is not a valid feature.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports</a>
 */
public class isSupported01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Node rootNode;
        boolean state;
        doc = sampleXmlFile("staff.xml");
        rootNode = doc.getDocumentElement();
        state = rootNode.isSupported("XXX", "1.0");
        assertFalse("throw_False", state);
    }
}

