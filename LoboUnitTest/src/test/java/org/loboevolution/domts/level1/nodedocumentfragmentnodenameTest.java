
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;

import static org.junit.Assert.*;


/**
 * The string returned by the "getNodeName()" method for a
 * DocumentFragment Node is "#document-frament".
 * Retrieve the DOM document and invoke the
 * "createDocumentFragment()" method and check the string
 * returned by the "getNodeName()" method.   It should be
 * equal to "#document-fragment".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A3">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A3</a>
 */
public class nodedocumentfragmentnodenameTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        String documentFragmentName;
        doc = sampleXmlFile("staff.xml");
        docFragment = doc.createDocumentFragment();
        documentFragmentName = docFragment.getNodeName();
        assertEquals("nodeDocumentFragmentNodeNameAssert1", "[object DocumentFragment]", documentFragmentName);
    }
}

