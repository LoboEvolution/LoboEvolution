
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
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "getName()" method contains the name of the DTD.
 * <p>
 * Retrieve the Document Type for this document and examine
 * the string returned by the "getName()" method.
 * It should be set to "staff".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A31">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A31</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1844763134">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1844763134</a>
 */
public class documenttypegetdoctypeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        String name;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        name = docType.getName();
        assertEquals("documenttypeGetDocTypeAssert", "staff", name);
    }
}

