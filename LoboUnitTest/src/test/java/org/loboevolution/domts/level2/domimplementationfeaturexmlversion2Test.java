
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
import org.loboevolution.html.node.DOMImplementation;

import static org.junit.Assert.assertTrue;


/**
 * The "feature" parameter in the
 * "hasFeature(feature,version)" method is the package name
 * of the feature.  Legal values are XML and HTML.
 * (Test for feature "xml" and version "2.0")
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "getImplementation()" method.  This should create a
 * DOMImplementation object whose "hasFeature(feature,
 * version)" method is invoked with "feature" equal to "xml".
 * The method should return a boolean "true".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7</a>
 */
public class domimplementationfeaturexmlversion2Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        boolean state;
        doc = sampleXmlFile("staff.xml");
        
        domImpl = doc.getImplementation();
        state = domImpl.hasFeature("xml", "2.0");
        assertTrue("domimplementationFeaturexmlVersion2Assert", state);
    }
}

