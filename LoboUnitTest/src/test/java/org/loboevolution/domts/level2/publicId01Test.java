
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
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertEquals;


/**
 * The "getPublicId()" method of a documenttype node contains
 * the public identifier associated with the external subset.
 * <p>
 * Retrieve the documenttype.
 * Apply the "getPublicId()" method.  The string "STAFF" should be
 * returned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-publicId">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-publicId</a>
 */
public class publicId01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        String publicId;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();
        publicId = docType.getPublicId();
        assertEquals("throw_Equals", "STAFF", publicId);
    }
}

