
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertTrue;


/**
 * The "getImplementation()" method returns the
 * DOMImplementation object that handles this document.
 * Retrieve the entire DOM document and invoke its
 * "getImplementation()" method.  It should return a
 * DOMImplementation whose "hasFeature("XML","1.0")
 * method returns the boolean value "true".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1B793EBA">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1B793EBA</a>
 */
public class documentgetimplementationTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        DOMImplementation docImpl;
        boolean state;
        doc = (DocumentImpl) sampleXmlFile("staff.xml");
        doc.setTest(true);
        docImpl = doc.getImplementation();
        state = docImpl.hasFeature("XML", "1.0");
        assertTrue("documentGetImplementationAssert", state);
    }
}

